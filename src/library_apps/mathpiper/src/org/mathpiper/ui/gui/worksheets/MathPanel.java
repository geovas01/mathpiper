

package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import org.mathpiper.ui.gui.worksheets.symbolboxes.GraphicsPrimitives;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SBox;

    public class MathPanel extends JPanel
    {
        SBox sBoxExpression;

        public MathPanel(SBox sBoxExpression)
        {
            this.sBoxExpression = sBoxExpression;

        }


        public void paint(Graphics g)
        {

            Graphics2D g2d = (Graphics2D) g;
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

            g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(Color.black);
            GraphicsPrimitives gp = new GraphicsPrimitives(g2d);
            gp.setLineThickness(0);
            int x = 100;
            int y = 100;
            int iIndent = 0;
            sBoxExpression.calculatePositions(gp, 3, new java.awt.Point(x + iIndent, y + sBoxExpression.getCalculatedAscent() + 10));
            sBoxExpression.render(gp);

        }

    }//end class.