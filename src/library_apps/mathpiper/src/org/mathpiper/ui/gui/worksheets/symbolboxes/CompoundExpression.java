/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;

abstract class CompoundExpression extends SymbolBox {

    //SymbolBox[] iExpressions;

   /* CompoundExpression(int aNrSubExpressions) {
        iExpressions = new SymbolBox[aNrSubExpressions];
    }*/

    public void render(ScaledGraphics sg) {

    }

    
    public void drawBoundingBox(ScaledGraphics sg) {

        drawBoundingBox(sg, Color.BLUE);
        /*sg.setColor(Color.blue);
        sg.setLineThickness(0);

        double x0 = iPosition.x;
        double y0 = iPosition.y - getCalculatedAscent();
        double x1 = x0 + iDimension.getTextWidth;
        double y1 = y0 + iDimension.getTextHeight;
        sg.drawLine(x0, y0, x1, y0);
        sg.drawLine(x1, y0, x1, y1);
        sg.drawLine(x1, y1, x0, y1);
        sg.drawLine(x0, y1, x0, y0);

        sg.drawscaledText("" + sequence++, x0, y0 + 3, .2);

        sg.setColor(Color.black);*/



    }



    public String toString()
    {
        String returnString = "<Compound Expression>";
        return returnString;
    }//end method.

}//end class
