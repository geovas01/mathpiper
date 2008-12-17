package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class TempPanel extends JPanel implements KeyListener{

    public TempPanel() {

        System.out.println("Initializing.");
        this.setSize(400, 400); //todo:tk:

        setBackground(Color.WHITE);
        setLayout(null);
        this.setPreferredSize(new Dimension(400,400));
        this.setFocusable(true);
        requestFocus();
        addKeyListener(this);


    }//end constructor.


        public void keyPressed(KeyEvent e)
    {
        System.out.println("KeyPressed.");
        //processKeyEvent(e);
    }

    public void keyTyped(KeyEvent e)
    {
        System.out.println("KeyPressed.");
        //    processKeyEvent(e);
    }

    public void keyReleased(KeyEvent e)
    {
        System.out.println("KeyPressed.");
        //    processKeyEvent(e);
    }

}