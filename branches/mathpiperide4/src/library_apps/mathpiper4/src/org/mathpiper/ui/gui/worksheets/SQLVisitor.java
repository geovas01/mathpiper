package org.mathpiper.ui.gui.worksheets;

import com.foundationdb.sql.parser.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

// Experimental class to show SQL ASTs.

public class SQLVisitor implements Visitor {

    private SymbolNode rootNode;

    private SymbolNode parentNode;


    public SQLVisitor(SymbolNode rootNode) {
        this.rootNode = rootNode;

        this.parentNode = rootNode;
    }

    public Visitable visit(Visitable node) {

        System.out.println("Catch All :" + node.getClass().getName());

        try {
            if (node instanceof ValueNode) {
                SymbolNode childNode = new SymbolNode();
                parentNode.addChild(childNode);

                Object operator;
                if (node instanceof ConstantNode) {
                    operator = ((ConstantNode) node).getValue();

                } else if (node instanceof BinaryOperatorNode) {
                    operator = "XX " + ((BinaryOperatorNode) node).getOperator();

                } else if (node instanceof ColumnReference) {
                    ColumnReference columnReference = (ColumnReference) node;
                    operator = columnReference.getColumnName();
                    if (columnReference.getTableName() != null) {
                        operator = columnReference.getTableName() + ": " + operator;
                    }

                } else if (node instanceof CurrentDatetimeOperatorNode) {
                    CurrentDatetimeOperatorNode currentDatetimeOperator = (CurrentDatetimeOperatorNode) node;
                    operator = "Current Time";

                }else {
                    operator = node.getClass().getSimpleName();
                }
                
                childNode.setOperator(operator.toString(), true);

            } else {

                SymbolNode childNode = new SymbolNode();
                parentNode.addChild(childNode);
                parentNode = childNode;

                String operator;

                if (node instanceof FromBaseTable) {
                    FromBaseTable fromBaseTable = (FromBaseTable) node;
                    operator = (fromBaseTable.getTableName() != null ? fromBaseTable.getTableName().toString() : "null");
                    
                    operator = "ZZ " + operator;

                } else {
                    operator = node.getClass().getSimpleName();
                    
                    operator = "YY " + operator;
                }

                childNode.setOperator(operator, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;

    }


    public boolean stopTraversal() {
        return false;
    }


    public boolean skipChildren(Visitable node) {
        return false;
    }


    public boolean visitChildrenFirst(Visitable node) {
        return false;
    }

    public static void main(String[] args) {

        try {
            SQLParser parser = new SQLParser();

            //StatementNode stmt = parser.parseStatement("SELECT hotel_no, SUM(price) FROM room r WHERE room_no NOT IN (SELECT room_no FROM booking b, hotel h WHERE (date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE) AND b.hotel_no = h.hotel_no) GROUP BY hotel_no");
            StatementNode stmt = parser.parseStatement("SELECT hotel_no FROM room WHERE (date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE)");

            //StatementNode stmt = parser.parseStatement("SELECT * from hotel,booking where hotel_no = 7");
            
            stmt.treePrint();

            SymbolNode rootNode = new SymbolNode();
            rootNode.setOperator(stmt.getClass().getSimpleName(), true);

            SQLVisitor visitor = new SQLVisitor(rootNode);

            stmt.accept(visitor);

            double viewScale = 1.5;

            TreePanelCons treePanel = new TreePanelCons(null, viewScale, null);
            treePanel.setMainRootNode(rootNode);

            treePanel.layoutTree();

            JFrame frame = new JFrame();
            Container contentPane = frame.getContentPane();
            frame.setBackground(Color.WHITE);
            contentPane.setBackground(Color.WHITE);

            //
            JTabbedPane tabbedPane = new JTabbedPane();
            //Tree viewer.
            JPanel treeControllerPanel = new JPanel();
            treeControllerPanel.setLayout(new BorderLayout());

            //
            MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
            JScrollPane treeScrollPane = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            treeControllerPanel.add(treeScrollPane);
            treeControllerPanel.add(treePanelScaler, BorderLayout.NORTH);
            tabbedPane.addTab("SQL", null, treeControllerPanel, "SQL tree viewer..");
            //

            Box box = Box.createVerticalBox();

            box.add(tabbedPane);

            contentPane.add(box);

            //frame.setAlwaysOnTop(false);
            frame.setTitle("SQL Viewer");
            frame.setResizable(true);
            frame.pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height;
            int width = screenSize.width;
            frame.setSize(width / 2, height / 2);
            frame.setLocationRelativeTo(null);
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
