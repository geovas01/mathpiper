package be.ugent.caagt.jmathtex;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test {

    public static void main(String[] args) {
        float fontSize = 100;

        TeXFormula formula = new TeXFormula("\\sqrt[p]{n^k}");  //"\\int_{3}^{30} \\frac{1}{2}");

        TeXIcon icon = formula.createTeXIcon(0, fontSize);
        icon.setInsets(new Insets(1, 1, 1, 1));

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        label.setAlignmentY(icon.getBaseLine());
        label.setIcon(icon);

        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JPanel panel = new JPanel();
        panel.add(label);
        contentPane.add(panel);


        frame.setAlwaysOnTop(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("JMathTeX");
        frame.setSize(new Dimension(300, 200));
        frame.setPreferredSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);



    }//end main.
}//end class.


