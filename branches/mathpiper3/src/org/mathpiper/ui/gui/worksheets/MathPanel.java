

package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import org.mathpiper.ui.gui.worksheets.symbolboxes.Bounds;
import org.mathpiper.ui.gui.worksheets.symbolboxes.Position;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

    public class MathPanel extends JPanel implements ViewPanel, MouseListener
    {
        protected SymbolBox symbolBox;
        protected double viewScale = 1;
        private boolean paintedOnce = false;
        private int xOffset = 0;
        private int yOffset = 0;

        public MathPanel(SymbolBox symbolBox, double viewScale)
        {
            this.symbolBox = symbolBox;
            this.setOpaque(true);
            this.viewScale = viewScale;
            this.setBackground(Color.white);

            this.addMouseListener(this);
     

            //Bounds bounds = search(symbolBox);

            //xOffset = Math.abs((int) bounds.left);
            //yOffset = Math.abs((int) bounds.top);



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

            //int iIndent = 0;
            double calculatedAscent = symbolBox.getCalculatedAscent();

            if(paintedOnce == false)
            {
                symbolBox.calculatePositions(sg, 3, new Position(0, 0));
                Bounds bounds = search(symbolBox);

                xOffset = Math.abs((int) bounds.left);
                yOffset = Math.abs((int) bounds.top);

                super.revalidate();
                
                paintedOnce = true;
                
            }//end if.

            symbolBox.calculatePositions(sg, 3, new Position(xOffset, yOffset));
            SymbolBox.setSequence(1);
            symbolBox.render(sg);
            
        }

 
        public Dimension getPreferredSize() {
           if(paintedOnce)
           {
                Bounds maxBounds = search(symbolBox);

                //System.out.println(maxBounds.toString());

                Dimension scaledDimension = maxBounds.getScaledDimension(this.viewScale);

                return scaledDimension;
           }
           else
           {
                return new Dimension(700,600);
           }
            
        }//end method.

        
        public void setViewScale(double viewScale)
        {
            this.viewScale = viewScale;
            this.revalidate();
            this.repaint();
        }


        public Bounds search(SymbolBox currentNode)
        {
            Bounds myBounds = currentNode.getScaledBounds(viewScale);

            double topMost = myBounds.getTop();
            double bottomMost = myBounds.getBottom();
            double leftMost = myBounds.getLeft();
            double rightMost = myBounds.getRight();
            /*
            double topMost = currentNode.getCalculatedPosition().getY() - currentNode.getDimension().getHeight() ;
            double bottomMost = currentNode.getCalculatedPosition().getY();
            double leftMost = currentNode.getCalculatedPosition().getX();
            double rightMost = currentNode.getCalculatedPosition().getX() + currentNode.getDimension().getWidth();
             */

            SymbolBox[] children = currentNode.getChildren();

            if(children.length != 0)
            {
                for(SymbolBox child:children)
                {
                    if(child != null)
                    {
                        Bounds bounds = search(child);

                        if(bounds.getTop() < topMost)
                        {
                            topMost = bounds.getTop();
                        }

                        if(bounds.getBottom() > bottomMost)
                        {
                            bottomMost = bounds.getBottom();
                        }

                        if(bounds.getLeft() < leftMost)
                        {
                            leftMost = bounds.getLeft();
                        }

                        if(bounds.getRight() > rightMost)
                        {
                            rightMost = bounds.getRight();
                        }

                        //return new Bounds(topMost, bottomMost, leftMost, rightMost);
                    }
                }

            }//end if.

            return new Bounds(topMost, bottomMost, leftMost, rightMost);
        }//end method.


        public void mouseClicked(MouseEvent me)
        {
            System.out.println("X: " + me.getX() + "   Y: " + me.getY());
        }

        public void mouseEntered(MouseEvent me)
        {
        }

        public void mouseExited(MouseEvent me)
        {
        }

        public void mousePressed(MouseEvent me)
        {
        }

        public void mouseReleased(MouseEvent me)
        {
        }
        
        


    }//end class.