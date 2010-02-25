

package org.mathpiper.ui.gui.worksheets;

import java.util.LinkedList;
import java.util.Queue;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;


public class ViewTree {

    private Queue<SymbolBox> queue = new LinkedList();


    public void walkTree(SymbolBox node)
    {
        queue.add(node);

        SymbolBox currentNode;
        StringBuilder treeData = new StringBuilder();

        while(! queue.isEmpty())
        {
            currentNode = queue.remove();

            if(currentNode != null)
            {
                String nodeString = currentNode.toString() + "  ";

                treeData.append(nodeString);

                SymbolBox[] children = currentNode.getChildren();

                if(children != null)
                {
                    treeData.append("\n");

                    for(SymbolBox child:children)
                    {
                        if(child != null)
                        {
                            queue.add(child);
                        }
                    }

                }//end if.
            }
            else
            {
                System.out.print("<Null>");
            }

        }//end while.
        System.out.println(treeData.toString());
    }//end method.

}//end clas.
