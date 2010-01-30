package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxDivisor extends SBoxCompoundExpression {

    int iDashheight = 0;

    SBoxDivisor(SBox aNumerator, SBox aDenominator) {
        super(2);
        iExpressions[0] = aNumerator;
        iExpressions[1] = aDenominator;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;
        iDashheight = SBoxBuilder.fontForSize(iSize);

        if (iDimension == null) {
            iExpressions[0].calculatePositions(sg, aSize, null);
            iExpressions[1].calculatePositions(sg, aSize, null);

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
            double ynumer = aPosition.y - ndim.height + iExpressions[0].getCalculatedAscent() - iDashheight;
            double ydenom = aPosition.y + iExpressions[1].getCalculatedAscent();
            iExpressions[0].calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ndim.width) / 2, (int)ynumer));
            iExpressions[1].calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ddim.width) / 2, (int)ydenom));
        }
    }

    public void render(ScaledGraphics sg) {
        super.render(sg);

        if(drawBoundingBox) drawBoundingBox(sg);

        java.awt.Dimension ndim = iExpressions[0].getDimension();
        java.awt.Dimension ddim = iExpressions[1].getDimension();
        int width = ndim.width;

        if (width < ddim.width) {
            width = ddim.width;
        }

        sg.setLineThickness(1);
        sg.drawLine(iPosition.x, iPosition.y - iDashheight / 2 + 2, iPosition.x + width, iPosition.y - iDashheight / 2 + 2);
    }
}
