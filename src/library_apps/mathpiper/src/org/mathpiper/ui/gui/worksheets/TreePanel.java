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

        queue.add(symbolBox);

        SymbolBox currentNode;

        double xPosition = 50;

        double yPosition = 50;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();

            if (currentNode != null) {
                String nodeString = currentNode.toString();

                sg.drawText(nodeString, xPosition, yPosition);

                if (currentNode.isEndOfLevel()) {
                    xPosition = 50;
                    yPosition += 50;
                } else {
                    xPosition += sg.getTextWidth(nodeString) + 20;
                }

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
}//end class.
