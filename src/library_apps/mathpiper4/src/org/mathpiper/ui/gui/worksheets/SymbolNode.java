package org.mathpiper.ui.gui.worksheets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import org.mathpiper.lisp.Utility;
import org.scilab.forge.mp.jlatexmath.TeXConstants;
import org.scilab.forge.mp.jlatexmath.TeXFormula;

public class SymbolNode {

    private final static Map<String, String> latexMap = new HashMap();

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
        latexMap.put("/", "/"); // "\\div"
        latexMap.put("*", "*"); // "\\times"
        latexMap.put("==", "=");
        latexMap.put("^", "^\\wedge");
        latexMap.put("Sqrt", "\\sqrt");
        latexMap.put("<-", "\\leftarrow");
        latexMap.put("<--", "\\longleftarrow");

    }
    private String symbolString;

    private List<SymbolNode> children = new ArrayList();

    private Icon icon;

    private TeXFormula texFormula;

    private int treeLeftX;

    private int treeY;

    private Color highlightColor;

    private String highlightNodeShape = "SQUARE";
    
    private String position = "";
    
    private boolean isSlected = false;

    public void setOperator(String symbolString, boolean isCodeForm) throws Exception {

        symbolString = Utility.stripEndQuotesIfPresent(symbolString);
        
        if (symbolString == null) {
            this.symbolString = "NULL";
        } else {
            this.symbolString = symbolString;
        }

        String latex = "";

        if (isCodeForm)
        {
            icon = new DynamicIcon(this.symbolString, 12);
        }
        else
        {
            latex = latexMap.get(symbolString);

            if (latex == null) {
                latex = symbolString;
            }
            
            latex = latex.replace("_", "");

            texFormula = new TeXFormula(latex);

            icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) 12);
        }

    }
    
    public void setOperator(String symbolString, boolean isCodeForm, int wordWrap) throws Exception {
        if(isCodeForm && wordWrap > 0)
        {
            StringBuilder sb = new StringBuilder(symbolString);

            int i = 0;
            while (i + wordWrap < sb.length() && (i = sb.lastIndexOf(" ", i + wordWrap)) != -1) {
                sb.replace(i, i + 1, "\n");
            }

            symbolString = sb.toString();
        }

        setOperator(symbolString, isCodeForm);
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
        return treeLeftX;
    }

    public void setTreeLeftX(int treeX) {
        this.treeLeftX = treeX;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public boolean isSlected() {
        return isSlected;
    }

    public void select(boolean isSlected) {
        this.isSlected = isSlected;
    }


}
