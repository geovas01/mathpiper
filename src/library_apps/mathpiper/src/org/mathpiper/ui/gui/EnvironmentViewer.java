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
package org.mathpiper.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.mathpiper.lisp.Environment;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import org.mathpiper.lisp.GlobalVariable;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.userfunctions.BranchParameter;
import org.mathpiper.lisp.userfunctions.BranchRuleBase;
import org.mathpiper.lisp.userfunctions.MultipleArityUserFunction;
import org.mathpiper.lisp.userfunctions.UserFunction;
import org.mathpiper.ui.gui.MultiSplitLayout.Divider;
import org.mathpiper.ui.gui.MultiSplitLayout.Leaf;
import org.mathpiper.ui.gui.MultiSplitLayout.Split;

/**
 * Provides a GUI viewer for a runtime environment.
 */
public class EnvironmentViewer implements ActionListener {

    private JTextArea textArea = new JTextArea(4, 4);
    private List tables = new ArrayList();
    private JFrame frame;

    public EnvironmentViewer() {
        super();
    }

    public JFrame getViewerFrame(Environment aEnvironment) {

        frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //siteTable.getModel().fireTableDataChanged();

        Leaf one = new Leaf("one");
        Leaf two = new Leaf("two");
        Leaf three = new Leaf("three");
        Leaf four = new Leaf("four");

        one.setWeight(0.15);
        two.setWeight(0.28);
        three.setWeight(0.28);
        four.setWeight(0.29);

        List children = Arrays.asList(one, new Divider(), two, new Divider(), three, new Divider(), four);
        MultiSplitLayout.Split modelRoot = new Split();
        modelRoot.setChildren(children);
        MultiSplitPane multiSplitPane = new MultiSplitPane();
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);




        textArea.setEditable(false);
        JScrollPane outputPane = new JScrollPane(textArea);


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, multiSplitPane, outputPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);


        Dimension minimumSize = new Dimension(100, 50);
        multiSplitPane.setMinimumSize(minimumSize);
        textArea.setMinimumSize(minimumSize);


        Container contentPane = frame.getContentPane();
        contentPane.add(splitPane);


        //Add global state.
        JTable table = this.getGlobalStateTable(aEnvironment);
        tables.add(table);
        JScrollPane scrollPane = new JScrollPane(table);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "one");

        //Add user functions.
        table = this.getUserFunctionsTable(aEnvironment);
        tables.add(table);
        scrollPane = new JScrollPane(table);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "two");

        //Add builtin functions.
        table = this.getBuiltinFunctionsTable(aEnvironment);
        tables.add(table);
        scrollPane = new JScrollPane(table);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "three");

        //Add tokens.
        table = this.getTokenTable(aEnvironment);
        tables.add(table);
        scrollPane = new JScrollPane(table);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "four");

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        refreshButton.setActionCommand("refresh");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(refreshButton);
        contentPane.add(buttonsPanel, BorderLayout.NORTH);



        frame.pack();
