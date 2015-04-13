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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.ResponseListener;
import org.mathpiper.interpreters.SynchronousInterpreter;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.lisp.parsers.LispParser;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.ui.gui.RulesPanel;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.mathpiper.ui.gui.worksheets.TreePanelCons;
import org.scilab.forge.mp.jlatexmath.TeXFormula;

/**
 *
 *
 */
public class TreeView extends BuiltinFunction {
    // Show(TreeView( "a*(b+c) == a*b + a*c",  Resizable: True, IncludeExpression: True))
    // Show(TreeView( "(+ 1 2)", Prefix: True, Code: True, Resizable: True, IncludeExpression: True))
    // MetaSet(Car(Cdr(Car('(a+(b+c) == d)))),"op",True)
    // Show(StepsView(SolveEquation( '( ((- a^2) * b )/ c + d == e ), a), ShowTree: True))

    private Map defaultOptions;
    
    private TreePanelCons treePanel;
    
    private RulesPanel rulesPanel;
    
    private Cons candidateResult;
    
    private String candidateRuleName;
    
    private String candidatePositionString;
    
    private List<String> steps = new ArrayList<String>();
    
    private TeXFormula formula;
    
    private JLabel latexLabel;
    
    private LatexRenderingController latexPanelController;
    
    public void plugIn(Environment aEnvironment)  throws Throwable
    {
	this.functionName = "TreeView";
	
        aEnvironment.getBuiltinFunctions().put(
                "TreeView", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));

        defaultOptions = new HashMap();

        defaultOptions.put("Scale", 2.5);
        defaultOptions.put("Resizable", true);
        defaultOptions.put("IncludeExpression", true);
        defaultOptions.put("Lisp", false);
        defaultOptions.put("Code", true);
        defaultOptions.put("Debug", false);
        defaultOptions.put("WordWrap", 0);
        defaultOptions.put("ShowPositions", false);




    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons arguments = getArgument(aEnvironment, aStackTop, 1);
        
        Cons options = (Cons) Cons.cddar(arguments);

