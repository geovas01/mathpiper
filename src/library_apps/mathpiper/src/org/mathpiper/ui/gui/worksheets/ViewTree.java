

package org.mathpiper.ui.gui.worksheets;

import java.util.LinkedList;
import java.util.Queue;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;


public class ViewTree {

    private Queue<SymbolBox> queue = new LinkedList();


    public void walkTree(SymbolBox node)
    {
        SymbolBox currentNode = node;

        currentNode.setEndOfLevel(true);

        //Walk the leftmost path of the tree and place end of level markers at each node.
        while(currentNode != null)
        {
            SymbolBox[] children = currentNode.getChildren();

            if(children != null)
            {

                currentNode = children[children.length - 1];

                currentNode.setEndOfLevel(true);

            }
            else
            {
                currentNode = null;
            }
        }//end while.


        

        queue.add(node);

        StringBuilder treeData = new StringBuilder();

        while(! queue.isEmpty())
        {
            currentNode = queue.remove();

            if(currentNode != null)
            {
                String nodeString = currentNode.toString() + "  ";

                if(currentNode.isEndOfLevel())
                {
                    nodeString = nodeString + "\n";
                }

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
