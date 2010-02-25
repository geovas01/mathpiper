/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets.symbolboxes;

class Bracket extends CompoundExpression {

    double iBracketWidth;
    String iClose;
    double iFontSize;
    String iOpen;

    private SymbolBox iExpression;

    Bracket(SymbolBox aExpression, String aOpen, String aClose) {
        //super(1);
        iOpen = aOpen;
        iClose = aClose;
        iExpression = aExpression;
    }

    public void calculatePositions(ScaledGraphics sg, int aSize, Position aPosition) {
        iSize = aSize;
        iPosition = aPosition;

        if (iDimension == null) {
            iExpression.calculatePositions(sg, aSize, null);

            Dimensions dim = iExpression.getDimension();
            iFontSize =  dim.height;
            sg.setFontSize( dim.height);
            iBracketWidth = ScaledGraphics.fontForSize(aSize) / 2;
            iDimension = new Dimensions(dim.width + 2 * iBracketWidth, dim.height);
            iAscent = iExpression.getCalculatedAscent();
        }

        if (aPosition != null) {

            Dimensions dim = iExpression.getDimension();
            iExpression.calculatePositions(sg, aSize, new Position(aPosition.x + iBracketWidth, aPosition.y));
        }
    }

    public void render(ScaledGraphics sg) {

        if(drawBoundingBox) drawBoundingBox(sg);

        iExpression.render(sg);

        Dimensions dim = iExpression.getDimension();
        drawBracket(sg, iOpen, iPosition.x, iPosition.y - getCalculatedAscent());
        drawBracket(sg, iClose, iPosition.x + dim.width + iBracketWidth, iPosition.y - getCalculatedAscent());
    }

    void drawBracket(ScaledGraphics sg, String bracket, double x, double y) {

        Dimensions dim = iExpression.getDimension();

        if (bracket.equals("[") || bracket.equals("]")) {

            int margin = 2;
            sg.setLineThickness(2);

            if (bracket.equals("[")) {
                sg.drawLine(x + margin, y, x + margin, y + dim.height);
            } else {
                sg.drawLine(x + iBracketWidth - margin, y, x + iBracketWidth - margin, y + dim.height);
            }

            sg.setLineThickness(1);
            sg.drawLine(x + iBracketWidth - margin, y, x + margin, y);
            sg.drawLine(x + margin, y + dim.height, x + iBracketWidth - margin, y + dim.height);
        } else if (bracket.equals("(") || bracket.equals(")")) {

            double xstart;
            double xend;

            if (bracket.equals("(")) {
                xstart = x + iBracketWidth;
                xend = x;
            } else {
                xstart = x;
                xend = x + iBracketWidth;
            }

            double delta = xend - xstart;

            double[] steps = new double[3];

            double verticalOffset = 2;

            steps[0] = 0.2;
            steps[1] = 0.6;
            steps[2] = 0.8;

            sg.setLineThickness(1.1);
            sg.drawLine( (xstart + (delta * steps[0])), y + verticalOffset + (0 * dim.height) / 6,  xstart + (delta * steps[1]), y + verticalOffset + (1 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[1])), y + verticalOffset + (1 * dim.height) / 6,  xstart + (delta * steps[2]), y + verticalOffset + (2 * dim.height) / 6);

            sg.setLineThickness(1.5);
            sg.drawLine( (xstart + (delta * steps[2])), y + verticalOffset + (2 * dim.height) / 6,  xstart + (delta * steps[2]), y + verticalOffset + (4 * dim.height) / 6);

            sg.setLineThickness(1.3);
            sg.drawLine( (xstart + (delta * steps[2])), y + verticalOffset + (4 * dim.height) / 6, xstart + (delta * steps[1]), y + verticalOffset + (5 * dim.height) / 6);

            sg.setLineThickness(1.1);
            sg.drawLine( (xstart + (delta * steps[1])), y + verticalOffset + (5 * dim.height) / 6, xstart + (delta * steps[0]), y + verticalOffset + (6 * dim.height) / 6);


           /* sg.setColor(Color.RED);
            sg.setLineThickness(1.6);
            sg.drawLine( (xstart + (delta * steps[2])), y + (2 * dim.height) / 6,  xstart + (delta * steps[2]), y + (4 * dim.height) / 6);

            sg.drawArc(xstart + (delta * .8), y + (0 * dim.height)/6,30, 30, 180, -60);
            sg.setColor(Color.black);*/

        } else {
            sg.setFontSize(iFontSize);

            double offset = (iFontSize - iAscent) / 2;
            sg.drawText(bracket, x, y + offset);
        }
    }//end method.



    public SymbolBox[] getChildren()
    {
        return new SymbolBox[] {this.iExpression};
    }//end method.



    public String toString()
    {
        String returnString = "<Bracket>";
        return returnString;
    }//end method.

}//end class
