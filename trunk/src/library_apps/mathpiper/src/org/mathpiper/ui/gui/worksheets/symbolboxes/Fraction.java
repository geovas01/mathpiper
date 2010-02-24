package org.mathpiper.ui.gui.worksheets.symbolboxes;

class Fraction extends CompoundExpression {

    private int iDashheight = 0;

    private SymbolBox iNumerator;

    private SymbolBox iDenominator;

    Fraction(SymbolBox aNumerator, SymbolBox aDenominator) {

        iNumerator = aNumerator;

        iDenominator = aDenominator;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;
        iDashheight = ScaledGraphics.fontForSize(iSize);

        if (iDimension == null) {
            iNumerator.calculatePositions(sg, aSize, null);
            iDenominator.calculatePositions(sg, aSize, null);

            Dimensions ndim = iNumerator.getDimension();
            Dimensions ddim = iDenominator.getDimension();
            double width = ndim.width;

            if (width < ddim.width) {
                width = ddim.width;
            }

            iDimension = new Dimensions(width, ndim.height + ddim.height + iDashheight);
            iAscent = ndim.height + iDashheight;
        }

        if (aPosition != null) {

            Dimensions ndim = iNumerator.getDimension();
            Dimensions ddim = iDenominator.getDimension();
            double ynumer = aPosition.y - ndim.height + iNumerator.getCalculatedAscent() - iDashheight;
            double ydenom = aPosition.y + iDenominator.getCalculatedAscent();
            iNumerator.calculatePositions(sg, aSize, new Position( (aPosition.x + (iDimension.width - ndim.width) / 2), ynumer));
            iDenominator.calculatePositions(sg, aSize, new Position( (aPosition.x + (iDimension.width - ddim.width) / 2), ydenom));
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iNumerator.render(sg);
        
        iDenominator.render(sg);

        Dimensions ndim = iNumerator.getDimension();
        Dimensions ddim = iDenominator.getDimension();
        double width = ndim.width;

        if (width < ddim.width) {
            width = ddim.width;
        }

        sg.setLineThickness(1);
        sg.drawLine(iPosition.x, iPosition.y - iDashheight / 2 + 2, iPosition.x + width, iPosition.y - iDashheight / 2 + 2);
    }
}
