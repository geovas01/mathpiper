package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;
import java.awt.Point;

class SBoxSubSuperfix extends SBoxCompoundExpression {

    int iExtent = 0;
    int iSubOffset = 0;
    int iSuperOffset = 0;

    SBoxSubSuperfix(SBox aExpr, SBox aSuperfix, SBox aSubfix) {
        super(3);
        iExpressions[0] = aExpr;
        iExpressions[1] = aSuperfix;
        iExpressions[2] = aSubfix;
    }

    void setSuperfix(SBox aExpression) {
        iExpressions[1] = aExpression;
    }

    void setSubfix(SBox aExpression) {
        iExpressions[2] = aExpression;
    }

    boolean hasSuperfix() {

        return (iExpressions[1] != null);
    }

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {

            Dimension dsfix = new Dimension(0, 0);
            Dimension dlfix = new Dimension(0, 0);
            iExpressions[0].calculatePositions(g, aSize, null);

            if (iExpressions[1] != null) {
                iExpressions[1].calculatePositions(g, aSize - 1, null);
            }

            if (iExpressions[2] != null) {
                iExpressions[2].calculatePositions(g, aSize - 1, null);
            }

            Dimension dexpr = iExpressions[0].getDimension();

            if (iExpressions[1] != null) {
                dsfix = iExpressions[1].getDimension();
            }

            if (iExpressions[2] != null) {
                dlfix = iExpressions[2].getDimension();
            }

            if (iExpressions[0] instanceof SBoxSum || iExpressions[0] instanceof SBoxInt) {
                iSuperOffset = 0;
                iSubOffset = 0;

                if (iExpressions[1] != null) {
                    iExtent = iExtent + iExpressions[1].iAscent;
                }

                if (iExpressions[2] != null) {
                    iExtent = iExtent + iExpressions[2].iAscent;
                }

                int fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                if (dexpr.width > fixMaxWidth) {
                    fixMaxWidth = dexpr.width;
                }

                iDimension = new Dimension(fixMaxWidth, dexpr.height + iExtent);
            } else {

                if (iExpressions[1] != null) {
                    iSuperOffset = iExpressions[1].getDimension().height - iExpressions[1].iAscent - iExpressions[0].getDimension().height / 4;
                    iExtent = iExtent + iSuperOffset + iExpressions[1].iAscent;
                }

                if (iExpressions[2] != null) {
                    iSubOffset = iExpressions[2].iAscent;

                    int delta = iSubOffset + (iExpressions[2].getDimension().height - iExpressions[2].iAscent) - (iExpressions[0].getDimension().height - iExpressions[0].iAscent);
                    iExtent = iExtent + delta;
                }

                int fixMaxWidth = dsfix.width;

                if (dlfix.width > fixMaxWidth) {
                    fixMaxWidth = dlfix.width;
                }

                iDimension = new Dimension(dexpr.width + fixMaxWidth, dexpr.height + iExtent);
            }

            iAscent = iExpressions[0].getCalculatedAscent() + iExtent;

            if (iExpressions[2] != null) {
                iAscent = iAscent - iExpressions[2].getDimension().height;
            }
        }

        if (aPosition != null) {

            Dimension dsfix = new Dimension(0, 0);
            Dimension dlfix = new Dimension(0, 0);
            Dimension dexpr = iExpressions[0].getDimension();

            if (iExpressions[1] != null) {
                dsfix = iExpressions[1].getDimension();
            }

            if (iExpressions[2] != null) {
                dlfix = iExpressions[2].getDimension();
            }

            iExpressions[0].calculatePositions(g, aSize, new Point(aPosition.x, aPosition.y));

            if (iExpressions[0] instanceof SBoxSum || iExpressions[0] instanceof SBoxInt) {

                if (iExpressions[1] != null) {
                    iExpressions[1].calculatePositions(g, aSize - 1, new Point(aPosition.x, aPosition.y - iExpressions[0].iAscent - dsfix.height));
                }

                if (iExpressions[2] != null) {
                    iExpressions[2].calculatePositions(g, aSize - 1, new Point(aPosition.x, aPosition.y + iExpressions[2].iAscent + dlfix.height));
                }
            } else {

                if (iExpressions[1] != null) {
                    iExpressions[1].calculatePositions(g, aSize - 1, new Point(aPosition.x + dexpr.width, aPosition.y - iExpressions[0].iAscent - iSuperOffset));
                }

                if (iExpressions[2] != null) {
                    iExpressions[2].calculatePositions(g, aSize - 1, new Point(aPosition.x + dexpr.width, aPosition.y + iSubOffset));
                }
            }
        }
    }
}
