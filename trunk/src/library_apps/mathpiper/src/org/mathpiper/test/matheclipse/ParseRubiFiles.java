package org.mathpiper.test.matheclipse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.ast.FloatNode;
import org.matheclipse.parser.client.ast.FractionNode;
import org.matheclipse.parser.client.ast.FunctionNode;
import org.matheclipse.parser.client.ast.IntegerNode;
import org.matheclipse.parser.client.ast.PatternNode;
import org.matheclipse.parser.client.ast.StringNode;
import org.matheclipse.parser.client.ast.SymbolNode;

public class ParseRubiFiles {

    private static HashMap mathematicaToMathPiperMap = new HashMap();


    static {
        mathematicaToMathPiperMap.put("SetDelayed", "<--");
        mathematicaToMathPiperMap.put("Condition", "_");

    }//end static.


    public static List<ASTNode> parseFileToList(String fileName) {
        try {
            File file = new File(fileName);
            final BufferedReader f = new BufferedReader(new FileReader(file));
            final StringBuffer buff = new StringBuffer(1024);
            String line;
            while ((line = f.readLine()) != null) {
                buff.append(line);
                buff.append("\n");
            }
            f.close();
            String inputString = buff.toString();
            Parser p = new Parser(false, true);

            //inputString = "zz[x_] := 2 /; x < 7\n";

            inputString = "Int[u_,x_Symbol] := Module[{v=TrigSimplify[u]}, Int[v,x] /; v=!=u] /; Not[MatchQ[u,w_.*(a_.+b_.*v_)^m_.*(c_.+d_.*v_)^n_. /;  FreeQ[{a,b,c,d},x] && IntegerQ[{m,n}] && m<0 && n<0]]";


            return p.parseList(inputString);
            // assertEquals(obj.toString(),
            // "Plus[Plus[Times[-1, a], Times[-1, Times[b, Factorial2[c]]]], d]");
        } catch (Exception e) {
            e.printStackTrace();
            // assertEquals("", e.getMessage());
        }
        return null;
    }//end method.



