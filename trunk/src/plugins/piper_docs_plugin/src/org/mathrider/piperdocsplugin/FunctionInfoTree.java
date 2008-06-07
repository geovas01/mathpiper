package org.mathrider.piperdocsplugin;

import javax.swing.JTree;
import javax.swing.tree.*;

public class FunctionInfoTree extends JTree
{
	public FunctionInfoTree(DefaultMutableTreeNode node)
	{
		super(node);
	}
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
	
}//end class

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
