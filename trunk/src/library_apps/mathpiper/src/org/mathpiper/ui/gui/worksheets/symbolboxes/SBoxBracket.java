/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxBracket extends SBoxCompoundExpression {

    int iBracketWidth;
    String iClose;
    int iFontSize;
    String iOpen;

    SBoxBracket(SBox aExpression, String aOpen, String aClose) {
        super(1);
        iOpen = aOpen;
        iClose = aClose;
        iExpressions[0] = aExpression;
    }

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpressions[0].calculatePositions(g, aSize, null);

            Dimension dim = iExpressions[0].getDimension();
            iFontSize = dim.height;
            g.setFontSize(dim.height);
            iBracketWidth = SBoxBuilder.fontForSize(aSize) / 2;
            iDimension = new Dimension(dim.width + 2 * iBracketWidth, dim.height);
            iAscent = iExpressions[0].getCalculatedAscent();
        }

        if (aPosition != null) {

            Dimension dim = iExpressions[0].getDimension();
            iExpressions[0].calculatePositions(g, aSize, new java.awt.Point(aPosition.x + iBracketWidth, aPosition.y));
        }
    }

    public void render(GraphicsPrimitives g) {
        super.render(g);

        Dimension dim = iExpressions[0].getDimension();
        drawBracket(g, iOpen, iPosition.x, iPosition.y - getCalculatedAscent());
        drawBracket(g, iClose, iPosition.x + dim.width + iBracketWidth, iPosition.y - getCalculatedAscent());
    }

    void drawBracket(GraphicsPrimitives g, String bracket, int x, int y) {

        Dimension dim = iExpressions[0].getDimension();

        if (bracket.equals("[") || bracket.equals("]")) {

            int margin = 2;
            g.setLineThickness(2);

            if (bracket.equals("[")) {
                g.drawLine(x + margin, y, x + margin, y + dim.height);
            } else {
                g.drawLine(x + iBracketWidth - margin, y, x + iBracketWidth - margin, y + dim.height);
            }

            g.setLineThickness(1);
            g.drawLine(x + iBracketWidth - margin, y, x + margin, y);
            g.drawLine(x + margin, y + dim.height, x + iBracketWidth - margin, y + dim.height);
        } else if (bracket.equals("(") || bracket.equals(")")) {

            int xstart;
            int xend;

            if (bracket.equals("(")) {
                xstart = x + iBracketWidth;
                xend = x;
            } else {
                xstart = x;
                xend = x + iBracketWidth;
            }

            int delta = xend - xstart;
            float[] steps = new float[3];
            steps[0] = 0.2f;
            steps[1] = 0.6f;
            steps[2] = 0.8f;
            g.setLineThickness(1f);
            g.drawLine((int) (xstart + (delta * steps[0])), y + (0 * dim.height) / 6, (int) (xstart + (delta * steps[1])), y + (1 * dim.height) / 6);
            g.setLineThickness(1.3f);
            g.drawLine((int) (xstart + (delta * steps[1])), y + (1 * dim.height) / 6, (int) (xstart + (delta * steps[2])), y + (2 * dim.height) / 6);
            g.setLineThickness(1.6f);
            g.drawLine((int) (xstart + (delta * steps[2])), y + (2 * dim.height) / 6, (int) (xstart + (delta * steps[2])), y + (4 * dim.height) / 6);
            g.setLineThickness(1.3f);
            g.drawLine((int) (xstart + (delta * steps[2])), y + (4 * dim.height) / 6, (int) (xstart + (delta * steps[1])), y + (5 * dim.height) / 6);
            g.setLineThickness(1f);
            g.drawLine((int) (xstart + (delta * steps[1])), y + (5 * dim.height) / 6, (int) (xstart + (delta * steps[0])), y + (6 * dim.height) / 6);
        } else {
            g.setFontSize(iFontSize);

            int offset = (iFontSize - iAscent) / 2;
            g.drawText(bracket, x, y + offset);
        }
    }
}
