package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxDivisor extends SBoxCompoundExpression {

    private int iDashheight = 0;

    private SBox iNumerator;

    private SBox iDenominator;

    SBoxDivisor(SBox aNumerator, SBox aDenominator) {

        iNumerator = aNumerator;

        iDenominator = aDenominator;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;
        iDashheight = SBoxBuilder.fontForSize(iSize);

        if (iDimension == null) {
            iNumerator.calculatePositions(sg, aSize, null);
            iDenominator.calculatePositions(sg, aSize, null);

            Dimension ndim = iNumerator.getDimension();
            Dimension ddim = iDenominator.getDimension();
            int width = ndim.width;

            if (width < ddim.width) {
                width = ddim.width;
            }

            iDimension = new Dimension(width, ndim.height + ddim.height + iDashheight);
            iAscent = ndim.height + iDashheight;
        }

        if (aPosition != null) {

            Dimension ndim = iNumerator.getDimension();
            Dimension ddim = iDenominator.getDimension();
            double ynumer = aPosition.y - ndim.height + iNumerator.getCalculatedAscent() - iDashheight;
            double ydenom = aPosition.y + iDenominator.getCalculatedAscent();
            iNumerator.calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ndim.width) / 2, (int)ynumer));
            iDenominator.calculatePositions(sg, aSize, new java.awt.Point(aPosition.x + (iDimension.width - ddim.width) / 2, (int)ydenom));
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iNumerator.render(sg);
        
        iDenominator.render(sg);

        java.awt.Dimension ndim = iNumerator.getDimension();
        java.awt.Dimension ddim = iDenominator.getDimension();
        int width = ndim.width;

        if (width < ddim.width) {
            width = ddim.width;
        }

        sg.setLineThickness(1);
        sg.drawLine(iPosition.x, iPosition.y - iDashheight / 2 + 2, iPosition.x + width, iPosition.y - iDashheight / 2 + 2);
    }
}
