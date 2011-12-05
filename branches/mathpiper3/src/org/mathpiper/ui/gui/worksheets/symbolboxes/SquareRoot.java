package org.mathpiper.ui.gui.worksheets.symbolboxes;

public class SquareRoot extends CompoundExpression {

    private SymbolBox iExpression;

    public SquareRoot(SymbolBox aExpression) {
        iExpression = aExpression;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpression.calculatePositions(sg, aSize, null);

            Dimensions dim = iExpression.getDimension();
            iDimension = new Dimensions( (dim.width + 6), dim.height + 3);
            iAscent = iExpression.getCalculatedAscent() + 3;
        }

        if (aPosition != null) {

            Dimensions dim = iExpression.getDimension();
            iExpression.calculatePositions(sg, aSize, new Position( (aPosition.x + 6), aPosition.y));
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpression.render(sg);

        sg.setLineThickness(1);

        Dimensions dim = iExpression.getDimension();
        double x0 = iPosition.x;
        double y0 = iPosition.y - iAscent;
        double x1 = x0 + dim.width + 6;
        double y1 = y0 + dim.height + 6;
        sg.drawLine(x0, y0 + 1, x0 + 3, y1 - 1);
        sg.drawLine(x0 + 3, y1 - 1, x0 + 6, y0 + 2);
        sg.drawLine(x0 + 6, y0 + 1, x1, y0 + 1);
    }



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iExpression};
    }//end method.




    public String toString()
    {
        String returnString = "<Square Root>";
        return returnString;
    }//end method.

}//end class
