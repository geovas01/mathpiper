package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


/* ResizableComponent.java */

public class ResizableComponent extends JFrame {

  private JPanel panel = new JPanel(null);
  private Resizable resizer;


  public ResizableComponent() {

      add(panel);

      JPanel area = new JPanel();
      area.setBackground(Color.white);
      resizer = new Resizable(area);
      resizer.setBounds(50, 50, 200, 150);
      panel.add(resizer);


      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(new Dimension(350, 300));
      setTitle("Resizable Component");
      setLocationRelativeTo(null);

      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent me) {

          requestFocus();
          resizer.repaint();
        }
      });
  }

  public static void main(String[] args) {
      ResizableComponent rc = new ResizableComponent();
      rc.setVisible(true);
  }
}
