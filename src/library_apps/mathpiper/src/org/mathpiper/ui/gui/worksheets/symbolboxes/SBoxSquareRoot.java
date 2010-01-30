package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxSquareRoot extends SBoxCompoundExpression {

    SBoxSquareRoot(SBox aExpression) {
        super(1);
        iExpressions[0] = aExpression;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpressions[0].calculatePositions(sg, aSize, null);

            Dimension dim = iExpressions[0].getDimension();
            iDimension = new Dimension((int) (dim.width + 6), dim.height + 3);
            iAscent = iExpressions[0].getCalculatedAscent() + 3;
        }

        if (aPosition != null) {

            Dimension dim = iExpressions[0].getDimension();
            iExpressions[0].calculatePositions(sg, aSize, new java.awt.Point((int) (aPosition.x + 6), aPosition.y));
        }
    }

    public void render(ScaledGraphics sg) {
        super.render(sg);
        
        if(drawBoundingBox) drawBoundingBox(sg);

        sg.setLineThickness(1);

        Dimension dim = iExpressions[0].getDimension();
        double x0 = iPosition.x;
        double y0 = iPosition.y - iAscent;
        double x1 = x0 + dim.width + 6;
        double y1 = y0 + dim.height + 6;
        sg.drawLine(x0, y0 + 1, x0 + 3, y1 - 1);
        sg.drawLine(x0 + 3, y1 - 1, x0 + 6, y0 + 2);
        sg.drawLine(x0 + 6, y0 + 1, x1, y0 + 1);
    }
}
