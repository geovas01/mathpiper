package org.mathpiper.ui.gui.consoles;

import javax.swing.*;

public class StructureDialog extends JDialog {

    EditorPaneStructure pnlStructure;
    public StructureDialog(JEditorPane source) {
        //super(parent, "Structure");
        super();

        pnlStructure=new EditorPaneStructure(source);
        pnlStructure.refresh();
        getContentPane().add(pnlStructure);
        setSize(700,500);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}