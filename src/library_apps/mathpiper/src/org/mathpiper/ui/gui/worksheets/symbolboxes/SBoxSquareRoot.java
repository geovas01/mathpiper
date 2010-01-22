package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxSquareRoot extends SBoxCompoundExpression {

    SBoxSquareRoot(SBox aExpression) {
        super(1);
        iExpressions[0] = aExpression;
    }

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpressions[0].calculatePositions(g, aSize, null);

            Dimension dim = iExpressions[0].getDimension();
            iDimension = new Dimension((int) (dim.width + 6), dim.height + 3);
            iAscent = iExpressions[0].getCalculatedAscent() + 3;
        }

        if (aPosition != null) {

            Dimension dim = iExpressions[0].getDimension();
            iExpressions[0].calculatePositions(g, aSize, new java.awt.Point((int) (aPosition.x + 6), aPosition.y));
        }
    }

    public void render(GraphicsPrimitives g) {
        super.render(g);
        g.setLineThickness(1);

        Dimension dim = iExpressions[0].getDimension();
        int x0 = iPosition.x;
        int y0 = iPosition.y - iAscent;
        int x1 = x0 + dim.width + 6;
        int y1 = y0 + dim.height + 6;
        g.drawLine(x0, y0 + 1, x0 + 3, y1 - 1);
        g.drawLine(x0 + 3, y1 - 1, x0 + 6, y0 + 2);
        g.drawLine(x0 + 6, y0 + 1, x1, y0 + 1);
    }
}
