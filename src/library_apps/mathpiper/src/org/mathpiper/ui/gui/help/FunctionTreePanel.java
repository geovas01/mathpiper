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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;

public class FunctionTreePanel extends JPanel implements TreeSelectionListener, HyperlinkListener {

    private JScrollPane docsScrollPane;
    private String[][] userFunctionsData;
    private String[][] programmerFunctionsData;
    private String[][] operatorsData;
    private DefaultMutableTreeNode userFunctionsNode;
    private DefaultMutableTreeNode programmerFunctionsNode;
    private DefaultMutableTreeNode operatorsNode;
    private List allFunctions;
    private FunctionInfoTree functionsTree;
    private Map documentationIndex;
    private RandomAccessFile documentFile;
    private JEditorPane editorPane;
    private static StringBuilder seeFunctionsBuilder = new StringBuilder();
    private List pageList;
    private ToolPanel toolPanel = null;
    private String selectedFunctionName = "";
    private boolean showPrivateFunctions = false;
    private boolean showExperimentalFunctions = true;
    private JScrollPane treeViewScrollPane;
    private JSplitPane splitPane;
    private JPanel treePanel;
    private ArrayList<HelpListener> helpListeners;


    public FunctionTreePanel() throws FileNotFoundException {
        
        helpListeners = new ArrayList<HelpListener>();

        this.setLayout(new BorderLayout());

        pageList = new ArrayList();
        pageList.add("HomePage");

        InputStream functionCategoriesStream = FunctionTreePanel.class.getResourceAsStream("/org/mathpiper/ui/gui/help/data/function_categories.txt");

        if (functionCategoriesStream == null) {
            throw new FileNotFoundException("The file function_categories.txt was not found.");
        }

        loadCategories(functionCategoriesStream);

        InputStream documentationIndexStream = FunctionTreePanel.class.getResourceAsStream("/org/mathpiper/ui/gui/help/data/documentation_index.txt");

        if (documentationIndexStream == null) {
            throw new FileNotFoundException("The file documentation_index.txt was not found.");
        }

        loadDocumentationIndex(documentationIndexStream);

        createTree();

        ToolTipManager.sharedInstance().registerComponent(functionsTree);

        treePanel = new JPanel();
        treePanel.setLayout(new BorderLayout());
        treePanel.add(functionsTree);
        treeViewScrollPane = new JScrollPane(treePanel);
        treeViewScrollPane.getVerticalScrollBar().setUnitIncrement(60);
        treeViewScrollPane.getVerticalScrollBar().setBlockIncrement(180);



        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        editorPane.addHyperlinkListener(this);
        //editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        //JdocsScrollPane editorScrollPane = new JScrollPane(editorPane);
        docsScrollPane = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel treePanelContainer = new JPanel();


        //Collapse tree button.
        JButton collapseButton = new javax.swing.JButton("Collapse");
        collapseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                collapse();
            }

        });
        collapseButton.setEnabled(true);
        collapseButton.setToolTipText("Collapse function tree.");
        add(collapseButton);

        treePanelContainer.setLayout(new BorderLayout());

        Box treeToolPanel = new Box(BoxLayout.X_AXIS);

        treeToolPanel.add(collapseButton);

        treeToolPanel.add(Box.createHorizontalGlue());

        treePanelContainer.add(treeToolPanel, BorderLayout.NORTH);

        treePanelContainer.add(treeViewScrollPane);

        tabbedPane.addTab("Functions", null, treePanelContainer, "Functions tree.");

        tabbedPane.addTab("Search", null, new SearchPanel(), "Search the function descriptions.");


        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, docsScrollPane);
        splitPane.setOneTouchExpandable(true);
        //tree.getPreferredScrollableViewportSize().width;
        splitPane.setDividerLocation(290);
        this.add(splitPane);

        toolPanel = new ToolPanel();
        home();

    }//end constructor.


    private void loadCategories(InputStream inputStream) {
        BufferedReader categoriesFile = null;
        List userFunctions = new ArrayList();
        List programmerFunctions = new ArrayList();
        List operators = new ArrayList();




        try {
            categoriesFile = new BufferedReader(new InputStreamReader(inputStream));

            String line;


            while ((line = categoriesFile.readLine()) != null) {
                line = line + ",Alphabetical";

                List<String> functionDatalineFields = parseCSV(line);

                String functionCategory = functionDatalineFields.get(0).trim();

                functionDatalineFields.remove(0);

                String[] functionDatalineFieldsArray = functionDatalineFields.toArray(new String[functionDatalineFields.size()]);  //line.split(",");

                if (functionCategory.equalsIgnoreCase("User Functions")) {
                    userFunctions.add(functionDatalineFieldsArray);
                } else if (functionCategory.equalsIgnoreCase("Programmer Functions")) {
                    programmerFunctions.add(functionDatalineFieldsArray);
                } else {
                    operators.add(functionDatalineFieldsArray);
                }

            }//end while.

            userFunctionsData = (String[][]) userFunctions.toArray(new String[userFunctions.size()][]);
            programmerFunctionsData = (String[][]) programmerFunctions.toArray(new String[programmerFunctions.size()][]);
            operatorsData = (String[][]) operators.toArray(new String[operators.size()][]);

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


    private List parseCSV(String line) {
        List list = new ArrayList();
        String CSV_PATTERN = "\"([^\"]+?)\",?|([^,]+),?|,";
        Pattern csvRE = Pattern.compile(CSV_PATTERN);
        Matcher m = csvRE.matcher(line);
        // For each field
        while (m.find()) {
            String match = m.group();
            if (match == null) {
                break;
            }
            if (match.endsWith(",")) {  // trim trailing ,
                match = match.substring(0, match.length() - 1);
            }
            if (match.startsWith("\"")) { // assume also ends with
                match = match.substring(1, match.length() - 1);
            }
            //if (match.length() == 0)
            //match = null;
            list.add(match);
        }
        return list;
    }


    private void populateUserFunctionNodeWithCategories() {
        userFunctionsNode = new DefaultMutableTreeNode(new FunctionInfo("User Functions", "Functions for MathPiper users."));

        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Alphabetical", "All functions in alphabetical order.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Analytic Geometry", "Functions that are related to analytic geometry.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Built In", "Functions that are implemented in Java.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Calculus Related (Symbolic)", "Functions for differentiation, integration, and solving of equations.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Combinatorics", "Combinatorics related functions.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Constants (Mathematical)", "Mathematical constants.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Constants (System)", "System related constants.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Control Flow", "Controls the order in which statements or function calls are executed.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Differential Equations", "In this section, some facilities for solving differential equations are described. Currently only simple equations without auxiliary conditions are supported.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Expression Manipulation", "This section describes functions which allow expressions to be manipulated.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Expression Simplification", "This section describes functions that allow simplification of expressions.")));
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
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Propositional Logic", "Functions for propositional logic.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Series", "Functions which operate on series.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Solvers (Numeric)", "Functions for solving equations numerically.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Solvers (Symbolic)", "By solving one tries to find a mathematical object that meets certain criteria.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Special Functions", "In this section, special and transcendental mathematical functions are described.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Statistics & Probability", "Statistics & Probability.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("String Manipulation", "Functions for manipulating strings.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Transforms", "In this section, some facilities for various transforms are described.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Trigonometry (Numeric)", "Functions for working with trigonometry numerically.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Trigonometry (Symbolic)", "Functions for working with trigonometry symbolically.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Variables", "Functions that work with variables.")));
        userFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Visualization", "Functions that help visualize data.")));

    }//end method.


    private void populateProgrammerFunctionNodeWithCategories() {
        programmerFunctionsNode = new DefaultMutableTreeNode(new FunctionInfo("Programmer Functions", "Functions for MathPiper code developers."));

        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Alphabetical", "All functions in alphabetical order.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Built In", "MathPiper has a small set of built-in functions and a large library of user-defined functions. Some built-in functions are in this section.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Debugging", "Functions used for debugging MathPiper code.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Error Reporting", "Functions which are useful for reporting errors to the user.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Native Objects", "Functions for allowing the MathPiper interpreter access native code.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Numerical (Arbitrary Precision)", "Functions for programming numerical calculations with arbitrary precision.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Programming", "Functions which are useful for writing MathPiper scripts.")));
        programmerFunctionsNode.add(new DefaultMutableTreeNode(new FunctionInfo("Testing", "Functions for verifying the correct operation of MathPiper code.")));

    }//end method.


    private void populateNode(DefaultMutableTreeNode treeNode, String[][] functionDataStringArray) {
        for (int row = 0; row < functionDataStringArray.length; row++) {

            if (this.showPrivateFunctions == true && functionDataStringArray[row][1].equals("private")) {
                //Pass through to populate.
            } else if (this.showExperimentalFunctions == true && functionDataStringArray[row][1].equals("experimental")) {
                //Pass through to populate.
            } else if (functionDataStringArray[row][1].equals("public")) {
                //Pass through to populate.
            } else {
                //Skip populate.
                continue;
            }

            //Populate.
            for (int column = 3; column < functionDataStringArray[row].length; column++) {
                String category = functionDataStringArray[row][column];
                //System.out.println("XXXXX " + descriptionsStringArray[row][column]);


                boolean hasCategory = false;
                Enumeration children = treeNode.children();
                for (; children.hasMoreElements();) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
                    if (child.getUserObject().toString().equalsIgnoreCase(category)) //Add leaf to existing category.
                    {
                        child.add(new DefaultMutableTreeNode(new FunctionInfo(functionDataStringArray[row][0], functionDataStringArray[row][1], functionDataStringArray[row][2])));
                        hasCategory = true;
                    }


                }//end for.

                if (hasCategory == false) {
                    DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(new FunctionInfo(functionDataStringArray[row][0], functionDataStringArray[row][1], functionDataStringArray[row][2]));
                    DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(functionDataStringArray[row][column]);
                    categoryNode.add(leaf);
                    treeNode.add(categoryNode);
                }

            }//end column for.



        }//end row for.

    }//end method.


    private void processFunctionData() {
        allFunctions = new java.util.ArrayList();

        int progFuncDescIndex = 0;
        int userFuncDescIndex = 0;

        boolean endFlag = false;

        while (endFlag != true) {

            String[] functionData = new String[4];

            if (userFuncDescIndex != userFunctionsData.length && progFuncDescIndex != programmerFunctionsData.length) {

                if (userFunctionsData[userFuncDescIndex][0].compareToIgnoreCase(programmerFunctionsData[progFuncDescIndex][0]) == 0) {
                    //If the same function is in the user function list and the programmer function list, skip the one in the
                    //user function list and use the one which is in the programmer function list.
                    userFuncDescIndex++;
                }//end if.


                if (userFunctionsData[userFuncDescIndex][0].compareToIgnoreCase(programmerFunctionsData[progFuncDescIndex][0]) < 0) {
                    functionData[0] = userFunctionsData[userFuncDescIndex][0];
                    functionData[1] = userFunctionsData[userFuncDescIndex][1];
                    functionData[2] = userFunctionsData[userFuncDescIndex][2];
                    functionData[3] = "All Functions";
                    allFunctions.add(functionData);
                    //System.out.println("USER: " + desc[0] + " position: " + userFuncDescIndex);
                    userFuncDescIndex++;
                } else {
                    functionData[0] = programmerFunctionsData[progFuncDescIndex][0];
                    functionData[1] = programmerFunctionsData[progFuncDescIndex][1];
                    functionData[2] = programmerFunctionsData[progFuncDescIndex][2];
                    functionData[3] = "All Functions";
                    allFunctions.add(functionData);
                    //System.out.println("Programmer: " + desc[0] + " position: " + progFuncDescIndex);
                    progFuncDescIndex++;
                }
            } else if (userFuncDescIndex != userFunctionsData.length) {
                functionData[0] = userFunctionsData[userFuncDescIndex][0];
                functionData[1] = userFunctionsData[userFuncDescIndex][1];
                functionData[2] = userFunctionsData[userFuncDescIndex][2];
                functionData[3] = "All Functions";
                allFunctions.add(functionData);
                userFuncDescIndex++;
            } else if (progFuncDescIndex != programmerFunctionsData.length) {
                functionData[0] = programmerFunctionsData[progFuncDescIndex][0];
                functionData[1] = programmerFunctionsData[progFuncDescIndex][1];
                functionData[2] = programmerFunctionsData[progFuncDescIndex][2];
                functionData[3] = "All Functions";
                allFunctions.add(functionData);
                progFuncDescIndex++;
            } else {
                endFlag = true;
            }
        }//end while.


    }//end method.


    public void createTree() {

        this.populateUserFunctionNodeWithCategories();

        this.populateProgrammerFunctionNodeWithCategories();

        operatorsNode = new DefaultMutableTreeNode(new FunctionInfo("Operators", "Operators."));

        processFunctionData();


        DefaultMutableTreeNode mathpiperFunctionsRootNode = new DefaultMutableTreeNode(new FunctionInfo("MathPiper Functions                                           ", "All MathPiper functions and constants."));

        String[][] allFunctionsArray = (String[][]) allFunctions.toArray(new String[allFunctions.size()][]);

        populateNode(mathpiperFunctionsRootNode, allFunctionsArray);

        populateNode(userFunctionsNode, userFunctionsData);
        mathpiperFunctionsRootNode.add(userFunctionsNode);

        populateNode(programmerFunctionsNode, programmerFunctionsData);
        mathpiperFunctionsRootNode.add(programmerFunctionsNode);

        populateNode(operatorsNode, operatorsData);
        mathpiperFunctionsRootNode.add(operatorsNode);

        DefaultTreeModel model = new DefaultTreeModel(mathpiperFunctionsRootNode);



        functionsTree = new FunctionInfoTree(model);

        functionsTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        functionsTree.addTreeSelectionListener(this);
        functionsTree.setShowsRootHandles(true);


    }//end method.


    private void loadDocumentationIndex(InputStream inputStream) {
        documentationIndex = new HashMap();
        try {
            BufferedReader documentationIndexReader = new BufferedReader(new InputStreamReader(inputStream));

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


    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) functionsTree.getLastSelectedPathComponent();
        //System.out.println("XXXXX");
        if (node == null) //Nothing is selected.
        {
            return;
        }

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            selectedFunctionName = nodeInfo.toString();
            viewFunction(selectedFunctionName, true);
        } else {
            //toolPanel.sourceButtonEnabled(false);
            //Note:tk:Perhaps display top of chapter here?
        }
    }//end method.


    public boolean viewFunction(String functionName, boolean save) {

        if (this.documentationIndex.containsKey(functionName)) {

            String functionIndexesString = (String) this.documentationIndex.get(functionName);
            String[] functionIndexes = functionIndexesString.split(",");
            int startIndex = Integer.parseInt(functionIndexes[0]);
            int endIndex = Integer.parseInt(functionIndexes[1]);
            int length = endIndex - startIndex;
            byte[] documentationData = new byte[length];
            //char[] documentationData = new char[length];
            //System.out.println("yyyy " + functionName + "  " + startIndex + " " + endIndex + " " + length);
            try {

                BufferedInputStream documentationStream = new BufferedInputStream(FunctionTreePanel.class.getResourceAsStream("/org/mathpiper/ui/gui/help/data/documentation.txt"));

                if (documentationStream == null) {
                    throw new FileNotFoundException("The file documentation.txt was not found.");
                }

                documentationStream.skip(startIndex);
                documentationStream.read(documentationData, 0, length);
                //docsStream.close();




                String documentationDataString = new String(documentationData);

                //documentationDataString = documentationDataString.replace("$", "");

                String html = textToHtml(documentationDataString);

                html = processLatex(html);

                setPage(functionName, html, save);

                //functionInfo = nodeInfo;
                //displayFunctionDocs(functionInfo.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }//end catch.

            return true;


        } else {
            return false;
        }
    }//end method.


    public static String processLatex(String html) {
        StringBuilder stringBuilder = new StringBuilder();

        int startIndex = -1;

        int endIndex = -1;

        for (int index = 0; index < html.length(); index++) {
            if (html.charAt(index) == '$') {
                if (html.charAt(index - 1) == '\\') {
                    //Strip \ character in escaped \$.
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.append(html.charAt(index));
                } else {
                    if (startIndex == -1) {
                        startIndex = index + 1;

                        endIndex = 0;
                    } else {
                        endIndex = index;

                        String latexCode = html.substring(startIndex, endIndex);

                        latexCode = latexCode.replace(" ", "");

                        //String latexEmbedString = "<object classid=\"org.mathpiper.ui.gui.hoteqn.sHotEqn\" equation=\"" + latexCode + "\" >  </object>";

                        String latexEmbedString = "<object classid=\"org.mathpiper.ui.gui.help.RenderedLatex\" latex=\"" + latexCode + "\" >  </object>";

                        //System.out.println("LATEX: " + latexEmbedString);

                        stringBuilder.append(latexEmbedString);

                        startIndex = -1;

                        endIndex = -1;
                    }//end else.

                }//end else.
            } else {
                if (endIndex == -1) {
                    stringBuilder.append(html.charAt(index));
                }
            }
        }//end for.


        return stringBuilder.toString();
    }


    private static String applyBold(String line) {


        //line = line.replaceAll("\\{", "<b><tt>");
        //line = line.replaceAll("\\}", "</tt></b>");

        StringBuilder stringBuilder = new StringBuilder();

        int startIndex = -1;

        int endIndex = -1;

        for (int index = 0; index < line.length(); index++) {
            if (line.charAt(index) == '{') {
                stringBuilder.append("<b><tt>");
            } else if (line.charAt(index) == '}') {
                stringBuilder.append("</tt></b>");
            } else {
                stringBuilder.append(line.charAt(index));
            }

        }//end for.


        return stringBuilder.toString();
    }//end method.


    /*private static String applyPre(String line) {
    line = line.replaceAll("\\[", "<pre>");
    line = line.replaceAll("\\]", "</pre>");
    return line;
    }//end method.
     */
    public static String textToHtml(String scriptCode) {
//s = "*CMD D --- take derivative of expression with respect to variable\n*STD\n*CALL\n	D(variable) expression\n	D(list) expression\n	D(variable,n) expression\n\n*PARMS\n\n{variable} -- variable\n\n{list} -- a list of variables\n\n{expression} -- expression to take derivatives of\n\n{n} -- order of derivative\n\n*DESC\n\nThis function calculates the derivative of the expression {expr} with\nrespect to the variable {var} and returns it. If the third calling\nformat is used, the {n}-th derivative is determined. Yacas knows\nhow to differentiate standard functions such as {Ln}\nand {Sin}.\n\nThe {D} operator is threaded in both {var} and\n{expr}. This means that if either of them is a list, the function is\napplied to each entry in the list. The results are collected in\nanother list which is returned. If both {var} and {expr} are a\nlist, their lengths should be equal. In this case, the first entry in\nthe list {expr} is differentiated with respect to the first entry in\nthe list {var}, the second entry in {expr} is differentiated with\nrespect to the second entry in {var}, and so on.\n\nThe {D} operator returns the original function if $n=0$, a common\nmathematical idiom that simplifies many formulae.\n\n*E.G.\n\n	In> D(x)Sin(x*y)\n	Result: y*Cos(x*y);\n	In> D({x,y,z})Sin(x*y)\n	Result: {y*Cos(x*y),x*Cos(x*y),0};\n	In> D(x,2)Sin(x*y)\n	Result: -Sin(x*y)*y^2;\n	In> D(x){Sin(x),Cos(x)}\n	Result: {Cos(x),-Sin(x)};\n\n*SEE Integrate, Taylor, Diverge, Curl\n";

        String convertedScriptCode = scriptCode;

        //convertedScriptCode = convertedScriptCode.replaceAll("\\$.*\\$", "\b");

        convertedScriptCode = convertedScriptCode.replace("&", "&amp;");
        convertedScriptCode = convertedScriptCode.replace("<", "&lt;");
        convertedScriptCode = convertedScriptCode.replace(">", "&gt;");

        String[] lines = convertedScriptCode.split("\n");

        StringBuilder html = new StringBuilder();

        html.append("<html>\n");

        for (int x = 0; x < lines.length; x++) {
            //foldOutput = foldOutput + lines[x];

            String line = lines[x].trim();

            if (line.startsWith("*CMD")) {
                line = line.substring(line.indexOf(" "), line.length());
                html.append("<h3>\n<hr>" + line + "\n</h3>\n\n");

            } else if (line.startsWith("*STD")) {
                html.append("<h4 align=right>Standard library</h4>\n\n");
            } else if (line.startsWith("*CORE")) {
                html.append("<h4 align=right>Built in function</h4>\n\n");
            } else if (line.startsWith("*CALL")) {
                html.append("<h4> Calling format:\n</h4>\n<table cellpadding=\"0\" width=\"100%\">\n<tr><td width=100% bgcolor=\"#DDDDEE\"><pre>\n");

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
                html.append("<h4>\nParameters:\n</h4>\n");

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
                html.append("<h4>\nDescription:\n</h4>\n");

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
                    //line = applyPre(line);  Removed the [] <pre> symbol replacement because it clashes with normal brackets.



                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("\n");
            } else if (line.startsWith("*E.G.")) {
                html.append("<h4>\nExamples:\n</h4>\n<table cellpadding=\"0\" width=\"100%\">\n<tr><td width=100% bgcolor=\"#DDDDEE\"><pre>");

                while (true) {
                    x++;

                    if (x == lines.length) {
                        //This code exits the converter if it is the last *XXX command in the document.
                        break;
                    }//end if.

                    line = lines[x];

                    line = line.replace("/%", "%");

                    if (line.startsWith("*")) {
                        x--;
                        break;
                    }
                    if (line.equalsIgnoreCase("")) {
                        html.append("\n");
                        continue;
                    }

                    line = line.replaceAll(">", "&gt;");


                    html.append(line);
                    html.append("\n");
                }//end while.

                html.append("\n</pre></tr>\n</table>\n<p>\n");
            } else if (line.startsWith("*SEE")) {

                //line = lines[x].trim();
                line = line.substring(4, line.length());
                line = line.replace(" ", "");

                String[] seeFunctions = line.split(",");

                for (String seeFunction : seeFunctions) {
                    seeFunctionsBuilder.append("<a href=\"http://" + seeFunction + "\">" + seeFunction + "</a>, ");
                }

                html.append("<h4>See also:</h4>" + seeFunctionsBuilder.toString() + "\n");

                seeFunctionsBuilder.delete(0, seeFunctionsBuilder.length());

            } else if (line.startsWith("*SOURCE")) {

                html.append("<br /> <h4>Source:</h4>");

                line = line.substring(7, line.length());
                line = line.trim();
                
                if(line.endsWith(".mpw"))
                {
                        html.append(line);
                	html.append("<br /> <a href=\"file://" + line + "\">View source code</a>\n");
                }
                else
                {
                	html.append(
                	"This is a built-in function and its source file is written in Java. <br />" + 
			"The path to the Java source code for this function is: <br />" + line.substring(1, line.length()) + "<br /><br />" +
			"The source code can be browsed on the MathPiper project site at: <br />" +
			"http://code.google.com/p/mathpiper/source/browse/");
			
                }//end else.
                
            }//end else/if.



        }//end for.

        html.append("</html>\n");
        
        
        Pattern p = Pattern.compile("\\$.*\\$");
        Matcher originalCodeMatcher = p.matcher(scriptCode);
        Matcher htmlMatcher = p.matcher(html); // get a matcher object
        StringBuffer convertedCodeStringBuffer = new StringBuffer();
        
        while(htmlMatcher.find()){
            originalCodeMatcher.find();
            String latexCode = originalCodeMatcher.group();
            latexCode = latexCode.replace("\\", "\\\\");
            latexCode = latexCode.replace("$", "\\$");
            htmlMatcher.appendReplacement(convertedCodeStringBuffer,latexCode);
        }
        htmlMatcher.appendTail(convertedCodeStringBuffer);

        String convertedCode = convertedCodeStringBuffer.toString();

        return convertedCode;

    }//end method.


    public void hyperlinkUpdate(HyperlinkEvent event) {
        //System.out.println(event.toString());
        URL url = event.getURL();
//System.out.println("YYYPiperDocsYYY: " + url.getPath() + " reference: " + url.getRef() + " query: " + url.getQuery() );
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

            String functionName = "";

            if (url != null) {
            	    
            //System.out.println("XXXXX: " + url);
            	        
                String protocol = url.getProtocol();
                
                if (protocol.equalsIgnoreCase("file")) {
                    String mpwFilePath = url.getFile();
                    
                    if(mpwFilePath.endsWith(".mpw"))
                    {

			    java.io.InputStream inputStream = FunctionTreePanel.class.getResourceAsStream(mpwFilePath);
	
			    if (inputStream != null) //File is on the classpath.
			    {
	
				try{
				    String mpwFileText = convertStreamToString(inputStream);
				    
				    HelpEvent helpEvent = new HelpEvent(mpwFilePath, mpwFileText);
	
				    this.notifyListeners(helpEvent);
	
				    inputStream.close();
				}
				catch(Exception e)
				{
				    System.out.println(e.getMessage());
				}
	
			    }//end if.
                    
		    }else
		    {
		    	   //.java file.
		           //HelpEvent helpEvent = new HelpEvent(mpwFilePath, null);
		           //this.notifyListeners(helpEvent);
		    }

                } else {
                    String urlString = url.toString();
                    functionName = urlString.substring(7, urlString.length());
                }
                
                
                
                
            } else {
                //Hack to get around problem of null url object being returned for the := operator.
                if (event.getDescription().contains("http://:=")) {
                    functionName = ":=";
                }
            }
            
            //System.out.println(functionName);
            viewFunction(functionName, true);



        }//end if.  + getRef())

    }//end method.

    private int pageIndex = -1;


    private void setPage(String functionName, String html, boolean save) {
        editorPane.setText(html);

        //HTMLEditorKit editorKit = (HTMLEditorKit) editorPane.getEditorKit();
        //Style style = editorKit.getStyleSheet().getRule("object");


        //forward button logic.
        if (pageIndex + 1 == pageList.size()) {
            toolPanel.forwardButtonEnabled(false);
        } else {
            toolPanel.forwardButtonEnabled(true);
        }//end else.




        if (save) {
            if (pageIndex >= 0 && functionName == pageList.get(pageIndex)) {
                //System.out.println("VVVVVM Same URL");
                return;
            }//end if.
            if (pageIndex + 1 != pageList.size()) {
                pageList = pageList.subList(0, pageIndex + 1);
                toolPanel.forwardButtonEnabled(false);
            }//end if.


            pageList.add(functionName);
            pageIndex++;
        }//end if.


        //back button logic.
        if (pageIndex <= 0) {
            toolPanel.backButtonEnabled(false);
        } else {
            toolPanel.backButtonEnabled(true);
        }//end else.

        //System.out.println("TTTTT " + pageList );


        final JScrollBar verticalScrollBar = docsScrollPane.getVerticalScrollBar();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                verticalScrollBar.setValue(verticalScrollBar.getMinimum());
            }

        });


    }//end method.


    private String getSource() {
        Interpreter interpreter = Interpreters.getSynchronousInterpreter();
        //item = list.getSelectedValue();

        EvaluationResponse evaluationResponse = interpreter.evaluate("FindFunction(\"" + selectedFunctionName + "\");");
        String location = evaluationResponse.getResult();

        return location;
    }//end method.


    private void collapse() {
        functionsTree.collapseAll();
    }//end method.


    private void back() {
        if (pageIndex != 0) {
            String functionName = (String) pageList.get(--pageIndex);

            if (functionName.equals("HomePage")) {
                home();
            } else {
                this.viewFunction(functionName, false);
            }

        }//end if.
    }//end method.


    private void forward() {
        String functionName = (String) pageList.get(++pageIndex);

        if (functionName.equals("HomePage")) {
            home();
        } else {
            this.viewFunction(functionName, false);
        }

    }//end method.


    private void home() {
        //toolPanel.sourceButtonEnabled(false);
        
        String homePageText = "<html><h1><font color=\"red\">MathPiper Function Documentation.</font></h1> \n"
         + "<br />\n"
         + "Open the tree nodes to the left to access the function documentation. </html>\n";


        setPage("HomePage", homePageText, true);
    }//end method.


        public String convertStreamToString(InputStream inputStream) throws IOException {

        if (inputStream != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } finally {
                inputStream.close();
            }
            return stringBuilder.toString();
        } else {
            return "";
        }
    }//end method.



    public void addHelpListener(HelpListener listener) {
        helpListeners.add(listener);
    }

    public void removeHelpListener(HelpListener listener) {
        helpListeners.remove(listener);
    }

    protected void notifyListeners(HelpEvent helpEvent) {

        for (HelpListener listener : helpListeners) {
            listener.helpEvent(helpEvent);
        }//end for.

    }//end method.



    public JPanel getToolPanel() {
        return toolPanel;
    }//end method.

    private class ToolPanel extends JPanel implements ItemListener {

        private JLabel label;
        //private JButton sourceButton;
        private JButton backButton;
        private JButton forwardButton;
        private JButton homeButton;
        private JButton fontSizeIncreaseButton;
        private JButton fontSizeDecreaseButton;
        private JCheckBox showExperimentalFunctionsCheckBox;
        private JCheckBox showPrivateFunctionsCheckBox;
        private boolean isShowPrivateFunctions = false;


        private ToolPanel() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));



            //View source button.
            /*sourceButton = new javax.swing.JButton("Source");
            sourceButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    //source();
                }

            });
            sourceButton.setEnabled(false);
            sourceButton.setToolTipText("View script source.");
            add(sourceButton);*/


            showExperimentalFunctionsCheckBox = new JCheckBox("Experimental");
            showExperimentalFunctionsCheckBox.setSelected(true);
            showExperimentalFunctionsCheckBox.addItemListener(this);
            add(showExperimentalFunctionsCheckBox);

            showPrivateFunctionsCheckBox = new JCheckBox("Private");
            showPrivateFunctionsCheckBox.setSelected(false);
            showPrivateFunctionsCheckBox.addItemListener(this);
            add(showPrivateFunctionsCheckBox);


            add(Box.createGlue());

            //fontSize increase button.
            fontSizeIncreaseButton = new javax.swing.JButton("Font+");
            fontSizeIncreaseButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {

                    Font font = editorPane.getFont();

                    int fontSize = font.getSize();
                    fontSize = fontSize += 2;

                    editorPane.setFont(font.deriveFont(fontSize));

                    System.out.println("Increasing font size.");


                    //editorPane.

                }//end method.

            });
            fontSizeIncreaseButton.setEnabled(true);
            //add(fontSizeIncreaseButton);



            //back button.
            backButton = new javax.swing.JButton("Back");
            backButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    back();
                }

            });
            backButton.setEnabled(false);
            add(backButton);


            //forward button.
            forwardButton = new javax.swing.JButton("Forward");
            forwardButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    forward();
                }

            });
            forwardButton.setEnabled(false);
            add(forwardButton);


            //Home button.
            homeButton = new javax.swing.JButton("Home");
            homeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    home();
                }

            });
            homeButton.setEnabled(true);
            add(homeButton);




        }//end constructor.


        //public void sourceButtonEnabled(Boolean state) {
        //    sourceButton.setEnabled(state);
        //}//end method.


        public void backButtonEnabled(Boolean state) {
            backButton.setEnabled(state);
        }//end method.


        public void forwardButtonEnabled(Boolean state) {
            forwardButton.setEnabled(state);
        }//end method.


        public void itemStateChanged(ItemEvent ie) {
            Object source = ie.getSource();

            if (source == showPrivateFunctionsCheckBox || source == showExperimentalFunctionsCheckBox) {
                if (source == showPrivateFunctionsCheckBox) {

                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        showPrivateFunctions = true;
                    } else {
                        showPrivateFunctions = false;
                    }//end if/else.

                } else if (source == showExperimentalFunctionsCheckBox) {

                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        showExperimentalFunctions = true;
                    } else {
                        showExperimentalFunctions = false;
                    }//end if/else.


                }//end if.

                treePanel.removeAll();
                createTree();
                treePanel.add(functionsTree);
                treeViewScrollPane.revalidate();

            }//End if.


        }//end method.

    }//end class.

    private class SearchPanel extends JPanel implements ActionListener, ListSelectionListener {

        private JTextField searchTextField;
        private Vector hits = new Vector();
        private JScrollPane listScroller;
        private JList list;


        public SearchPanel() {
            this.setLayout(new BorderLayout());

            searchTextField = new JTextField();

            searchTextField.setActionCommand("search");

            searchTextField.addActionListener(this);

            this.add(searchTextField, BorderLayout.NORTH);


            hits.add("Enter a search term or phrase into the");
            hits.add("above text field and press <Enter> to search.");
            hits.add(" ");
            hits.add("Select a returned function to view its documentation.");

            list = new JList(hits);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            list.addListSelectionListener(this);
            list.setVisibleRowCount(-1);

            listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            this.add(listScroller);


        }//end constructor.


        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("search")) {
                JTextField textField = (JTextField) e.getSource();

                String searchString = textField.getText();

                searchString = searchString.toLowerCase();

                hits.removeAllElements();

                int index = 0;

                //Search user functions.
                hits.add("USER FUNCTIONS:");

                for (index = 0; index < userFunctionsData.length; index++) {
                    if (userFunctionsData[index][0].toLowerCase().contains(searchString) || userFunctionsData[index][2].toLowerCase().contains(searchString)) {
                        hits.add(userFunctionsData[index][0] + " -- " + userFunctionsData[index][2] + ".");
                    }
                }//end for.


                //Search programmer functions.
                hits.add(" ");
                hits.add("PROGRAMMER FUNCTIONS:");

                for (index = 0; index < programmerFunctionsData.length; index++) {
                    if (programmerFunctionsData[index][0].toLowerCase().contains(searchString) || programmerFunctionsData[index][2].toLowerCase().contains(searchString)) {
                        hits.add(programmerFunctionsData[index][0] + " -- " + programmerFunctionsData[index][2] + ".");
                    }
                }//end for.


                //Search operators.
                hits.add(" ");
                hits.add("OPERATORS:");

                for (index = 0; index < operatorsData.length; index++) {
                    if (operatorsData[index][0].toLowerCase().contains(searchString) || operatorsData[index][2].toLowerCase().contains(searchString)) {
                        hits.add(operatorsData[index][0] + " -- " + operatorsData[index][2] + ".");
                    }
                }//end for.


                list.setListData(hits);

                listScroller.revalidate();

            }//end if.

        }//end method.


        public void valueChanged(ListSelectionEvent e) {
            JList list = (JList) e.getSource();
            if (!list.getSelectionModel().getValueIsAdjusting()) {
                String function = (String) list.getSelectedValue();
                if (function != null) {
                    String functionName = function.split("-")[0].trim();
                    viewFunction(functionName, true);
                }
            }
        }//end method.


    }//end class.


    public static void main(String[] args) {

        JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        FunctionTreePanel functionTreePanel = null;

        try {

            functionTreePanel = new FunctionTreePanel();

            Container contentPane = frame.getContentPane();
            contentPane.add(functionTreePanel.getToolPanel(), BorderLayout.NORTH);
            contentPane.add(functionTreePanel, BorderLayout.CENTER);

            frame.pack();

            frame.setTitle("MathPiper Help");
            frame.setSize(new Dimension(700, 700));
            //frame.setResizable(false);
            frame.setPreferredSize(new Dimension(700, 700));
            frame.setLocationRelativeTo(null); // added

            frame.setVisible(true);

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }



    }//end main.
}
