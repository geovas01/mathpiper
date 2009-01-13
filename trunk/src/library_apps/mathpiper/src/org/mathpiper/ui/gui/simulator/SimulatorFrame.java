

package org.mathpiper.ui.gui.simulator;




import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SimulatorFrame extends JFrame implements WindowListener, ActionListener
{
  private static int DEFAULT_FPS = 80;

  private SimulatorPanel simulatorPanel;        // where the worm is drawn
  private JTextField jtfBox;   // displays no.of boxes used
  private JTextField jtfTime;  // displays time spent in game


  public SimulatorFrame(long period)
  { 
      super("MathPiper Simulator");
      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

   if(period == -1)
   {
    int fps = DEFAULT_FPS;

    period = (long) 1000.0/fps;
    //System.out.println("fps: " + fps + "; period: " + period + " ms");

    period = period*1000000;    // ms --> nanosecs
   }

    makeGUI(period);

    addWindowListener( this );
    pack();
    setResizable(true);
    setVisible(true);
  }  // end of WormChase() constructor

  public SimulatorFrame()
  {
        this(-1);
  }

  public void plotPoint(int x, int y)
  {
      simulatorPanel.plotPoint(x, y);
  }

  private void makeGUI(long period)
  {
    Container container = getContentPane();    // default BorderLayout used

    simulatorPanel = new SimulatorPanel(this, period);
    container.add(simulatorPanel, "Center");

    JPanel ctrls = new JPanel();   // a row of textfields
    ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));

    jtfBox = new JTextField("Boxes used: 0");
    jtfBox.setEditable(false);
    ctrls.add(jtfBox);

    jtfTime = new JTextField("Time Spent: 0 secs");
    jtfTime.setEditable(false);
    ctrls.add(jtfTime);

    JButton closeButton = new JButton("Clear");
    closeButton.addActionListener(this);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(closeButton);

    container.add(buttonPanel, "South");
  }  // end of makeGUI()

  public void actionPerformed(ActionEvent e)
  {
        simulatorPanel.clear();
  }


  public void setBoxNumber(int no)
  {  jtfBox.setText("Boxes used: " + no);  }

  public void setTimeSpent(long t)
  {  jtfTime.setText("Time Spent: " + t + " secs"); }


  // ----------------- window listener methods -------------

  public void windowActivated(WindowEvent e)
  { simulatorPanel.resumeSimulator();  }

  public void windowDeactivated(WindowEvent e)
  {  simulatorPanel.pauseSimulator();  }


  public void windowDeiconified(WindowEvent e)
  {  simulatorPanel.resumeSimulator();  }

  public void windowIconified(WindowEvent e)
  {  simulatorPanel.pauseSimulator(); }


  public void windowClosing(WindowEvent e)
  {  simulatorPanel.stopSimulator();  }


  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

  // ----------------------------------------------------

  public static void main(String args[])
  {
    int fps = DEFAULT_FPS;
    if (args.length != 0)
      fps = Integer.parseInt(args[0]);

    long period = (long) 1000.0/fps;
    //System.out.println("fps: " + fps + "; period: " + period + " ms");

    new SimulatorFrame(period*1000000L);    // ms --> nanosecs
  }

} // end of WormChase class

