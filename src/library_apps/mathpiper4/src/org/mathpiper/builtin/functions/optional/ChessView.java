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
package org.mathpiper.builtin.functions.optional;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import org.mathpiper.builtin.BuiltinFunction;
import static org.mathpiper.builtin.BuiltinFunction.getArgument;
import static org.mathpiper.builtin.BuiltinFunction.setTopOfStack;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.lisp.parsers.LispParser;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.ui.gui.applications.chess.ChessBoard;

/**
 *
 *
 */
public class ChessView extends BuiltinFunction {

    private Map defaultOptions;
    
    public void plugIn(Environment aEnvironment)  throws Throwable
    {
	this.functionName = "ChessView";
	
        aEnvironment.getBuiltinFunctions().put(
                "ChessView", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));

        defaultOptions = new HashMap();

        defaultOptions.put("Scale", 2.5);
        defaultOptions.put("Resizable", true);
        defaultOptions.put("IncludeExpression", true);
        defaultOptions.put("Lisp", false);
        defaultOptions.put("Code", true);
        defaultOptions.put("Debug", false);




    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
	

        Cons arguments = getArgument(aEnvironment, aStackTop, 1);
        
        Cons options = (Cons) Cons.cddar(arguments);

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        
        

        Object argument = ((Cons)getArgument(aEnvironment, aStackTop, 1).car()).cdr().car();
        
        String texString = "";
        
        Cons expression = null;

        if ((argument instanceof String)) {
           
            
            String expressionString = (String) argument;

            expressionString = Utility.stripEndQuotesIfPresent(expressionString);
            
            Parser parser;
            
            if(((Boolean)userOptions.get("Lisp")) == true)
            {
        	texString = expressionString;
        	texString = texString.replace(" ", "\\ ");
            
                StringInputStream newInput = new StringInputStream(expressionString , aEnvironment.iInputStatus);

        	parser = new LispParser(aEnvironment.iCurrentTokenizer, newInput, aEnvironment);
            }
            else
            {
        	texString = expressionString;
        	
        	if (!expressionString.endsWith(";")) {
                    expressionString = expressionString + ";";
                }
                expressionString = expressionString.replaceAll(";;;", ";");
                expressionString = expressionString.replaceAll(";;", ";");
            
                StringInputStream newInput = new StringInputStream(expressionString , aEnvironment.iInputStatus);

        	parser = new MathPiperParser(new MathPiperTokenizer(), newInput, aEnvironment, aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
            }
            
            expression = parser.parse(aStackTop);
           
        }
        else
        {

            if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");
    
            arguments = (Cons) arguments.car(); //Go to sub list.
    
            arguments = arguments.cdr(); //Strip List tag.
            
            expression = (Cons) arguments;
            //expression = (Cons) argument;
            
        }
        
        
       final ChessBoard chessBoard = new ChessBoard();
        
       Runnable r = new Runnable() {

            @Override
            public void run() {

                JFrame f = new JFrame("TheoremChess");
                f.add(chessBoard.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);


        JavaObject response = new JavaObject(chessBoard);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.
    

}//end class.





/*
%mathpiper_docs,name="TreeView",categories="Mathematics Functions;Visualization"
*CMD TreeView --- display an expression tree

*CALL
    TreeView(expression, option, option, option...)

*PARMS
{expression} -- an expression (which may be in string form) to display as an expression tree

{Options:}

{Scale} -- a value that sets the initial size of the tree

{Resizable} -- if set to True, a resizing slider is displayed

{IncludeExpression} -- if set to True, the algebraic form of the expression is included above the tree

{Lisp} -- if set to True, the expression must be a string that is in Lisp form

{Code} -- if set to True, the expression is rendered using code symbols instead of mathematical symbols


*DESC
Returns a Java GUI component that contains an expression rendered as an
expression tree.

Options are entered using the : operator.
For example, here is how to disable {Resizable} option: {Resizable: False}.

Right click on the images that are displayed to save them.
 
*E.G.

In> Show(TreeView( '(a*(b+c) == a*b + a*c)))
Result: java.awt.Component

In> Show(TreeView( "a*(b+c) == a*b + a*c"))
Result: java.awt.Component

In> Show(TreeView( "(+ 1 (* 2 3))", Lisp: True))
Result: java.awt.Component


*SEE Show
%/mathpiper_docs
*/



