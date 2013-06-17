/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin.functions.core;

import org.mathpiper.lisp.Utility;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;

import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

/**
 *
 *  
 */
public class XmlExplodeTag extends BuiltinFunction {

    private XmlExplodeTag() {
    }

    public XmlExplodeTag(Environment aEnvironment, String functionName) {
        try {

            this.functionName = functionName;
            
            Utility.lispEvaluate(aEnvironment, -1, "RulebaseHoldArguments(\"XmlTag\",[_x,_y,_z]);");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        Cons out = getArgument(aEnvironment, aStackTop, 1);
        LispError.checkIsString(aEnvironment, aStackTop, out, 1);

        String str = (String) out.car();
        int strInd = 0;
        strInd++;
        if (str.charAt(strInd) != '<') {
            setTopOfStack(aEnvironment, aStackTop, out);
            return;
        }
        if( str.charAt(strInd) != '<') LispError.checkArgument(aEnvironment, aStackTop, 1);
        strInd++;
        String type = "\"Open\"";

        if (str.charAt(strInd) == '/') {
            type = "\"Close\"";
            strInd++;
        }
        String tag = new String();

        tag = tag + "\"";
        while (MathPiperTokenizer.isAlpha(str.charAt(strInd))) {
            char c = str.charAt(strInd);
            strInd++;
            if (c >= 'a' && c <= 'z') {
                c = (char) (c + ('A' - 'a'));
            }
            tag = tag + c;
        }
        tag = tag + "\"";

        Cons info = null;

        while (str.charAt(strInd) == ' ') {
            strInd++;
        }
        while (str.charAt(strInd) != '>' && str.charAt(strInd) != '/') {
            String name = new String();
            name = name + "\"";

            while (MathPiperTokenizer.isAlpha(str.charAt(strInd))) {
                char c = str.charAt(strInd);
                strInd++;
                if (c >= 'a' && c <= 'z') {
                    c = (char) (c + ('A' - 'a'));
                }
                name = name + c;
            }
            name = name + "\"";
            if(str.charAt(strInd) != '=') LispError.checkArgument(aEnvironment, aStackTop, 1);
            strInd++;
            if( str.charAt(strInd) != '\"') LispError.checkArgument(aEnvironment, aStackTop, 1);
            String value = new String();

            value = value + (str.charAt(strInd));
            strInd++;
            while (str.charAt(strInd) != '\"') {
                value = value + (str.charAt(strInd));
                strInd++;
            }
            value = value + (str.charAt(strInd));
            strInd++;

            //printf("[%s], [%s]\n",name.String(),value.String());
            {
                Cons ls = AtomCons.getInstance(aEnvironment, aStackTop, "List");
                Cons nm = AtomCons.getInstance(aEnvironment, aStackTop, name);
                Cons vl = AtomCons.getInstance(aEnvironment, aStackTop, value);
                nm.setCdr(vl);
                ls.setCdr(nm);
                Cons newinfo = SublistCons.getInstance(aEnvironment, ls);
                newinfo.setCdr(info);
                info = newinfo;
            }
            while (str.charAt(strInd) == ' ') {
                strInd++;

                //printf("End is %c\n",str[0]);
            }
        }
        if (str.charAt(strInd) == '/') {
            type = "\"OpenClose\"";
            strInd++;
            while (str.charAt(strInd) == ' ') {
                strInd++;
            }
        }
        {
            Cons ls = AtomCons.getInstance(aEnvironment, aStackTop, "List");
            ls.setCdr(info);
            info = SublistCons.getInstance(aEnvironment, ls);
        }

        Cons xm = AtomCons.getInstance(aEnvironment, aStackTop, "XmlTag");
        Cons tg = AtomCons.getInstance(aEnvironment, aStackTop, tag);
        Cons tp = AtomCons.getInstance(aEnvironment, aStackTop, type);
        info.setCdr(tp);
        tg.setCdr(info);
        xm.setCdr(tg);
        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, xm));

    }
}



/*
%mathpiper_docs,name="XmlExplodeTag",categories="Programming Functions;Input/Output;Built In"
*CMD XmlExplodeTag --- convert XML strings to tag objects
*CORE
*CALL
	XmlExplodeTag(xmltext)
*PARMS

{xmltext} -- string containing some XML tokens

*DESC

{XmlExplodeTag} parses the car XML token in {xmltext}
and returns a MathPiper expression.

The following subset of XML syntax is supported currently:

*	{<TAG [options]>} -- an opening tag
*	{</TAG [options]>} -- a closing tag
*	{<TAG [options] />} -- an open/close tag
*	plain (non-tag) text

The tag options take the form {paramname="value"}.

If given an XML tag, {XmlExplodeTag} returns a structure of the form {XmlTag(name,params,type)}.
In the returned object,
{name} is the (capitalized) tag name, {params} is an assoc list with
the options (key fields capitalized), and type can be either "Open", "Close" or "OpenClose".

If given a plain text string, the same string is returned.


*E.G.
In> XmlExplodeTag("some plain text")
Result: "some plain text";

In> XmlExplodeTag("<a name=\"blah blah\" align=\"left\">")
Result: XmlTag("A",[["ALIGN","left"], ["NAME","blah blah"]],"Open");

In> XmlExplodeTag("</p>")
Result: XmlTag("P",[],"Close");

In> XmlExplodeTag("<br/>")
Result: XmlTag("BR",[],"OpenClose");

*SEE XmlTokenizer
%/mathpiper_docs





%mathpiper,name="XmlExplodeTag",subtype="automatic_test"

// Reported by Serge: xml tokenizer not general enough
Verify(XmlExplodeTag("<p/>"),   XmlTag("P",[],"OpenClose"));
Verify(XmlExplodeTag("<p / >"), XmlTag("P",[],"OpenClose"));

%/mathpiper
*/
