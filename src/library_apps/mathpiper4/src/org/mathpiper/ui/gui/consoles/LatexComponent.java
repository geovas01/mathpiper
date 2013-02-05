package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.lisp.Utility;
import org.scilab.forge.jlatexmath.JMathTeXException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexComponent extends JPanel implements RenderingComponent, MouseListener {

    private TeXFormula texFormula;
    private JLabel renderedResult;
    private JTextField inputTextField;
    private String resultString;
    private String latexString;
    private int toggle = 0;
    private SpinButton spinButton;
    private GoAwayButton goAwayButton;
    private int fontPointSize;
    private boolean latexMode = false;
    private final GraphicConsole console;


    public LatexComponent(int fontPointSize, GraphicConsole console) {
	
	this.setBorder(new EmptyBorder(1,1,1,1));

        this.console = console;

        this.fontPointSize = fontPointSize;

        this.latexString = "\\square";



        this.renderedResult = new JLabel();

        try {
            texFormula = new TeXFormula(latexString);
            TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
            icon.setInsets(new Insets(5, 5, 5, 5));
            renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            renderedResult.setAlignmentY(icon.getBaseLine());
            renderedResult.setIcon(icon);
        } catch (JMathTeXException e) {
            renderedResult.setText(resultString);
            renderedResult.setAlignmentY(.9f);
        }




        renderedResult.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        //renderedResult.setToolTipText("Click to see text versions of this expression.");
        renderedResult.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                //eventOutput("Mouse clicked (# of clicks: "  + e.getClickCount() + ")", e);
                toggle = 0;
                toggleView();
            }

        }//end method.
                );


        inputTextField = new JTextField(10);
        inputTextField.setAlignmentY(.7f);
        inputTextField.setEditable(true);
        inputTextField.setBackground(Color.white);
        Font newFontSize = new Font(inputTextField.getFont().getName(), inputTextField.getFont().getStyle(), fontPointSize);
        inputTextField.setFont(newFontSize);
        inputTextField.setMaximumSize(inputTextField.getPreferredSize());
        inputTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //The enter key was pressed in the inputTextField.
                removeAll();
                add(renderedResult);
                LatexComponent.this.console.giveFocus();
            }//end method.

        });

        inputTextField.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
            }


            public void insertUpdate(DocumentEvent e) {
                editCode();
            }


            public void removeUpdate(DocumentEvent e) {
                editCode();
            }

        });


        inputTextField.repaint();


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
                //System.out.println(inputTextField.getText());
            }//end method.

        });
        goAwayButton.setEnabled(true);
        goAwayButton.setAlignmentY(.9f);

        this.addMouseListener(this);


        this.add(renderedResult);
        this.add(inputTextField);


        this.setFocusable(true);


    }//end constructor.


    public void giveFocus()
    {
        inputTextField.requestFocusInWindow();
    }


    public void setScale(int scaleValue) {

        this.fontPointSize = scaleValue;

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
        icon.setInsets(new Insets(5, 5, 5, 5));
        renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        renderedResult.setAlignmentY(icon.getBaseLine());
        renderedResult.setIcon(icon);
        renderedResult.repaint();



        Font newFontSize = new Font(inputTextField.getFont().getName(), inputTextField.getFont().getStyle(), fontPointSize);
        inputTextField.setFont(newFontSize);
        inputTextField.setMaximumSize(inputTextField.getPreferredSize());
        inputTextField.repaint();

    }//end method.


    void eventOutput(String eventDescription, MouseEvent e) {
        //System.out.println(eventDescription + " detected on " + e.getComponent().getClass().getName() + ".");

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
        this.add(inputTextField);

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


    public boolean isLatexMode() {
        return latexMode;
    }


    public void setLatexMode(boolean latexMode) {
        this.latexMode = latexMode;
    }


    


    public void editCode() {

        if (this.latexMode) {

            latexString = inputTextField.getText();
        } else {
            Interpreter mathPiperInterpreter = Interpreters.getSynchronousInterpreter();

            String mathPiperCode = inputTextField.getText();
            EvaluationResponse response = mathPiperInterpreter.evaluate("TeXForm(" + mathPiperCode + ");");

            if (response.isExceptionThrown()) {
                return;
            }

            latexString = response.getResult();

            if(latexString.equals("TeXForm()"))
            {
                latexString = "\\square";
            }
            
            try{

            latexString = Utility.stripEndQuotesIfPresent(null, -1, latexString);

            latexString = Utility.stripEndDollarSigns(latexString);
            }
            catch(Throwable e)
            {

            }
        }

        //System.out.println(latexString);

        TeXFormula texFormula2 = null;
        try {
            texFormula2 = new TeXFormula(latexString);
            TeXIcon icon = texFormula2.createTeXIcon(TeXConstants.STYLE_DISPLAY, this.fontPointSize);
            icon.setInsets(new Insets(5, 5, 5, 5));
            renderedResult.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            renderedResult.setAlignmentY(icon.getBaseLine());
            renderedResult.setIcon(icon);
            renderedResult.repaint();
            
            
            texFormula = texFormula2;
        } catch (Throwable ex) {
        }

    }
    
    
    public void paint(Graphics g)
    {
	super.paint(g);
	Dimension d = getPreferredSize(); 
	g.drawRect(0, 0, d.width, d.height);
    }



    public String toString()
    {
        if(latexMode)
        {
            return this.latexString;
        }
        else
        {
            return this.inputTextField.getText();
        }
    }

}//end class.