    /*

    zz[x_] := 2 /; x < 7  Mathematica

    SetDelayed[zz[x_], Condition[2, Less[x, 7]]]  Parsed


    zz(_x)_(x < 7) <-- 2; MathPiper

    (<-- (_ (zz (_ x) ) (< x 7 ) ) 2)

    (<--
      (_
        (zz (_ x))
        (< x 7))
      2 )



    mockmma_with_abcl$ java -jar abcl.jar

    Armed Bear Common Lisp 0.21.0
    Java 1.6.0_20 Sun Microsystems Inc.
    Java HotSpot(TM) 64-Bit Server VM
    Low-level initialization completed in 0.57 seconds.
    Startup completed in 1.667 seconds.
    Type ":help" for a list of available commands.

    CL-USER(1): (load "mma")
    T

    CL-USER(2): (load "parser")
    T

    CL-USER(3): (in-package :mma)
    #<PACKAGE "MMA">

    MMA(4): (p)
    2+2
    (PLUS 2 2)

     */
    public static void convert(ASTNode node, StringBuffer buf) {

        if (node == null) {

            buf.append("NULL");

            return;
        }

        if (node instanceof FunctionNode) {
            final FunctionNode functionNode = (FunctionNode) node;

            int numberOfArguments = functionNode.size();


            ASTNode mathematicaFunctionNode = (ASTNode) functionNode.get(0);

            FunctionNode conditionNode = null;

            String mathematicaFunctionName = "";

            String mathPiperConditionFunctionName = "";

            buf.append("(");

            if (mathematicaFunctionNode == null) {
                buf.append("<null-tag>");
            } else {
                mathematicaFunctionName = mathematicaFunctionNode.toString();


                String mathPiperFunctionName = (String) mathematicaToMathPiperMap.get(mathematicaFunctionName);

                if (mathPiperFunctionName == null) {
                    buf.append(mathematicaFunctionName);
                } else {
                    buf.append(mathPiperFunctionName);
                }


                if (mathematicaFunctionName.equals("SetDelayed")) {
                    numberOfArguments--;

                    conditionNode = (FunctionNode) functionNode.get(numberOfArguments);

                    buf.append("(");

                    ASTNode symbolNode = conditionNode.get(0);

                    String conditionNodeName = symbolNode.toString();

                    mathPiperConditionFunctionName = (String) mathematicaToMathPiperMap.get(conditionNodeName);

                    buf.append(mathPiperConditionFunctionName);

                    buf.append(" ");

                }

            }//end else.



            for (int i = 1; i < numberOfArguments; i++) {

                ASTNode parameterNode = (ASTNode) functionNode.get(i);

                if (parameterNode == functionNode) {
                    buf.append("(this ListNode)");
                } else {
                    convert((ASTNode) functionNode.get(i), buf);
                }


                if (i < numberOfArguments - 1) {
                    buf.append(" ");
                }
            }//end for.


            if (mathematicaFunctionName.equals("SetDelayed")) {
                buf.append("(");


                for (int i = 1; i < conditionNode.size(); i++) {

                    ASTNode parameterNode = (ASTNode) conditionNode.get(i);


                    convert((ASTNode) parameterNode, buf);


                    if (i < conditionNode.size() - 1) {
                        buf.append(" ");
                    }
                }//end for.

                buf.append(")");

                buf.append(")");
            }



            buf.append(")");


            return;


        }//end if.



        if (node instanceof SymbolNode) {

            buf.append(node.getString());


            return;//node.getString();

        }

        if (node instanceof PatternNode) {
            final PatternNode pn = (PatternNode) node;

            buf.append(" ");

            buf.append("(");

            buf.append("_");

            buf.append(" ");

            if (pn.getSymbol() != null) {
                convert(pn.getSymbol(), buf);
            }

            if (pn.isfOptional()) {
                buf.append(".");
            }

            if (pn.getConstraint() != null) {
                convert(pn.getConstraint(), buf);
            }

            buf.append(")");

            return;// convert(pn.getSymbol()), convert(pn.getConstraint()));
        }



        if (node instanceof IntegerNode) {
            final IntegerNode integerNode = (IntegerNode) node;
            final String iStr = integerNode.getString();
            if (iStr != null) {

                buf.append(integerNode.getNumberFormat());
                return; //integerNode.getNumberFormat());
            }

            buf.append(integerNode.getIntValue());

            return;  //integerNode.getIntValue());
        }

        if (node instanceof FractionNode) {
            FractionNode fr = (FractionNode) node;

            if (fr.isSign()) {
                return; //convert(fr.getNumerator()), (IInteger) convert(fr.getDenominator())).negate();
            }
            return;//convert(((FractionNode) node).getNumerator()), (IInteger) convert(((FractionNode) node).getDenominator()));
        }

        if (node instanceof StringNode) {
            buf.append(node.getString());
            return;//node.getString());
        }

        if (node instanceof FloatNode) {
            buf.append(node.getString());
            return;//node.getString());
        }

        buf.append(node.getString());
        return;// Symbol   node.toString());
    }


    public static void main(String[] args) {

        String path = "/home/tkosan/NetBeansProjects/mathpiper/src/org/mathpiper/test/matheclipse/";

        String[] fileNames = {"GeneralIntegrationRules.m"};

        /* String[] fileNames = {
        "GeneralIntegrationRules.m",
        "RationalFunctionIntegrationRules.m",
        "AlgebraicFunctionIntegrationRules.m",
        "ExponentialFunctionIntegrationRules.m",
        "TrigFunctionIntegrationRules.m",
        "HyperbolicFunctionIntegrationRules.m",
        "LogarithmFunctionIntegrationRules.m",
        "InverseTrigFunctionIntegrationRules.m",
        "InverseHyperbolicFunctionIntegrationRules.m",
        "ErrorFunctionIntegrationRules.m",
        "IntegralFunctionIntegrationRules.m",
        "SpecialFunctionIntegrationRules.m"
        };*/


        for (int i = 0; i < fileNames.length; i++) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(">>>>> File name: " + fileNames[i]);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
            List<ASTNode> list = parseFileToList(path + fileNames[i]);
            if (list != null) {
                for (ASTNode astNode : list) {
                    System.out.println(astNode);
                    System.out.println();

                    System.out.println("////////////////////");

                    StringBuffer buffer = new StringBuffer();
                    convert(astNode, buffer);
                    System.out.println(buffer);

                    System.out.println("////////////////////\n\n");
                }
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println(">>>>> Number of entries: " + list.size());
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
            }

            //System.out.println("\n\n\n\n");




        }//end for.



    }

}
