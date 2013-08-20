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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import org.mathpiper.lisp.variables.GlobalVariable;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.rulebases.Rule;
import org.mathpiper.lisp.rulebases.MultipleArityRulebase;
import org.mathpiper.lisp.rulebases.SingleArityRulebase;
import org.mathpiper.ui.gui.MultiSplitLayout.Divider;
import org.mathpiper.ui.gui.MultiSplitLayout.Leaf;
import org.mathpiper.ui.gui.MultiSplitLayout.Split;

/**
 * Provides a GUI viewer for a runtime environment.
 */
public class EnvironmentViewer implements ActionListener {

    private JTextArea textArea = new JTextArea(4, 8);
    private List tables = new ArrayList();
    private JFrame frame;
    private JPopupMenu popupMenu = new JPopupMenu();


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

        one.setWeight(0.25);
        two.setWeight(0.35);
        three.setWeight(0.40);

        List children = Arrays.asList(one, new Divider(), two, new Divider(), three);
        MultiSplitLayout.Split modelRoot = new Split();
        modelRoot.setChildren(children);
        MultiSplitPane multiSplitPane = new MultiSplitPane();
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);




        textArea.setEditable(false);
        JScrollPane outputPane = new JScrollPane(textArea);
        outputPane.getVerticalScrollBar().setUnitIncrement(16);


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, multiSplitPane, outputPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);


        Dimension minimumSize = new Dimension(100, 100);
        multiSplitPane.setMinimumSize(minimumSize);
        textArea.setMinimumSize(minimumSize);


        Container contentPane = frame.getContentPane();
        contentPane.add(splitPane);





        //Add global state.
        JTable table = this.getGlobalStateTable(aEnvironment, true);
        table.getSelectionModel().addListSelectionListener(new GlobalVariableListener(table, aEnvironment));
        tables.add(table);
        JScrollPane scrollPane = new JScrollPane(table);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "one");

        //Add user functions.
        table = this.getUserFunctionsTable(aEnvironment);
        tables.add(table);
        scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "two");

        //Add builtin functions.
        table = this.getBuiltinFunctionsTable(aEnvironment);
        tables.add(table);
        scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tables.add(scrollPane);
        multiSplitPane.add(scrollPane, "three");



        JPanel buttonsPanel = new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        refreshButton.setActionCommand("refresh");
        buttonsPanel.add(refreshButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clear");
        buttonsPanel.add(clearButton);

        contentPane.add(buttonsPanel, BorderLayout.NORTH);




        frame.pack();
//frame.setAlwaysOnTop(true);
        frame.setTitle("MathPiper Environment");
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setLocationRelativeTo(null); // added

        frame.setVisible(true);

        return frame;
    }


    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();

        if (actionCommand.equalsIgnoreCase("refresh")) {
            this.refresh();
        } else if (actionCommand.equalsIgnoreCase("clear")) {
            textArea.setText("");
        }

    }//end method.


    private void refresh() {
        Iterator tablesIterator = tables.iterator();
        while (tablesIterator.hasNext()) {
            JTable table = (JTable) tablesIterator.next();
            JScrollPane scrollPane = (JScrollPane) tablesIterator.next();
            //AbstractTableModel model = (AbstractTableModel) table.getModel();
            //model.fireTableDataChanged();

            SwingUtilities.updateComponentTreeUI(scrollPane);
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

        final Map map = aEnvironment.iUserRulebases;

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
                Collections.sort(keyList, new Comparator<String>() {

                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });//end method.

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }

        });

        table.addMouseListener(new MouseAdapter() {

            private void maybeShowPopup(MouseEvent e) {
                JTable jTable = (JTable) e.getSource();

                if (e.isPopupTrigger() && jTable.isEnabled()) {
                    Point p = new Point(e.getX(), e.getY());
                    int col = jTable.columnAtPoint(p);
                    int row = jTable.rowAtPoint(p);

                    // translate table index to model index
                    int mcol = jTable.getColumn(
                            jTable.getColumnName(col)).getModelIndex();

                    if (row >= 0 && row < jTable.getRowCount()) {
                        cancelCellEditing(jTable);

                        // create popup menu...
                        JPopupMenu contextMenu = createContextMenu(row, mcol, jTable, map, this);

                        // ... and show it
                        if (contextMenu != null
                                && contextMenu.getComponentCount() > 0) {
                            contextMenu.show(jTable, p.x, p.y);
                        }
                    }
                }
            }


            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }


            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
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
        table.getSelectionModel().addListSelectionListener(new DummyListener(table, aEnvironment));

        final Map map =  aEnvironment.iBuiltinFunctions;

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
                Collections.sort(keyList, new Comparator<String>() {

                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });//end method.

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
    public static JTable getGlobalStateTable(Environment aEnvironment, boolean showAll) {
        JTable table = new JTable();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
	Map<String, GlobalVariable> globalState = (Map<String, GlobalVariable>) aEnvironment
		.getGlobalState();

	java.util.Set<String> variablesSet = globalState.keySet();

	Map<String,GlobalVariable> userVariablesMap = new HashMap<String,GlobalVariable>();

	for (String key : variablesSet) {
	    if (!key.contains("$")
		    && !key.equals("I")
		    && !key.equals("#")
		    && ((GlobalVariable) globalState.get(key)).iConstant == false) {
		
		
		GlobalVariable globalVariable = globalState.get(key);


		userVariablesMap.put(key, globalVariable);
	    }

	}

 
        final Map map = (showAll) ?aEnvironment.iGlobalState :userVariablesMap;
        
        final Environment finalEnvironment = aEnvironment;
        

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
                    return "Name";
                } else {
                    return "Value";
                }
            }


            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return getKey(rowIndex);
                } else {
                    GlobalVariable globalVariable = (GlobalVariable) map.get(getKey(rowIndex));
                	    
            	    Object value = globalVariable.iValue;
    		
    		    if(value instanceof Cons)
    		    {
    		        try {
			    value = Utility.printMathPiperExpression(-1,  ((Cons) globalVariable.iValue), finalEnvironment, 0);
			} catch (Throwable e) {
			    e.printStackTrace();
			}
    		    }
    		
                    return value;
                } // if-else

            }


            private String getKey(int a_index) {
                String retval = "";
                ArrayList keyList = new ArrayList(map.keySet());
                Collections.sort(keyList, new Comparator<String>() {

                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });//end method.

                // for (int i = 0; i < a_index + 1; i++) {
                //         retval = e.next();
                // } // for
                retval = (String) keyList.get(a_index);

                return retval;
            }

        });


        table.addMouseListener(new MouseAdapter() {

            private void maybeShowPopup(MouseEvent e) {
                JTable jTable = (JTable) e.getSource();

                if (e.isPopupTrigger() && jTable.isEnabled()) {
                    Point p = new Point(e.getX(), e.getY());
                    int col = jTable.columnAtPoint(p);
                    int row = jTable.rowAtPoint(p);

                    // translate table index to model index
                    int mcol = jTable.getColumn(
                            jTable.getColumnName(col)).getModelIndex();

                    if (row >= 0 && row < jTable.getRowCount()) {
                        cancelCellEditing(jTable);

                        // create popup menu...
                        JPopupMenu contextMenu = createContextMenu(row, mcol, jTable, map, this);

                        // ... and show it
                        if (contextMenu != null
                                && contextMenu.getComponentCount() > 0) {
                            contextMenu.show(jTable, p.x, p.y);
                        }
                    }
                }
            }


            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }


            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
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

            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if (listSelectionModel.isSelectionEmpty()) {
                return;
            }

            int row = table.getSelectionModel().getLeadSelectionIndex();

            String name = (String) table.getValueAt(row, 0);
            GlobalVariable o = (GlobalVariable) table.getValueAt(row, 1);
            try {
                String data = Utility.printMathPiperExpression(-1, o.getValue(), iEnvironment, 0);
                //System.out.println(data);
                textArea.append(name + ": " + data + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            } catch (Throwable ex) {
                System.out.print(ex);
            }

            table.clearSelection();

        }//end method

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

            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if (listSelectionModel.isSelectionEmpty()) {
                return;
            }

            //System.out.println(event);

            int row = table.getSelectionModel().getLeadSelectionIndex();

            String name = (String) table.getValueAt(row, 0);

            MultipleArityRulebase multipleArityUserfunction = (MultipleArityRulebase) table.getModel().getValueAt(row, 1);

            String defFileLocation = multipleArityUserfunction.iFileLocation;
            String location = "Not specified in a .def file.";
            if (defFileLocation != null) {
                location = defFileLocation;
            }



            textArea.append("-------------------------------------------------------------------------------------------------------------\n");
            textArea.append("Name: " + name + "\n");
            textArea.append("Source Script: " + location + "\n\n");

            Iterator multipleArityUserFunctionIterator = multipleArityUserfunction.getFunctions();

            while (multipleArityUserFunctionIterator.hasNext()) {
                SingleArityRulebase userFunction = (SingleArityRulebase) multipleArityUserFunctionIterator.next();
                Iterator rulesIterator = userFunction.getRules();

                while (rulesIterator.hasNext()) {

                    Rule branchRuleBase = (Rule) rulesIterator.next();

                    String ruleDump = org.mathpiper.lisp.Utility.dumpRule(-1, branchRuleBase, iEnvironment, userFunction);
                    textArea.append(ruleDump);
                    textArea.append("\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength());


                }//end while.

            }//end while.

            table.clearSelection();

        }//end method.

    }//end class.

    private class DummyListener implements ListSelectionListener {

        private JTable table;
        private Environment iEnvironment;


        public DummyListener(JTable table, Environment aEnvironment) {
            this.table = table;
            this.iEnvironment = aEnvironment;
        }


        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if (listSelectionModel.isSelectionEmpty()) {
                return;
            }

            table.clearSelection();

        }//end method.

    } //end class.

    private class FunctionNameComparator implements Comparator<String> {

        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }//end method.

    }//end class.

