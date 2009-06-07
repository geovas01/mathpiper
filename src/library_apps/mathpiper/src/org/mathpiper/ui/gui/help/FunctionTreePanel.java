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
package org.mathpiper.ui.gui.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FunctionTreePanel {

    private String[][] userFunctionsDescriptions;
    private String[][] programmerFunctionsDescriptions;
    private String[][] operatorsDescriptions;
    private DefaultMutableTreeNode userFunctionsNode;
    private DefaultMutableTreeNode programmerFunctionsNode;
    private DefaultMutableTreeNode operatorsNode;
    private List allFunctions;

    public FunctionTreePanel() {
        URL fileURL = java.lang.ClassLoader.getSystemResource("org/mathpiper/ui/gui/help/data/function_categories.txt");
        if (fileURL != null) //File is on the classpath.
        {
            System.out.println("Found categories file.");
            loadCategories(fileURL);
            this.populateUserFunctionNode();
            this.populateProgrammerFunctionNode();
            operatorsNode = new DefaultMutableTreeNode(new FunctionInfo("Operators", "Operators."));

            processFunctionDescriptions();

            createTree();

        }//end if.
    }//end constructor.

    private void loadCategories(URL url) {
        BufferedReader categoriesFile = null;
        List userFunctions = new ArrayList();
        List programmerFunctions = new ArrayList();
        List operators = new ArrayList();

        try {
            categoriesFile = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = categoriesFile.readLine()) != null) {
                line = line + ",Alphabetical";
                String[] functionDataLine = line.split(",");

                String functionCategory = functionDataLine[0].trim();

                String[] functionData = Arrays.copyOfRange(functionDataLine, 1, functionDataLine.length);

                if (functionCategory.equalsIgnoreCase("User Functions")) {
                    userFunctions.add(functionData);
                } else if (functionCategory.equalsIgnoreCase("Programmer Functions")) {
                    programmerFunctions.add(functionData);
                } else {
                    operators.add(functionData);
                }

            }//end while.

            userFunctionsDescriptions = (String[][]) userFunctions.toArray(new String[userFunctions.size()][]);
            programmerFunctionsDescriptions = (String[][]) programmerFunctions.toArray(new String[programmerFunctions.size()][]);
            operatorsDescriptions = (String[][]) operators.toArray(new String[operators.size()][]);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (categoriesFile != null) {
                    categoriesFile.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }//end finally.

    }//end method.

    private void populateUserFunctionNode() {
        userFunctionsNode = new DefaultMutableTreeNode(new FunctionInfo("User Functions", "Functions for MathPiper users."));

        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Alphabetical", "All functions in alphabetical order.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Built In", "Functions that are implemented in Java.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Calculus Related (Symbolic)", "Functions for differentiation, integration, and solving of equations.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Combinatorics", "Combinatorics related functions.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Constants (Mathematical)", "Mathematical constants.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Constants (System)", "System related constants.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Control Flow", "Controls the order in which statements or function calls are executed.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Differential Equations", "In this section, some facilities for solving differential equations are described. Currently only simple equations without auxiliary conditions are supported.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Expression Simplification", "This section describes the functions offered that allow simplification of expressions.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Functional Operators", "These operators can help the user to program in the style of functional programming languages such as Miranda or Haskell.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Special", "In this section, special and transcendental mathematical functions are described.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Input/Output", "Functions for input, output, and plotting.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Linear Algebra", "Functions used to manipulate vectors (represented as lists) and matrices (represented as lists of lists).")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Lists (Operations)", "Most objects that can be of variable size are represented as lists.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Matrices (Predicates)", "Predicates related to matrices.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Matrices (Special)", "Various special matricies")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numeric", "Functions that calculate numerically (like those found on a scientific calculator.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numbers (Complex)", "Functions that allow manipulation of complex numbers.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numbers (Operations)", "Besides the usual arithmetical operations, MathPiper defines some more advanced operations on numbers. Many of them also work on polynomials.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numbers (Predicates)", "Predicates relating to numbers.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numbers (Random)", "Random number related functions.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Number Theory", "Functions that are of interest in number theory. They typically operate on integers")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Polynomials (Operations)", "Functions to manipulate polynomials, including functions for constructing and evaluating orthogonal polynomials.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Polynomials (Special)", "Special polynomials.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Predicates", "A predicate is a function that returns a boolean value, i.e. True or False.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Probability & Statistics", "Probability and statistics.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Propositional Logic", "Functions for propositional logic.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Series", "Functions which operate on series.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Solvers (Numeric)", "Functions for solving equations numerically.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Solvers (Symbolic)", "By solving one tries to find a mathematical object that meets certain criteria.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("String Manipulation", "Functions for manipulating strings.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Transforms", "In this section, some facilities for various transforms are described.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Variables", "Functions that work with variables.")));

    }//end method.

    private void populateProgrammerFunctionNode() {
        programmerFunctionsNode = new DefaultMutableTreeNode(new FunctionInfo("Programmer Functions", "Functions for MathPiper code developers."));

        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Alphabetical", "All functions in alphabetical order.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Built In", "MathPiper has a small set of built-in functions and a large library of user-defined functions. Some built-in functions are in this section.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Error Reporting", "Functions which are useful for reporting errors to the user.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Native Objects", "Functions for allowing the MathPiper interpreter access native code.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numerical (Arbitrary Precision)", "Functions for programming numerical calculations with arbitrary precision.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Programming", "Functions which are useful for writing MathPiper scripts.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Testing", "Functions for verifying the correct operation of MathPiper code.")));

    }//end method.

    private void populateNode(DefaultMutableTreeNode treeNode, String[][] descriptionsStringArray) {
        for (int row = 0; row < descriptionsStringArray.length; row++) {
            //Populate JList.
            //jlist.addElement(descriptionsStringArray[row][0]);

            //Populate.
            for (int column = 2; column < descriptionsStringArray[row].length; column++) {
                String category = descriptionsStringArray[row][column];
                //System.out.println("XXXXX " + descriptionsStringArray[row][column]);


                boolean hasCategory = false;
                Enumeration children = treeNode.children();
                for (; children.hasMoreElements();) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
                    if (child.getUserObject().toString().equalsIgnoreCase(category)) //Add leaf to existing category.
                    {
                        child.add(new DefaultMutableTreeNode(new FunctionInfo(descriptionsStringArray[row][0], descriptionsStringArray[row][1])));
                        hasCategory = true;
                    }


                }//end for.

                if (hasCategory == false) {
                    DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(new FunctionInfo(descriptionsStringArray[row][0], descriptionsStringArray[row][1]));
                    DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(descriptionsStringArray[row][column]);
                    categoryNode.add(leaf);
                    treeNode.add(categoryNode);
                }

            }//end for.

        }//end for/

    }//end method.

    private void processFunctionDescriptions() {
        allFunctions = new java.util.ArrayList();

        int progFuncDescIndex = 0;
        int userFuncDescIndex = 0;

        boolean endFlag = false;

        while (endFlag != true) {
            if (userFuncDescIndex != userFunctionsDescriptions.length && progFuncDescIndex != programmerFunctionsDescriptions.length) {
                if (userFunctionsDescriptions[userFuncDescIndex][0].compareToIgnoreCase(programmerFunctionsDescriptions[progFuncDescIndex][0]) == 0) {
                    userFuncDescIndex++;
                }//end if.

                if (userFunctionsDescriptions[userFuncDescIndex][0].compareToIgnoreCase(programmerFunctionsDescriptions[progFuncDescIndex][0]) < 0) {
                    String[] desc = new String[3];
                    desc[0] = userFunctionsDescriptions[userFuncDescIndex][0];
                    desc[1] = userFunctionsDescriptions[userFuncDescIndex][1];
                    desc[2] = "All Functions";
                    allFunctions.add(desc);
                    //System.out.println("USER: " + desc[0] + " position: " + userFuncDescIndex);
                    userFuncDescIndex++;
                } else {
                    String[] desc = new String[3];
                    desc[0] = programmerFunctionsDescriptions[progFuncDescIndex][0];
                    desc[1] = programmerFunctionsDescriptions[progFuncDescIndex][1];
                    desc[2] = "All Functions";
                    allFunctions.add(desc);
                    //System.out.println("Programmer: " + desc[0] + " position: " + progFuncDescIndex);
                    progFuncDescIndex++;
                }
            } else if (userFuncDescIndex != userFunctionsDescriptions.length) {
                String[] desc = new String[3];
                desc[0] = userFunctionsDescriptions[userFuncDescIndex][0];
                desc[1] = userFunctionsDescriptions[userFuncDescIndex][1];
                desc[2] = "All Functions";
                allFunctions.add(desc);
                userFuncDescIndex++;
            } else if (progFuncDescIndex != programmerFunctionsDescriptions.length) {
                String[] desc = new String[3];
                desc[0] = programmerFunctionsDescriptions[progFuncDescIndex][0];
                desc[1] = programmerFunctionsDescriptions[progFuncDescIndex][1];
                desc[2] = "All Functions";
                allFunctions.add(desc);
                progFuncDescIndex++;
            } else {
                endFlag = true;
            }
        }//end while.
    }//end method.

    public void createTree() {
        DefaultMutableTreeNode mathpiperFunctionsRootNode = new DefaultMutableTreeNode(new FunctionInfo("MathPiper Functions                                           ", "All MathPiper functions and constants."));

        populateNode(mathpiperFunctionsRootNode, (String[][]) allFunctions.toArray(new String[allFunctions.size()][]));

        populateNode(userFunctionsNode, userFunctionsDescriptions);
        mathpiperFunctionsRootNode.add(userFunctionsNode);

        populateNode(programmerFunctionsNode, programmerFunctionsDescriptions);
        mathpiperFunctionsRootNode.add(programmerFunctionsNode);

        populateNode(mathpiperFunctionsRootNode, operatorsDescriptions);

        JTree tree = new FunctionInfoTree(mathpiperFunctionsRootNode);
        int x=0;
    }//end method.

    public static void main(String[] args) {
        new FunctionTreePanel();
    }//end main.
}
