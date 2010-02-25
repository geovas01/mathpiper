package org.mathpiper.ui.gui.worksheets.symbolboxes;


class SubFix extends CompoundExpression {

    double iExtent = 0;
    double iSubOffset = 0;
    double iSuperOffset = 0;

    private SymbolBox iExpr;

    private SymbolBox iSubfix;

    SubFix(SymbolBox aExpr, SymbolBox aSubfix) {

        iExpr = aExpr;
        iSubfix = aSubfix;
    }


    void setSubfix(SymbolBox aExpression) {
        iSubfix = aExpression;
    }



    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {

            Dimensions dsfix = new Dimensions(0, 0);
            Dimensions dlfix = new Dimensions(0, 0);
            iExpr.calculatePositions(sg, aSize, null);


            if (iSubfix != null) {
                iSubfix.calculatePositions(sg, aSize - 1, null);
            }

            Dimensions dexpr = iExpr.getDimension();


            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            if (iExpr instanceof Sum || iExpr instanceof Integral) {
                iSuperOffset = 0;
                iSubOffset = 0;


                if (iSubfix != null) {
                    iExtent = iExtent + iSubfix.iAscent;
                }

                double fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                if (dexpr.width > fixMaxWidth) {
                    fixMaxWidth = dexpr.width;
                }

                iDimension = new Dimensions(fixMaxWidth, (dexpr.height + iExtent));
            } else {


                if (iSubfix != null) {
                    iSubOffset = iSubfix.iAscent;

                    double delta = iSubOffset + (iSubfix.getDimension().height - iSubfix.iAscent) - (iExpr.getDimension().height - iExpr.iAscent);
                    iExtent = iExtent + delta;
                }

                double fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                iDimension = new Dimensions(dexpr.width + fixMaxWidth, (dexpr.height + iExtent));
            }

            iAscent = iExpr.getCalculatedAscent() + iExtent;

            if (iSubfix != null) {
                iAscent = iAscent - iSubfix.getDimension().height;
            }
        }

        if (aPosition != null) {

            Dimensions dsfix = new Dimensions(0, 0);
            Dimensions dlfix = new Dimensions(0, 0);
            Dimensions dexpr = iExpr.getDimension();


            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            iExpr.calculatePositions(sg, aSize, new Position(aPosition.x, aPosition.y));

            if (iExpr instanceof Sum || iExpr instanceof Integral) {

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Position(aPosition.x,  (aPosition.y + iSubfix.iAscent + dlfix.height)));
                }
            } else {

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Position( (aPosition.x + dexpr.width),  (aPosition.y + iSubOffset)));
                }
            }
        }
    }//end calculate positions.

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpr.render(sg);

        iSubfix.render(sg);
    }



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iExpr, this.iSubfix};
    }//end method.




    public String toString()
    {
        String returnString = "<Super Sub Fix>";
        return returnString;
    }//end method.

}//end class
