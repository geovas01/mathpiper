package org.mathpiper.ui.gui;

import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import org.mathpiper.ui.gui.consoles.ResultHolder;

public class Utility {

    public static void saveImageOfComponent(JComponent component) {
        JFileChooser saveImageFileChooser = new JFileChooser();

        int returnValue = saveImageFileChooser.showSaveDialog(component);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File exportImageFile = saveImageFileChooser.getSelectedFile();
            try {
                ScreenCapture.createImage(component, exportImageFile.getAbsolutePath());
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
            }//end try/catch.

        }
    }

}
