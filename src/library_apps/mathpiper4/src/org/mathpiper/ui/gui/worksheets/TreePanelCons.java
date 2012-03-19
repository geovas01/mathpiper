package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;

public class TreePanelCons extends JPanel implements ViewPanel {

    protected Cons expressionCons;
    protected double viewScale = 1;
    private Queue<SymbolNode> queue = new LinkedList();
    private int[] lastOnRasterArray = new int[10000];
    private int maxTreeY = 0;
    private boolean paintedOnce = false;
    private SymbolNode rootNode = null;

    public TreePanelCons(Cons expressionCons, double viewScale) {
        this.expressionCons = expressionCons;
        this.setOpaque(true);
        this.viewScale = viewScale;
        this.setBackground(Color.white);

        for(int index = 0; index < lastOnRasterArray.length; index++)
        {
            lastOnRasterArray[index] = -1;
        }//end for.
        
        
        rootNode = new SymbolNode();
        
        try {
	    listToTree(rootNode, expressionCons);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
    
    
    
    
    private void listToTree(SymbolNode node, Cons cons) throws Exception
    {
	
	Cons r = (Cons) cons.car(); //Go into sublist.
	
	String operator = (String) r.car();
	
	node.setOperator(operator);
	
	while(r.cdr() != null)
	{
	    r = r.cdr();
	    
	    if(r instanceof SublistCons)
	    {
		SymbolNode node3 = new SymbolNode();
		
		listToTree(node3, r);
		
		node.addChild(node3);
		
		
	    }
	    else
	    {
		SymbolNode node2 = new SymbolNode();
		
		operator = (String) r.car();
		
		node2.setOperator(operator);
		
		node.addChild(node2);
	    }
	}
	
	int xx = 4;
	

    }//end method.
    
    
    
    
    

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        g2d.setBackground(Color.white);
        ScaledGraphics sg = new ScaledGraphics(g2d);
        
        sg.setFontSize(viewScale * 10);
        
        sg.setLineThickness(0);
        sg.setViewScale(viewScale);

        int x = 0;
        int y = 0;
        //rootNode.calculatePositions(sg, 3, new Position(x , y));


        for(int index = 0; index < lastOnRasterArray.length; index++)
        {
            lastOnRasterArray[index] = -1;
        }//end for.

        maxTreeY = 0;
        
        layoutTree(rootNode, 50/*yPosition*/,  -20/*position*/, null, sg);


        queue.add(rootNode);

        SymbolNode currentNode;


        while (!queue.isEmpty()) {
            currentNode = queue.remove();

            if (currentNode != null) {
                String nodeString = currentNode.toString();

                sg.drawText(nodeString, currentNode.getTreeX(), currentNode.getTreeY() );//xPosition, yPosition);

                SymbolNode[] children = currentNode.getChildren();

                if (children != null) {
                    for (SymbolNode child : children) {
                        if (child != null) {
                            queue.add(child);

                            sg.setColor(Color.BLACK);
                            sg.setLineThickness(1.3);
                            sg.drawLine(currentNode.getTreeX() + currentNode.getTextWidth(sg)/2, 
                        	    currentNode.getTreeY() + 1, 
                        	    child.getTreeX() + child.getTextWidth(sg)/2, 
                        	    child.getTreeY() - child.getTextHeight(sg) + 1);

                        }
                    }

                }//end if.

                
            } else {
                System.out.print("<Null>");
            }

            if(paintedOnce == false)
            {
                super.revalidate();
                paintedOnce = true;
            }

        }//end while.

    }

    public Dimension getPreferredSize() {
        
        if(paintedOnce == false)
        {
            return new Dimension(0,0);
        }

        int maxWidth = 0;

        int index = 0;

        for(; index < lastOnRasterArray.length; index++)
        {
            if(lastOnRasterArray[index] > maxWidth)
            {
                maxWidth = lastOnRasterArray[index];
            }//end if.

        }//end for.

        maxWidth = (int) ((maxWidth + 100) * viewScale);

        int maxHeight = (int) ((maxTreeY) * viewScale);

        return(new Dimension(maxWidth, maxHeight));

    }//end method.


    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
        this.revalidate();
        this.repaint();
    }


