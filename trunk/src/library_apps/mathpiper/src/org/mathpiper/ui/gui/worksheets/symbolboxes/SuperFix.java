package org.mathpiper.ui.gui.worksheets.symbolboxes;


class SuperFix extends CompoundExpression {

    double iExtent = 0;

    double iSuperOffset = 0;

    private SymbolBox iExpr;

    private SymbolBox iSuperfix;



    SuperFix(SymbolBox aExpr, SymbolBox aSuperfix) {

        iExpr = aExpr;

        iSuperfix = aSuperfix;

    }

    void setSuperfix(SymbolBox aExpression) {
        iSuperfix = aExpression;
    }


    boolean hasSuperfix() {

        return (iSuperfix != null);
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {

            Dimensions dsfix = new Dimensions(0, 0);
            Dimensions dlfix = new Dimensions(0, 0);
            iExpr.calculatePositions(sg, aSize, null);

            if (iSuperfix != null) {
                iSuperfix.calculatePositions(sg, aSize - 1, null);
            }



            Dimensions dexpr = iExpr.getDimension();

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }


            if (iExpr instanceof Sum || iExpr instanceof Integral) {
                iSuperOffset = 0;

                if (iSuperfix != null) {
                    iExtent = iExtent + iSuperfix.iAscent;
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

                if (iSuperfix != null) {
                    iSuperOffset = iSuperfix.getDimension().height - iSuperfix.iAscent - iExpr.getDimension().height / 4;
                    iExtent = iExtent + iSuperOffset + iSuperfix.iAscent;
                }


                double fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                iDimension = new Dimensions(dexpr.width + fixMaxWidth, (dexpr.height + iExtent));
            }

            iAscent = iExpr.getCalculatedAscent() + iExtent;


        }

        if (aPosition != null) {

            Dimensions dsfix = new Dimensions(0, 0);
            Dimensions dlfix = new Dimensions(0, 0);
            Dimensions dexpr = iExpr.getDimension();

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }

            iExpr.calculatePositions(sg, aSize, new Position(aPosition.x, aPosition.y));

            if (iExpr instanceof Sum || iExpr instanceof Integral) {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Position(aPosition.x,  (aPosition.y - iExpr.iAscent - dsfix.height)));
                }


            } else {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Position( (aPosition.x + dexpr.width),  (aPosition.y - iExpr.iAscent - iSuperOffset)));
                }

            }
        }
    }//end calculate positions.

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpr.render(sg);

        iSuperfix.render(sg);

    }



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iExpr, this.iSuperfix};
    }//end method.




    public String toString()
    {
        String returnString = "<SuperFix>";
        return returnString;
    }//end method.

}//end class
