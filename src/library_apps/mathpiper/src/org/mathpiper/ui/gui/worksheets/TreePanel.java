package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;
import org.mathpiper.ui.gui.worksheets.symbolboxes.InfixOperator;
import org.mathpiper.ui.gui.worksheets.symbolboxes.Position;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

public class TreePanel extends JPanel implements ViewPanel {

    protected SymbolBox symbolBox;
    protected double viewScale = 1;
    private Queue<SymbolBox> queue = new LinkedList();

    public TreePanel(SymbolBox symbolBox, double viewScale) {
        this.symbolBox = symbolBox;
        this.setOpaque(true);
        this.viewScale = viewScale;
        this.setBackground(Color.white);


    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        g2d.setBackground(Color.white);
        ScaledGraphics sg = new ScaledGraphics(g2d);
        sg.setLineThickness(0);
        sg.setViewScale(viewScale);

        int x = 10;
        int y = 30;
        int iIndent = 0;

        symbolBox.calculatePositions(sg, 3, new Position(x + iIndent, y + /*calculatedAscent + 10*/ 30));

        doShapeTree(symbolBox, 150/*yPosition*/, new int[20000]/*int[] last*/, 10/*position*/, sg);


        queue.add(symbolBox);

        SymbolBox currentNode;

        //double xPosition = 50;

        //double yPosition = 50;


        while (!queue.isEmpty()) {
            currentNode = queue.remove();

            if (currentNode != null) {
                String nodeString = currentNode.toString();

                sg.drawText(nodeString, currentNode.getTreeX(), currentNode.getTreeY() );//xPosition, yPosition);

                /*if (currentNode.isEndOfLevel()) {
                    xPosition = 50;
                    yPosition += 50;
                } else {
                    xPosition += sg.getScaledTextWidth(nodeString) + 20;
                }*/

                SymbolBox[] children = currentNode.getChildren();

                if (children != null) {
                    for (SymbolBox child : children) {
                        if (child != null) {
                            queue.add(child);
                        }
                    }

                }//end if.

                
            } else {
                System.out.print("<Null>");
            }

        }//end while.

    }

    public Dimension getPreferredSize() {
        return new Dimension(700, 600);
    }

    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
    }


    private int doShapeTree(SymbolBox tree, int yPosition, int[] last, int position, ScaledGraphics sg)
    {
        int Y_SEPARATION = 20;
        int MIN_X_SEPARATION = 15;

        int branchPosition;
        int i;
        int leftPosition;
        int rightPosition;
        int width;


        //System.out.println("W: " + tree.width(sg));
        if(tree instanceof InfixOperator)
        {
            int xx = 0;
            int wi = tree.width(sg);
            xx = 0;
        }

        if(tree == null)
        {
            return position;
        }
        else /* Place subtree. */
        {
            /* Ensure the nominal position of the node is right of any other node. */
            for(i = yPosition - Y_SEPARATION; i < yPosition+tree.height(sg); i++)
            {
                int possibleNewPosition = (last[i] + MIN_X_SEPARATION + tree.width(sg)/2);

                if(possibleNewPosition > position)
                {
                    position = possibleNewPosition;
                }//end if.
                
            }//end for.


            if(tree.getChildren().length >= 1){ /* Place branches if they exist. */
                if(tree.getChildren().length > 1) {
                    width = (tree.getChildren()[0].width(sg) +
                            tree.getChildren()[tree.getChildren().length-1].width(sg))/2 +
                            (tree.getChildren().length-1)*MIN_X_SEPARATION;
                    for(i=1; i < tree.getChildren().length-1; i++)
                        width += tree.getChildren()[i].width(sg);}

                else
                    width = 0;

                branchPosition = position = width/2;
                /* Position far left branch. */
                leftPosition = doShapeTree(tree.getChildren()[0], yPosition+tree.height(sg)+Y_SEPARATION,
                        last, branchPosition, sg);

                /* Position the other branches if they exist. */
                rightPosition = leftPosition;
                for(i = 1; i < tree.getChildren().length; i++){
                    branchPosition += MIN_X_SEPARATION +
                            (tree.getChildren()[i-1].width(sg) +
                            tree.getChildren()[i].width(sg))/2;
                    rightPosition = doShapeTree(tree.getChildren()[i], yPosition+tree.height(sg)+Y_SEPARATION,
                            last, branchPosition, sg);
                } /* for */

                position = (leftPosition+rightPosition)/2;

            }//end if tree -> nrBranches >= 1 */

            /* Add node to list. */
            for(i = yPosition-Y_SEPARATION; i < yPosition+tree.height(sg); i++)
                last[i] = position + (tree.width(sg)+1)/2;


            tree.setTreeX(position);

            tree.setTreeY(yPosition);

            return position;


        }//end else.


    }//end method.

}//end class.
