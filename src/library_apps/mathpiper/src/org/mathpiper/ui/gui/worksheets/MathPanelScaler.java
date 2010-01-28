

package org.mathpiper.ui.gui.worksheets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MathPanelScaler extends JPanel implements ChangeListener{

        private JSlider scaleSlider;
        private MathPanel mathPanel;

        public MathPanelScaler(MathPanel mathPanel) {
            super();
            this.mathPanel = mathPanel;

            scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
            scaleSlider.addChangeListener(this);

            //Turn on labels at major tick marks.
            //framesPerSecond.setMajorTickSpacing(10);
            //framesPerSecond.setMinorTickSpacing(1);
            //framesPerSecond.setPaintTicks(true);
            scaleSlider.setPaintLabels(true);

            this.add(new JLabel("Adjust Expression Scale"));
            this.add(scaleSlider);
        }

        public void stateChanged(ChangeEvent e) {
            
            JSlider source = (JSlider) e.getSource();
            //if (!source.getValueIsAdjusting()) {
                int intValue = (int) source.getValue();
                double doubleValue = intValue/10.0;
                //System.out.println("XXX: " + doubleValue);
                mathPanel.setViewScale(doubleValue);
                mathPanel.repaint();

            //}
        }
    }