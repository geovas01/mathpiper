package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.scilab.forge.jlatexmath.JMathTeXException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexComponent extends JPanel implements MouseListener {

    private TeXFormula texFormula;
    private JLabel renderedResult;
    private JTextField codeResult;
    private JTextField latexResult;
    private String resultString;
    private String latexString;
    private int toggle = 0;
    private SpinButton spinButton;
    private GoAwayButton goAwayButton;
    private int fontPointSize;


    public LatexComponent(int fontPointSize) {

        this.fontPointSize = fontPointSize;

        this.latexString = "x";



        this.renderedResult = new JLabel();

        try {
            texFormula = new TeXFormula(latexString);
            TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
            renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            renderedResult.setAlignmentY(icon.getBaseLine());
            renderedResult.setIcon(icon);
        } catch (JMathTeXException e) {
            renderedResult.setText(resultString);
            renderedResult.setAlignmentY(.9f);
        }




        renderedResult.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        renderedResult.setToolTipText("Click to see text versions of this expression.");
        renderedResult.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                //eventOutput("Mouse clicked (# of clicks: "  + e.getClickCount() + ")", e);
                toggle = 0;
                toggleView();
            }

        }//end method.
                );


        latexResult = new JTextField(10);
        latexResult.setAlignmentY(.7f);
        latexResult.setEditable(true);
        latexResult.setBackground(Color.white);
        Font newFontSize = new Font(latexResult.getFont().getName(), latexResult.getFont().getStyle(), fontPointSize);
        latexResult.setFont(newFontSize);
        latexResult.setMaximumSize(latexResult.getPreferredSize());
        latexResult.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                removeAll();
                add(renderedResult);
            }//end method.

        });

        latexResult.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {

            }


            public void insertUpdate(DocumentEvent e) {
                editLatex();
            }


            public void removeUpdate(DocumentEvent e) {
                editLatex();
            }

        });


        latexResult.repaint();


        this.setBackground(Color.white);

        this.setOpaque(true);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //this.add(latexResult);


        spinButton = new SpinButton();
        spinButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                LatexComponent.this.toggleView();
            }//end method.

        });
        spinButton.setEnabled(true);
        spinButton.setAlignmentY(.9f);


        goAwayButton = new GoAwayButton();
        goAwayButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //LatexComponent.this.goAway();
                System.out.println(latexResult.getText());
            }//end method.

        });
        goAwayButton.setEnabled(true);
        goAwayButton.setAlignmentY(.9f);

        this.addMouseListener(this);


        this.add(renderedResult);
        this.add(latexResult);


    }//end constructor.


    public void setScale(int scaleValue) {

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, scaleValue);
        renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        renderedResult.setAlignmentY(icon.getBaseLine());
        renderedResult.setIcon(icon);
        renderedResult.repaint();


        Font newFontSize = new Font(codeResult.getFont().getName(), codeResult.getFont().getStyle(), scaleValue);
        codeResult.setFont(newFontSize);
        codeResult.setMaximumSize(codeResult.getPreferredSize());
        codeResult.repaint();


        newFontSize = new Font(latexResult.getFont().getName(), latexResult.getFont().getStyle(), scaleValue);
        latexResult.setFont(newFontSize);
        latexResult.setMaximumSize(latexResult.getPreferredSize());
        latexResult.repaint();

    }//end method.


    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on " + e.getComponent().getClass().getName() + ".");

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


    public void toggleView() {
        this.removeAll();

        this.add(renderedResult);
        this.add(latexResult);

        this.revalidate();
        this.repaint();
    }


    private void goAway() {
        this.removeAll();
        this.add(renderedResult);
    }


    public String getCodeResult() {
        return resultString;
    }


    public void editLatex()
    {
                latexString = latexResult.getText();

                System.out.println(latexString);

                TeXFormula texFormula2 = null;
                try {
                    texFormula2 = new TeXFormula(latexString);
                    TeXIcon icon = texFormula2.createTeXIcon(TeXConstants.STYLE_DISPLAY, 18);
                    renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                    renderedResult.setAlignmentY(icon.getBaseLine());
                    renderedResult.setIcon(icon);
                } catch (Exception ex) {
                }
    }

}//end class.

