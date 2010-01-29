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

    public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition) {

        int height = SBoxBuilder.fontForSize(aSize);
        g.setFontSize(height);
        iSize = aSize;
        iPosition = aPosition;

        if (iSymbol.equals("\\pi") || iSymbol.equals("\\wedge") || iSymbol.equals("\\vee")) {
            iDimension = new Dimension(g.textWidth("M"), height);
            iAscent = g.getAscent();
        } else if (iSymbol.equals("\\neq")) {
            iDimension = new Dimension(g.textWidth("="), height);
            iAscent = g.getAscent();
        } else if (iSymbol.equals("\\infty")) {
            iDimension = new Dimension(g.textWidth("oo"), height);
            iAscent = g.getAscent();
        } else if (iSymbol.equals("\\cdot")) {
            iDimension = new Dimension(g.textWidth("."), height);
            iAscent = g.getAscent();
        } else {
            iAscent = g.getAscent();
            iDimension = new Dimension(g.textWidth(iSymbol), height);
        }
    }

    public void render(GraphicsPrimitives g) {

        if(drawBoundingBox) drawBoundingBox(g);

        if (iSymbol.equals("\\pi")) {

            double deltax = 0.15 * iDimension.width;
            double deltay = 0.2 * iDimension.height;
            g.drawLine((int) (iPosition.x + 1 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + iDimension.width - 1 * deltax), (int) (iPosition.y - iAscent + 2 * deltay));
            g.drawLine((int) (iPosition.x + 2 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + 2 * deltax), (int) (iPosition.y - iAscent + iDimension.height + 0 * deltay));
            g.drawLine((int) (iPosition.x + iDimension.width - 2 * deltax), (int) (iPosition.y - iAscent + 2 * deltay), (int) (iPosition.x + iDimension.width - 2 * deltax), (int) (iPosition.y - iAscent + iDimension.height + 0 * deltay));
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

            g.drawLine((int) (iPosition.x + 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
            g.drawLine((int) (iPosition.x + iDimension.width - 1 * deltax), ybase, iPosition.x + iDimension.width / 2, ytip);
        } else if (iSymbol.equals("\\neq")) {
            g.setFontSize(SBoxBuilder.fontForSize(iSize));
            g.drawText("=", iPosition.x, iPosition.y);
            g.drawLine(iPosition.x + (2 * iDimension.width) / 3, iPosition.y - iAscent + (2 * iDimension.height) / 6, iPosition.x + (1 * iDimension.width) / 3, iPosition.y - iAscent + (6 * iDimension.height) / 6);
        } else if (iSymbol.equals("\\infty")) {
            g.setFontSize(SBoxBuilder.fontForSize(iSize));
            g.drawText("o", iPosition.x + 1, iPosition.y);
            g.drawText("o", iPosition.x + g.textWidth("o") - 2, iPosition.y);
        } else if (iSymbol.equals("\\cdot")) {

            int height = SBoxBuilder.fontForSize(iSize);
            g.setFontSize(height);
            g.drawText(".", iPosition.x, iPosition.y - height / 3);
        } else {
            g.setFontSize(SBoxBuilder.fontForSize(iSize));
            g.drawText(iSymbol, iPosition.x, iPosition.y);
        }
    }
}
