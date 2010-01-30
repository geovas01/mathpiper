package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;

class SBoxSymbolName extends SBox {

    public String iSymbol;

    SBoxSymbolName(String aSymbol) {
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

    public void calculatePositions(ScaledGraphics sg, int aSize, java.awt.Point aPosition) {

        int height = SBoxBuilder.fontForSize(aSize);
        sg.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;

        if (iSymbol.equals("\\pi") || iSymbol.equals("\\wedge") || iSymbol.equals("\\vee")) {
            iDimension = new Dimension(sg.getTextWidth("M"), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\neq")) {
            iDimension = new Dimension(sg.getTextWidth("="), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\infty")) {
            iDimension = new Dimension(sg.getTextWidth("oo"), height);
            iAscent = sg.getAscent();
        } else if (iSymbol.equals("\\cdot")) {
            iDimension = new Dimension(sg.getTextWidth("."), height);
            iAscent = sg.getAscent();
        } else {
            iAscent = sg.getAscent();
            iDimension = new Dimension(sg.getTextWidth(iSymbol), height);
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        if (iSymbol.equals("\\pi")) {

            double deltax = 0.15 * iDimension.width;
            double deltay = 0.2 * iDimension.height;
            sg.drawLine((int) (iPosition.x + 1 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + iDimension.width - 1 * deltax), (int) (iPosition.y - iAscent + 2 * deltay));
            sg.drawLine((int) (iPosition.x + 2 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + 2 * deltax), (int) (iPosition.y - iAscent + iDimension.height + 0 * deltay));
            sg.drawLine((int) (iPosition.x + iDimension.width - 2 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + iDimension.width - 2 * deltax), (int) (iPosition.y - iAscent + iDimension.height + 0 * deltay));
        } else if (iSymbol.equals("\\wedge") || iSymbol.equals("\\vee")) {

            double deltax = 0.15 * iDimension.width;
            double deltay = 0.2 * iDimension.height;
            int ytip = (int) (iPosition.y - iAscent + iDimension.height + 0 * deltay);
            int ybase = (int) (iPosition.y - iAscent + 2 * deltay);

            if (iSymbol.equals("\\wedge")) {

                int swap = ytip;
                ytip = ybase;
                ybase = swap;
            }

            sg.drawLine((int) (iPosition.x + 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
            sg.drawLine((int) (iPosition.x + iDimension.width - 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
        } else if (iSymbol.equals("\\neq")) {
            sg.setFontSize(SBoxBuilder.fontForSize(iSize));
            sg.drawText("=", iPosition.x, iPosition.y);
            sg.drawLine(iPosition.x + (2 * iDimension.width) / 3, iPosition.y - iAscent + (2 * iDimension.height) / 6, iPosition.x + (1 * iDimension.width) / 3, iPosition.y - iAscent + (6 * iDimension.height) / 6);
        } else if (iSymbol.equals("\\infty")) {
            sg.setFontSize(SBoxBuilder.fontForSize(iSize));
            sg.drawText("o", iPosition.x + 1, iPosition.y);
            sg.drawText("o", iPosition.x + sg.getTextWidth("o") - 2, iPosition.y);
        } else if (iSymbol.equals("\\cdot")) {

            int height = SBoxBuilder.fontForSize(iSize);
            sg.setFontSize(height);
            sg.drawText(".", iPosition.x, iPosition.y - height / 3);
        } else {
            sg.setFontSize(SBoxBuilder.fontForSize(iSize));
            sg.drawText(iSymbol, iPosition.x, iPosition.y);
        }
    }
}
