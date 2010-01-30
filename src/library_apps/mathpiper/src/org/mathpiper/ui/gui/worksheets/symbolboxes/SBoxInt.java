package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxInt extends SBox {

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {

        int height = SBoxBuilder.fontForSize(aSize);
        sg.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;
        iAscent = height / 2 + sg.getAscent();
        iDimension = new Dimension((1 * height) / 2, 2 * height);
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        int height = SBoxBuilder.fontForSize(iSize);
        sg.setLineThickness(2);

        double x0 = iPosition.x;
        double y0 = iPosition.y - iAscent;
        double x1 = x0 + iDimension.width;
        double y1 = y0 + iDimension.height;
        sg.drawLine(x1, y0, x1 - iDimension.width / 4, y0);
        sg.drawLine(x1 - iDimension.width / 4, y0, x1 - (2 * iDimension.width) / 4, y0 + iDimension.width / 4);
        sg.drawLine(x1 - (2 * iDimension.width) / 4, y0 + iDimension.width / 4, x1 - (2 * iDimension.width) / 4, y0 + iDimension.height - iDimension.width / 4);
        sg.drawLine(x1 - (2 * iDimension.width) / 4, y0 + iDimension.height - iDimension.width / 4, x1 - (3 * iDimension.width) / 4, y0 + iDimension.height);
        sg.drawLine(x1 - (3 * iDimension.width) / 4, y0 + iDimension.height, x0, y0 + iDimension.height);
    }
}
