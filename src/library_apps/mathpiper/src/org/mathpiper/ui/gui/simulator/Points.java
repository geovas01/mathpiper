

package org.mathpiper.ui.gui.simulator;




import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Points
{
  private List<Point> pointsList = Collections.synchronizedList(new ArrayList<Point>());

  private static final int DOTSIZE = 4;
  private static final int RADIUS = DOTSIZE/2;
  private static final int MAXPOINTS = 40;


  private int pWidth, pHeight;   // panel dimensions
  private long startTime;        // in ms



  public Points(int pW, int pH)
  {
    pWidth = pW; pHeight = pH;

  }

  public synchronized void addPoint(int x, int y)
  {
      pointsList.add(new Point(x,y));
  }


 

  public synchronized void draw(Graphics g)
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
