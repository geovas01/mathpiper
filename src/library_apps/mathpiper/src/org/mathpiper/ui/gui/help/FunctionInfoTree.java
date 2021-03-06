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
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
package org.mathpiper.ui.gui.help;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.*;


public class FunctionInfoTree extends JTree {

    private DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                    row, hasFocus);

            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode DefaultMutableTreeNode = (DefaultMutableTreeNode) value;
                Object userObject = DefaultMutableTreeNode.getUserObject();

                if (!(userObject instanceof String)) {

                    FunctionInfo functionInfo = (FunctionInfo) userObject;

                    String access = functionInfo.getAccess();

                    if (access.equals("private")) {
                        //this.setTextSelectionColor(Color.RED);
                        //this.setTextNonSelectionColor(Color.RED);
                        this.setForeground(Color.RED);
                    }
                    else if (access.equals("experimental")) {
                        //this.setTextSelectionColor(Color.RED);
                        //this.setTextNonSelectionColor(Color.RED);
                        this.setForeground(new Color(155,0,153));
                    } else {
                        //this.setTextSelectionColor(Color.BLACK);
                        //this.setTextNonSelectionColor(Color.BLACK);
                        this.setForeground(Color.BLACK);
                    }

                }//end if.
            }//end if.


            return this;
        }


    };


    public FunctionInfoTree() {
        super();
        this.setCellRenderer(renderer);
    }


    public FunctionInfoTree(DefaultMutableTreeNode node) {
        super(node);
        this.setCellRenderer(renderer);
    }


    public FunctionInfoTree(TreeModel model) {
        super(model);
        this.setCellRenderer(renderer);
    }


    public void setNode(DefaultMutableTreeNode node) {
        setModel(new DefaultTreeModel(node));
    }//end method.


    public String getToolTipText(java.awt.event.MouseEvent e) {
        DefaultMutableTreeNode node = null;
        FunctionInfo functionInfo = null;
        String tip = null;
        TreePath path = getPathForLocation(e.getX(), e.getY());

        if (path != null) {
            node = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object object = node.getUserObject();
            if(object instanceof FunctionInfo)
            {
                functionInfo = (FunctionInfo) object;
                tip = functionInfo.getDescription();
            }
            else
            {
                tip = (String) object;
            }//end if/else.
            
        }//end if.

        return tip == null ? null : tip;

    }//end method.


    // If expand is true, expands all nodes in the tree.
    // Otherwise, collapses all nodes in the tree.
    public void collapseAll() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getModel().getRoot();

        // Traverse tree from root
        expandAll(this, new TreePath(root), false);
    }


    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (java.util.Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }


}//end class

