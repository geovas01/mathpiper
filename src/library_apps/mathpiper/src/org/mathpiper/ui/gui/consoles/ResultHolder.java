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
    private JLabel renderedResult;
    private JTextField codeResult;
    private JTextField latexResult;
    private String resultString;
    private String latexString;
    private int toggle = 0;
    private ArrowButton toggleButton;
    private GoAwayButton goAwayButton;



    public ResultHolder(String latexString, String resultString, int fontPointSize) {


        this.latexString = latexString;
        this.resultString = resultString;


        this.renderedResult = new JLabel();
        texFormula = new TeXFormula(latexString);
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
        renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        renderedResult.setAlignmentY(icon.getBaseLine());
        renderedResult.setIcon(icon);

        codeResult = new JTextField(resultString);
        codeResult.setAlignmentY(.7f);
        codeResult.setEditable(false);
        codeResult.setBackground(Color.white);
        Font newFontSize = new Font(codeResult.getFont().getName(), codeResult.getFont().getStyle(), fontPointSize);
        codeResult.setFont(newFontSize);
        codeResult.setMaximumSize( codeResult.getPreferredSize() );
        codeResult.repaint();


        latexResult = new JTextField("$" + latexString + "$");
        latexResult.setAlignmentY(.7f);
        latexResult.setEditable(false);
        latexResult.setBackground(Color.white);
        newFontSize = new Font(latexResult.getFont().getName(), latexResult.getFont().getStyle(), fontPointSize);
        latexResult.setFont(newFontSize);
        latexResult.setMaximumSize( latexResult.getPreferredSize() );
        latexResult.repaint();


        this.setBackground(Color.white);

        this.setOpaque(true);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.add(renderedResult);


        toggleButton = new ArrowButton(BasicArrowButton.NORTH);
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ResultHolder.this.toggleView();
            }//end method.
        });
        toggleButton.setEnabled(true);
        toggleButton.setAlignmentY(.9f);


        goAwayButton = new GoAwayButton();
        goAwayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ResultHolder.this.goAway();
            }//end method.
        });
        goAwayButton.setEnabled(true);
        goAwayButton.setAlignmentY(.9f);

        this.addMouseListener(this);

    }//end constructor.




    public void setScale(int scaleValue) {

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, scaleValue);
        renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        renderedResult.setAlignmentY(icon.getBaseLine());
        renderedResult.setIcon(icon);
        renderedResult.repaint();


        Font newFontSize = new Font(codeResult.getFont().getName(), codeResult.getFont().getStyle(), scaleValue);
        codeResult.setFont(newFontSize);
        codeResult.setMaximumSize( codeResult.getPreferredSize() );
        codeResult.repaint();


        newFontSize = new Font(latexResult.getFont().getName(), latexResult.getFont().getStyle(), scaleValue);
        latexResult.setFont(newFontSize);
        latexResult.setMaximumSize( latexResult.getPreferredSize() );
        latexResult.repaint();

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
        toggle = 0;
        toggleView();

    }//end method.


    public void toggleView()
    {
        this.removeAll();

        if(toggle == 1)
        {
            toggle = 0;
            this.add(latexResult);
        }
        else
        {
            toggle = 1;

            this.add(codeResult);
        }

        this.add(toggleButton);
        this.add(goAwayButton);

        this.revalidate();
        this.repaint();
    }



    private void goAway()
    {
        this.removeAll();
        this.add(renderedResult);
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

        public Dimension getMinimumSize(){
            return this.getPreferredSize();
        }

    }
    
    
    public String getCodeResult()
    {
	    return resultString;
    }



}//end class.

