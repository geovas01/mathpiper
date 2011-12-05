
package org.mathpiper.ui.gui.worksheets;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.mathpiper.ui.gui.Utility;


public class ScreenCapturePanel extends JPanel implements MouseListener{
    
    public ScreenCapturePanel()
    {
        this.addMouseListener(this);
        this.setBackground(Color.white);
    }


    public void mousePressed(MouseEvent e) {
        //eventOutput("Mouse pressed (# of clicks: " + e.getClickCount() + ")", e);
    }


    public void mouseReleased(MouseEvent e) {
        //eventOutput("Mouse released (# of clicks: " + e.getClickCount() + ")", e);
    }


    public void mouseEntered(MouseEvent e) {
        //eventOutput("Mouse entered", e);
    }


    public void mouseExited(MouseEvent e) {
        //eventOutput("Mouse exited", e);
    }


    public void mouseClicked(MouseEvent e) {
        //eventOutput("Mouse clicked (# of clicks: "  + e.getClickCount() + ")", e);

                int buttonNumber = e.getButton();

                if (buttonNumber == MouseEvent.BUTTON3) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem menuItem = new JMenuItem("Save image to file");
                    menuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                           Utility.saveImageOfComponent(ScreenCapturePanel.this);
                        }

                    });

                    popup.add(menuItem);
                    popup.show(ScreenCapturePanel.this, 10, 10);


                }
    }//end method.
}