        final Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        
        

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
            expression.setCdr(null);
            //expression = (Cons) argument;
            
        }
        
        Box latexBox = new Box(BoxLayout.Y_AXIS);

        
        Box treeBox = new Box(BoxLayout.Y_AXIS);
        
        JPanel latexScreenCapturePanel = new ScreenCapturePanel();
        if(!((Boolean) userOptions.get("Lisp")))
        {
            if(!((Boolean) userOptions.get("Code")))
            {
                try
                {
                    String latexString = expressionToLatex(aEnvironment, aStackTop, expression);

                    formula = new TeXFormula(latexString);
                    latexLabel = new JLabel();
                    latexPanelController = new LatexRenderingController(formula, latexLabel, 40); // Do not delete.
                    latexScreenCapturePanel.add(latexLabel);
                    
                    JScrollPane latexScrollPane = new JScrollPane(latexScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    latexBox.add(latexScrollPane);
                    latexBox.add(latexPanelController);
                }
                catch(Throwable t)
                {
                    t.printStackTrace();
                }
            }
            else
            {
                texString = Utility.printMathPiperExpression(aStackTop, expression, aEnvironment, -1);
                JLabel codeLabel = new JLabel(texString);
                codeLabel.setFont(new Font("Serif", Font.PLAIN, 25));
                latexScreenCapturePanel.add(codeLabel);
                JScrollPane latexScrollPane = new JScrollPane(latexScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                latexBox.add(latexScrollPane);
            }
        }


        
        double viewScale = ((Double)userOptions.get("Scale")).doubleValue();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        
	panel.setBackground(Color.white);
	//box.setOpaque(true);
        
        
        this.saveStep(aEnvironment, aStackTop, expression, "", "");


        treePanel = new TreePanelCons(aEnvironment, expression, viewScale, userOptions);
        
        
        final Environment environment = aEnvironment;
        final int stackTop = aStackTop;
        
        treePanel.addResponseListener(new ResponseListener() {

            public void response(EvaluationResponse response) {
                String positionString = response.getResult();
                Cons expression = response.getResultList();

                try {
                    TreeView.this.clearMetaInformation(expression);
                    
                    if(positionString != null)
                    {
                        Cons subExpression = expression;
                        for (int index = 0; index < positionString.length(); index++) {
                            int position = Integer.parseInt("" + positionString.charAt(index));
                            subExpression = Utility.nth(environment, stackTop, subExpression, position, false);
                        }

                        highlightTree(environment, stackTop, subExpression, "ForestGreen");
                    }
                    
                    String latexString = expressionToLatex(environment, stackTop, expression);
                    formula.setLaTeX(latexString);
                    TreeView.this.latexPanelController.adjust();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            public boolean remove() {
                return false;
            }
        });
        
        JPanel treeScreenCapturePanel = new ScreenCapturePanel();
        
        treeScreenCapturePanel.add(treePanel);
        
        //JPanel screenCapturePanel = new ScreenCapturePanel();   
        //screenCapturePanel.add(treePanel);
	

	boolean includeSlider = (Boolean) userOptions.get("Resizable");
	boolean includeExpression = (Boolean) userOptions.get("IncludeExpression");
        
        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.NORTH);

        if(userOptions.containsKey("Manipulate") && ((Boolean)userOptions.get("Manipulate")) == true)
        {
            rulesPanel = new RulesPanel(aEnvironment, userOptions);
            //panel.add(new RulesPanel(aEnvironment, userOptions), BorderLayout.EAST);

            JButton applyButton = new JButton("Apply");

            
            applyButton.addActionListener(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if(treePanel.getPositionString() != null)
                    {
                        String positionString = treePanel.getPositionString();
                        String operatorString = treePanel.getOperatorString();

                        if(rulesPanel.getSelectedRowCount() != 0)
                        {
                            try
                            {
                                int selectedRow = rulesPanel.getSelectedRow() + 1;

                                Cons cons = (Cons) userOptions.get("Theorems");

                                Cons cons2 = Utility.nth(environment, -1, cons, selectedRow, true);

                                Cons ruleName = Utility.nth(environment, -1, cons2, 1, true);
                                String ruleNameString = Utility.toNormalString(environment, -1, ruleName.toString());
                                Cons pattern = Utility.nth(environment, -1, cons2, 2, true);
                                Cons patternTex = Utility.nth(environment, -1, cons2, 3, true);
                                String patternTexString = Utility.toNormalString(environment, -1, patternTex.toString());
                                Cons replacement = Utility.nth(environment, -1, cons2, 4, true);
                                Cons replacementTex = Utility.nth(environment, -1, cons2, 5, true);
                                String replacementTexString = Utility.toNormalString(environment, -1, replacementTex.toString());


                                if(userOptions.containsKey("Substitute"))
                                {
                                    Cons cons3 = (Cons) userOptions.get("Substitute");

                                    try
                                    {
                                        Cons from = AtomCons.getInstance(environment, -1, "\"expression\"");
                                        Cons to = TreeView.this.treePanel.getExpression();
                                        org.mathpiper.lisp.astprocessors.ExpressionSubstitute behaviour = new org.mathpiper.lisp.astprocessors.ExpressionSubstitute(environment, from, to);
                                        cons3 = Utility.substitute(environment,-1, cons3, behaviour);
                                        
                                        from = AtomCons.getInstance(environment, -1, "\"pattern\"");
                                        to = pattern;
                                        behaviour = new org.mathpiper.lisp.astprocessors.ExpressionSubstitute(environment, from, to);
                                        cons3 = Utility.substitute(environment,-1, cons3, behaviour);

                                        from = AtomCons.getInstance(environment, -1, "\"replacement\"");
                                        to = replacement;
                                        behaviour = new org.mathpiper.lisp.astprocessors.ExpressionSubstitute(environment, from, to);
                                        cons3 = Utility.substitute(environment,-1, cons3, behaviour);

                                        from = AtomCons.getInstance(environment, -1, "\"position\"");
                                        to = AtomCons.getInstance(environment, -1, "\"" + positionString + "\"");
                                        behaviour = new org.mathpiper.lisp.astprocessors.ExpressionSubstitute(environment, from, to);
                                        cons3 = Utility.substitute(environment,-1, cons3, behaviour);
                                    }
                                    catch(Throwable t)
                                    {
                                        t.printStackTrace();
                                    }

                                    Interpreter interpreter = SynchronousInterpreter.getInstance();

                                    EvaluationResponse response2 = interpreter.evaluate(cons3);


                                    treePanel.relayoutTree(response2.getResultList());
                                    
                                    TreeView.this.candidateResult = response2.getResultList();
                                    
                                    TreeView.this.candidateRuleName = ruleNameString;
                                    
                                    TreeView.this.candidatePositionString = positionString;
                                    
                                    try
                                    {
                                        

                                        //Cons head0 = SublistCons.getInstance(environment, AtomCons.getInstance(environment, stackTop, "RemoveDollarSigns"));
                                        //((Cons) head0.car()).setCdr(candidateResult);
                                        //Cons result0 = environment.iLispExpressionEvaluator.evaluate(environment, stackTop, head0);
                
                                        Cons subExpression = candidateResult;
                                        for(int index = 0; index < positionString.length(); index++)
                                        {
                                            int position = Integer.parseInt("" + positionString.charAt(index));
                                            subExpression = Utility.nth(environment, stackTop, subExpression, position, false);
                                        }
                                        
                                        highlightTree(environment, stackTop, subExpression, "YellowOrange");
                                        String latexString = expressionToLatex(environment, stackTop, candidateResult);
                                        formula.setLaTeX(latexString);
                                        TreeView.this.latexPanelController.adjust();
                                    }
                                    catch(Throwable t)
                                    {
                                        t.printStackTrace();
                                    }
                                    
                                    clearMetaInformation(TreeView.this.candidateResult);
                                    

                                }
                            }
                            catch(Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }
                }
            });

            buttonPanel.add(applyButton);
            
            
            
            JButton acceptButton = new JButton("Accept");
            
            acceptButton.addActionListener(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TreeView.this.treePanel.setExpression(candidateResult);
                    treePanel.relayoutTree(candidateResult);
                    
                    try
                    {
                        String latexString = expressionToLatex(environment, stackTop, candidateResult);
                        formula.setLaTeX(latexString);
                        TreeView.this.latexPanelController.adjust();
                        
                        saveStep(environment, stackTop, candidateResult, candidateRuleName, candidatePositionString);
                    }
                    catch(Throwable t)
                    {
                        t.printStackTrace();
                    }
                    
                    
                }
            });
            
            buttonPanel.add(acceptButton);
            
            
            JButton showStepsButton = new JButton("Show Steps");
            
            showStepsButton.addActionListener(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    final StringBuilder sb = new StringBuilder();

                    for (String step : TreeView.this.steps) {
                        sb.append(step);
                        sb.append("\n");
                    }

                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            
                            JTextArea stepsTextArea = new JTextArea();
                            
                            stepsTextArea.setFont(new Font("Monospaced", 0, 14));
                            
                            stepsTextArea.setText(sb.toString());

                            JFrame f = new JFrame("Steps");
                            f.add(new JScrollPane(stepsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

                            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            f.setLocationByPlatform(true);
                            f.pack();
                            f.setMinimumSize(f.getSize());
                            f.setVisible(true);
                        }
                    };
                    SwingUtilities.invokeLater(r);

                }
            });
            
            buttonPanel.add(showStepsButton);
        
        }
        
        
        JCheckBox showPositionsCheckBox = new JCheckBox("Show Positions");
        
        showPositionsCheckBox.setSelected(false);
        
        showPositionsCheckBox.addItemListener(new ItemListener() {
            
            public void itemStateChanged(ItemEvent ie)
            {
                if(ie.getStateChange() == ItemEvent.SELECTED)
                {
                    TreeView.this.treePanel.showPositions(true);      
                }
                else
                {
                    TreeView.this.treePanel.showPositions(false); 
                }
            }
        
        
        });
	

	if(includeSlider && includeExpression)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    
	    JScrollPane treeScrollPane = new JScrollPane(treeScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    
            treeBox.add(treeScrollPane);
            
            if(rulesPanel == null)
            {
                JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, latexBox, treeBox);
                panel.add(verticalSplitPane, BorderLayout.CENTER);
            }
            else
            {
                JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeBox, rulesPanel);
                horizontalSplitPane.setOneTouchExpandable(true);
                //splitPane.setDividerLocation(150);
                JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, latexBox, horizontalSplitPane);
                panel.add(verticalSplitPane, BorderLayout.CENTER);
            }
            treeBox.add(treePanelScaler);
            treeBox.add(showPositionsCheckBox);
	}
	else if(includeSlider)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    
	    JScrollPane treeScrollPane = new JScrollPane(treeScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    treeBox.add(treeScrollPane);
            if(rulesPanel == null)
            {
                panel.add(treeBox, BorderLayout.CENTER);
            }
            else
            {
                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeBox, rulesPanel);
                splitPane.setOneTouchExpandable(true);
                //splitPane.setDividerLocation(150);
                panel.add(splitPane, BorderLayout.CENTER);
            }
            treeBox.add(treePanelScaler);
            treeBox.add(showPositionsCheckBox);
	}
	else if(includeExpression)
	{
            if(rulesPanel == null)
            {
                JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, latexBox, treeScreenCapturePanel);
                panel.add(verticalSplitPane, BorderLayout.CENTER);
            }
            else
            {
                JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScreenCapturePanel, rulesPanel);
                horizontalSplitPane.setOneTouchExpandable(true);
                //splitPane.setDividerLocation(150);
                JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, latexBox, horizontalSplitPane);
                panel.add(verticalSplitPane, BorderLayout.CENTER);
            }
	}
	else
	{
	    if(rulesPanel == null)
            {
                panel.add(treeScreenCapturePanel, BorderLayout.CENTER);
            }
            else
            {
                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScreenCapturePanel, rulesPanel);
                splitPane.setOneTouchExpandable(true);
                //splitPane.setDividerLocation(150);
                panel.add(treeScreenCapturePanel, BorderLayout.CENTER);
            }
	}
        
        

        JavaObject response = new JavaObject(panel);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.
    
    

    private void saveStep(Environment environment, int stackTop, Cons expression, String ruleName, String positionString)
    {
        if(positionString.equals(""))
        {
            positionString = "0";
        }
        
        try
        {
            //Utility.loadLibraryFunction("RemoveDollarSigns", environment, stackTop);
            
            Cons head0 = SublistCons.getInstance(environment, AtomCons.getInstance(environment, stackTop, "MetaToObject"));
            ((Cons) head0.car()).setCdr(expression);
            Cons result0 = environment.iLispExpressionEvaluator.evaluate(environment, stackTop, head0);                     


            TreeView.this.steps.add(Utility.printMathPiperExpression(stackTop, result0, environment, 0) + " : " + ruleName + " : " + positionString);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }
   
    
    public void clearMetaInformation(Cons expression) throws Throwable {
        expression.setMetadataMap(null);
        
        Cons cons = (Cons) expression.car(); // Go into sublist.

        cons.setMetadataMap(null);

        while (cons.cdr() != null) {
            
            cons = cons.cdr();
            
            if (cons instanceof SublistCons) {

               clearMetaInformation(cons);
            }
            else
            {
                cons.setMetadataMap(null);
            }
        }
    }
    
    
    public void highlightTree(Environment environment, int stackTop, Cons expression, String color) throws Throwable {
        Map metaDataMap = null;
        if(expression.getMetadataMap() == null)
        {
            metaDataMap = new HashMap();
            expression.setMetadataMap(metaDataMap);
        }
        else
        {
            metaDataMap = expression.getMetadataMap();
        }
        metaDataMap.put("\"HighlightColor\"", AtomCons.getInstance(environment, stackTop, "\"" + color  + "\""));
        
        if(expression instanceof SublistCons)
        {
        
            Cons cons = (Cons) expression.car(); // Go into sublist.


            if(cons.getMetadataMap() == null)
            {
                metaDataMap = new HashMap();
                cons.setMetadataMap(metaDataMap);
            }
            else
            {
                metaDataMap = cons.getMetadataMap();
            }
            metaDataMap.put("\"HighlightColor\"", AtomCons.getInstance(environment, stackTop, "\"" + color + "\""));

            while (cons.cdr() != null) {
                cons = cons.cdr();

                highlightTree(environment, stackTop, cons, color);
            }
        }
    }
    
    
    public String expressionToLatex(Environment aEnvironment, int aStackTop, Cons expression) throws Throwable
    {
                        //Evaluate Hold function.
                Cons holdAtomCons = AtomCons.getInstance(aEnvironment, aStackTop, "Hold");
                holdAtomCons.setCdr(Cons.deepCopy(aEnvironment, aStackTop, expression));
                Cons holdSubListCons = SublistCons.getInstance(aEnvironment, holdAtomCons);
                Cons holdInputExpression = holdSubListCons;        
                
        	//Obtain LaTeX version of the expression.
        	Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "UnparseLatex"));
                ((Cons) head.car()).setCdr(holdInputExpression);
                Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, head);
                String texString = (String) result.car();
                texString = Utility.stripEndQuotesIfPresent(texString);
                texString = texString.substring(1, texString.length());
                texString = texString.substring(0, texString.length() - 1);
                
                texString = texString.replace("$", "");

                return texString;
    }
    

    

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



