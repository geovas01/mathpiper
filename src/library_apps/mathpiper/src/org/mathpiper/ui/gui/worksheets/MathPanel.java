

package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import org.mathpiper.ui.gui.worksheets.symbolboxes.Position;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SBox;

    public class MathPanel extends JPanel
    {
        private SBox sBoxExpression;
        private double viewScale = 1;

        public MathPanel(SBox sBoxExpression, double viewScale)
        {
            this.sBoxExpression = sBoxExpression;
            this.setOpaque(true);
            this.viewScale = viewScale;
            this.setBackground(Color.white);

        }


        public void paint(Graphics g)
        {
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
            double calculatedAscent = sBoxExpression.getCalculatedAscent();
            sBoxExpression.calculatePositions(sg, 3, new Position(x + iIndent, y + /*calculatedAscent + 10*/30));
            SBox.setSequence(1);
            sBoxExpression.render(sg);

        }

 
        public Dimension getPreferredSize() {
            return new Dimension(700,600);
        }

        public void setViewScale(double viewScale)
        {
            this.viewScale = viewScale;
        }


    }//end class.