//============================
    private static final String PROP_CHANGE_QUANTITY = "CHANGE_QUANTITY";


    private static String getClipboardContents(Object requestor) {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(requestor);
        if (t != null) {
            DataFlavor df = DataFlavor.stringFlavor;
            if (df != null) {
                try {
                    Reader r = df.getReaderForText(t);
                    char[] charBuf = new char[512];
                    StringBuffer buf = new StringBuffer();
                    int n;
                    while ((n = r.read(charBuf, 0, charBuf.length)) > 0) {
                        buf.append(charBuf, 0, n);
                    }
                    r.close();
                    return (buf.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedFlavorException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }


    private static boolean isClipboardContainingText(Object requestor) {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(requestor);
        return t != null
                && (t.isDataFlavorSupported(DataFlavor.stringFlavor) || t.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor()));
    }


    private static void setClipboardContents(String s) {
        StringSelection selection = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                selection, selection);
    }

    private JPanel jContentPane;
    private JScrollPane jScrollPane;


    private static void cancelCellEditing(JTable table) {
        CellEditor ce = table.getCellEditor();
        if (ce != null) {
            ce.cancelCellEditing();
        }
    }


    private static JPopupMenu createContextMenu(final int rowIndex, final int columnIndex, JTable table, Map map, Object requester) {

        final Map finalMap = map;

        JPopupMenu contextMenu = new JPopupMenu();

        final JTable jTable = table;

        JMenuItem copyMenu = new JMenuItem();
        copyMenu.setText("Copy");
        copyMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Object value = jTable.getModel().getValueAt(rowIndex,
                        columnIndex);
                setClipboardContents(value == null ? "" : value.toString());
            }

        });
        contextMenu.add(copyMenu);

        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText("Paste");
        final Object requesterFinal = requester;
        if (isClipboardContainingText(requester) && table.getModel().isCellEditable(rowIndex, columnIndex)) {
            pasteMenu.addActionListener(new ActionListener() {
        	
        	

                public void actionPerformed(ActionEvent e) {
                    String value = getClipboardContents(requesterFinal);
                    jTable.getModel().setValueAt(value, rowIndex,
                            columnIndex);
                }

            });
        } else {
            pasteMenu.setEnabled(false);
        }
        contextMenu.add(pasteMenu);


        /*
        JMenuItem unbindMenu = new JMenuItem();
        unbindMenu.setText("Unassign");

        unbindMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Object object = jTable.getModel().getValueAt(rowIndex, columnIndex);

                if (object instanceof String) {
                    String string = (String) object;

                    finalMap.remove(string);

                    //EnvironmentViewer.this.refresh();
                }
                int x = 1;
            }

        });

        contextMenu.add(unbindMenu);
        */



        switch (columnIndex) {
            case 1:
                break;
            case 2:
                break;
            /*
            case 3:
                contextMenu.addSeparator();
                ActionListener changer = new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        JMenuItem sourceItem = (JMenuItem) e.getSource();
                        Object value = sourceItem.getClientProperty(PROP_CHANGE_QUANTITY);
                        if (value instanceof Integer) {
                            Integer changeValue = (Integer) value;
                            Integer currentValue = (Integer) jTable.getModel().getValueAt(rowIndex, columnIndex);
                            jTable.getModel().setValueAt(
                                    new Integer(currentValue.intValue()
                                    + changeValue.intValue()), rowIndex,
                                    columnIndex);
                        }
                    }

                };
                JMenuItem changeItem = new JMenuItem();
                changeItem.setText("+1");
                changeItem.putClientProperty(PROP_CHANGE_QUANTITY,
                        new Integer(1));
                changeItem.addActionListener(changer);
                contextMenu.add(changeItem);

                changeItem = new JMenuItem();
                changeItem.setText("-1");
                changeItem.putClientProperty(PROP_CHANGE_QUANTITY,
                        new Integer(-1));
                changeItem.addActionListener(changer);
                contextMenu.add(changeItem);

                changeItem = new JMenuItem();
                changeItem.setText("+10");
                changeItem.putClientProperty(PROP_CHANGE_QUANTITY,
                        new Integer(10));
                changeItem.addActionListener(changer);
                contextMenu.add(changeItem);

                changeItem = new JMenuItem();
                changeItem.setText("-10");
                changeItem.putClientProperty(PROP_CHANGE_QUANTITY,
                        new Integer(-10));
                changeItem.addActionListener(changer);
                contextMenu.add(changeItem);

                changeItem = null;
                break;
            case 4:
                break;
             */
            default:
                break;
        }
        return contextMenu;
    }


    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJScrollPane(),
                    java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }


    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            //jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

}//end class.

