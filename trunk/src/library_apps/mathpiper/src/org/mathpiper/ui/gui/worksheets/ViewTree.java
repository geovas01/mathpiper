

package org.mathpiper.ui.gui.worksheets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;


public class ViewTree {

    private Queue<SymbolBox> queue = new LinkedList();

    public void walkTree(SymbolBox node)
    {

        SymbolBox currentNode = node;

        currentNode.setEndOfLevel(true);

        //Walk the rightmost path of the tree and place end of level markers at each node.
        while(currentNode != null)
        {
            SymbolBox[] children = currentNode.getChildren();

            if(children.length != 0)
            {

                currentNode = children[children.length - 1];

                currentNode.setEndOfLevel(true);

            }
            else
            {
                currentNode = null;
            }
        }//end while.


        
        //Breadth first tree search.
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

                if(children.length != 0)
                {
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



  /*      postorder(node)
  if node.left  ≠ null then postorder(node.left)
  if node.right ≠ null then postorder(node.right)
  print node.value

        */







        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        TreePanel treePanel = new TreePanel(node, 1.5);

        MathPanelController mathPanelScaler = new MathPanelController(treePanel,2.0);

        JScrollPane scrollPane = new JScrollPane(treePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        contentPane.add(scrollPane);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        frame.setAlwaysOnTop(false);
        frame.setTitle("Syntax Tree Viewer");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }//end method.


}//end class.
