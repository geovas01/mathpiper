package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;

class SymbolName extends SymbolBox {

    public String iSymbol;

    SymbolName(String aSymbol) {
        iSymbol = aSymbol;

        if (iSymbol.indexOf("\\") == 0) {

            if (iSymbol.equals("\\pi")) {
            } else if (iSymbol.equals("\\infty")) {
            } else if (iSymbol.equals("\\cdot")) {
            } else if (iSymbol.equals("\\wedge")) {
            } else if (iSymbol.equals("\\vee")) {
            } else if (iSymbol.equals("\\neq")) {
            } else {
                iSymbol = iSymbol.substring(1);
            }
        }
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {

        int height = ScaledGraphics.fontForSize(aSize);
        sg.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;

        if (iSymbol.equals("\\pi") || iSymbol.equals("\\wedge") || iSymbol.equals("\\vee")) {
            iDimension = new Dimensions(sg.getTextWidth("M"), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\neq")) {
            iDimension = new Dimensions(sg.getTextWidth("="), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\infty")) {
            iDimension = new Dimensions(sg.getTextWidth("oo"), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\cdot")) {
            iDimension = new Dimensions(sg.getTextWidth("."), height);
            iAscent = sg.getAscent();
        } else {
            iAscent = sg.getAscent();
            iDimension = new Dimensions(sg.getTextWidth(iSymbol), height);
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg, Color.RED);

        if (iSymbol.equals("\\pi")) {

            double deltax = 0.15 * iDimension.width;
            double deltay = 0.2 * iDimension.height;
            sg.drawLine( (iPosition.x + 1 * deltax),  (iPosition.y - iAscent + 2 * deltay),  (iPosition.x + iDimension.width - 1 * deltax),  (iPosition.y - iAscent + 2 * deltay));
            sg.drawLine( (iPosition.x + 2 * deltax),  (iPosition.y - iAscent + 2 * deltay),  (iPosition.x + 2 * deltax),  (iPosition.y - iAscent + iDimension.height + 0 * deltay));
            sg.drawLine( (iPosition.x + iDimension.width - 2 * deltax),  (iPosition.y - iAscent + 2 * deltay),  (iPosition.x + iDimension.width - 2 * deltax),  (iPosition.y - iAscent + iDimension.height + 0 * deltay));
        } else if (iSymbol.equals("\\wedge") || iSymbol.equals("\\vee")) {

            double deltax = 0.15 * iDimension.width;
            double deltay = 0.2 * iDimension.height;
            double ytip = (iPosition.y - iAscent + iDimension.height + 0 * deltay);
            double ybase = (iPosition.y - iAscent + 2 * deltay);

            if (iSymbol.equals("\\wedge")) {

                double swap = ytip;
                ytip = ybase;
                ybase = swap;
            }

            sg.drawLine( (iPosition.x + 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
            sg.drawLine( (iPosition.x + iDimension.width - 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
        } else if (iSymbol.equals("\\neq")) {
            sg.setFontSize(ScaledGraphics.fontForSize(iSize));
            sg.drawText("=", iPosition.x, iPosition.y);
            sg.drawLine(iPosition.x + (2 * iDimension.width) / 3, iPosition.y - iAscent + (2 * iDimension.height) / 6, iPosition.x + (1 * iDimension.width) / 3, iPosition.y - iAscent + (6 * iDimension.height) / 6);
        } else if (iSymbol.equals("\\infty")) {
            sg.setFontSize(ScaledGraphics.fontForSize(iSize));
            sg.drawText("o", iPosition.x + 1, iPosition.y);
            sg.drawText("o", iPosition.x + sg.getTextWidth("o") - 2, iPosition.y);
        } else if (iSymbol.equals("\\cdot")) {

            int height = ScaledGraphics.fontForSize(iSize);
            sg.setFontSize(height);
            sg.drawText(".", iPosition.x, iPosition.y - height / 3);
        } else {
            sg.setFontSize(ScaledGraphics.fontForSize(iSize));
            sg.drawText(iSymbol, iPosition.x, iPosition.y);
        }
    }
}
