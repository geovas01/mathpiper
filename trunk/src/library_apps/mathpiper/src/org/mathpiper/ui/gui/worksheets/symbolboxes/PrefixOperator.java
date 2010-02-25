package org.mathpiper.ui.gui.worksheets.symbolboxes;



class PrefixOperator extends CompoundExpression {

    private SymbolBox iLeft;

    private SymbolBox iRight;

    PrefixOperator(SymbolBox aLeft, SymbolBox aRight) {
        //super(2);
        iLeft = aLeft;
        iRight = aRight;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {
            iLeft.calculatePositions(sg, aSize, null);
            iRight.calculatePositions(sg, aSize, null);

            Dimensions dleft = iLeft.getDimension();
            Dimensions dright = iRight.getDimension();
            double height = dleft.height;

            if (height < dright.height) {
                height = dright.height;
            }

            iDimension = new Dimensions(dleft.width + dright.width + 2, height);
            iAscent = iLeft.getCalculatedAscent();

            if (iAscent < iRight.getCalculatedAscent()) {
                iAscent = iRight.getCalculatedAscent();
            }
        }

        if (aPosition != null) {

            Dimensions dleft = iLeft.getDimension();
            Dimensions dright = iRight.getDimension();
            iLeft.calculatePositions(sg, aSize, new Position(aPosition.x, aPosition.y)); /*+(iAscent-iLeft.getCalculatedAscent())*/
            iRight.calculatePositions(sg, aSize, new Position( (aPosition.x + dleft.width + 2), aPosition.y)); /*+(iAscent-iRight.getCalculatedAscent())*/
        }
    }//end calculatePositions.


    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iLeft.render(sg);

        iRight.render(sg);
    }//end render.



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iLeft, this.iRight};
    }//end method.




    public String toString()
    {
        String returnString = "<Prefix Operator>";
        return returnString;
    }//end method.

}//end class
