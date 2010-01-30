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

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpressions[0].calculatePositions(sg, aSize, null);

            Dimension dim = iExpressions[0].getDimension();
            iFontSize = dim.height;
            sg.setFontSize(dim.height);
            iBracketWidth = SBoxBuilder.fontForSize(aSize) / 2;
            iDimension = new Dimension(dim.width + 2 * iBracketWidth, dim.height);
            iAscent = iExpressions[0].getCalculatedAscent();
        }

        if (aPosition != null) {

            Dimension dim = iExpressions[0].getDimension();
            iExpressions[0].calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + iBracketWidth, aPosition.y));
        }
    }

    public void render(ScaledGraphics sg) {
        super.render(sg);

        if(drawBoundingBox) drawBoundingBox(sg);

        Dimension dim = iExpressions[0].getDimension();
        drawBracket(sg, iOpen, iPosition.x, iPosition.y - getCalculatedAscent());
        drawBracket(sg, iClose, iPosition.x + dim.width + iBracketWidth, iPosition.y - getCalculatedAscent());
    }

    void drawBracket(ScaledGraphics sg, String bracket, double x, double y) {

        Dimension dim = iExpressions[0].getDimension();

        if (bracket.equals("[") || bracket.equals("]")) {

            int margin = 2;
            sg.setLineThickness(2);

            if (bracket.equals("[")) {
                sg.drawLine(x + margin, y, x + margin, y + dim.height);
            } else {
                sg.drawLine(x + iBracketWidth - margin, y, x + iBracketWidth - margin, y + dim.height);
            }

            sg.setLineThickness(1);
            sg.drawLine(x + iBracketWidth - margin, y, x + margin, y);
            sg.drawLine(x + margin, y + dim.height, x + iBracketWidth - margin, y + dim.height);
        } else if (bracket.equals("(") || bracket.equals(")")) {

            double xstart;
            double xend;

            if (bracket.equals("(")) {
                xstart = x + iBracketWidth;
                xend = x;
            } else {
                xstart = x;
                xend = x + iBracketWidth;
            }

            double delta = xend - xstart;
            double[] steps = new double[3];
            steps[0] = 0.2;
            steps[1] = 0.6;
            steps[2] = 0.8;

            sg.setLineThickness(1);
            sg.drawLine( (xstart + (delta * steps[0])), y + (0 * dim.height) / 6,  xstart + (delta * steps[1]), y + (1 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[1])), y + (1 * dim.height) / 6,  xstart + (delta * steps[2]), y + (2 * dim.height) / 6);

            sg.setLineThickness(1.6);
            sg.drawLine( (xstart + (delta * steps[2])), y + (2 * dim.height) / 6,  xstart + (delta * steps[2]), y + (4 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[2])), y + (4 * dim.height) / 6, xstart + (delta * steps[1]), y + (5 * dim.height) / 6);

            sg.setLineThickness(1);
            sg.drawLine( (xstart + (delta * steps[1])), y + (5 * dim.height) / 6, xstart + (delta * steps[0]), y + (6 * dim.height) / 6);

        } else {
            sg.setFontSize(iFontSize);

            double offset = (iFontSize - iAscent) / 2;
            sg.drawText(bracket, x, y + offset);
        }
    }
}
