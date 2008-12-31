

package org.mathpiper.ui.gui.simulator;



import java.awt.*;
import java.util.ArrayList;


public class Points
{
  private ArrayList<Point> pointsList = new ArrayList();

  private static final int DOTSIZE = 12;
  private static final int RADIUS = DOTSIZE/2;
  private static final int MAXPOINTS = 40;


  private int pWidth, pHeight;   // panel dimensions
  private long startTime;        // in ms



  public Points(int pW, int pH)
  {
    pWidth = pW; pHeight = pH;

  }

  public void addPoint(int x, int y)
  {
      pointsList.add(new Point(x,y));
  }


 

  public void draw(Graphics g)
  // draw a black worm with a red head
  {
    
      g.setColor(Color.black);
      for(Point point : pointsList)
      {
        g.fillOval(point.getX(), point.getY(), DOTSIZE, DOTSIZE);
      }

    
  }  // end of draw()

  class Point
  {
      private int x;
      private int y;

      private Point(int x, int y)
      {
          this.x=x;
          this.y=y;
      }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

  }

}  // end of Worm class
