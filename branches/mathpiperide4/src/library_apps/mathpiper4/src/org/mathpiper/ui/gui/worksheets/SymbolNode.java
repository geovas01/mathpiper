package org.mathpiper.ui.gui.worksheets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import org.scilab.forge.mp.jlatexmath.TeXConstants;
import org.scilab.forge.mp.jlatexmath.TeXFormula;

public class SymbolNode {

    private static Map<String, String> latexMap = new HashMap();

    static {
        latexMap.put(":=", ":=");
        latexMap.put("=?", "=");
        latexMap.put("!=?", "\\neq");
        latexMap.put("<=?", "\\leq");
        latexMap.put(">=?", "\\geq");
        latexMap.put("<?", "<");
        latexMap.put(">?", ">");
        latexMap.put("And?", "\\wedge");
        latexMap.put("Or?", "\\vee");
        latexMap.put("<>", "\\sim");
        latexMap.put("<=>", "\\approx");
        latexMap.put("Implies?", "\\Rightarrow");
        latexMap.put("Equivales?", "\\equiv");
        latexMap.put("%", "\\bmod");
        latexMap.put("Not?", "\\neg");
        latexMap.put("+", "+");
        latexMap.put("-", "-");
        latexMap.put("/", "\\div");
        latexMap.put("*", "\\times");
        latexMap.put("==", "=");
        latexMap.put("^", "^\\wedge");
        latexMap.put("Sqrt", "\\sqrt");

    }
    private String symbolString;

    private List<SymbolNode> children = new ArrayList();

    private Icon icon;

    private TeXFormula texFormula;

    private int treeX;

    private int treeY;

    private Color highlightColor;

    private String highlightNodeShape = "SQUARE";

    public void setOperator(String symbolString, boolean isCodeForm) throws Exception {

        if (symbolString == null) {
            this.symbolString = "NULL";
        } else {
            this.symbolString = symbolString;
        }

        String latex = "";

        if (isCodeForm) {
            latex = symbolString;
        } else {
            latex = latexMap.get(symbolString);

            if (latex == null) {
                latex = symbolString;
            }
        }

        latex = latex.replace("_", "");

        texFormula = new TeXFormula(latex);

	icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) (12));
        //icon = new DynamicIcon(this.symbolString);

    }

    public TeXFormula getTeXFormula() {
        return texFormula;
    }

    public void addChild(SymbolNode child) {
        children.add(child);
    }

    public SymbolNode[] getChildren() {
        return (SymbolNode[]) children.toArray(new SymbolNode[children.size()]);
    }

    public int getNodeWidth() {
        return (int) icon.getIconWidth();
    }

    public int getNodeHeight() {
        return (int) icon.getIconHeight();
    }

    public int getTreeX() {
        return treeX;
    }

    public void setTreeX(int treeX) {
        this.treeX = treeX;
    }

    public int getTreeY() {
        return treeY;
    }

    public void setTreeY(int treeY) {
        this.treeY = treeY;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color color) {
        this.highlightColor = color;
    }

    public String getHighlightNodeShape() {
        return highlightNodeShape;
    }

    public void setHighlightNodeShape(String hilightNodeShape) {
        this.highlightNodeShape = hilightNodeShape;
    }

    public String toString() {
        return symbolString;
    }

    public TeXFormula getTexFormula() {
        return this.texFormula;
    }

    public Icon getIcon() {
        return icon;
    }

    private class DynamicIcon implements Icon {

        Font font = new Font("SansSerif", Font.PLAIN, 12);
        private final static int DEFAULT_SIZE = 16;
        private int width = DEFAULT_SIZE;
        private int height = DEFAULT_SIZE;

        private String iconText;

        public DynamicIcon(String iconText) {
            this.iconText = iconText;

            recalculateIconWidth(iconText);
        }

        private void recalculateIconWidth(String iconText) {
            FontRenderContext frc = new FontRenderContext(null, true, true);
            Rectangle2D bounds = font.getStringBounds(iconText, frc);
            width = (int) bounds.getWidth();
            height = (int) bounds.getHeight();
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setFont(font);

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //FontColor
            g2d.setPaint(Color.BLACK);
            g2d.drawString(iconText, x, y);
        }
    }

}
