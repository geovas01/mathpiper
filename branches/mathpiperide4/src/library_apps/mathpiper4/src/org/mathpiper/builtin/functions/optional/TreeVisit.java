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

import org.jfree.chart.plot.PlotOrientation;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.builtin.functions.plugins.jfreechart.ChartUtility;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.astprocessors.ASTProcessor;
import org.mathpiper.lisp.astprocessors.PatternVisitor;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class TreeVisit extends BuiltinFunction
{

    private Map defaultOptions;

    public void plugIn(Environment aEnvironment)  throws Throwable, Throwable
    {
        aEnvironment.getBuiltinFunctions().put(
                "TreeVisit", new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);


    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	
	//TreeVisit(expression,pattern,function,options)
	//TreeVisit(a+b-c,_x + _y, ["xx"])
	
/*
In> TreeVisit(a+b-c,_x + _y, [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));})]])
Result: True

In> zz := PatternCompile( _x + _y <- 3)[1][2]
Result: class org.mathpiper.builtin.PatternContainer

In> PatternMatch?(zz, a+b-c)
Result: True 

In>Show(TreeView('(a*b + a*b + a*b + a*b + a*b + a*b)))
Result: class javax.swing.JFrame

In> TreeVisit('(a*b + c*d + e*f + g*h + i*j + k*l),_x * _y, ["xx"])
Result: True

=============

In> zz := PatternCompile( _x * _y <- 3)[1][2]
Result: class org.mathpiper.builtin.PatternContainer

In> PatternMatch?(zz, '(a/b + c/d))
Result: True 

 */
	
        Cons expression = getArgument(aEnvironment, aStackTop, 1);
        Cons pattern = getArgument(aEnvironment, aStackTop, 2);
        Cons function = getArgument(aEnvironment, aStackTop, 3);
        
        /*
        Cons patternTraverser = pattern;
        if(patternTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (patternTraverser.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 1);
        patternTraverser = (Cons) patternTraverser.car();
        if(patternTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        */
        

 
        
        PatternVisitor behaviour = new org.mathpiper.lisp.astprocessors.PatternVisitor(aEnvironment, pattern, function);
        
        
        this.traverse(aEnvironment, aStackTop, expression, behaviour);
        
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
    
    
    
    public static Cons traverse(Environment aEnvironment, int aStackTop, Cons aSource, ASTProcessor astProcessor) throws Throwable {
        
        Cons sourceCons = aSource;

        Cons aDestination = null;

        if(sourceCons == null) LispError.lispAssert(aEnvironment, aStackTop);


        if((aDestination = astProcessor.matches(aEnvironment, aStackTop,  aSource)) != null)
        {
            //Base case.
            return aDestination;
        }
        else
        {
            //Recursively process list.

            Object sourceListCar = sourceCons.car();

            Cons sourceList = null;

            if (sourceListCar instanceof Cons) {
                Cons cons = (Cons) sourceListCar;
                sourceList = cons;
            }

            if (sourceList != null) {

                Cons headCons = null;

                Cons indexCons = null;

                boolean isHead = true;

                while (sourceList != null) {


                    Cons result = traverse(aEnvironment, aStackTop, sourceList, astProcessor);

                    if(isHead == true)
                    {
                        headCons = result;
                        indexCons = headCons;
                        isHead = false;
                    }
                    else
                    {
                        //Point to next cons in the destination list.
                        indexCons.setCdr(result);
                        indexCons = result;
                    }

                    //Point to next cons in the source list.
                    sourceList = sourceList.cdr();



                }
                
                aDestination = SublistCons.getInstance(aEnvironment, headCons);
                
            } else {
                //Handle atoms.
                aDestination = sourceCons.copy(false);
            }

            return aDestination;

        }//end matches if.
    }

}


/*
%mathpiper_docs,name="TreeVisit",categories="Programmer Functions;Miscellaneous;Built In"
*CMD TreeVisit --- visit the nodes in a tree
*CORE
*CALL
	TreeVisit(expression,pattern,associationlist,options)

*PARMS

{expression} -- expression that will have its tree visited

{pattern} -- a pattern that matches on parts of the tree

{associationlist} -- an association list that contains an anonymous function that is called at each match

*DESC

Visits a tree

*E.G.

%/mathpiper_docs
*/
