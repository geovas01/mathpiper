package org.mathpiper.ui.gui.worksheets;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

public class MathPanelController extends JPanel implements ChangeListener, ItemListener {

    private JSlider scaleSlider;
    private MathPanel mathPanel;

    public MathPanelController(MathPanel mathPanel, double initialValue) {
        super();
        this.mathPanel = mathPanel;

        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, (int) (initialValue*10));
        scaleSlider.addChangeListener(this);

        //Turn on labels at major tick marks.
        //framesPerSecond.setMajorTickSpacing(10);
        //framesPerSecond.setMinorTickSpacing(1);
        //framesPerSecond.setPaintTicks(true);
        scaleSlider.setPaintLabels(true);

        this.add(new JLabel("Adjust Expression Scale"));
        this.add(scaleSlider);

        JCheckBox drawBoundingBoxCheckBox = new JCheckBox("Draw Bounding Boxes");

        drawBoundingBoxCheckBox.setSelected(SymbolBox.isDrawBoundingBox());

        drawBoundingBoxCheckBox.addItemListener(this);

        this.add(drawBoundingBoxCheckBox);


    }

    public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        //if (!source.getValueIsAdjusting()) {
        int intValue = (int) source.getValue();
        double doubleValue = intValue / 10.0;
        //System.out.println("XXX: " + doubleValue);
        mathPanel.setViewScale(doubleValue);
        mathPanel.repaint();

        //}
        }//end method.

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            SymbolBox.setDrawBoundingBox(true);
            mathPanel.repaint();

        } else {
            SymbolBox.setDrawBoundingBox(false);
            mathPanel.repaint();
        }

    }//end method.
    }
