

package org.mathpiper.ui.gui.help;

import java.util.*;
import javax.swing.tree.*;


public class FunctionInfoNode extends DefaultMutableTreeNode  {

  protected boolean isVisible;

  public FunctionInfoNode() {
    this(null);
  }

  public FunctionInfoNode(Object userObject) {
    this(userObject, true, true);
  }

  public FunctionInfoNode(Object userObject, boolean allowsChildren
		       , boolean isVisible) {
    super(userObject, allowsChildren);
    this.isVisible = isVisible;
  }


  public TreeNode getChildAt(int index,boolean isVisible) {
    if (! isVisible) {
      return super.getChildAt(index);
    }
    if (children == null) {
      throw new ArrayIndexOutOfBoundsException("node has no children");
    }

    int realIndex    = -1;
    int visibleIndex = -1;
    Enumeration enumeration = children.elements();
    while (enumeration.hasMoreElements()) {
      FunctionInfoNode node = (FunctionInfoNode)enumeration.nextElement();
      if (node.isVisible()) {
	visibleIndex++;
      }
      realIndex++;
      if (visibleIndex == index) {
	return (TreeNode)children.elementAt(realIndex);
      }
    }

    throw new ArrayIndexOutOfBoundsException("index unmatched");

  }

  public int getChildCount(boolean filterIsActive) {
    if (! filterIsActive) {
      return super.getChildCount();
    }
    if (children == null) {
      return 0;
    }

    int count = 0;
    Enumeration enumeration = children.elements();
    while (enumeration.hasMoreElements()) {
      FunctionInfoNode node = (FunctionInfoNode)enumeration.nextElement();
      if (node.isVisible()) {
	count++;
      }
    }

    return count;
  }

  public void setVisible(boolean visible) {
    this.isVisible = visible;
  }

  public boolean isVisible() {
    return isVisible;
  }

}

