package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;
import java.awt.Point;

class SBoxPrefixOperator extends SBoxCompoundExpression {

    SBoxPrefixOperator(SBox aLeft, SBox aRight) {
        super(2);
        iExpressions[0] = aLeft;
        iExpressions[1] = aRight;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {
            iExpressions[0].calculatePositions(sg, aSize, null);
            iExpressions[1].calculatePositions(sg, aSize, null);

            Dimension dleft = iExpressions[0].getDimension();
            Dimension dright = iExpressions[1].getDimension();
            int height = dleft.height;

            if (height < dright.height) {
                height = dright.height;
            }

            iDimension = new Dimension(dleft.width + dright.width + 2, height);
            iAscent = iExpressions[0].getCalculatedAscent();

            if (iAscent < iExpressions[1].getCalculatedAscent()) {
                iAscent = iExpressions[1].getCalculatedAscent();
            }
        }

        if (aPosition != null) {

            Dimension dleft = iExpressions[0].getDimension();
            Dimension dright = iExpressions[1].getDimension();
            iExpressions[0].calculatePositions(sg, aSize, new Point(aPosition.x, aPosition.y)); /*+(iAscent-iExpressions[0].getCalculatedAscent())*/
            iExpressions[1].calculatePositions(sg, aSize, new Point(aPosition.x + dleft.width + 2, aPosition.y)); /*+(iAscent-iExpressions[1].getCalculatedAscent())*/
        }
    }
}
