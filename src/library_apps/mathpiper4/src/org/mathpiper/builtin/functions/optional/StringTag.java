// Copyright 2003-2008.  Mark Watson (markw@markwatson.com).  All rights reserved.
// This software is released under the LGPL (www.fsf.org)
// For an alternative non-GPL license: contact the author
// THIS SOFTWARE COMES WITH NO WARRANTY
package org.mathpiper.builtin.functions.optional;

import java.io.*;
import java.util.*;
import org.mathpiper.builtin.BuiltinFunction;
import static org.mathpiper.builtin.BuiltinFunction.getArgument;
import static org.mathpiper.builtin.BuiltinFunction.setTopOfStack;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.functions.optional.speech.Tokenizer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 * <p/>
 * Copyright 2002-2007 by Mark Watson. All rights reserved.
 * <p/>
 */
public class StringTag extends BuiltinFunction {

    private Map<String, String[]> lexicon;

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "StringTag";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        lexicon = buildLexicon();
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        if (getArgument(aEnvironment, aStackTop, 1) == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }

        Object argument = getArgument(aEnvironment, aStackTop, 1).car();

        if (!(argument instanceof String)) {
            LispError.raiseError("The argument to TellUser must be a string.", aStackTop, aEnvironment);
        }

        String messageString = (String) argument;

        if (messageString == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }

        messageString = Utility.stripEndQuotesIfPresent(messageString);

        List<String> words = Tokenizer.wordsToList(messageString);
        List<String> tags = tag(words);
        
        ArrayList<String> wordsAndTagsList = new ArrayList<String>();

        for (int i = 0; i < words.size(); i++) {
            wordsAndTagsList.add("\"" + words.get(i) + "_" + tags.get(i) + "\"");
        }

        Cons listCons = Utility.iterableToList(aEnvironment, aStackTop, wordsAndTagsList);

        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, listCons));
    }//end method.

    /**
     *
     * @param word
     * @return true if the input word is in the lexicon, otherwise return false
     */
    private boolean wordInLexicon(String word) {
        String[] ss = lexicon.get(word);
        if (ss != null) {
            return true;
        }
        // 1/22/2002 mod (from Lisp code): if not in hash, try lower case:
        if (ss == null) {
            ss = lexicon.get(word.toLowerCase());
        }
        if (ss != null) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param words list of strings to tag with parts of speech
     * @return list of strings for part of speech tokens
     */
    private List<String> tag(List<String> words) {
        List<String> ret = new ArrayList<String>(words.size());
        for (int i = 0, size = words.size(); i < size; i++) {
            String[] ss = (String[]) lexicon.get(words.get(i));
            // 1/22/2002 mod (from Lisp code): if not in hash, try lower case:
            if (ss == null) {
                ss = lexicon.get(words.get(i).toLowerCase());
            }
            if (ss == null && words.get(i).length() == 1) {
                ret.add(words.get(i) + "^");
            } else if (ss == null) {
                ret.add("NN");
            } else {
                ret.add(ss[0]);
            }
        }
        /**
         * Apply transformational rules
		 *
         */
        for (int i = 0; i < words.size(); i++) {
            String word = ret.get(i);
            // rule 1: DT, {VBD | VBP} --> DT, NN
            if (i > 0 && ret.get(i - 1).equals("DT")) {
                if (word.equals("VBD") || word.equals("VBP") || word.equals("VB")) {
                    ret.set(i, "NN");
                }
            }
            // rule 2: convert a noun to a number (CD) if "." appears in the word
            if (word.startsWith("N")) {
                if (words.get(i).indexOf(".") > -1) {
                    ret.set(i, "CD");
                }
                try {
                    Float.parseFloat(words.get(i));
                    ret.set(i, "CD");
                } catch (Exception e) { // ignore: exception OK: this just means
                    // that the string could not parse as a
                    // number
                }
            }
            // rule 3: convert a noun to a past participle if words.get(i) ends with "ed"
            if (ret.get(i).startsWith("N") && words.get(i).endsWith("ed")) {
                ret.set(i, "VBN");
            }
            // rule 4: convert any type to adverb if it ends in "ly";
            if (words.get(i).endsWith("ly")) {
                ret.set(i, "RB");
            }
            // rule 5: convert a common noun (NN or NNS) to a adjective if it ends with "al"
            if (ret.get(i).startsWith("NN") && words.get(i).endsWith("al")) {
                ret.set(i, "JJ");
            }
            // rule 6: convert a noun to a verb if the preceeding work is "would"
            if (i > 0 && ret.get(i).startsWith("NN")
                    && words.get(i - 1).equalsIgnoreCase("would")) {
                ret.set(i, "VB");
            }
			// rule 7: if a word has been categorized as a common noun and it ends with "s",
            // then set its type to plural common noun (NNS)
            if (ret.get(i).equals("NN") && words.get(i).endsWith("s")) {
                ret.set(i, "NNS");
            }
            // rule 8: convert a common noun to a present participle verb (i.e., a gerand)
            if (ret.get(i).startsWith("NN") && words.get(i).endsWith("ing")) {
                ret.set(i, "VBG");
            }
        }
        return ret;
    }

    private static Map<String, String[]> buildLexicon() throws Throwable {
        Map<String, String[]> lexicon = new HashMap<String, String[]>();

        InputStream ins = StringTag.class.getClassLoader().getResourceAsStream("org/mathpiper/builtin/functions/optional/speech/english_lexicon.txt");
        if (ins == null) {
            throw new Exception("The file english_lexicon.txt was not found on the classpath.");
        }
        Scanner scanner = new Scanner(ins);
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            int count = 0;
            for (int i = 0, size = line.length(); i < size; i++) {
                if (line.charAt(i) == ' ') {
                    count++;
                }
            }
            if (count == 0) {
                continue;
            }
            String[] ss = new String[count];
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(" ");
            String word = lineScanner.next();
            count = 0;
            while (lineScanner.hasNext()) {
                ss[count++] = lineScanner.next();
            }
            lineScanner.close();
            lexicon.put(word, ss);
        }
        scanner.close();

        return Collections.unmodifiableMap(lexicon);
    }
    
