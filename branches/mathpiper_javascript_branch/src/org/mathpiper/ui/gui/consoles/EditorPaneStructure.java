package org.mathpiper.ui.gui.consoles;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.Enumeration;

public class EditorPaneStructure extends JPanel {

    JEditorPane sourcePane;
    JLabel lblViewBounds = new JLabel() {

        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(new Color(200, 200, 255, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    };
    JTree trDocument = new JTree() {

        public String getToolTipText(MouseEvent event) {
            return processDocumentTooltip(event);
        }

    };
    JTree trView = new JTree() {

        public String getToolTipText(MouseEvent event) {
            return processViewTooltip(event);
        }

    };
    JButton btnRefresh = new JButton("Refresh");


    public EditorPaneStructure(JEditorPane source) {
        this.sourcePane = source;

        init();
        initListeners();
    }


    protected void init() {

        setLayout(new BorderLayout());

        JPanel treePanel = new JPanel();

        treePanel.setLayout(new GridBagLayout());

        treePanel.add(new JLabel("Document structure"), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        JScrollPane scroll = new JScrollPane(trDocument);
        scroll.setPreferredSize(new Dimension(300, 200));
        treePanel.add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        treePanel.add(new JLabel("Views structure (Select node to highlight the view's bounds)"), new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        scroll = new JScrollPane(trView);
        scroll.setPreferredSize(new Dimension(300, 200));
        treePanel.add(scroll, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        treePanel.add(btnRefresh, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        btnRefresh.setToolTipText("Press here to refresh trees");

        this.add(treePanel);


        JPanel buttonPanel = new JPanel();

        JButton refreshButton = new javax.swing.JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                EditorPaneStructure.this.refresh();
            }//end method.

        });
        refreshButton.setEnabled(true);
        buttonPanel.add(refreshButton);

        this.add(buttonPanel, BorderLayout.NORTH);
    }


    protected void initListeners() {
        btnRefresh.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                refresh();
            }

        });

        trView.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                if (e.getNewLeadSelectionPath() != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
                    View v = (View) node.getUserObject();
                    if (v.getParent() == null) {
                        View vParent = (View) ((DefaultMutableTreeNode) node.getParent()).getUserObject();
                        v = vParent.getView(vParent.getViewIndex(v.getStartOffset(), Position.Bias.Forward));
                    }
                    Rectangle r = getAllocation(v, sourcePane).getBounds();
                    lblViewBounds.setBounds(r);
                    sourcePane.add(lblViewBounds);
                    sourcePane.repaint();
                }
            }

        });
    }


    public void refresh() {
        if (sourcePane != null) {
            Document doc = sourcePane.getDocument();

            Element elem = doc.getDefaultRootElement();
            if (elem instanceof TreeNode) {
                trDocument.setModel(new DefaultTreeModel((TreeNode) elem));
            } else {
                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(elem);
                buildElementsTree(node1, elem);
                trDocument.setModel(new DefaultTreeModel(node1));
            }
            int row = 0;
            while (row < trDocument.getRowCount()) {
                trDocument.expandRow(row);
                row++;
            }
            trDocument.setToolTipText(" ");

            View v = sourcePane.getUI().getRootView(sourcePane);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(v);
            buildViewTree(node, v);
            trView.setModel(new DefaultTreeModel(node));
            row = 0;
            while (row < trView.getRowCount()) {
                trView.expandRow(row);
                row++;
            }
            trView.setToolTipText(" ");
        }
    }


    public void buildElementsTree(DefaultMutableTreeNode root, Element elem) {
        for (int i = 0; i < elem.getElementCount(); i++) {
            AttributeSet attrs = getAttributes(elem.getElement(i));
            String str = elem.getElement(i).toString() + " " + attrs.getClass().getName() + "@" + Integer.toHexString(attrs.hashCode());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(str);
            root.add(node);
            buildElementsTree(node, elem.getElement(i));
        }
    }


    public void buildViewTree(DefaultMutableTreeNode root, View v) {
        for (int i = 0; i < v.getViewCount(); i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(v.getView(i));
            root.add(node);
            buildViewTree(node, v.getView(i));
        }
    }


    protected AttributeSet getAttributes(Element elem) {
        if (elem instanceof AbstractDocument.AbstractElement) {
            try {
                Field f = AbstractDocument.AbstractElement.class.getDeclaredField("attributes");
                f.setAccessible(true);
                AttributeSet res = (AttributeSet) f.get(elem);
                return res;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    protected String processDocumentTooltip(MouseEvent e) {
        int rn = trDocument.getRowForLocation(e.getX(), e.getY());
        if (trDocument.getPathForRow(rn) != null) {
            Element tn = (Element) trDocument.getPathForRow(rn).getLastPathComponent();
            StringBuffer buff = new StringBuffer();
            buff.append("<html>");
            buff.append("<b>Start offset: </b>").append(tn.getStartOffset()).append("<br>");
            buff.append("<b>End offset: </b>").append(tn.getEndOffset()).append("<br>");
            buff.append("<b>Child count: </b>").append(tn.getElementCount()).append("<br>");
            buff.append("<b>Text: </b>\"").append(getText(tn.getDocument(), tn.getStartOffset(), tn.getEndOffset())).append("\"<br>");
            buff.append("<b>Attributes: </b>").append("<br>");
            Enumeration names = tn.getAttributes().getAttributeNames();
            while (names.hasMoreElements()) {
                Object name = names.nextElement();
                Object value = tn.getAttributes().getAttribute(name);
                buff.append("&nbsp;&nbsp;<b>").append(name).append(":</b>").append(value).append("<br>");
            }
            buff.append("</html>");
            return buff.toString();
        }

        return null;
    }


    protected String getText(Document doc, int startOffset, int endOffset) {
        try {
            String text = doc.getText(startOffset, endOffset - startOffset);
            text = text.replaceAll("\n", "\\\\n");
            text = text.replaceAll("\t", "\\\\t");
            text = text.replaceAll("\r", "\\\\r");

            return text;
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }

        return null;
    }


    protected String processViewTooltip(MouseEvent e) {
        int rn = trView.getRowForLocation(e.getX(), e.getY());
        if (trView.getPathForRow(rn) != null) {
            View tn = (View) ((DefaultMutableTreeNode) trView.getPathForRow(rn).getLastPathComponent()).getUserObject();
            StringBuffer buff = new StringBuffer();
            buff.append("<html>");
            buff.append("<b>Start offset: </b>").append(tn.getStartOffset()).append("<br>");
            buff.append("<b>End offset: </b>").append(tn.getEndOffset()).append("<br>");
            buff.append("<b>Child count: </b>").append(tn.getViewCount()).append("<br>");
            buff.append("<b>Text: </b>\"").append(getText(tn.getDocument(), tn.getStartOffset(), tn.getEndOffset())).append("\"<br>");
            if (tn.getAttributes() != null) {
                buff.append("<b>Attributes: </b>").append("<br>");
                Enumeration names = tn.getAttributes().getAttributeNames();
                while (names.hasMoreElements()) {
                    Object name = names.nextElement();
                    Object value = tn.getAttributes().getAttribute(name);
                    buff.append("&nbsp;&nbsp;<b>").append(name).append(":</b>").append(value).append("<br>");
                }
            }
            buff.append("</html>");
            return buff.toString();
        }

        return null;
    }


    protected static Shape getAllocation(View v, JEditorPane edit) {
        Insets ins = edit.getInsets();
        View vParent = v.getParent();
        int x = ins.left;
        int y = ins.top;
        while (vParent != null) {
            int i = vParent.getViewIndex(v.getStartOffset(), Position.Bias.Forward);
            Shape alloc = vParent.getChildAllocation(i, new Rectangle(0, 0, Short.MAX_VALUE, Short.MAX_VALUE));
            x += alloc.getBounds().x;
            y += alloc.getBounds().y;

            vParent = vParent.getParent();
        }

        if (v instanceof BoxView) {
            int ind = v.getParent().getViewIndex(v.getStartOffset(), Position.Bias.Forward);
            Rectangle r2 = v.getParent().getChildAllocation(ind, new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE)).getBounds();

            return new Rectangle(x, y, r2.width, r2.height);
        }

        return new Rectangle(x, y, (int) v.getPreferredSpan(View.X_AXIS), (int) v.getPreferredSpan(View.Y_AXIS));
    }

}