    //Layout algorithm from "Esthetic Layout of Generalized Trees" by Anthony Bloesch.
    private int layoutTree(SymbolNode tree, int yPosition,  int position, SymbolNode parent, ScaledGraphics sg)
    {
        int Y_SEPARATION = 35;
        int MIN_X_SEPARATION = 20;

        int branchPosition;
        int i;
        int leftPosition;
        int rightPosition;
        int width;

        int interBranchSpace = 75;


        if(tree == null)
        {
            return position;
        }
        else /* Place subtree. */
        {
            /* Ensure the nominal position of the node is to the right of any other node. */
            for(i = yPosition - Y_SEPARATION; i < yPosition + tree.getTextHeight(sg); i++)
            {
                int lastOnRaster = lastOnRasterArray[i];
                
                int possibleNewPosition = (lastOnRaster + MIN_X_SEPARATION + tree.getTextWidth(sg)/2);

                if(possibleNewPosition > position)
                {
                    position = possibleNewPosition;
                }//end if.
                
            }//end for.


            if(tree.getChildren().length >= 1){ /* Place branches if they exist. */

                if(tree.getChildren().length > 1) {

                    width = (tree.getChildren()[0].getTextWidth(sg) +
                            tree.getChildren()[tree.getChildren().length-1].getTextWidth(sg))/2 +
                            (tree.getChildren().length-1)*MIN_X_SEPARATION;

                    for(i=1; i < tree.getChildren().length-1; i++)
                        width += tree.getChildren()[i].getTextWidth(sg);}

                else
                    width = 0;

                branchPosition = position - width/2;
                /* Position far left branch. */
                leftPosition = layoutTree(tree.getChildren()[0], yPosition + tree.getTextHeight(sg) + Y_SEPARATION,
                         branchPosition, tree, sg);

                /* Position the other branches if they exist. */
                rightPosition = leftPosition;
                for(i = 1; i < tree.getChildren().length; i++){
                    branchPosition += MIN_X_SEPARATION +
                            (tree.getChildren()[i-1].getTextWidth(sg) +
                            tree.getChildren()[i].getTextWidth(sg))/2;
                    rightPosition = layoutTree(tree.getChildren()[i], yPosition + tree.getTextHeight(sg) + Y_SEPARATION,
                             branchPosition, tree, sg);
                } /* for */

                position = (leftPosition+rightPosition)/2;

            }//end if tree -> nrBranches >= 1 */

            /* Add node to list. */
            for(i = yPosition - Y_SEPARATION; i < yPosition+tree.getTextHeight(sg); i++)
            {
                lastOnRasterArray[i] = position + ((tree.getTextWidth(sg) + interBranchSpace) + 1)/2;
                if(i > maxTreeY)
                {
                    maxTreeY = i;
                }//end if.
            }//end for.


            tree.setTreeX(position);

            tree.setTreeY(yPosition);

            return position;


        }//end else.


    }//end method.
    
    
    private class SymbolNode
    {
	private String symbolString;
	
	private List<SymbolNode> children = new ArrayList();
	
	private int treeX;

	private int treeY;
	
	
	public void setOperator(String symbolString)
	{
	    this.symbolString = symbolString;
	}
	
	
	public void addChild(SymbolNode child)
	{
	    children.add(child);
	}
	
	
	public SymbolNode[] getChildren()
	{
	    return (SymbolNode[]) children.toArray(new SymbolNode[children.size()]);
	}
	
	
	
	    public int getTextWidth(ScaledGraphics sg)
	    {
	        return (int) sg.getScaledTextWidth(toString());
	    }

	    public int getTextHeight(ScaledGraphics sg)
	    {
	        return (int) sg.getScaledTextHeight(toString());
	    }
	
	    public int getTreeX() {
	        return treeX;
	    }

	    public void setTreeX(int treeX) {
	        this.treeX = treeX;
	    }

	    public int getTreeY() {
	        return treeY;
	    }

	    public void setTreeY(int treeY) {
	        this.treeY = treeY;
	    }
	    
	    
	    public String toString()
	    {
		return symbolString;
	    }

    }

}//end class.
