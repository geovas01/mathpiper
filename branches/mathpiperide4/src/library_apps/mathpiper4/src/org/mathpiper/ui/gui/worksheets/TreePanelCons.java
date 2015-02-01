package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JComponent;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.scilab.forge.mp.jlatexmath.TeXConstants;
import org.scilab.forge.mp.jlatexmath.TeXFormula;
import org.scilab.forge.mp.jlatexmath.TeXIcon;

import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

public class TreePanelCons extends JComponent implements ViewPanel {

	protected Cons expressionCons;
	protected double viewScale = 1;
	private Queue<SymbolNode> queue = new LinkedList();
	private int[] lastOnRasterArray = new int[10000];
	private int maxTreeY = 0;
	private SymbolNode mainRootNode = null;

	private int leftMostPosition = Integer.MAX_VALUE; // Initialize to maximum
													  // possible position.
	private int rightMostPosition = 0; // Initialize to minimum possible
									   // position.
	private double defaultLineThickness = .6;
	private int fontSize = 11;

	

	private Map<String, Color> colorMap = new HashMap();

	private int yPositionAdjust = 39; /*
									   * Adjust y position of whole tree,
									   * smaller numbers moves the tree down.
									   */

	private int adjust = 1;

	private boolean isCodeForm = false;

	// Show(TreeView(a/b == 3))
	// 99 # UnparseLatex(_x / _y, _p)_( <-- UnparseLatexBracketIf(p <?
	// PrecedenceGet("/"), ConcatStrings("\\frac{", UnparseLatex(x,
	// UnparseLatexMaxPrec()), "}{", UnparseLatex(y, UnparseLatexMaxPrec()),
	// "} ") );
	// Show(TreeView( '(a*(b+c) == a*b + a*c)))
	// Show(TreeView( '(a*(b+c) == a*b + a*c), Resizable -> True,
	// IncludeExpression -> True))
	// Show(TreeView( '(-500), Resizable -> True, IncludeExpression -> True))
	// Show(TreeView( '(-500 * a), Resizable -> True, IncludeExpression ->
	// True))
	// Show(TreeView( '(2*3+8-4), Resizable -> True, IncludeExpression -> True))
	// Show(TreeView( '(-50000000000000*a), Resizable -> True, IncludeExpression
	// -> True))

	public TreePanelCons(Cons expressionCons, double viewScale, boolean isCodeForm) {

		super();

		// this.setBorder(new EmptyBorder(1,1,1,1));



		colorMap.put("\"RED\"", Color.RED);
		colorMap.put("\"BLACK\"", Color.BLACK);
		colorMap.put("\"BLUE\"", Color.BLUE);
		colorMap.put("\"CYAN\"", Color.CYAN);
		colorMap.put("\"DARKGRAY\"", Color.DARK_GRAY);
		colorMap.put("\"GRAY\"", Color.GRAY);
		colorMap.put("\"GREEN\"", Color.GREEN);
		colorMap.put("\"MAGENTA\"", Color.MAGENTA);
		colorMap.put("\"ORANGE\"", Color.ORANGE);
		colorMap.put("\"RED\"", Color.RED);
		colorMap.put("\"WHITE\"", Color.WHITE);
		colorMap.put("\"YELLOW\"", Color.YELLOW);

		this.isCodeForm = isCodeForm;

		this.setLayout(null);

		this.expressionCons = expressionCons;
		this.setOpaque(true);
		this.viewScale = viewScale;

		for (int index = 0; index < lastOnRasterArray.length; index++) {
			lastOnRasterArray[index] = -1;
		}// end for.

                
                if(expressionCons != null)
                {
                    mainRootNode = new SymbolNode();

                    try {
                            // listToTree(rootNode, expressionCons, null, null);

                            String operator = (String) Cons.caar(expressionCons);

                            mainRootNode.setOperator(operator, isCodeForm);

                            handleSublistCons(mainRootNode, expressionCons, null, null);

                    } catch (Throwable e) {
                            e.printStackTrace();
                    }

                    layoutTree();
                }

	}

