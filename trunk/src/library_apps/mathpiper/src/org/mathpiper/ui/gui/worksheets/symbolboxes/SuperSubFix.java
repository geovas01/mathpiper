package org.mathpiper.ui.gui.worksheets.symbolboxes;


public class SuperSubFix extends CompoundExpression {

    double iExtent = 0;
    double iSubOffset = 0;
    double iSuperOffset = 0;

    private SymbolBox iExpr;

    private SymbolBox iSuperfix;

    private SymbolBox iSubfix;

    public SuperSubFix(SymbolBox aExpr, SymbolBox aSuperfix, SymbolBox aSubfix) {

        iExpr = aExpr;
        iSuperfix = aSuperfix;
        iSubfix = aSubfix;
    }

    public void setSuperfix(SymbolBox aExpression) {
        iSuperfix = aExpression;
    }

    public void setSubfix(SymbolBox aExpression) {
        iSubfix = aExpression;
    }

    public boolean hasSuperfix() {

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

            if (iSubfix != null) {
                iSubfix.calculatePositions(sg, aSize - 1, null);
            }

            Dimensions dexpr = iExpr.getDimension();

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }

            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            if (iExpr instanceof Sum || iExpr instanceof Integral) {
                iSuperOffset = 0;
                iSubOffset = 0;

                if (iSuperfix != null) {
                    iExtent = iExtent + iSuperfix.iAscent;
                }

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

                if (iSuperfix != null) {
                    iSuperOffset = iSuperfix.getDimension().height - iSuperfix.iAscent - iExpr.getDimension().height / 4;
                    iExtent = iExtent + iSuperOffset + iSuperfix.iAscent;
                }

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

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }

            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            iExpr.calculatePositions(sg, aSize, new Position(aPosition.x, aPosition.y));

            if (iExpr instanceof Sum || iExpr instanceof Integral) {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Position(aPosition.x,  (aPosition.y - iExpr.iAscent - dsfix.height)));
                }

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Position(aPosition.x,  (aPosition.y + iSubfix.iAscent + dlfix.height)));
                }
            } else {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Position( (aPosition.x + dexpr.width),  (aPosition.y - iExpr.iAscent - iSuperOffset)));
                }

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Position( (aPosition.x + dexpr.width),  (aPosition.y + iSubOffset)));
                }
            }
        }
    }//end calculate positions.

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpr.render(sg);

        if(iSuperfix != null)
        {
            iSuperfix.render(sg);
        }
        
        if(iSubfix != null)
        {
            iSubfix.render(sg);
        }
    }



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iExpr, this.iSuperfix, this.iSubfix};
    }//end method.




    public String toString()
    {   
        String returnString = "<Super Sub Fix:";

        if(this.iSuperfix != null)
        {
            returnString = returnString + " Super ";
        }

        if(this.iSubfix != null)
        {
            returnString = returnString + " Sub ";
        }

        returnString = returnString + ">";
        
        return returnString;
    }//end method.

}//end class
