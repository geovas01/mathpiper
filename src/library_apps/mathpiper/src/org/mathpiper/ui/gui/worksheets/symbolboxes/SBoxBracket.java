/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;
import java.awt.Dimension;

class SBoxBracket extends SBoxCompoundExpression {

    int iBracketWidth;
    String iClose;
    int iFontSize;
    String iOpen;

    private SBox iExpression;

    SBoxBracket(SBox aExpression, String aOpen, String aClose) {
        //super(1);
        iOpen = aOpen;
        iClose = aClose;
        iExpression = aExpression;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpression.calculatePositions(sg, aSize, null);

            Dimension dim = iExpression.getDimension();
            iFontSize = dim.height;
            sg.setFontSize(dim.height);
            iBracketWidth = SBoxBuilder.fontForSize(aSize) / 2;
            iDimension = new Dimension(dim.width + 2 * iBracketWidth, dim.height);
            iAscent = iExpression.getCalculatedAscent();
        }

        if (aPosition != null) {

            Dimension dim = iExpression.getDimension();
            iExpression.calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + iBracketWidth, aPosition.y));
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpression.render(sg);

        Dimension dim = iExpression.getDimension();
        drawBracket(sg, iOpen, iPosition.x, iPosition.y - getCalculatedAscent());
        drawBracket(sg, iClose, iPosition.x + dim.width + iBracketWidth, iPosition.y - getCalculatedAscent());
    }

    void drawBracket(ScaledGraphics sg, String bracket, double x, double y) {

        Dimension dim = iExpression.getDimension();

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

            double verticalOffset = 2;

            steps[0] = 0.2;
            steps[1] = 0.6;
            steps[2] = 0.8;

            sg.setLineThickness(1.1);
            sg.drawLine( (xstart + (delta * steps[0])), y + verticalOffset + (0 * dim.height) / 6,  xstart + (delta * steps[1]), y + verticalOffset + (1 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[1])), y + verticalOffset + (1 * dim.height) / 6,  xstart + (delta * steps[2]), y + verticalOffset + (2 * dim.height) / 6);

            sg.setLineThickness(1.5);
            sg.drawLine( (xstart + (delta * steps[2])), y + verticalOffset + (2 * dim.height) / 6,  xstart + (delta * steps[2]), y + verticalOffset + (4 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[2])), y + verticalOffset + (4 * dim.height) / 6, xstart + (delta * steps[1]), y + verticalOffset + (5 * dim.height) / 6);

            sg.setLineThickness(1.1);
            sg.drawLine( (xstart + (delta * steps[1])), y + verticalOffset + (5 * dim.height) / 6, xstart + (delta * steps[0]), y + verticalOffset + (6 * dim.height) / 6);


           /* sg.setColor(Color.RED);
            sg.setLineThickness(1.6);
            sg.drawLine( (xstart + (delta * steps[2])), y + (2 * dim.height) / 6,  xstart + (delta * steps[2]), y + (4 * dim.height) / 6);

            sg.drawArc(xstart + (delta * .8), y + (0 * dim.height)/6,30, 30, 180, -60);
            sg.setColor(Color.black);*/

        } else {
            sg.setFontSize(iFontSize);

            double offset = (iFontSize - iAscent) / 2;
            sg.drawText(bracket, x, y + offset);
        }
    }
}
