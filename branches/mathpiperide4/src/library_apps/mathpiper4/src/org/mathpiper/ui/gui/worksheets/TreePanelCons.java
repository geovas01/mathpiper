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

	private Map<String, String> latexMap = new HashMap();

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

		latexMap.put(":=", ":=");
		latexMap.put("=?", "=");
		latexMap.put("!=?", "\\neq");
		latexMap.put("<=?", "\\leq");
		latexMap.put(">=?", "\\geq");
		latexMap.put("<?", "<");
		latexMap.put(">?", ">");
		latexMap.put("And?", "\\wedge");
		latexMap.put("Or?", "\\vee");
		latexMap.put("<>", "\\sim");
		latexMap.put("<=>", "\\approx");
		latexMap.put("Implies?", "\\Rightarrow");
		latexMap.put("Equivales?", "\\equiv");
		latexMap.put("%", "\\bmod");
		latexMap.put("Not?", "\\neg");
		latexMap.put("+", "+");
		latexMap.put("-", "-");
		latexMap.put("/", "\\div");
		latexMap.put("*", "\\times");
		latexMap.put("==", "=");
		latexMap.put("^", "^\\wedge");
		latexMap.put("Sqrt", "\\sqrt");

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

		mainRootNode = new SymbolNode();

		try {
			// listToTree(rootNode, expressionCons, null, null);

			String operator = (String) Cons.caar(expressionCons);

			mainRootNode.setOperator(operator);

			handleSublistCons(mainRootNode, expressionCons, null, null);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		layoutTree();

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

		rootNode.setOperator(operator);

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

				node2.setOperator(operator);

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
				sg.drawLatex(currentNode.texFormula, nodeX0, nodeY0);
				
				//sg.drawRectangle(nodeX0, nodeY0, currentNode.getNodeWidth(), currentNode.getNodeHeight());

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

	private void layoutTree() {
		
		rightMostPosition = 0;

		for (int index = 0; index < lastOnRasterArray.length; index++) {
			lastOnRasterArray[index] = -1;
		}// end for.

		maxTreeY = 0;

		layoutTree(mainRootNode, 40/* yPosition */, 0/* position */, null);

	}

	// Layout algorithm from "Esthetic Layout of Generalized Trees" by Anthony
	// Bloesch.
	private int layoutTree(SymbolNode treeNode, int yPosition, int leftSidePosition, SymbolNode parent) {
		int Y_SEPARATION = 30;//35 /* y stretch from top of parent to top of child. */;
		int MIN_X_SEPARATION = 20;

		int branchPosition; // Adjusts the x position of all nodes.
		int i;
		int childLeftSidePosition; // Adjusts the x position of all nodes.
		int childRightSidePosition; // Adjusts the x position of all nodes.
		int width; // Adjusting width causes very little change in the tree.

		if (treeNode == null) {
			return leftSidePosition;
		} 
		else { /* Place subtree. */
			/*
			 * Ensure the nominal position of the node is to the right of any
			 * other node.
			 */
			for (i = yPosition - Y_SEPARATION; i < yPosition + treeNode.getNodeHeight(); i++) {
				int lastOnRaster = lastOnRasterArray[i];

				/*
				 * possibleNewPosition adjusts the horizontal stretch of the
				 * whole tree
				 */
				int possibleNewPosition = (lastOnRaster + MIN_X_SEPARATION + treeNode.getNodeWidth() / 2);

				if (possibleNewPosition > leftSidePosition) {
					leftSidePosition = possibleNewPosition;
				}// end if.

			}// end for.

			if (treeNode.getChildren().length >= 1) { /* Place branches if they exist. */

				/* todo:tk
				if (treeNode.getChildren().length > 1) {

					width = (treeNode.getChildren()[0].getNodeWidth() + treeNode.getChildren()[treeNode.getChildren().length - 1]
							.getNodeWidth()) / 2 + (treeNode.getChildren().length - 1) * MIN_X_SEPARATION;

					for (i = 1; i < treeNode.getChildren().length - 1; i++){
						width += treeNode.getChildren()[i].getNodeWidth();
					}
				}

				else 

				
				{
					width = 0;
				}

				branchPosition = leftSidePosition - width / 2;
				
				*/
				
				branchPosition = leftSidePosition;
				
				/* Position far left branch. */
				childLeftSidePosition = layoutTree(treeNode.getChildren()[0], yPosition + Y_SEPARATION, branchPosition, treeNode);

				/* Position the other branches if they exist. */
				childRightSidePosition = childLeftSidePosition;
				
				for (i = 1; i < treeNode.getChildren().length; i++) {
					branchPosition += MIN_X_SEPARATION /*adjusts space between siblings*/ + (treeNode.getChildren()[i - 1].getNodeWidth() + treeNode.getChildren()[i].getNodeWidth()) / 2;
					
					childRightSidePosition = layoutTree(treeNode.getChildren()[i], yPosition + Y_SEPARATION, branchPosition, treeNode);
				} /* for */

				if (childLeftSidePosition < leftMostPosition) {
					leftMostPosition = childLeftSidePosition;
				}

				if (childRightSidePosition > rightMostPosition) {
					rightMostPosition = childRightSidePosition;
				}

				if(treeNode.getChildren().length == 1)
				{
					leftSidePosition = (childLeftSidePosition + childRightSidePosition) / 2;
				}
				else
				{
					leftSidePosition = (((childLeftSidePosition + (treeNode.getChildren()[0].getNodeWidth()/2)) + (childRightSidePosition + (treeNode.getChildren()[treeNode.getChildren().length-1].getNodeWidth()/2))   ) /2) - (treeNode.getNodeWidth()/2);// / 2;
				}
				
				//position = position - adjust; //Adjusts the position of the operator.

				treeNode.setTreeX(leftSidePosition);

			} else if (parent.getChildren().length == 1) {
				childLeftSidePosition = (leftSidePosition + (parent.getNodeWidth() / 2)) - treeNode.getNodeWidth() / 2;

				childRightSidePosition = leftSidePosition + treeNode.getNodeWidth() / 2;

				if (childLeftSidePosition < leftMostPosition) {
					leftMostPosition = childLeftSidePosition;
				}

				if (childRightSidePosition > rightMostPosition) {
					rightMostPosition = childRightSidePosition;
				}
				
				//position = position - adjust; //Adjusts the position of the operator.

				treeNode.setTreeX((leftSidePosition + parent.getNodeWidth() / 2) - treeNode.getNodeWidth() / 2);

			} else {
				//Place leaf.
				
				childRightSidePosition = leftSidePosition + treeNode.getNodeWidth()/2;
				
				leftSidePosition = leftSidePosition - treeNode.getNodeWidth()/2;
				
				childLeftSidePosition = leftSidePosition;

				if (childLeftSidePosition < leftMostPosition) {
					leftMostPosition = childLeftSidePosition;
				}

				if (childRightSidePosition > rightMostPosition) {
					rightMostPosition = childRightSidePosition;
				}
				
				//position = (position + rightPosition)/2;
				
				//position = position - adjust; //Adjusts the position of the operator.

				treeNode.setTreeX(leftSidePosition);
			}

			/* Add node to last. */
			for (i = yPosition - Y_SEPARATION; i < yPosition + treeNode.getNodeHeight(); i++) {
				//lastOnRasterArray[i] = leftSidePosition + ((treeNode.getNodeWidth() + interBranchSpace) + 1) / 2;
				
				lastOnRasterArray[i] = rightMostPosition;
				
				if (i > maxTreeY) {
					maxTreeY = i;
				}// end if.
			}// end for.

			// treeNode.setTreeX(position);

			treeNode.setTreeY(yPosition);

			return leftSidePosition;

		}// end else.

	}// end method.

	private class SymbolNode {
		private String symbolString;

		private List<SymbolNode> children = new ArrayList();

		private TeXIcon icon;

		private TeXFormula texFormula;

		private int treeX;

		private int treeY;

		private Color highlightColor;

		private String highlightNodeShape = "SQUARE";

		public void setOperator(String symbolString) {
			this.symbolString = symbolString;

			String latex = "";

			if (isCodeForm) {
				latex = symbolString;
			} else {
				latex = latexMap.get(symbolString);

				if (latex == null) {
					latex = symbolString;
				}
			}

			latex = latex.replace("_", "");

			texFormula = new TeXFormula(latex);

			icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) (12));

		}

		public TeXFormula getTeXFormula() {
			return texFormula;
		}

		public void addChild(SymbolNode child) {
			children.add(child);
		}

		public SymbolNode[] getChildren() {
			return (SymbolNode[]) children.toArray(new SymbolNode[children.size()]);
		}

		public int getNodeWidth() {
			return (int) icon.getIconWidth();
		}

		public int getNodeHeight() {
			return (int) icon.getIconHeight();
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

		public Color getHighlightColor() {
			return highlightColor;
		}

		public void setHighlightColor(Color color) {
			this.highlightColor = color;
		}

		public String getHighlightNodeShape() {
			return highlightNodeShape;
		}

		public void setHighlightNodeShape(String hilightNodeShape) {
			this.highlightNodeShape = hilightNodeShape;
		}

		public String toString() {
			return symbolString;
		}

	}

}// end class.
