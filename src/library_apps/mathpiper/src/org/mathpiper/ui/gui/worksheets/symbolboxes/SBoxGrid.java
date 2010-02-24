package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;
import java.awt.Point;

class SBoxGrid extends SBoxCompoundExpression {

    int iHeight;
    int[] iHeights;
    int iWidth;
    int[] iWidths;

    private SBox iExpressions[];

    SBoxGrid(int aWidth, int aHeight) {
        //super(aWidth * aHeight);
        iExpressions = new SBox[aWidth * aHeight];
        iWidth = aWidth;
        iHeight = aHeight;
    }

    void SetSBox(int x, int y, SBox aExpression) {
        iExpressions[x + iWidth * y] = aExpression;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {

        int spacing = 12;
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {

            int i;
            int j;

            for (i = 0; i < iWidth * iHeight; i++) {
                iExpressions[i].calculatePositions(sg, aSize, null);
            }

            iWidths = new int[iWidth];
            iHeights = new int[iHeight];

            for (i = 0; i < iWidth; i++) {
                iWidths[i] = 0;
            }

            for (i = 0; i < iHeight; i++) {
                iHeights[i] = 0;
            }

            for (i = 0; i < iWidth; i++) {

                for (j = 0; j < iHeight; j++) {

                    Dimension d = iExpressions[i + iWidth * j].getDimension();

                    if (iWidths[i] < d.width) {
                        iWidths[i] = d.width;
                    }

                    if (iHeights[j] < d.height) {
                        iHeights[j] = d.height;
                    }
                }
            }

            int totalWidth = 0;

            for (i = 0; i < iWidth; i++) {
                totalWidth = totalWidth + iWidths[i];
            }

            int totalHeight = 0;

            for (j = 0; j < iHeight; j++) {
                totalHeight = totalHeight + iHeights[j];
            }

            iDimension = new Dimension(totalWidth + spacing * (iWidth), totalHeight + spacing * (iHeight));
            iAscent = iDimension.height / 2;
        }

        if (aPosition != null) {

            int i;
            int j;
            double h = -iAscent;

            for (j = 0; j < iHeight; j++) {

                double maxAscent = -10000;

                for (i = 0; i < iWidth; i++) {

                    if (maxAscent < iExpressions[i + j * iWidth].iAscent) {
                        maxAscent = iExpressions[i + j * iWidth].iAscent;
                    }
                }

                h = h + maxAscent;

                double w = 0;

                for (i = 0; i < iWidth; i++) {
                    iExpressions[i + j * iWidth].calculatePositions(sg, aSize, new Point((int) (aPosition.x + w), (int) (aPosition.y + h)));
                    w += iWidths[i] + spacing;
                }

                h = h - maxAscent;
                h = h + iHeights[j] + spacing;
            }
        }
    }//end calculatePositions.


    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        int i;

        for (i = 0; i < iExpressions.length; i++) {

            if (iExpressions[i] != null) {
                iExpressions[i].render(sg);
            }
        }//end for.

    }//end render.

}//end class
