package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class ResultHolder extends JPanel implements MouseListener{

    private TeXFormula texFormula;
    private JLabel texLabel;
    private JTextField textResult;
    private String resultString;
    private int toggle = 0;
    private ArrowButton toggleButton;



    public ResultHolder(String latexString, String resultString, int fontPointSize) {
        this.texLabel = new JLabel();
        this.resultString = resultString;

        texFormula = new TeXFormula(latexString);
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);

        textResult = new JTextField(resultString);
        textResult.setAlignmentY(.7f);
        textResult.setEditable(false);
        textResult.setBackground(Color.white);
        textResult.setMaximumSize( textResult.getPreferredSize() );

        this.setBackground(Color.white);

        this.setOpaque(true);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.add(texLabel);


        toggleButton = new ArrowButton(BasicArrowButton.NORTH);
        toggleButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ResultHolder.this.toggleView();
            }//end method.

        });
        toggleButton.setEnabled(true);
        toggleButton.setAlignmentY(.9f);


        this.addMouseListener(this);

    }//end constructor.




    public void setScale(int scaleValue) {

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, scaleValue);
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        texLabel.repaint();


        Font newFontSize = new Font(textResult.getFont().getName(), textResult.getFont().getStyle(), scaleValue);
        textResult.setFont(newFontSize);
        textResult.setMaximumSize( textResult.getPreferredSize() );
        textResult.repaint();

    }//end method.




    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "." );

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
        toggleView();

    }//end method.


    public void toggleView()
    {
        this.removeAll();

        if(toggle == 1)
        {
            toggle = 0;
            this.add(texLabel);
        }
        else
        {
            toggle = 1;

            this.add(textResult);
            this.add(toggleButton);



            
        }

        this.revalidate();
    }

    private class ArrowButton extends BasicArrowButton{
        private ArrowButton(){
            super(BasicArrowButton.NORTH);
        }

        public ArrowButton(int direction){
            super(direction);
        }

        public Dimension getMaximumSize(){
            return this.getPreferredSize();
        }

    }



}//end class.