/*
    
%mathpiper_docs,name="StringTag",categories="Programming Functions;Strings",access="experimental"
*CMD StringTag --- Add parts of speech tags to the words in English sentences. 
*CALL
	StringTag(string)

*PARMS
{string} -- a string that contains English sentences


*DESC
This function accepts a string that contains English sentences, and it returns a list of strings that
contain words tagged with the following parts of speech tags:

|table
|"|quote|"
|#|Pound sign|#
|$|Dollar sign|$
|(|Left paren|(
|)|Right paren|)
|,|Comma|,
|.|Sent-final punct|. ! ?
|:|Mid-sent punctuation|: ;
|CC|Coordinating conjunction|and,but,or
|CD|Cardinal number|one,two
|DT|Determiner|the,some
|EX|Existential|there,there
|FW|Foreign word|mon,dieu
|IN|Preposition or subordinating conjunction|of,in,by
|JJ|Adjective|big	
|JJR|Adjective, comparative|bigger
|JJS|Adjective, superlative|biggest
|LS|List item marke|1,One
|MD|Modal|can,should
|NN|Noun, singular or mass|dog
|NNP|Proper noun, singular|Edinburgh
|NNPS|Proper noun, plural|Smiths
|NNS|Noun, plural|dogs
|PDT|Predeterminer|all,both
|POS|Possessive ending|'s
|PP|Personal pronoun|I,you,she
|PP$|Possessive pronoun|my,one's
|PRP|Personal|pronoun
|PRP$|Possessive|pronoun
|RB|Adverb|quickly
|RBR|Adverb, comparative|faster
|RBS|Adverb, superlative|fastest
|RP|Particle|up,off
|SYM|Symbol|+,%,&
|TO|'to'|to
|UH|Interjection|oh,oops
|VB|Verb, base form|eat
|VBD|Verb, past tense|ate
|VBG|Verb, gerund or present participle|eating	
|VBN|Verb, past participle|eaten
|VBP|Verb, non-3rd person singular present|eat
|VBZ|Verb, 3rd person singular present|eats
|WDT|Wh-determiner|which,that
|WP|Wh-pronoun|who,what	
|WP$|Possessive wh-pronoun|whose
|WRB|Wh-adverb|how,where
|/table

*E.G.
In> StringTag("The rain in Spain falls mainly on the plain.")

%/mathpiper_docs
*/

}
