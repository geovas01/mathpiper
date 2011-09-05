

package org.mathpiper.ui.gui.simulator;




import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Points
{
  private List<Point> pointsList = Collections.synchronizedList(new ArrayList<Point>());

  //private static final int DOTSIZE = 4;
  //private static final int RADIUS = DOTSIZE/2;
  private static final int MAXPOINTS = 40;

  private static volatile Color color = new Color(0,0,0);

  private static volatile int plotWidth = 4;


  private int pWidth, pHeight;   // panel dimensions
  private long startTime;        // in ms



  public Points(int pW, int pH)
  {
    pWidth = pW; pHeight = pH;

  }

  public synchronized void addPoint(int x, int y)
  {
      pointsList.add(new Point(x,y, color, plotWidth));
  }

  public synchronized void clear()
  {
      pointsList.clear();
  }


 

  public synchronized void draw(Graphics g)
  // draw a black worm with a red head
  {
    
      //g.setColor(color);

      for(Point point : pointsList)
      {
        g.setColor(point.getColor());
        g.fillOval(point.getX(), point.getY(), point.getPlotWidth(), point.getPlotWidth());
      }

    
  }  // end of draw()

  class Point
  {
      private int x;
      private int y;
      private Color color;
      private int plotWidth;

      private Point(int x, int y, Color color, int plotWidth)
      {
          this.x=x;
          this.y=y;
          this.color = color;
          this.plotWidth = plotWidth;
      }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return this.color;
        }

        public int getPlotWidth() {
            return this.plotWidth;
        }

  }

public static synchronized void setColor(int red, int green, int blue)
{
    if( (red >= 0) && (red <= 255) && (green >= 0) && (green <= 255) && (blue >= 0) && (blue <= 255) )
    {
        color = new Color(red, green, blue);
    }
}

  public static void setPlotWidth(int plotWidthParam)
  {
       plotWidth = plotWidthParam;
  }

}  // end of Worm class