	private void listToTree(SymbolNode rootNode, Cons rootCons, Color markAllColor, String markAllNodeShape) throws Throwable {

		Cons cons = (Cons) rootCons.car(); // Go into sublist.

		if (markAllColor != null) {
			rootNode.setHighlightColor(markAllColor);
		} else if (cons.getMetadataMap() != null) {
			Map map = cons.getMetadataMap();

			if (map.containsKey("\"HighlightColor\"")) {

				Cons atomCons = (Cons) map.get("\"HighlightColor\"");

				if (atomCons != null) {
					rootNode.setHighlightColor(colorMap.get(atomCons.car()));
				}
			}
		}

		if (markAllNodeShape != null) {
			rootNode.setHighlightNodeShape(markAllNodeShape);
		} else if (cons.getMetadataMap() != null) {
			Map map = cons.getMetadataMap();

			if (map.containsKey("\"HighlightNodeShape\"")) {

				Cons atomCons = (Cons) map.get("\"HighlightNodeShape\"");

				if (atomCons != null) {
					rootNode.setHighlightNodeShape(Utility.stripEndQuotesIfPresent((String) atomCons.car()));
				}
			}
		}

		String operator = (String) cons.car();

		rootNode.setOperator(operator, isCodeForm);

		while (cons.cdr() != null) {
			cons = cons.cdr();

			SymbolNode node2 = new SymbolNode();

			if (cons instanceof SublistCons) {

				handleSublistCons(node2, cons, markAllColor, markAllNodeShape);

				rootNode.addChild(node2);

			} else {

				if (markAllColor != null) {
					node2.setHighlightColor(markAllColor);
				} else if (cons.getMetadataMap() != null) {
					Map map = cons.getMetadataMap();

					if (map.containsKey("\"HighlightColor\"")) {

						Cons atomCons = (Cons) map.get("\"HighlightColor\"");

						if (atomCons != null) {
							node2.setHighlightColor(colorMap.get(atomCons.car()));
						}
					}
				}

				if (markAllNodeShape != null) {
					node2.setHighlightNodeShape(markAllNodeShape);
				} else if (cons.getMetadataMap() != null) {
					Map map = cons.getMetadataMap();

					if (map.containsKey("\"HighlightNodeShape\"")) {

						Cons atomCons = (Cons) map.get("\"HighlightNodeShape\"");

						if (atomCons != null) {
							node2.setHighlightNodeShape(Utility.stripEndQuotesIfPresent((String) atomCons.car()));
						}
					}
				}

				operator = (String) cons.car();

				node2.setOperator(operator, isCodeForm);

				rootNode.addChild(node2);
			}
		}

	}// end method.

	private void handleSublistCons(SymbolNode node2, Cons cons, Color markAllColor, String markAllNodeShape) throws Throwable {
		Color markSubtreeColor = null;

		if (markAllColor != null) {
			markSubtreeColor = markAllColor;
		} else if (cons.getMetadataMap() != null) {
			Map map = cons.getMetadataMap();

			if (map.containsKey("\"HighlightColor\"")) {

				Cons atomCons = (Cons) map.get("\"HighlightColor\"");

				if (atomCons != null) {
					markSubtreeColor = colorMap.get(atomCons.car());
				}
			}
		}

		String markSubtreeNodeShape = null;

		if (markAllNodeShape != null) {
			markSubtreeNodeShape = markAllNodeShape;
		} else if (cons.getMetadataMap() != null) {
			Map map = cons.getMetadataMap();

			if (map.containsKey("\"HighlightNodeShape\"")) {

				Cons atomCons = (Cons) map.get("\"HighlightNodeShape\"");

				if (atomCons != null) {
					markSubtreeNodeShape = Utility.stripEndQuotesIfPresent((String) atomCons.car());
				}
			}
		}

		listToTree(node2, cons, markSubtreeColor, markSubtreeNodeShape);
	}

	//Uncomment for debugging.
	/*
	 public void paint(Graphics g) { 
		 super.paint(g); Dimension d = getPreferredSize(); g.drawRect(0, 0, d.width - 1, d.height - 1); 
	 }
	*/
	 

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		layoutTree();
		
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

		sg.setViewScale(viewScale);

		queue.add(mainRootNode);
		paintHighlightLayer(sg);