//frame.setAlwaysOnTop(true);
        frame.setTitle("MathPiper Environment");
        frame.setSize(new Dimension(700, 300));
        //frame.setResizable(false);
        frame.setPreferredSize(new Dimension(700, 300));
        frame.setLocationRelativeTo(null); // added

        frame.setVisible(true);

        return frame;
    }

    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();

        if (actionCommand.equalsIgnoreCase("refresh")) {
            Iterator tablesIterator = tables.iterator();
            while (tablesIterator.hasNext()) {
                JTable table = (JTable) tablesIterator.next();
                JScrollPane scrollPane = (JScrollPane) tablesIterator.next();
                AbstractTableModel model = (AbstractTableModel) table.getModel();
                model.fireTableDataChanged();

                SwingUtilities.updateComponentTreeUI(scrollPane);


            }
        }

    }

    /**
     * Returns a GUI table which contains a sorted list of the user functions.
     * 
     * @param aEnvironment the environment to view
     * @return a JTable which contains the user function names
     */
    public JTable getUserFunctionsTable(Environment aEnvironment) {
        JTable table = new JTable();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new FunctionListener(table, aEnvironment));

        final java.util.Map map = (java.util.Map) aEnvironment.getUserFunctions().getMap();

        table.setModel(new AbstractTableModel() {

            private static final long serialVersionUID = 1L;

            public int getColumnCount() {
                return 1;
            }

            public int getRowCount() {
                return map.size();
            }

            public String getColumnName(int column) {
                if (column == 0) {
                    return "User Functions";
                } else {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return getKey(rowIndex);
                } else {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index) {
                String retval = "";
                ArrayList keyList = new ArrayList(map.keySet());
                Collections.sort(keyList);

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }
        });

        return table;
    }//end class

    /**
     * Returns a GUI table which contains a sorted list of the builtin functions.
     * 
     * @param aEnvironment the environment to view
     * @return a JTable which contains the built in function names
     */
    public JTable getBuiltinFunctionsTable(Environment aEnvironment) {
        JTable table = new JTable();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // table.getSelectionModel().addListSelectionListener(new FunctionListener(table, aEnvironment));

        final java.util.Map map = (java.util.Map) aEnvironment.getBuiltinFunctions().getMap();

        table.setModel(new AbstractTableModel() {

            private static final long serialVersionUID = 1L;

            public int getColumnCount() {
                return 1;
            }

            public int getRowCount() {
                return map.size();
            }

            public String getColumnName(int column) {
                if (column == 0) {
                    return "Built-In Functions";
                } else {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return getKey(rowIndex);
                } else {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index) {
                String retval = "";
                ArrayList keyList = new ArrayList(map.keySet());
                Collections.sort(keyList);

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }
        });

        return table;
    }//end class

    /**
     * Returns a GUI table which contains a sorted list of the global variables.
     * 
     * @param aEnvironment the environment to view
     * @return a JTable which contains the global variable names
     */
    public JTable getGlobalStateTable(Environment aEnvironment) {
        JTable table = new JTable();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new GlobalVariableListener(table, aEnvironment));

        final java.util.Map map = (java.util.Map) aEnvironment.getGlobalState().getMap();

        table.setModel(new AbstractTableModel() {

            private static final long serialVersionUID = 1L;

            public int getColumnCount() {
                return 2;
            }

            public int getRowCount() {
                return map.size();
            }

            public String getColumnName(int column) {
                if (column == 0) {
                    return "Global Variables";
                } else {
                    return "Values";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return getKey(rowIndex);
                } else {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index) {
                String retval = "";
                ArrayList keyList = new ArrayList(map.keySet());
                Collections.sort(keyList);

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }
        });

        return table;
    }//end method.

    /**
     * Returns a GUI table which contains a sorted list of the tokens.
     * 
     * @param aEnvironment the environment to view
     * @return a JTable which contains the token names
     */
    public JTable getTokenTable(Environment aEnvironment) {
        JTable table = new JTable();
        final java.util.Map m_hash = (java.util.Map) aEnvironment.getTokenHash().getMap();

        table.setModel(new AbstractTableModel() {

            private static final long serialVersionUID = 1L;

            public int getColumnCount() {
                return 1;
            }

            public int getRowCount() {
                return m_hash.size();
            }

            public String getColumnName(int column) {
                if (column == 0) {
                    return "Tokens";
                } else {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return getKey(rowIndex);
                } else {
                    return m_hash.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index) {
                String retval = "";
                ArrayList keyList = new ArrayList(m_hash.keySet());
                Collections.sort(keyList);

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }
        });

        return table;
    }//end method.

    private class GlobalVariableListener implements ListSelectionListener {

        private JTable table;
        private Environment iEnvironment;

        public GlobalVariableListener(JTable table, Environment aEnvironment) {
            this.table = table;
            this.iEnvironment = aEnvironment;
        }

        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }

            int row = table.getSelectionModel().getLeadSelectionIndex();

            String name = (String) table.getValueAt(row, 0);
            GlobalVariable o = (GlobalVariable) table.getValueAt(row, 1);
            try {
                String data = UtilityFunctions.printExpression(o.getValue(), iEnvironment, 0);
                //System.out.println(data);
                textArea.append(name + ": " + data + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            } catch (Exception ex) {
                System.out.print(ex);
            }

        }
    }//end class.

    private class FunctionListener implements ListSelectionListener {

        private JTable table;
        private Environment iEnvironment;

        public FunctionListener(JTable table, Environment aEnvironment) {
            this.table = table;
            this.iEnvironment = aEnvironment;
        }

        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }

            int row = table.getSelectionModel().getLeadSelectionIndex();
            String name = (String) table.getValueAt(row, 0);
            textArea.append("-------------------------------------------------------------------------------------------------------------\n");
            textArea.append(name + ": \n");

            MultipleArityUserFunction multipleArityUserfunction = (MultipleArityUserFunction) table.getModel().getValueAt(row, 1);

            Iterator multipleArityUserFunctionIterator = multipleArityUserfunction.getFunctions();

            while (multipleArityUserFunctionIterator.hasNext()) {
                UserFunction userFunction = (UserFunction) multipleArityUserFunctionIterator.next();
                Iterator rulesIterator = userFunction.getRules();

                while (rulesIterator.hasNext()) {

                    BranchRuleBase branchRuleBase = (BranchRuleBase) rulesIterator.next();

                    try {
                        int precedence = branchRuleBase.getPrecedence();
                        ConsPointer predicatePointer1 = branchRuleBase.getPredicate();
                        String predicate = "";
                        if (predicatePointer1.toString().equalsIgnoreCase("Empty.")) {
                            predicate = "NoPredicate";
                        } else {
                            predicate = UtilityFunctions.printExpression(predicatePointer1, iEnvironment, 0);
                        }
                        if (predicate.equalsIgnoreCase("\"Pattern\"")) {
                            Iterator patternPredicatesIterator = branchRuleBase.getPredicates();
                            predicate += ":";
                            while (patternPredicatesIterator.hasNext()) {
                                ConsPointer predicatePointer = (ConsPointer) patternPredicatesIterator.next();
                                String patternPredicate = UtilityFunctions.printExpression(predicatePointer, iEnvironment, 0);
                                predicate += patternPredicate + "__";
                            }

                        }

                        Iterator paremetersIterator = userFunction.getParameters();
                        String parameters = "";
                        boolean isHold = false;
                        while(paremetersIterator.hasNext())
                        {
                            BranchParameter branchParameter = (BranchParameter) paremetersIterator.next();
                            parameters = branchParameter.getParameter();
                            isHold = branchParameter.isHold();
                            parameters += parameters + ":" + isHold;
                        }

                        String body = UtilityFunctions.printExpression(branchRuleBase.getBody(), iEnvironment, 0);
                        //System.out.println(data);
                        textArea.append("    " + precedence + "__" + predicate + "__" + parameters + "__" + body + "\n\n");

                        textArea.setCaretPosition(textArea.getDocument().getLength());
                    } catch (Exception ex) {
                        System.out.print(ex);
                    }

                }//end while.

            }//end while.

        }
    }//end class.
}//end class.