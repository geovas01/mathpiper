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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class FunctionTreePanel extends JPanel implements TreeSelectionListener, HyperlinkListener {

    private JScrollPane docsScrollPane;
    private String[][] userFunctionsDescriptions;
    private String[][] programmerFunctionsDescriptions;
    private String[][] operatorsDescriptions;
    private DefaultMutableTreeNode userFunctionsNode;
    private DefaultMutableTreeNode programmerFunctionsNode;
    private DefaultMutableTreeNode operatorsNode;
    private List allFunctions;
    private JTree functionsTree;
    private Map documentationIndex;
    private RandomAccessFile documentFile;
    private JEditorPane editorPane;
    private StringBuilder seeFunctionsBuilder = new StringBuilder();
    private ClassLoader classLoader;

    public FunctionTreePanel(ClassLoader classLoader) {
        this.classLoader = classLoader;

        this.setLayout(new BorderLayout());

        URL fileURL = classLoader.getSystemResource("org/mathpiper/ui/gui/help/data/function_categories.txt");
        if (fileURL != null) //File is on the classpath.
        {
            System.out.println("Found categories file.");
            loadCategories(fileURL);
            loadDocumentationIndex(classLoader.getSystemResource("org/mathpiper/ui/gui/help/data/documentation_index.txt"));
            this.openDocumentationFile(classLoader.getSystemResource("org/mathpiper/ui/gui/help/data/documentation.txt"));


            this.populateUserFunctionNode();
            this.populateProgrammerFunctionNode();
            operatorsNode = new DefaultMutableTreeNode(new FunctionInfo("Operators", "Operators."));

            processFunctionDescriptions();

            createTree();

            ToolTipManager.sharedInstance().registerComponent(functionsTree);

            functionsTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
            functionsTree.addTreeSelectionListener(this);
            functionsTree.setShowsRootHandles(true);
//tree.setRootVisible(false);
            JScrollPane treeView = new JScrollPane(functionsTree);


            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
            editorPane.addHyperlinkListener(this);

            //JdocsScrollPane editorScrollPane = new JScrollPane(editorPane);
            docsScrollPane = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, docsScrollPane);
            splitPane.setOneTouchExpandable(true);
            //tree.getPreferredScrollableViewportSize().width;
            splitPane.setDividerLocation(290);
            this.add(splitPane);

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
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Special", "In this section, special and transcendental mathematical functions are described.")));
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

        populateNode(operatorsNode, operatorsDescriptions);
        mathpiperFunctionsRootNode.add(operatorsNode);

        functionsTree = new FunctionInfoTree(mathpiperFunctionsRootNode);

    }//end method.

    private void loadDocumentationIndex(URL url) {
        documentationIndex = new HashMap();
        try {
            BufferedReader documentationIndexReader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = documentationIndexReader.readLine()) != null) {

                String[] values = line.split(",");

                if (values[0].indexOf(";") != -1) {
                    String[] functionNames = values[0].split(";");
                    for (String name : functionNames) {
                        documentationIndex.put(name, values[1] + "," + values[2]);
                    }//end for.
                } else {
                    documentationIndex.put(values[0], values[1] + "," + values[2]);
                }//end else.
            }//end while.

            documentationIndexReader.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }//end method.

    private void openDocumentationFile(URL url) {

        try {
            //documentationReader = new InputStreamReader(url.openStream());
            documentFile = new RandomAccessFile(new File(url.toURI()), "r");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }//end method.

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) functionsTree.getLastSelectedPathComponent();
        //System.out.println("XXXXX");
        if (node == null) //Nothing is selected.
        {
            return;
        }

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            String functionName = nodeInfo.toString();
            viewFunction(functionName);
        } else {
            //toolPanel.sourceButtonEnabled(false);
            //Note:tk:Perhaps display top of chapter here?
        }
    }//end method.



    private void viewFunction(String functionName)
    {
            String functionIndexesString = (String) this.documentationIndex.get(functionName);
            String[] functionIndexes = functionIndexesString.split(",");
            int startIndex = Integer.parseInt(functionIndexes[0]);
            int endIndex = Integer.parseInt(functionIndexes[1]);
            int length = endIndex - startIndex;
            byte[] documentationData = new byte[length];
            System.out.println("yyyy " + functionName + "  " + startIndex + " " + endIndex + " " + length);
            try {

                documentFile.seek(startIndex);
                documentFile.read(documentationData, 0, length);
                String documentationDataString = new String(documentationData);

                documentationDataString = documentationDataString.replace("$", "");

                String html = textToHtml(documentationDataString);
                editorPane.setText(html);
                //editorPane.validate();

                final JScrollBar verticalScrollBar = docsScrollPane.getVerticalScrollBar();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
                    }
                });

                //functionInfo = nodeInfo;
                //displayFunctionDocs(functionInfo.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }//end method.



    private String applyBold(String line) {
        line = line.replaceAll("\\{", "<b><tt>");
        line = line.replaceAll("\\}", "</tt></b>");
        return line;
    }//end method.


    
    private String textToHtml(String s) {
//s = "*CMD D --- take derivative of expression with respect to variable\n*STD\n*CALL\n	D(variable) expression\n	D(list) expression\n	D(variable,n) expression\n\n*PARMS\n\n{variable} -- variable\n\n{list} -- a list of variables\n\n{expression} -- expression to take derivatives of\n\n{n} -- order of derivative\n\n*DESC\n\nThis function calculates the derivative of the expression {expr} with\nrespect to the variable {var} and returns it. If the third calling\nformat is used, the {n}-th derivative is determined. Yacas knows\nhow to differentiate standard functions such as {Ln}\nand {Sin}.\n\nThe {D} operator is threaded in both {var} and\n{expr}. This means that if either of them is a list, the function is\napplied to each entry in the list. The results are collected in\nanother list which is returned. If both {var} and {expr} are a\nlist, their lengths should be equal. In this case, the first entry in\nthe list {expr} is differentiated with respect to the first entry in\nthe list {var}, the second entry in {expr} is differentiated with\nrespect to the second entry in {var}, and so on.\n\nThe {D} operator returns the original function if $n=0$, a common\nmathematical idiom that simplifies many formulae.\n\n*E.G.\n\n	In> D(x)Sin(x*y)\n	Out> y*Cos(x*y);\n	In> D({x,y,z})Sin(x*y)\n	Out> {y*Cos(x*y),x*Cos(x*y),0};\n	In> D(x,2)Sin(x*y)\n	Out> -Sin(x*y)*y^2;\n	In> D(x){Sin(x),Cos(x)}\n	Out> {Cos(x),-Sin(x)};\n\n*SEE Integrate, Taylor, Diverge, Curl\n";


        String[] lines = s.split("\n");

        StringBuilder html = new StringBuilder();

        html.append("<html>\n");

        for (int x = 0; x < lines.length; x++) {
            //foldOutput = foldOutput + lines[x];

            String line = lines[x].trim();

            if (line.startsWith("*CMD")) {
                line = line.substring(line.indexOf(" "), line.length());
                html.append("<h3>\n<hr>" + line + "\n</h3>\n\n");

            } else if (line.startsWith("*STD")) {
                html.append("<h5 align=right>Standard library</h5><h5>\n\n");
            } else if (line.startsWith("*CALL")) {
                html.append("Calling format:\n</h5>\n<table cellpadding=\"0\" width=\"100%\">\n<tr><td width=100% bgcolor=\"#DDDDEE\"><pre>\n");

                while (true) {
                    x++;

                    if (x == lines.length) {
                        //This code exits the converter if it is the last *XXX command in the document.
                        break;
                    }//end if.

                    line = lines[x].trim();

                    if (line.startsWith("*")) {
                        x--;
                        break;
                    }
                    if (line.equalsIgnoreCase("")) {
                        continue;
                    }

                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("</pre></tr>\n</table>\n<p>\n\n");
            } else if (line.startsWith("*PARMS")) {
                html.append("<h5>\nParameters:\n</h5>\n");

                while (true) {
                    x++;

                    if (x == lines.length) {
                        //This code exits the converter if it is the last *XXX command in the document.
                        break;
                    }//end if.

                    line = lines[x].trim();

                    if (line.startsWith("*")) {
                        x--;
                        break;
                    }
                    if (line.equalsIgnoreCase("")) {
                        continue;
                    }

                    line = applyBold(line);
                    //foldOutput = foldOutput + line;

                    html.append("\n<p>\n");
                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("\n<p>\n\n");
            } else if (line.startsWith("*DESC")) {
                html.append("<h5>\nDescription:\n</h5>\n");

                while (true) {
                    x++;

                    if (x == lines.length) {
                        //This code exits the converter if it is the last *XXX command in the document.
                        break;
                    }//end if.

                    line = lines[x].trim();

                    if (line.startsWith("*")) {
                        x--;
                        break;
                    }
                    if (line.equalsIgnoreCase("")) {
                        html.append("\n<p>\n");
                        continue;
                    }

                    line = applyBold(line);
                    //foldOutput = foldOutput + line;


                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("\n<p>\n\n");
            } else if (line.startsWith("*E.G.")) {
                html.append("<h5>\nExamples:\n</h5>\n<table cellpadding=\"0\" width=\"100%\">\n<tr><td width=100% bgcolor=\"#DDDDEE\"><pre>");

                while (true) {
                    x++;

                    if (x == lines.length) {
                        //This code exits the converter if it is the last *XXX command in the document.
                        break;
                    }//end if.

                    line = lines[x].trim();

                    if (line.startsWith("*")) {
                        x--;
                        break;
                    }
                    if (line.equalsIgnoreCase("")) {
                        html.append("\n<br />\n");
                        continue;
                    }

                    line = line.replaceAll(">", "&gt;");


                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("\n</pre></tr>\n</table>\n<p>\n\n");
            } else if (line.startsWith("*SEE")) {

                //line = lines[x].trim();
                line = line.substring(4, line.length());
                line = line.replace(" ", "");

                String[] seeFunctions = line.split(",");

                for (String seeFunction : seeFunctions) {
                    seeFunctionsBuilder.append("<a href=\"http://" + seeFunction + "\">" + seeFunction + "</a>, ");
                }

                html.append("<h5>\nSee also:\n</h5>" + seeFunctionsBuilder.toString() + "\n");

                seeFunctionsBuilder.delete(0, seeFunctionsBuilder.length());

            }



        }//end for.

        html.append("</html>\n");

        return html.toString();

    }//end method.

    public void hyperlinkUpdate(HyperlinkEvent event) {
        //System.out.println(event.toString());
        URL url = event.getURL();
//System.out.println("YYYPiperDocsYYY: " + url.getPath() + " reference: " + url.getRef() + " query: " + url.getQuery() );
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

            String urlString = url.toString();
            String functionName = urlString.substring(7,urlString.length());
            System.out.println(functionName);
            viewFunction(functionName);



        }//end if.  + getRef())

    }//end method.

    public static void main(String[] args) {

        JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel helpPanel = new FunctionTreePanel(FunctionTreePanel.class.getClassLoader());


        Container contentPane = frame.getContentPane();
        contentPane.add(helpPanel);

        frame.pack();
//frame.setAlwaysOnTop(true);
        frame.setTitle("MathPiper Help");
        frame.setSize(new Dimension(700, 700));
        //frame.setResizable(false);
        frame.setPreferredSize(new Dimension(700, 700));
        frame.setLocationRelativeTo(null); // added

        frame.setVisible(true);
    }//end main.
}
