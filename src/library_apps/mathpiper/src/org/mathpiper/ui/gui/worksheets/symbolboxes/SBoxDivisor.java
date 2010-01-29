package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxDivisor extends SBoxCompoundExpression {

    int iDashheight = 0;

    SBoxDivisor(SBox aNumerator, SBox aDenominator) {
        super(2);
        iExpressions[0] = aNumerator;
        iExpressions[1] = aDenominator;
    }

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;
        iDashheight = SBoxBuilder.fontForSize(iSize);

        if (iDimension == null) {
            iExpressions[0].calculatePositions(g, aSize, null);
            iExpressions[1].calculatePositions(g, aSize, null);

            Dimension ndim = iExpressions[0].getDimension();
            Dimension ddim = iExpressions[1].getDimension();
            int width = ndim.width;

            if (width < ddim.width) {
                width = ddim.width;
            }

            iDimension = new Dimension(width, ndim.height + ddim.height + iDashheight);
            iAscent = ndim.height + iDashheight;
        }

        if (aPosition != null) {

            Dimension ndim = iExpressions[0].getDimension();
            Dimension ddim = iExpressions[1].getDimension();
            int ynumer = aPosition.y - ndim.height + iExpressions[0].getCalculatedAscent() - iDashheight;
            int ydenom = aPosition.y + iExpressions[1].getCalculatedAscent();
            iExpressions[0].calculatePositions(g, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ndim.width) / 2, ynumer));
            iExpressions[1].calculatePositions(g, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ddim.width) / 2, ydenom));
        }
    }

    public void render(GraphicsPrimitives g) {
        super.render(g);

        if(drawBoundingBox) drawBoundingBox(g);

        java.awt.Dimension ndim = iExpressions[0].getDimension();
        java.awt.Dimension ddim = iExpressions[1].getDimension();
        int width = ndim.width;

        if (width < ddim.width) {
            width = ddim.width;
        }

        g.setLineThickness(1);
        g.drawLine(iPosition.x, iPosition.y - iDashheight / 2 + 2, iPosition.x + width, iPosition.y - iDashheight / 2 + 2);
    }
}
