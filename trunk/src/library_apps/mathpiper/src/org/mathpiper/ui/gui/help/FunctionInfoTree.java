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

import javax.swing.JTree;
import javax.swing.tree.*;

public class FunctionInfoTree extends JTree
{
	public FunctionInfoTree()
	{
		super();
	}

	public FunctionInfoTree(DefaultMutableTreeNode node)
	{
		super(node);
	}

	public void setNode(DefaultMutableTreeNode node)
	{
		setModel(new DefaultTreeModel(node));
	}//end method.

	public String getToolTipText(java.awt.event.MouseEvent e)
	{
		DefaultMutableTreeNode node = null;
		FunctionInfo functionInfo = null;
		String tip = null;
		TreePath path = getPathForLocation(e.getX(), e.getY());

		if (path != null) {
			node = (DefaultMutableTreeNode)path.getLastPathComponent();
			functionInfo = (FunctionInfo) node.getUserObject();
			tip = functionInfo.getDescription();
		}

		return tip == null ? null : tip;

	}//end method.


	// If expand is true, expands all nodes in the tree.
	// Otherwise, collapses all nodes in the tree.
	public void collapseAll() {
		TreeNode root = (TreeNode)this.getModel().getRoot();

		// Traverse tree from root
		expandAll(this, new TreePath(root), false);
	}
	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode)parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (java.util.Enumeration e=node.children(); e.hasMoreElements(); ) {
				TreeNode n = (TreeNode)e.nextElement();
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