		queue.add(mainRootNode);
		paintDrawingLayer(sg);

	}

	private void paintHighlightLayer(ScaledGraphics sg) {
		SymbolNode currentNode;

		while (!queue.isEmpty()) {

			currentNode = queue.remove();

			if (currentNode != null) {

				double nodeX0 = currentNode.getTreeX() - (leftMostPosition);

				double nodeY0 = currentNode.getTreeY() - (yPositionAdjust /* height of symbol */);

				double nodeX1 = nodeX0 + currentNode.getNodeWidth();

				double nodeY1 = nodeY0 + currentNode.getNodeHeight();

				if (currentNode.getHighlightColor() != null) {
					sg.setColor(currentNode.getHighlightColor());
					// sg.setLineThickness(1.0);

					if (currentNode.getHighlightNodeShape() != null && currentNode.getHighlightNodeShape().equals("RECTANGLE")) {
						sg.fillRect(nodeX0, nodeY0, currentNode.getNodeWidth(), currentNode.getNodeHeight());
					} else {
						sg.fillArc(nodeX0, nodeY0, currentNode.getNodeWidth(), currentNode.getNodeHeight(), 0, 360);
					}

					// sg.setLineThickness(defaultLineThickness);
					sg.setColor(Color.BLACK);
				}

				SymbolNode[] childrenNodes = currentNode.getChildren();

				if (childrenNodes != null) {

					for (SymbolNode childNode : childrenNodes) {
						// Draw highlighting. This is done in a separate for
						// loop to prevent overwriting the ends of normal arcs.

						if (childNode != null) {

							queue.add(childNode);

							if (currentNode.getHighlightColor() != null && childNode.getHighlightColor() != null) {

								double x0 = currentNode.getTreeX() + currentNode.getNodeWidth() / 2 - (leftMostPosition);

								double y0 = currentNode.getTreeY() + currentNode.getNodeHeight() - (yPositionAdjust * 1/* height of nodes
																													    */);

								double x1 = childNode.getTreeX() + childNode.getNodeWidth() / 2 - (leftMostPosition);

								double y1 = childNode.getTreeY() - (yPositionAdjust * 1 /* height of leaves */);

								sg.setColor(currentNode.getHighlightColor());
								sg.setLineThickness(defaultLineThickness * 4);
								sg.drawLine(x0, y0, x1, y1);
							}

						}
					}// end for

				}// end if.

			} else {
				System.out.print("<Null>");
			}

		}// end while.

	}

	private void paintDrawingLayer(ScaledGraphics sg) {
		SymbolNode currentNode;

		while (!queue.isEmpty()) {

			currentNode = queue.remove();

			if (currentNode != null) {

				double nodeX0 = currentNode.getTreeX() - (leftMostPosition);

				double nodeY0 = currentNode.getTreeY() - (yPositionAdjust /* height of symbol */);

				double nodeX1 = nodeX0 + currentNode.getNodeWidth();

				double nodeY1 = nodeY0 + currentNode.getNodeHeight();

				sg.setColor(Color.BLACK);
                                
                                if(this.isCodeForm)
                                {
                                    sg.drawText(currentNode.toString(), nodeX0, nodeY0 + currentNode.getNodeHeight() - 2);
                                }
                                else
                                {
                                    sg.drawLatex(currentNode.getTexFormula(), nodeX0, nodeY0);
                                }
				
				// sg.drawRectangle(nodeX0, nodeY0, currentNode.getNodeWidth(), currentNode.getNodeHeight());

				SymbolNode[] childrenNodes = currentNode.getChildren();

				if (childrenNodes != null) {

					for (SymbolNode childNode : childrenNodes) {
						if (childNode != null) {
							queue.add(childNode);

							double x0 = currentNode.getTreeX() + currentNode.getNodeWidth() / 2 - (leftMostPosition);

							double y0 = currentNode.getTreeY() + currentNode.getNodeHeight() - (yPositionAdjust * 1/* height of nodes */);

							double x1 = childNode.getTreeX() + childNode.getNodeWidth() / 2 - (leftMostPosition);

							double y1 = childNode.getTreeY() - (yPositionAdjust * 1 /* height of leaves */);

							sg.setColor(Color.BLACK);
							sg.setLineThickness(defaultLineThickness);
							sg.drawLine(x0, y0, x1, y1);
						}
					}// end for

				}// end if.

			} else {
				System.out.print("<Null>");
			}

		}// end while.

	}

	public Dimension getPreferredSize() {

		int maxHeightScaled = (int) ((maxTreeY - yPositionAdjust) * viewScale);

		int maxWidth = rightMostPosition ;//- leftMostPosition; //Adjusts how
										 // far to the right the component will
										 // extend.
		int maxWidthScaled = (int) ((maxWidth) * viewScale);

		// System.out.println("" + maxWidth + ", " + maxTreeY + ", " +
		// maxWidthScaled + ", " + maxHeightScaled);

		return (new Dimension(maxWidthScaled, maxHeightScaled));

	}// end method.

	public Dimension getMaximumSize() {
		return this.getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return this.getPreferredSize();
	}

	public void setViewScale(double viewScale) {
		this.viewScale = viewScale;

		//this.adjust = (int) viewScale;
		//System.out.println(adjust);

		this.revalidate();
		this.repaint();
	}

	public void layoutTree() {
		
		rightMostPosition = 0;

		for (int index = 0; index < lastOnRasterArray.length; index++) {
			lastOnRasterArray[index] = -1;
		}// end for.

		maxTreeY = 0;

		layoutTree(mainRootNode, 40/* yPosition */, 0/* position */, null, true); // todo:tk:add to 40 to stretch in the Y direction.

	}

	// Layout algorithm from "Esthetic Layout of Generalized Trees" by Anthony
	// Bloesch.
	private int layoutTree(SymbolNode treeNode, int yPosition, int parentLeftSidePosition, SymbolNode parent, boolean first) {
		int Y_SEPARATION = 30;// 35 /* y stretch from top of parent to top of child. */;
		int MIN_X_SEPARATION = 20;
		int branchPosition; // Adjusts the x position of all nodes.
		int i;
		int childLeftSidePosition; // Adjusts the x position of all nodes.
		int childRightSidePosition; // Adjusts the x position of all nodes.
		int width; // Adjusting width causes very little change in the tree.
		/* Place subtree. */
		
        /*
         * Ensure the nominal position of the node is to the right of any
         * other node.
         */
                int ySeparation;

        if(first == true)
        {
            ySeparation = 0;
        }
        else
        {
            ySeparation = Y_SEPARATION;
        }
        for (i = yPosition - ySeparation; i < yPosition
                + treeNode.getNodeHeight(); i++) {
            int lastOnRaster = lastOnRasterArray[i];
            /*
             * possibleNewPosition adjusts the horizontal stretch of the
             * whole tree
             */
            int possibleNewPosition = (lastOnRaster + MIN_X_SEPARATION + treeNode.getNodeWidth()/2);
            if (possibleNewPosition > parentLeftSidePosition) {
                parentLeftSidePosition = possibleNewPosition;
            }
        }
        if (treeNode.getChildren() != null && treeNode.getChildren().length >= 1) { /* Place branches if they exist. */

            branchPosition = parentLeftSidePosition;

            /* Position far left branch. */
            childLeftSidePosition = layoutTree(treeNode.getChildren()[0], yPosition
                    + Y_SEPARATION, branchPosition, treeNode, false);
            /* Position the other branches if they exist. */
            childRightSidePosition = childLeftSidePosition;// + treeNode.getChildren()[0].getNodeWidth() - treeNode.getNodeWidth()/2;
            
            branchPosition = childLeftSidePosition + treeNode.getChildren()[0].getNodeWidth()/2;

            for (i = 1; i < treeNode.getChildren().length; i++) {
                branchPosition += MIN_X_SEPARATION /* adjusts space between siblings */
                        + (treeNode.getChildren()[i-1].getNodeWidth() + treeNode.getChildren()[i].getNodeWidth())/2;

                childRightSidePosition = layoutTree(treeNode.getChildren()[i],
                        yPosition + Y_SEPARATION, branchPosition, treeNode, false);// + treeNode.getChildren()[i].getNodeWidth();
            }
            
            if (treeNode.getChildren().length == 1) {
                parentLeftSidePosition = (childLeftSidePosition + treeNode.getChildren()[0].getNodeWidth()/2) - treeNode.getNodeWidth()/2;
                treeNode.setTreeX(parentLeftSidePosition);
            } else {
                parentLeftSidePosition = (((childLeftSidePosition + (treeNode.getChildren()[0].getNodeWidth()/2)) + (childRightSidePosition 
                    + (treeNode.getChildren()[treeNode.getChildren().length - 1].getNodeWidth()/2)))/2)
                    - (treeNode.getNodeWidth()/2);
                treeNode.setTreeX(parentLeftSidePosition);
            }
        } else {
            //Leaf.
            parentLeftSidePosition = parentLeftSidePosition - treeNode.getNodeWidth()/2;
            treeNode.setTreeX(parentLeftSidePosition);
        }
        
        /* Add node to last. */
        for (i = yPosition - ySeparation; i < yPosition + treeNode.getNodeHeight(); i++) {
            lastOnRasterArray[i] = rightMostPosition; // PM.treeRightMostX;

            if (i > maxTreeY) {
                maxTreeY = i;
            }
        }

        treeNode.setTreeY(yPosition);
        
        if (treeNode.getTreeX() < leftMostPosition/*PM.treeLeftMostX*/) {
            leftMostPosition/*PM.treeLeftMostX*/ = treeNode.getTreeX();
        }
        if (treeNode.getTreeX() + treeNode.getNodeWidth() > rightMostPosition/*PM.treeRightMostX*/) {
            rightMostPosition/*PM.treeRightMostX*/ = treeNode.getTreeX() + treeNode.getNodeWidth();
        }			
			
			return parentLeftSidePosition;
}
        
    public SymbolNode getMainRootNode() {
        return mainRootNode;
    }

    public void setMainRootNode(SymbolNode mainRootNode) {
        this.mainRootNode = mainRootNode;
    }
        
        
        
    public static void main(String[] args) {
        try
        {
            SQLParser parser = new SQLParser();

                StatementNode stmt = parser.parseStatement("SELECT hotel_no, SUM(price) FROM room r WHERE room_no NOT IN (SELECT room_no FROM booking b, hotel h WHERE (date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE) AND b.hotel_no = h.hotel_no) GROUP BY hotel_no");
                stmt.treePrint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}// end class.
