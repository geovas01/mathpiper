package org.mathpiper.ui.gui.consoles;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.plaf.UIResource;


public class GoAwayButton extends JButton implements SwingConstants
{

        private Color shadow;
        private Color darkShadow;
        private Color highlight;

        private BasicStroke redXStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        public GoAwayButton() {
	    super();
	    //setRequestFocusEnabled(false);

            this.setBackground(UIManager.getColor("control"));
            this.shadow = UIManager.getColor("controlShadow");
            this.darkShadow = UIManager.getColor("controlDkShadow");
            this.highlight = UIManager.getColor("controlLtHighlight");
	}





	public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

	    Color origColor;
	    boolean isPressed, isEnabled;
	    int w, h, size;

            w = getSize().width;
            h = getSize().height;
	    origColor = g2d.getColor();
	    isPressed = getModel().isPressed();
	    isEnabled = isEnabled();

            g2d.setColor(getBackground());
            g2d.fillRect(1, 1, w-2, h-2);

            /// Draw the border
	    if (getBorder() != null && !(getBorder() instanceof UIResource)) {
		paintBorder(g2d);
	    } else if (isPressed) {
                g2d.setColor(shadow);
                g2d.drawRect(0, 0, w-1, h-1);
            } else {
                //Use the background color set above
                g2d.drawLine(0, 0, 0, h-1);
                g2d.drawLine(1, 0, w-2, 0);

                g2d.setColor(highlight);    //Inner 3D border.
                g2d.drawLine(1, 1, 1, h-3);
                g2d.drawLine(2, 1, w-3, 1);

                g2d.setColor(shadow);       //Inner 3D border.
                g2d.drawLine(1, h-2, w-2, h-2);
                g2d.drawLine(w-2, 1, w-2, h-3);

                g2d.setColor(darkShadow);     //Backdrop shadow.
                g2d.drawLine(0, h-1, w-1, h-1);
                g2d.drawLine(w-1, h-1, w-1, 0);
            }


            if(h < 6 || w < 6)      {
                g2d.setColor(origColor);
                return;
            }

            if (isPressed) {
                g2d.translate(1, 1);
            }


            g2d.setColor(Color.RED);
            g2d.setStroke(redXStroke);

            g2d.drawLine(3, 3, 12, 12);
            g2d.drawLine(12, 3, 3, 12);






            if (isPressed) {
                g2d.translate(-1, -1);
	    }


	    g2d.setColor(origColor);

        }


        public Dimension getPreferredSize() {
            return new Dimension(16, 16);
        }


        public Dimension getMinimumSize() {
            return getPreferredSize();
        }


        public Dimension getMaximumSize() {
            return getPreferredSize();
        }


    	public boolean isFocusTraversable() {
	  return false;
	}

}

