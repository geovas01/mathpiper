package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;

public class Sum extends SymbolBox {

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {

        int height = ScaledGraphics.fontForSize(aSize);
        sg.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;
        iAscent = height / 2 + sg.getAscent();
        iDimension = new Dimensions((4 * height) / 3, 2 * height);
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg, Color.RED);

        int height = ScaledGraphics.fontForSize(iSize);
        sg.setLineThickness(2);

        double x0 = iPosition.x;
        double y0 = iPosition.y - iAscent;
        double x1 = x0 + iDimension.width;
        double y1 = y0 + iDimension.height;
        sg.drawLine(x1, y0, x0, y0);
        sg.drawLine(x0, y0, x0 + (2 * height) / 4, (y0 + y1) / 2);
        sg.drawLine(x0 + (2 * height) / 4, (y0 + y1) / 2, x0, y1);
        sg.drawLine(x0, y1, x1, y1);
    }



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[0];
    }//end method.




    public String toString()
    {
        String returnString = "<Sum>";
        return returnString;
    }//end method.

}//end class
