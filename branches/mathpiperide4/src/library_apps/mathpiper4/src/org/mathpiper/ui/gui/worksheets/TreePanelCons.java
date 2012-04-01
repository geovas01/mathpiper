package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;

public class TreePanelCons extends JComponent implements ViewPanel {

    protected Cons expressionCons;
    protected double viewScale = 1;
    private Queue<SymbolNode> queue = new LinkedList();
    private int[] lastOnRasterArray = new int[10000];
    private int maxTreeY = 0;
    private boolean paintedOnce = false;
    private SymbolNode rootNode = null;
    
    private int leftMostPosition = Integer.MAX_VALUE;
    private int rightMostPosition = 0;
    private int topMostPosition = 0;
    private int lineThickness = 0;
    private int fontSize = 10;
    
   

    public TreePanelCons(Cons expressionCons, double viewScale) {
	
	super();
	
	this.setLayout(null);
	
        this.expressionCons = expressionCons;
        this.setOpaque(true);
        this.viewScale = viewScale;

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
        
        
        
        //Determine the preferred size of this component.
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        layoutTree(g2d);
        
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
	

    }//end method.
    
    
    
    
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        ScaledGraphics sg = layoutTree(g2d);


        queue.add(rootNode);

        SymbolNode currentNode;


        while (!queue.isEmpty()) {
            currentNode = queue.remove();

            if (currentNode != null) {
                String nodeString = currentNode.toString();
                
                int yPositionAdjust = 42;

                sg.drawText(nodeString, currentNode.getTreeX()-(leftMostPosition * Math.pow(viewScale,1/4)), currentNode.getTreeY() -(yPositionAdjust * Math.pow(viewScale,1/2)));//xPosition, yPosition);

                SymbolNode[] children = currentNode.getChildren();

                if (children != null) {
                    for (SymbolNode child : children) {
                        if (child != null) {
                            queue.add(child);

                            sg.setColor(Color.BLACK);
                            sg.setLineThickness(1.3);
                            sg.drawLine(currentNode.getTreeX() + currentNode.getTextWidth(sg)/2 -(leftMostPosition * Math.pow(viewScale,1/4)), 
                        	    currentNode.getTreeY() -(yPositionAdjust * Math.pow(viewScale,1/2)), 
                        	    child.getTreeX() + child.getTextWidth(sg)/2 -(leftMostPosition * Math.pow(viewScale,1/4)), 
                        	    child.getTreeY() - child.getTextHeight(sg) -(yPositionAdjust * Math.pow(viewScale,1/2)));
                        }
                    }

                }//end if.

                
            } else {
                System.out.print("<Null>");
            }



        }//end while.

    }

    
    
    public Dimension getPreferredSize() {
        
        int maxHeightScaled = (int) ((maxTreeY) * Math.pow(viewScale, .8));
        
        int maxWidth = rightMostPosition - leftMostPosition;
        int maxWidthScaled = (int) ((maxWidth) * Math.pow(viewScale, 1.1));
        
        //System.out.println("" + maxWidth + ", " + maxTreeY + ", " + maxWidthScaled + ", " + maxHeightScaled);

        return(new Dimension(maxWidthScaled, maxHeightScaled));

    }//end method.
    
    
    public Dimension getMaximumSize()
    {
	return this.getPreferredSize();
    }

    
    public Dimension getMinimumSize()
    {

	return this.getPreferredSize(); 
    }
    
    
    
    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
        this.revalidate();
        this.repaint();
    }
    
    
    
    private ScaledGraphics layoutTree(Graphics2D g2d)
    {
        int xInset = getInsets().left;
        int yInset = getInsets().top;
        int w = getWidth() - getInsets().left - getInsets().right;
        int h = getHeight() - getInsets().top - getInsets().bottom;
        g2d.setColor(Color.white);
        g2d.fillRect(xInset, yInset, w, h);     
        
        
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        
        ScaledGraphics sg = new ScaledGraphics(g2d);
        
        sg.setFontSize(viewScale * fontSize);
        
        sg.setLineThickness(lineThickness);
        
        sg.setViewScale(viewScale);

        for(int index = 0; index < lastOnRasterArray.length; index++)
        {
            lastOnRasterArray[index] = -1;
        }//end for.

        maxTreeY = 0;
        
        layoutTree(rootNode, 50/*yPosition*/,  -20/*position*/, null, sg);
        
        return sg;
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
                
                
                if(leftPosition < leftMostPosition)
                {
                    leftMostPosition = leftPosition;
                }
                
                if(rightPosition > rightMostPosition)
                {
                    rightMostPosition = rightPosition;
                }

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
