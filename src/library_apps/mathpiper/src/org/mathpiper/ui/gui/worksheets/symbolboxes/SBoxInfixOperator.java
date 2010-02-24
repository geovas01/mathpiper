package org.mathpiper.ui.gui.worksheets.symbolboxes;


class SBoxInfixOperator extends SBoxCompoundExpression {

    private SBox iLeft;

    private SBox iInfix;

    private SBox iRight;

    SBoxInfixOperator(SBox aLeft, SBox aInfix, SBox aRight) {
        iLeft = aLeft;
        iInfix = aInfix;
        iRight = aRight;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {
            iLeft.calculatePositions(sg, aSize, null);
            iInfix.calculatePositions(sg, aSize, null);
            iRight.calculatePositions(sg, aSize, null);

            Dimensions dleft = iLeft.getDimension();
            Dimensions dinfix = iInfix.getDimension();
            Dimensions dright = iRight.getDimension();
            double height = dleft.height;

            if (height < dinfix.height) {
                height = dinfix.height;
            }

            if (height < dright.height) {
                height = dright.height;
            }

            iDimension = new Dimensions(dleft.width + dinfix.width + dright.width + 4, height);
            iAscent = iLeft.getCalculatedAscent();

            if (iAscent < iInfix.getCalculatedAscent()) {
                iAscent = iInfix.getCalculatedAscent();
            }

            if (iAscent < iRight.getCalculatedAscent()) {
                iAscent = iRight.getCalculatedAscent();
            }
        }

        if (aPosition != null) {

            Dimensions dleft = iLeft.getDimension();
            Dimensions dinfix = iInfix.getDimension();
            Dimensions dright = iRight.getDimension();
            iLeft.calculatePositions(sg, aSize, new Position(aPosition.x, aPosition.y));
            iInfix.calculatePositions(sg, aSize, new Position( (aPosition.x + dleft.width + 2), aPosition.y) );
            iRight.calculatePositions(sg, aSize, new Position( (aPosition.x + dleft.width + dinfix.width + 4), aPosition.y));
        }
    }//end calculatePositions.


    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iLeft.render(sg);

        iInfix.render(sg);

        iRight.render(sg);
    }//end render.

}//end class.
