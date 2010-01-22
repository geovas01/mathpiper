package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxSum extends SBox {

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {

        int height = SBoxBuilder.fontForSize(aSize);
        g.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;
        iAscent = height / 2 + g.getAscent();
        iDimension = new Dimension((4 * height) / 3, 2 * height);
    }

    public void render(GraphicsPrimitives g) {

        int height = SBoxBuilder.fontForSize(iSize);
        g.setLineThickness(2);

        int x0 = iPosition.x;
        int y0 = iPosition.y - iAscent;
        int x1 = x0 + iDimension.width;
        int y1 = y0 + iDimension.height;
        g.drawLine(x1, y0, x0, y0);
        g.drawLine(x0, y0, x0 + (2 * height) / 4, (int) (y0 + y1) / 2);
        g.drawLine(x0 + (2 * height) / 4, (int) (y0 + y1) / 2, x0, y1);
        g.drawLine(x0, y1, x1, y1);
    }
}
