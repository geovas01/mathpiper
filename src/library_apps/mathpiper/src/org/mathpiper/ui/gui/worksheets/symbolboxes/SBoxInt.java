package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxInt extends SBox {

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {

        int height = SBoxBuilder.fontForSize(aSize);
        g.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;
        iAscent = height / 2 + g.getAscent();
        iDimension = new Dimension((1 * height) / 2, 2 * height);
    }

    public void render(GraphicsPrimitives g) {

        if(drawBoundingBox) drawBoundingBox(g);

        int height = SBoxBuilder.fontForSize(iSize);
        g.setLineThickness(2);

        int x0 = iPosition.x;
        int y0 = iPosition.y - iAscent;
        int x1 = x0 + iDimension.width;
        int y1 = y0 + iDimension.height;
        g.drawLine(x1, y0, x1 - iDimension.width / 4, y0);
        g.drawLine(x1 - iDimension.width / 4, y0, x1 - (2 * iDimension.width) / 4, y0 + iDimension.width / 4);
        g.drawLine(x1 - (2 * iDimension.width) / 4, y0 + iDimension.width / 4, x1 - (2 * iDimension.width) / 4, y0 + iDimension.height - iDimension.width / 4);
        g.drawLine(x1 - (2 * iDimension.width) / 4, y0 + iDimension.height - iDimension.width / 4, x1 - (3 * iDimension.width) / 4, y0 + iDimension.height);
        g.drawLine(x1 - (3 * iDimension.width) / 4, y0 + iDimension.height, x0, y0 + iDimension.height);
    }
}
