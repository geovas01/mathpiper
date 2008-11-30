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

import java.awt.Dimension;
import javax.swing.JFrame;
import org.mathpiper.lisp.Environment;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.mathpiper.ui.gui.MultiSplitLayout.Divider;
import org.mathpiper.ui.gui.MultiSplitLayout.Leaf;
import org.mathpiper.ui.gui.MultiSplitLayout.Split;

/**
 * Provides a GUI viewer for a runtime environment.
 */
public class EnvironmentViewer
{

    public EnvironmentViewer()
    {
        super();
    }


    public JFrame getViewerFrame(Environment aEnvironment)
    {

        JFrame frame = new javax.swing.JFrame();
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
        
        Container contentPane = frame.getContentPane();
        contentPane.add(multiSplitPane);


        //Add global state.
        JTable table = this.getGlobalStateTable(aEnvironment);
       // table.setPreferredSize(new Dimension(400,200));
        JScrollPane scrollPane = new JScrollPane(table);
        multiSplitPane.add(scrollPane, "one");

        //Add user functions.
        table = this.getUserFunctionsTable(aEnvironment);
        scrollPane = new JScrollPane(table);
        multiSplitPane.add(scrollPane, "two");

        //Add builtin functions.
        table = this.getBuiltinFunctionsTable(aEnvironment);
        scrollPane = new JScrollPane(table);
        multiSplitPane.add(scrollPane, "three");

        //Add tokens.
        table = this.getTokenTable(aEnvironment);
        scrollPane = new JScrollPane(table);
        multiSplitPane.add(scrollPane, "four");


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

    /**
     * Returns a GUI table which contains a sorted list of the user functions.
     * 
     * @param aEnvironment the environment to view
     * @return
     */
    public JTable getUserFunctionsTable(Environment aEnvironment)
    {
        JTable table = new JTable();
        final java.util.Map map = (java.util.Map) aEnvironment.getUserFunctions().getMap();

        table.setModel(new AbstractTableModel()
        {

            private static final long serialVersionUID = 1L;

            public int getColumnCount()
            {
                return 1;
            }

            public int getRowCount()
            {
                return map.size();
            }

            public String getColumnName(int column)
            {
                if (column == 0)
                {
                    return "User Functions";
                } else
                {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return getKey(rowIndex);
                } else
                {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index)
            {
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
     * @return
     */
    public JTable getBuiltinFunctionsTable(Environment aEnvironment)
    {
        JTable table = new JTable();
        final java.util.Map map = (java.util.Map) aEnvironment.getBuiltinFunctions().getMap();

        table.setModel(new AbstractTableModel()
        {

            private static final long serialVersionUID = 1L;

            public int getColumnCount()
            {
                return 1;
            }

            public int getRowCount()
            {
                return map.size();
            }

            public String getColumnName(int column)
            {
                if (column == 0)
                {
                    return "Built-In Functions";
                } else
                {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return getKey(rowIndex);
                } else
                {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index)
            {
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
     * @return
     */
    public JTable getGlobalStateTable(Environment aEnvironment)
    {
        JTable table = new JTable();
        final java.util.Map map = (java.util.Map) aEnvironment.getGlobalState().getMap();

        table.setModel(new AbstractTableModel()
        {

            private static final long serialVersionUID = 1L;

            public int getColumnCount()
            {
                return 2;
            }

            public int getRowCount()
            {
                return map.size();
            }

            public String getColumnName(int column)
            {
                if (column == 0)
                {
                    return "Global Variables";
                } else
                {
                    return "Values";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return getKey(rowIndex);
                } else
                {
                    return map.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index)
            {
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
     * Returns a GUI table which contains a sorted list of the tokens.
     * 
     * @param aEnvironment the environment to view
     * @return
     */
    public JTable getTokenTable(Environment aEnvironment)
    {
        JTable table = new JTable();
        final java.util.Map m_hash = (java.util.Map) aEnvironment.getTokenHash().getMap();

        table.setModel(new AbstractTableModel()
        {

            private static final long serialVersionUID = 1L;

            public int getColumnCount()
            {
                return 1;
            }

            public int getRowCount()
            {
                return m_hash.size();
            }

            public String getColumnName(int column)
            {
                if (column == 0)
                {
                    return "Tokens";
                } else
                {
                    return "";
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return getKey(rowIndex);
                } else
                {
                    return m_hash.get(getKey(rowIndex));
                } // if-else

            }

            private String getKey(int a_index)
            {
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
    }//end class
}//end class.