package org.mathpiper.ui.gui.worksheets;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.scilab.forge.mp.jlatexmath.TeXConstants;
import org.scilab.forge.mp.jlatexmath.TeXFormula;
import org.scilab.forge.mp.jlatexmath.TeXIcon;

public class LatexRenderingController extends JPanel implements ChangeListener {

    private JSlider scaleSlider;
    private JLabel texLabel;
    private TeXFormula texFormula;

    public LatexRenderingController(TeXFormula texFormula, JLabel texLabel, int initialValue) {
        super();
        this.texFormula = texFormula;
        this.texLabel = texLabel;

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, initialValue);
        icon.setInsets(new Insets(5, 5, 5, 5));
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);

        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 500, initialValue);
        scaleSlider.addChangeListener(this);

        //Turn on labels at major tick marks.
        //framesPerSecond.setMajorTickSpacing(10);
        //framesPerSecond.setMinorTickSpacing(1);
        //framesPerSecond.setPaintTicks(true);
        scaleSlider.setPaintLabels(true);

        this.add(new JLabel("Resize"));
        this.add(scaleSlider);

    }

    public void stateChanged(ChangeEvent e) {

        adjust();

    }//end method.
    
    
    public void adjust()
    {
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (int) scaleSlider.getValue());
        icon.setInsets(new Insets(5, 5, 5, 5));
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        texLabel.repaint();
        // Todo:tk:more work needs to be done here to get a scrollpane that contains this component to resize properly.
        // this.revalidate();
    }

}//end class.
