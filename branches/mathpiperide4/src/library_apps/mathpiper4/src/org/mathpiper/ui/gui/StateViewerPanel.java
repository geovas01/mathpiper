package org.mathpiper.ui.gui;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.ResponseListener;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.variables.GlobalVariable;

public class StateViewerPanel extends JPanel implements ResponseListener {
    private Interpreter interpreter; 
    private Environment environment;
    private JTextArea textArea = new JTextArea();
    private JTable table;
    private JScrollPane scrollPane;
    
    public StateViewerPanel(Interpreter interpreter)
    {
	this.setLayout(new BorderLayout());
	this.interpreter = interpreter;
	
	interpreter.addResponseListener(this);
	
	environment = interpreter.getEnvironment();
	
	table = EnvironmentViewer.getGlobalStateTable(environment, false);
	
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	
	this.add(scrollPane);
    }
    
    public void response(EvaluationResponse response) {
	
	scrollPane.getViewport().remove(table);

	table = EnvironmentViewer.getGlobalStateTable(environment, false);
	
	scrollPane.getViewport().add(table);
	
	//SwingUtilities.updateComponentTreeUI(scrollPane);
	
	scrollPane.revalidate();

    }
    
    public boolean remove() {
        return false;
    }
    
}
