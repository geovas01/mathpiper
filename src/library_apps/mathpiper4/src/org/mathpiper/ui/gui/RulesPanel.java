package org.mathpiper.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.scilab.forge.mp.jlatexmath.TeXConstants;
import org.scilab.forge.mp.jlatexmath.TeXFormula;

public class RulesPanel extends JPanel {

    private Interpreter interpreter;
    private Environment environment;
    private JTextArea textArea = new JTextArea();
    private JTable table;
    private JScrollPane scrollPane;
    private String column1Name = "Name";
    private String column2Name = "Rule";
    private TableColumnAdjuster tableColumnAdjuster;

    public RulesPanel(Environment aEnvironment, Map userOptions) {
        this.setLayout(new BorderLayout());

        table = makeTable(aEnvironment, userOptions);

        scrollPane = new JScrollPane(new JPanel().add(table), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
                
                int rowCount = table.getRowCount();
                TableModel model = table.getModel();
                for(int index = 0; index < rowCount; index++)
                {
                   LatexIcon latexIcon = (LatexIcon) model.getValueAt(index,1);
                   latexIcon.changeSize(.90);
                }
                
                RulesPanel.this.tableColumnAdjuster.adjustColumns();
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
                
                int rowCount = table.getRowCount();
                TableModel model = table.getModel();
                for(int index = 0; index < rowCount; index++)
                {
                   LatexIcon latexIcon = (LatexIcon) model.getValueAt(index,1);
                   latexIcon.changeSize(1.10);
                }
                
                RulesPanel.this.tableColumnAdjuster.adjustColumns();
            }
        });
        buttonsPanel.add(increase);

        add(buttonsPanel, BorderLayout.NORTH);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableColumnAdjuster = new TableColumnAdjuster(table);
        tableColumnAdjuster.adjustColumns();

    }

    public int getSelectedRowCount() {
        return this.table.getSelectedRowCount();
    }

    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    private JTable makeTable(Environment environment, Map m) {
        Cons cons = (Cons) m.get("Theorems");

        final JTable table = new JTable(new RulesTableModel());

        table.setFillsViewportHeight(true);

        RulesTableModel model = (RulesTableModel) table.getModel();

        Cons cons2 = null;

        try {
            int length = Utility.listLength(environment, -1, (Cons) ((Cons) cons.car()).cdr());

            for (int index = 1; index <= length; index++) {
                cons2 = Utility.nth(environment, -1, cons, index);

                Cons ruleName = Utility.nth(environment, -1, cons2, 1);
                String ruleNameString = Utility.toNormalString(environment, -1, ruleName.toString());
                Cons pattern = Utility.nth(environment, -1, cons2, 2);
                Cons patternTex = Utility.nth(environment, -1, cons2, 3);
                String patternTexString = Utility.toNormalString(environment, -1, patternTex.toString());
                Cons replacement = Utility.nth(environment, -1, cons2, 4);
                Cons replacementTex = Utility.nth(environment, -1, cons2, 5);
                String replacementTexString = Utility.toNormalString(environment, -1, replacementTex.toString());


                model.addRow(new Object[]{ruleNameString, new LatexIcon(patternTexString, replacementTexString, 14)});
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        /*
         TeXFormula texFormula = new TeXFormula("a\\_ + b\\_ \\leftarrow b + a");
         Icon rule = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) 12);
         model.addRow(new Object[]{"Commutative +", rule});
         */
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = table.getSelectedRow();
                }
            }
        });

        return table;
    }

    public class RulesTableModel extends DefaultTableModel {

        public RulesTableModel() {

            addColumn(column1Name);
            addColumn(column2Name);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            Class clazz = String.class;

            switch (columnIndex) {

                case 1:
                    clazz = Icon.class;
                    break;

            }

            return clazz;

        }

    }

    private class LatexIcon implements Icon {

        private TeXFormula texFormula;
        private Icon icon;
        private double size = 14;

        public LatexIcon(String patternTexString, String replacementTexString, double size) {
                texFormula = new TeXFormula(patternTexString + " \\ \\ \\boldsymbol{\\textcolor{red}{\\leftarrow}} \\ \\ " + replacementTexString);
                icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) size);
                this.size = size;
        }
        
        public void changeSize(double scale)
        {
            size = size * scale;
            icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) size);
            repaint();
        }
        

        public int getIconHeight() {
            return icon.getIconHeight();
        }

        public int getIconWidth() {
            return icon.getIconWidth();
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            icon.paintIcon(c, g, x, y);
        }

    }

}
