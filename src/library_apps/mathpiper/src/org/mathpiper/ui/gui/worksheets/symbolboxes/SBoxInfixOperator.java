package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;
import java.awt.Point;

class SBoxInfixOperator extends SBoxCompoundExpression {

    SBoxInfixOperator(SBox aLeft, SBox aInfix, SBox aRight) {
        super(3);
        iExpressions[0] = aLeft;
        iExpressions[1] = aInfix;
        iExpressions[2] = aRight;
    }

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        // Get dimensions first
        if (iDimension == null) {
            iExpressions[0].calculatePositions(g, aSize, null);
            iExpressions[1].calculatePositions(g, aSize, null);
            iExpressions[2].calculatePositions(g, aSize, null);

            Dimension dleft = iExpressions[0].getDimension();
            Dimension dinfix = iExpressions[1].getDimension();
            Dimension dright = iExpressions[2].getDimension();
            int height = dleft.height;

            if (height < dinfix.height) {
                height = dinfix.height;
            }

            if (height < dright.height) {
                height = dright.height;
            }

            iDimension = new Dimension(dleft.width + dinfix.width + dright.width + 4, height);
            iAscent = iExpressions[0].getCalculatedAscent();

            if (iAscent < iExpressions[1].getCalculatedAscent()) {
                iAscent = iExpressions[1].getCalculatedAscent();
            }

            if (iAscent < iExpressions[2].getCalculatedAscent()) {
                iAscent = iExpressions[2].getCalculatedAscent();
            }
        }

        if (aPosition != null) {

            Dimension dleft = iExpressions[0].getDimension();
            Dimension dinfix = iExpressions[1].getDimension();
            Dimension dright = iExpressions[2].getDimension();
            iExpressions[0].calculatePositions(g, aSize, new Point(aPosition.x, aPosition.y));
            iExpressions[1].calculatePositions(g, aSize, new Point(aPosition.x + dleft.width + 2, aPosition.y));
            iExpressions[2].calculatePositions(g, aSize, new Point(aPosition.x + dleft.width + dinfix.width + 4, aPosition.y));
        }
    }
}
