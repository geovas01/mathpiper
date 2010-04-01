/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Color;
import java.util.List;

public abstract class SymbolBox {

    static boolean drawBoundingBox = false;
    static int sequence = 0;

    protected Dimensions iDimension;

    protected Position iPosition;
    int iSize;
    double iAscent;

    private boolean endOfLevel = false;

    private int treeX;

    private int treeY;

    private int parentXAnchor = -1;

    private int parentYAnchor = -1;

    public static int getSequence() {
        return sequence;
    }

    public static void setSequence(int sequence) {
        SymbolBox.sequence = sequence;
    }


    abstract public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition);

    abstract public void render(ScaledGraphics sg);

    public Dimensions getDimension() {
        return iDimension;
    }

    public Position getCalculatedPosition() {
        return iPosition;
    }

    public int getSetSize() {
        return iSize;
    }

    public double getCalculatedAscent() {
        return iAscent;
    }

    public void drawBoundingBox(ScaledGraphics sg, Color color) {
        sg.setColor(color);
        sg.setLineThickness(0);
        double x0 = iPosition.x;
        double y0 = iPosition.y - getCalculatedAscent();
        double x1 = x0 + iDimension.width;
        double y1 = y0 + iDimension.height;

        sg.drawLine(x0, y0, x1, y0);
        sg.drawLine(x1, y0, x1, y1);
        sg.drawLine(x1, y1, x0, y1);
        sg.drawLine(x0, y1, x0, y0);

        sg.drawscaledText("" + sequence++, x0, y0 + 3, .2);



        sg.setColor(Color.black);
    }//end method.

    public static void setDrawBoundingBox(boolean drawBoundingBox) {
        SymbolBox.drawBoundingBox = drawBoundingBox;
    }

    public static boolean isDrawBoundingBox() {
        return drawBoundingBox;
    }

    public Bounds getScaledBounds(double scale)
    {
        scale = 1;
        double x0 = iPosition.x * scale;
        double y0 = (iPosition.y - getCalculatedAscent()) * scale;
        double x1 = (x0 + iDimension.width) * scale;
        double y1 = (y0 + iDimension.height) * scale;

        return new Bounds(y0, y1, x0, x1);
    }


    public abstract SymbolBox[] getChildren();
    
    

    public boolean isEndOfLevel() {
        return endOfLevel;
    }

    public void setEndOfLevel(boolean endOfLevel) {
        this.endOfLevel = endOfLevel;
    }


    public int width(ScaledGraphics sg)
    {
        return (int) sg.getScaledTextWidth(toString());
    }

    public int height(ScaledGraphics sg)
    {
        return (int) sg.getScaledTextHeight(toString());
    }

    public int getTreeX() {
        return treeX;
    }

    public void setTreeX(int treeX) {
        this.treeX = treeX;
    }

    public int getTreeY() {
        return treeY;
    }

    public void setTreeY(int treeY) {
        this.treeY = treeY;
    }

    public int getParentXAnchor() {
        return parentXAnchor;
    }

    public void setParentXAnchor(int parentXAnchor) {
        this.parentXAnchor = parentXAnchor;
    }

    public int getParentYAnchor() {
        return parentYAnchor;
    }

    public void setParentYAnchor(int parentYAnchor) {
        this.parentYAnchor = parentYAnchor;
    }





}//end class.

