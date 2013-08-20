package org.mathpiper.ui.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

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

    public StateViewerPanel(Interpreter interpreter) {
	this.setLayout(new BorderLayout());
	this.interpreter = interpreter;

	interpreter.addResponseListener(this);

	environment = interpreter.getEnvironment();

	table = new JTable();

	table.setModel(getTableModel());

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scrollPane.getVerticalScrollBar().setUnitIncrement(16);

	this.add(scrollPane);

	table.setPreferredScrollableViewportSize(table.getPreferredSize());
	JScrollPane scrollPane = new JScrollPane(table);
	add(scrollPane);

	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

	JButton decrease = new JButton("Zoom-");
	decrease.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		Font font = table.getFont();
		font = font.deriveFont((float) (font.getSize2D() * .90));
		table.setFont(font);
		FontMetrics fontMetrics = table.getGraphics().getFontMetrics(font);
		Rectangle2D rectangle = fontMetrics.getStringBounds("H", table.getGraphics());
		table.setRowHeight((int) rectangle.getHeight());

		JTableHeader tableHeader = table.getTableHeader();
		font = tableHeader.getFont();
		font = font.deriveFont((float) (font.getSize2D() * .90));
		tableHeader.setFont(font);
		fontMetrics = tableHeader.getGraphics().getFontMetrics(font);
		rectangle = fontMetrics.getStringBounds("H", tableHeader.getGraphics());
	    }
	});
	buttonsPanel.add(decrease);

	JButton increase = new JButton("Zoom+");
	increase.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		Font font = table.getFont();
		font = font.deriveFont((float) (font.getSize2D() * 1.10));
		table.setFont(font);
		FontMetrics fontMetrics = table.getGraphics().getFontMetrics(font);
		Rectangle2D rectangle = fontMetrics.getStringBounds("H", table.getGraphics());
		table.setRowHeight((int) rectangle.getHeight());

		JTableHeader tableHeader = table.getTableHeader();
		font = tableHeader.getFont();
		font = font.deriveFont((float) (font.getSize2D() * 1.10));
		tableHeader.setFont(font);
		fontMetrics = tableHeader.getGraphics().getFontMetrics(font);
		rectangle = fontMetrics.getStringBounds("H", tableHeader.getGraphics());
	    }
	});
	buttonsPanel.add(increase);

	add(buttonsPanel, BorderLayout.NORTH);

    }

    public void response(EvaluationResponse response) {

	table.setModel(getTableModel());

	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	scrollPane.revalidate();

    }

    public boolean remove() {
	return false;
    }

    private TableModel getTableModel() {
	Map<String, GlobalVariable> globalState = (Map<String, GlobalVariable>) environment.getGlobalState();

	java.util.Set<String> variablesSet = globalState.keySet();

	Map<String, GlobalVariable> userVariablesMap = new HashMap<String, GlobalVariable>();

	for (String key : variablesSet) {
	    if (!key.contains("$") && !key.equals("I") && !key.equals("#") && ((GlobalVariable) globalState.get(key)).iConstant == false) {

		GlobalVariable globalVariable = globalState.get(key);

		userVariablesMap.put(key, globalVariable);
	    }

	}

	final Map<String, GlobalVariable> map = userVariablesMap;

	final Environment finalEnvironment = environment;

	TableModel tableModel = new AbstractTableModel() {

	    private static final long serialVersionUID = 1L;

	    public int getColumnCount() {
		return 2;
	    }

	    public int getRowCount() {
		return map.size();
	    }

	    public String getColumnName(int column) {
		if (column == 0) {
		    return "Name";
		} else {
		    return "Value";
		}
	    }

	    public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
		    return getKey(rowIndex);
		} else {
		    GlobalVariable globalVariable = (GlobalVariable) map.get(getKey(rowIndex));

		    Object value = globalVariable.iValue;

		    if (value instanceof Cons) {
			try {
			    value = Utility.printMathPiperExpression(-1, ((Cons) globalVariable.iValue), finalEnvironment, 0);
			} catch (Throwable e) {
			    e.printStackTrace();
			}
		    }

		    return value;
		} // if-else

	    }

	    private String getKey(int a_index) {
		String retval = "";
		ArrayList<String> keyList = new ArrayList<String>(map.keySet());
		Collections.sort(keyList, new Comparator<String>() {

		    public int compare(String s1, String s2) {
			return s1.compareToIgnoreCase(s2);
		    }
		});// end method.

		retval = (String) keyList.get(a_index);

		return retval;
	    }

	};

	return tableModel;
    }

}
