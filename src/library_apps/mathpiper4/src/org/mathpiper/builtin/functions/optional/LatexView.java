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

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;

/**
 *
 *
 */
public class LatexView extends BuiltinFunction {

    private Map defaultOptions;
    
    public void plugIn(Environment aEnvironment)  throws Exception
    {
	this.functionName = "LatexView";
	
        aEnvironment.getBuiltinFunctions().setAssociation(
                "LatexView", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

        defaultOptions = new HashMap();
        defaultOptions.put("scale", 100.0);
        defaultOptions.put("slider", false);


        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
	DefaultTeXFont.registerAlphabet(new GreekRegistration());


    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
	
        Cons arguments = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        arguments = (Cons) arguments.car(); //Go to sub list.

        arguments = arguments.cdr(); //Strip List tag.

        //if(! Utility.isList(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.NOT_A_LIST, "");

        Object latexStringObject = arguments.car(); //Grab the first member of the list arguments list.
        
        if(! (latexStringObject instanceof String)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        String latexString = (String) latexStringObject;
        
        
        Cons options = arguments.cdr();

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        
   //===============================

        

        latexString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, latexString);

        latexString = Utility.stripEndDollarSigns(latexString);

        
        int viewScale = (int) ((Double)userOptions.get("scale")).doubleValue();
        


        /*sHotEqn hotEqn = new sHotEqn();
        hotEqn.setFontsizes(18,18,18,18);
        hotEqn.setEquation(latexString);
        JScrollPane hotEqnScrollPane = new JScrollPane(hotEqn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        */




        //MathPiper built-in math viewer.
        /*TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(latexString);
        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());
        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());
        JScrollPane mathPiperScrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         */


        

        /*
        DebugGraphics.setFlashCount(10);
        DebugGraphics.setFlashColor(Color.red);
        DebugGraphics.setFlashTime(1000);
        RepaintManager.currentManager(panel).setDoubleBufferingEnabled(false);
        panel.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
        panel.setDebugGraphicsOptions(DebugGraphics.LOG_OPTION);
         */

        Box box = Box.createVerticalBox();


        //JLateXMath
	TeXFormula formula = new TeXFormula(latexString);
	
	JLabel latexLabel = new JLabel();
        
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, viewScale);
        
        JPanel screenCapturePanel = new ScreenCapturePanel();
        
        screenCapturePanel.add(latexLabel);
	
	boolean includeSlider = (Boolean) userOptions.get("slider");
	
	if(includeSlider)
	{
            JScrollPane jMathTexScrollPane = new JScrollPane(screenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jMathTexScrollPane.getViewport().setBackground(Color.WHITE);
            box.add(jMathTexScrollPane);
            box.add(latexPanelController);
	}
	else
	{
	    box.add(screenCapturePanel);
	}
 

        JavaObject response = new JavaObject(box);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.


}//end class.





/*
%mathpiper_docs,name="LatexView",categories="User Functions;Visualization"
*CMD ViewLatex --- display rendered Latex code

*CALL
    LatexView(string)

*Params
{string} -- a string which contains Latex code

*DESC
Display rendered Latex code.  Note: backslashes must be escaped
with a backslash.
 
*E.G.
In> LatexView("2\\sum_{i=1}^n a_i")
Result: javax.swing.JFrame



The XXXView functions all return a reference to the Java Component which they are displayed in.
The Show function can be used to display these components.

In> component := ViewLatex("2\\sum_{i=1}^n a_i")
Result: java.awt.Component



*SEE Show
%/mathpiper_docs
*/



