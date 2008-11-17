/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTree;
import org.mathpiper.lisp.Environment;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 */
public class EnvironmentViewer
{

    public EnvironmentViewer()
    {
        super();
    }

    public JTree getGlobalStateViewer(Environment aEnvrionment)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        JTree.DynamicUtilTreeNode.createChildren(root, aEnvrionment.getIGlobalState().getIHashtable());
        return new JTree(root);
    }

    public JFrame getViewerFrame(Environment aEnvironment, JTable table)
    {

        JFrame frame = new javax.swing.JFrame();



        Container contentPane = frame.getContentPane();
        
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
//frame.setAlwaysOnTop(true);
        frame.setTitle("MathPiper Environment");
        frame.setSize(new Dimension(250, 200));
        //frame.setResizable(false);
        frame.setPreferredSize(new Dimension(250, 200));
        frame.setLocationRelativeTo(null); // added

        frame.show();
        
        return frame;
    }
    
    
    public JTable getTableViewer(Environment environment) 
    {
        JTable m_table = new JTable();
        final java.util.Map m_hash = (java.util.Map) environment.getIUserFunctions().getIHashtable();

        m_table.setModel(new AbstractTableModel(){
        private static final long serialVersionUID = 1L;

        public int getColumnCount() {
                return 2;
        }

        public int getRowCount() {
                return m_hash.size();
        }

        public String getColumnName(int column) {
                if (column == 0) {
                        return "Variables";
                } else {
                        return "Values";
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
                
                return m_table;
        }


}//end class.