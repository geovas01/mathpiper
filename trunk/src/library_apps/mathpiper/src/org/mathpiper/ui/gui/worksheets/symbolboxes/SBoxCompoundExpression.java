/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;

abstract class SBoxCompoundExpression extends SBox {

    SBox[] iExpressions;

    SBoxCompoundExpression(int aNrSubExpressions) {
        iExpressions = new SBox[aNrSubExpressions];
    }

    public void render(GraphicsPrimitives g) {

        //drawBoundingBox(g);
        if(drawBoundingBox) drawBoundingBox(g);
        
        int i;

        for (i = 0; i < iExpressions.length; i++) {

            if (iExpressions[i] != null) {
                iExpressions[i].render(g);
            }
        }
    }

    public void drawBoundingBox(GraphicsPrimitives g) {

        g.setColor(Color.blue);
        g.setLineThickness(0);

        int x0 = iPosition.x;
        int y0 = iPosition.y - getCalculatedAscent();
        int x1 = x0 + iDimension.width;
        int y1 = y0 + iDimension.height;
        g.drawLine(x0, y0, x1, y0);
        g.drawLine(x1, y0, x1, y1);
        g.drawLine(x1, y1, x0, y1);
        g.drawLine(x0, y1, x0, y0);

        g.setColor(Color.black);

        int i;

        for (i = 0; i < iExpressions.length; i++) {

            if (iExpressions[i] != null) {
                iExpressions[i].drawBoundingBox(g);
            }
        }
    }
}
