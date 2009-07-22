
package org.mathpiper.ui.gui.help;

import javax.swing.tree.*;


public class FunctionInfoTreeModel extends DefaultTreeModel {

  protected boolean hidePrivatefunctions;

  public FunctionInfoTreeModel(TreeNode root) {
    this(root, false);
  }

  public FunctionInfoTreeModel(TreeNode root, boolean asksAllowsChildren) {
    this(root, false, false);
  }

  public FunctionInfoTreeModel(TreeNode root, boolean asksAllowsChildren
			    ,boolean hidePrivateFunctions) {
    super(root, asksAllowsChildren);
    this.hidePrivatefunctions = hidePrivateFunctions;
  }

  public void activateFilter(boolean newValue) {
    hidePrivatefunctions = newValue;
  }

  public boolean isActivatedFilter() {
    return hidePrivatefunctions;
  }

  public Object getChild(Object parent, int index) {
    if (hidePrivatefunctions) {
      if (parent instanceof FunctionInfoNode) {
	return ((FunctionInfoNode)parent).getChildAt(index,hidePrivatefunctions);
      }
    }
    return ((TreeNode)parent).getChildAt(index);
  }

  public int getChildCount(Object parent) {
    if (hidePrivatefunctions) {
      if (parent instanceof FunctionInfoNode) {
	return ((FunctionInfoNode)parent).getChildCount(hidePrivatefunctions);
      }
    }
    return ((TreeNode)parent).getChildCount();
  }

}
