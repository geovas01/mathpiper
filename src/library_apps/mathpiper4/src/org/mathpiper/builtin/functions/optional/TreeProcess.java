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
 *///}}}

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
import org.mathpiper.lisp.astprocessors.PatternProcess;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class TreeProcess extends BuiltinFunction {

    private Map defaultOptions;



    public void plugIn(Environment aEnvironment) throws Throwable {
	aEnvironment.getBuiltinFunctions().put("TreeProcess",
		new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

	defaultOptions = new HashMap();
	defaultOptions.put("title", null);

    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

	//TreeProcess(expression,pattern,function,options)
	//TreeProcess(a+b-c,_x + _y, ["xx"])

	/*
	 
	  
	In> zz := '(a+b-c);
	Result: (a+b)-c

	In> TreeProcess(zz,a, [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])
	Result: [["track",["a"]],["function",Lambda([list,node],
	{
	    DestructiveAppend(list["track"],ToString(node));
	    MetaSet(node,"HighlightColor","GREEN");
	})]]

	In> zz
	Result: (a+b)-c

	In> Show(TreeView(zz))
	Result: class javax.swing.JFrame

	In> MetaKeys(zz[1][1])
	Result: [startIndex,"op",lineNumber,endIndex]
	  
	  
	  
	==================
	  
	In> zz := '(a+b-c*a);
	Result: (a+b)-(c*a)

	In> TreeProcess(zz,_y, [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])


	In> TreeProcess(zz,ToAtom("+"), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])


	In> TreeProcess(zz,y_Associative?, [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])


	In> TreeProcess(zz,y_Associative?:Commutative?, [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])

	In> TreeProcess(zz,(_x + _y)_(x >? 7), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])

	In> TreeProcess(zz,ToAtom("+"), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])
	In> TreeProcess(zz,ToAtom("+"), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])
	In> TreeProcess(zz,ToAtom("+"), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])
	In> TreeProcess(zz,ToAtom("+"), [["track",[]],["function",Lambda([list,node], {DestructiveAppend(list["track"],ToString(node));MetaSet(node,"HighlightColor","GREEN");})]])


	In> zz
	Result: (a+b)-c

	In> Show(TreeView(zz))
	Result: class javax.swing.JFrame

	In> MetaKeys(zz[1][1])
	Result: [startIndex,"op",lineNumber,endIndex]

	In> Show(TreeView('(y_(Associative?:Commutative?))))
	Result: class javax.swing.JFrame

	In> (_x + _y)_(x >? 7)
	Result: (_x+_y)_(7<?x)

	In> 

	=============

	In> zz := PatternCompile( _x * _y <- 3)[1][2]
	Result: class org.mathpiper.builtin.PatternContainer

	In> PatternMatch?(zz, '(a/b + c/d))
	Result: True 

	 */

	Cons expression = getArgument(aEnvironment, aStackTop, 1);
	
	Cons pattern = getArgument(aEnvironment, aStackTop, 2);

	Cons associationList = ((Cons) getArgument(aEnvironment, aStackTop, 3).car()).cdr();
	
	if(! Utility.isList(associationList)) LispError.throwError(aEnvironment, aStackTop, "The third argument must be an association list.");

	
	PatternProcess patternVisitor = new org.mathpiper.lisp.astprocessors.PatternProcess(aEnvironment, pattern, associationList);

	Cons resultCons = Utility.substitute(aEnvironment, aStackTop, expression, patternVisitor);

	setTopOfStack(aEnvironment, aStackTop, resultCons);
    }

}

/*
%mathpiper_docs,name="TreeProcess",categories="Programming Functions;Miscellaneous;Built In"
*CMD TreeProcess --- visit the nodes in a tree
*CORE
*CALL
	TreeProcess(expression,pattern,associationlist,options)

*PARMS

{expression} -- expression that will have its tree visited

{pattern} -- a pattern that matches on parts of the tree

{associationlist} -- an association list that contains an anonymous function that is called at each match

*DESC

Visits a tree

*E.G.

%/mathpiper_docs
*/
