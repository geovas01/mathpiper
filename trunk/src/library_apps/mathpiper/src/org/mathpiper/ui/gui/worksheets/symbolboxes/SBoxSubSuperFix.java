package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;
import java.awt.Point;

class SBoxSubSuperfix extends SBoxCompoundExpression {

    double iExtent = 0;
    double iSubOffset = 0;
    double iSuperOffset = 0;

    private SBox iExpr;

    private SBox iSuperfix;

    private SBox iSubfix;

    SBoxSubSuperfix(SBox aExpr, SBox aSuperfix, SBox aSubfix) {

        iExpr = aExpr;
        iSuperfix = aSuperfix;
        iSubfix = aSubfix;
    }

    void setSuperfix(SBox aExpression) {
        iSuperfix = aExpression;
    }

    void setSubfix(SBox aExpression) {
        iSubfix = aExpression;
    }

    boolean hasSuperfix() {

        return (iSuperfix != null);
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {

            Dimension dsfix = new Dimension(0, 0);
            Dimension dlfix = new Dimension(0, 0);
            iExpr.calculatePositions(sg, aSize, null);

            if (iSuperfix != null) {
                iSuperfix.calculatePositions(sg, aSize - 1, null);
            }

            if (iSubfix != null) {
                iSubfix.calculatePositions(sg, aSize - 1, null);
            }

            Dimension dexpr = iExpr.getDimension();

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }

            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            if (iExpr instanceof SBoxSum || iExpr instanceof SBoxInt) {
                iSuperOffset = 0;
                iSubOffset = 0;

                if (iSuperfix != null) {
                    iExtent = iExtent + iSuperfix.iAscent;
                }

                if (iSubfix != null) {
                    iExtent = iExtent + iSubfix.iAscent;
                }

                int fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                if (dexpr.width > fixMaxWidth) {
                    fixMaxWidth = dexpr.width;
                }

                iDimension = new Dimension(fixMaxWidth, (int) (dexpr.height + iExtent));
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

                int fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                iDimension = new Dimension(dexpr.width + fixMaxWidth, (int) (dexpr.height + iExtent));
            }

            iAscent = iExpr.getCalculatedAscent() + iExtent;

            if (iSubfix != null) {
                iAscent = iAscent - iSubfix.getDimension().height;
            }
        }

        if (aPosition != null) {

            Dimension dsfix = new Dimension(0, 0);
            Dimension dlfix = new Dimension(0, 0);
            Dimension dexpr = iExpr.getDimension();

            if (iSuperfix != null) {
                dsfix = iSuperfix.getDimension();
            }

            if (iSubfix != null) {
                dlfix = iSubfix.getDimension();
            }

            iExpr.calculatePositions(sg, aSize, new Point(aPosition.x, aPosition.y));

            if (iExpr instanceof SBoxSum || iExpr instanceof SBoxInt) {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Point(aPosition.x, (int) (aPosition.y - iExpr.iAscent - dsfix.height)));
                }

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Point(aPosition.x, (int) (aPosition.y + iSubfix.iAscent + dlfix.height)));
                }
            } else {

                if (iSuperfix != null) {
                    iSuperfix.calculatePositions(sg, aSize - 1, new Point(aPosition.x + dexpr.width, (int) (aPosition.y - iExpr.iAscent - iSuperOffset)));
                }

                if (iSubfix != null) {
                    iSubfix.calculatePositions(sg, aSize - 1, new Point(aPosition.x + dexpr.width, (int) (aPosition.y + iSubOffset)));
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
        else
        {
            iSubfix.render(sg);
        }
    }

}//end class.
