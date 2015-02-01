package org.mathpiper.ui.gui.worksheets;

import com.foundationdb.sql.parser.*;
import com.foundationdb.sql.unparser.NodeToString;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// Experimental class to show SQL ASTs.

public class SQLVisitor2 implements Visitor {

    private SymbolNode rootNode;

    private boolean stopTraversal = false;


    public SQLVisitor2() {
    }

    public Visitable visit(Visitable node) {

        System.out.println("Catch All :" + node.getClass().getName());

        if (node instanceof SelectNode) {

            try {

                stopTraversal = true;

                handleNode((SelectNode) node, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return node;

    }

    private void handleNode(QueryTreeNode treeNode, SymbolNode symbolNode) throws Exception {
        if (treeNode instanceof ValueNode) {
            valueNode((ValueNode) treeNode, symbolNode);
        } else {
            queryNode(treeNode, symbolNode);
        }
    }

    private void queryNode(QueryTreeNode treeNode, SymbolNode symbolNode) throws Exception {

        if (treeNode instanceof SelectNode) {

            try {
                SelectNode selectNode = (SelectNode) treeNode;

                SymbolNode selectSymbolNode = new SymbolNode();

                if (symbolNode == null) {
                    rootNode = selectSymbolNode;
                } else {
                    symbolNode.addChild(selectSymbolNode);
                }

                selectSymbolNode.setOperator(selectNode.statementToString(), true);

                // Result clause.
                ResultColumnList resultColumnsList = (ResultColumnList) selectNode.getResultColumns();

                //SymbolNode resultColumnNode = new SymbolNode();
                //parentNode.addChild(resultColumnNode);
                //resultColumnNode.setOperator("ResultColumns", true);
                for (int index = 0; index < resultColumnsList.size(); index++) {
                    QueryTreeNode elt = resultColumnsList.get(index);

                    handleNode(elt, selectSymbolNode);
                }

                // From clause.
                FromList fromList = (FromList) selectNode.getFromList();

                SymbolNode fromClauseNode = new SymbolNode();
                selectSymbolNode.addChild(fromClauseNode);
                fromClauseNode.setOperator("FRkkkkOM", true);

                for (int index = 0; index < fromList.size(); index++) {
                    QueryTreeNode elt = fromList.get(index);

                    handleNode(elt, fromClauseNode);
                }

                // WHERE clause
                ValueNode valueNode = (ValueNode) selectNode.getWhereClause();
                if (valueNode != null) {
                    SymbolNode whereClauseNode = new SymbolNode();
                    //selectSymbolNode.addChild(whereClauseNode);
                    fromClauseNode.addChild(whereClauseNode);
                    whereClauseNode.setOperator("WHERE", true);

                    valueNode(valueNode, whereClauseNode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (treeNode instanceof FromBaseTable) {
            FromBaseTable fromBaseTable = (FromBaseTable) treeNode;
            String operator = (fromBaseTable.getTableName() != null ? fromBaseTable.getTableName().toString() : "null");
            SymbolNode fromBaseTableNode = new SymbolNode();
            fromBaseTableNode.setOperator(operator, true);
            symbolNode.addChild(fromBaseTableNode);
        } else if (treeNode instanceof JoinNode) {
            JoinNode joinNode = (JoinNode) treeNode;
            //String operator = (fromBaseTable.getTableName() != null ? fromBaseTable.getTableName().toString() : "null");
            String operator = new NodeToString().toString(joinNode);
            System.out.println(operator);
            SymbolNode join = new SymbolNode();
            join.setOperator(operator, true);
            symbolNode.addChild(join);
        } else {
            SymbolNode childNode = new SymbolNode();
            childNode.setOperator("QueryNode:" + treeNode.getClass().getSimpleName(), true);
            symbolNode.addChild(childNode);

        }
    }

    private void valueNode(ValueNode valueNode, SymbolNode symbolNode) throws Exception {

        if (valueNode instanceof BinaryOperatorNode) {
            BinaryOperatorNode binaryOperatorNode = (BinaryOperatorNode) valueNode;

            SymbolNode binaryOperator = new SymbolNode();

            symbolNode.addChild(binaryOperator);

            binaryOperator.setOperator(binaryOperatorNode.getOperator(), true);

            ValueNode leftOperand = binaryOperatorNode.getLeftOperand();

            handleNode(leftOperand, binaryOperator);

            ValueNode rightOperand = binaryOperatorNode.getRightOperand();

            handleNode(rightOperand, binaryOperator);

        } else if (valueNode instanceof ConstantNode) {
            ConstantNode constantNode = (ConstantNode) valueNode;
            SymbolNode constant = new SymbolNode();
            constant.setOperator(((ConstantNode) constantNode).getValue().toString(), true);
            symbolNode.addChild(constant);
        } else if (valueNode instanceof ColumnReference) {
            ColumnReference columnReference = (ColumnReference) valueNode;
            String operator = columnReference.getColumnName();
            if (columnReference.getTableName() != null) {
                operator = columnReference.getTableName() + ": " + operator;
            }
            SymbolNode columnNameNode = new SymbolNode();
            columnNameNode.setOperator(operator, true);
            symbolNode.addChild(columnNameNode);

        } else if (valueNode instanceof CurrentDatetimeOperatorNode) {
            //CurrentDatetimeOperatorNode currentDatetimeOperator = (CurrentDatetimeOperatorNode) valueNode;
            SymbolNode currentDatetimeNode = new SymbolNode();
            currentDatetimeNode.setOperator("CurrentTime", true);
            symbolNode.addChild(currentDatetimeNode);

        } else if (valueNode instanceof ResultColumn) {
            ResultColumn resultColumn = (ResultColumn) valueNode;
            SymbolNode resultColumnNode = new SymbolNode();
            String operator = "XX";
            if (resultColumn instanceof AllResultColumn) {
                AllResultColumn allResultColumn = (AllResultColumn) resultColumn;
                operator = (allResultColumn.getFullTableName() != null ? allResultColumn.getFullTableName() + ".*" : "*");
            } else {
                operator = (resultColumn.getTableName() != null ? resultColumn.getTableName() + "." + resultColumn.getName() : resultColumn.getName());

            }
            resultColumnNode.setOperator(operator, true);
            symbolNode.addChild(resultColumnNode);

        } else if (valueNode instanceof SubqueryNode) {
            SubqueryNode subquery = (SubqueryNode) valueNode;
            handleNode(subquery.getResultSet(), symbolNode);

        } else {
            SymbolNode unknownNode = new SymbolNode();
            unknownNode.setOperator("ValueNode:" + valueNode.getClass().getSimpleName(), true);
            symbolNode.addChild(unknownNode);
        }
    }


    public boolean stopTraversal() {
        return stopTraversal;
    }


    public boolean skipChildren(Visitable node) {
        return false;
    }


    public boolean visitChildrenFirst(Visitable node) {
        return false;
    }

    public SymbolNode getRootNode() {
        return this.rootNode;
    }

    public static void main(String[] args) {

        String query = "NULL";

        // ======= Dreamhome
        /// 13.2
        // query = "select sno, fname, lname, salary from staff";
        // 13.4
        //query = "select sno, fname, lname, salary/12 from staff";
        /// 13.5
        //query = "select sno, fname, lname, position, salary from staff where salary > 10000";
        /// 13.6
        //query = "select bno, street, area, city, pcode from branch where city = 'London' OR city = 'Glasgow'";

        // 13.7
        query = "select sno, fname, lname, position, salary from staff where salary between 20000 AND 30000";
 
        
// 13.19
        //query = "select sno, fname, lname, position from staff where bno = (select bno from branch where street = '163 Main St')";
        // ======= Hotel 
        // 13.8.
        //String query = "SELECT * FROM hotel WHERE address LIKE '%London%'";
        //String query = "SELECT hotel_no, SUM(price) FROM room r WHERE room_no NOT IN (SELECT room_no FROM booking b, hotel h WHERE (date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE) AND b.hotel_no = h.hotel_no) GROUP BY hotel_no";
        //String query = "SELECT hotel_no,booking FROM room WHERE (date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE)";
        //String query = "SELECT * FROM guest WHERE guest_no = (SELECT guest_no FROM booking WHERE date_from <= CURRENT_DATE AND date_to >= CURRENT_DATE AND hotel_no = (SELECT hotel_no FROM hotel WHERE name = 'Grosvenor'))";
        //String query = "SELECT * from hotel,booking where hotel_no = 7";
        //String query = "select b.*, p.* from branch1 b LEFT JOIN property_for_rent1 p ON b.bcity = p.pcity";
        //String query = "SELECT AVG(price) FROM booking b, room r, hotel h WHERE (b.date_from <= CURRENT_DATE AND b.date_to >= CURRENT_DATE) AND r.hotel_no = h.hotel_no and r.room_no = b.room_no";
        // Hotel 13.18.
        //String query = "select r.*,occName from room r Left Join (select g.guest_no,g.name as occName,b.room_no as roomnum from booking b Left Join guest g on g.guest_no = b.guest_no where b.hotel_no =     (select hotel_no     from hotel     where name='Grosvenor') and current_date between date_from and date_to) on r.room_no=roomnum Where r.hotel_no =     (select hotel_no     from hotel     where name='Grosvenor')";
        try {
            SQLParser parser = new SQLParser();

            StatementNode stmt = parser.parseStatement(query);

            stmt.treePrint();

            SQLVisitor2 visitor = new SQLVisitor2();

            stmt.accept(visitor);

            double viewScale = 1.5;

            TreePanelCons treePanel = new TreePanelCons(null, viewScale, null);
            treePanel.setMainRootNode(visitor.getRootNode());

            treePanel.layoutTree();

            JFrame frame = new JFrame();
            Container contentPane = frame.getContentPane();
            frame.setBackground(Color.WHITE);
            contentPane.setBackground(Color.WHITE);

            //
            //Tree viewer.
            JPanel treeControllerPanel = new JPanel();
            treeControllerPanel.setLayout(new BorderLayout());

            //
            MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
            JScrollPane treeScrollPane = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            treeControllerPanel.add(treeScrollPane);
            treeControllerPanel.add(treePanelScaler, BorderLayout.NORTH);

            Box box = Box.createVerticalBox();

            box.add(treeControllerPanel);

            contentPane.add(box);

            JLabel sqlQueryLabel = new JLabel(query);

            sqlQueryLabel.setFont(new Font(sqlQueryLabel.getName(), Font.PLAIN, 30));

            JPanel jPanel = new JPanel();
            jPanel.setBackground(Color.WHITE);
            jPanel.add(sqlQueryLabel);

            JScrollPane queryScrollPane = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            queryScrollPane.getViewport().setPreferredSize(new Dimension(sqlQueryLabel.getWidth(), sqlQueryLabel.getHeight() + 50));

            contentPane.add(queryScrollPane, BorderLayout.NORTH);

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
