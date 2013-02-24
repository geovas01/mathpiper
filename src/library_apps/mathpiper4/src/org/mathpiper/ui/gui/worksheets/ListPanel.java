package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JPanel;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.symbolboxes.Bounds;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;

public class ListPanel extends JPanel implements ViewPanel {

    private ConsNode headNode;
    protected double viewScale = 1;
    private Queue<ConsNode> levelQueue = new LinkedList();
    private Stack<ConsXHolder> sequenceStack = new Stack();
    private boolean paintedOnce = false;
    private int largestX = 0;
    private int largestY = 0;

    /*
        The code in this constructor rearranges the cons cells that are in a Lisp list into a row-oriented data
        structure that more closely reflects the way the list will be displayed graphically.
     */

    public ListPanel(Environment aEnvironment, int aStackTop, Cons cons, double viewScale) {
        super();
        this.setOpaque(true);
        this.viewScale = viewScale;
        this.setBackground(Color.white);

        String sublistName = "(  )";

        int startY = 0;

        int yStep = 1;

        try {

            Cons headCons = cons;

            headNode = new ConsNode();
            if (headCons instanceof SublistCons) {
                headNode.setName(sublistName);
            } else {
                headNode.setName(headCons.car().toString());
            }

            headNode.setY(startY);

            if (headCons == null) {
                throw new Exception("Null cons.");
            }


            ConsXHolder consXHolder = new ConsXHolder(headCons.copy(false), headNode);

            sequenceStack.push(consXHolder);

            ConsNode currentNode = null;

            while (!sequenceStack.empty()) {
                consXHolder = sequenceStack.pop();

                //Remove rest because it has already been processed.
                consXHolder.getCons().setCdr(null);

                Cons currentCons = consXHolder.getCons();

                currentNode = consXHolder.getConsNode();

                while (currentCons.cdr() != null || (currentCons.car() instanceof Cons && ((Cons) currentCons.car()) != null)) {

                    if (currentCons.cdr() != null) {

                        currentCons = currentCons.cdr();

                        ConsNode newNode = new ConsNode();

                        if (!(currentCons instanceof SublistCons)) {
                            String name = currentCons.car().toString();
                            newNode.setName(name);
                        } else {
                            newNode.setName(sublistName);
                        }

                        currentNode.setCdr(newNode);

                        currentNode = newNode;

                        if (currentCons instanceof SublistCons) {
                            sequenceStack.push(new ConsXHolder(currentCons.copy(false), currentNode));

                            if (currentCons.cdr() == null) {
                                break;
                            }//end if.

                        }//end if.

                    } else {
                        if ((currentCons.car() instanceof Cons && ((Cons) currentCons.car()) == null))//! (currentConsPointer.getCons() instanceof SublistCons)) //(ConsPointer)currentConsPointer.car()).getCons() == null
                        {
                            break;
                        }

                        currentCons = (Cons) currentCons.car();

                        if (currentCons instanceof SublistCons) {
                            sequenceStack.push(new ConsXHolder(currentCons.copy(false), currentNode)); //currentNode.getX()));
                        }//end if.

                        ConsNode newNode = new ConsNode();

                        if (!(currentCons instanceof SublistCons)) {
                            String name = currentCons.car().toString();
                            newNode.setName(name);
                        } else {
                            newNode.setName(sublistName);
                        }

                        currentNode.setCar(newNode);

                        currentNode = newNode;

                        levelQueue.add(currentNode);

                    }//end else.

                }//end goNext while.

            }//end while.


            int y = startY;

            while (levelQueue.peek() != null) {
                ConsNode consNode = levelQueue.poll();
                consNode.setY(y += yStep);

                if (consNode.getY() > largestY) {
                    largestY = consNode.getY();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        g2d.setStroke(new BasicStroke((float) (2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        g2d.setBackground(Color.white);
        ScaledGraphics sg = new ScaledGraphics(g2d);
        sg.setLineThickness(1);
        sg.setViewScale(viewScale);
        int height = ScaledGraphics.fontForSize(1);
        sg.setFontSize(height);

        if (headNode != null) {
            drawBox(headNode, 0, sg);

        }//end if

        if (paintedOnce == false) {
            super.revalidate();

            paintedOnce = true;
        }

    }//end method.


    private void drawBox(ConsNode currentNode, double previousRightX, ScaledGraphics sg) {
        /*
        This method displays the cons cells which are in the row-oriented
        data structure that was created in the constructor.
         */

        int height = 25;
        int xGap = 10;
        int yGap = 10;
        int textOffset = 0;


        if (currentNode == null) {
            return;
        }


        double x = previousRightX + xGap;

        int y = currentNode.getY() * (height + yGap) + yGap;

        String name = currentNode.getName();

        double textWidth = sg.getScaledTextWidth(name);

        if (textWidth < 25) {
            textOffset = ((int) (25 - textWidth) / 2) + 1;
            textWidth = 25;
        } else {
            textOffset = 3;
            textWidth += 5;
        }

        double boxWidth = textWidth + 25;

        //Draw cons cell rectangle.
        sg.drawRectangle(x, y, boxWidth, height);

        //Draw cons cell dividing line.
        sg.drawLine(x + textWidth, y, x + textWidth, y + height);


        if (name != null) {
            sg.setColor(Color.BLUE);
            sg.drawscaledText(name, x + textOffset, y + 15, 1.0);
            sg.setColor(Color.BLACK);
        }

        if (currentNode.getCdr() != null)
        {
            currentNode.getCdr().setY(currentNode.getY());

            sg.drawLine(x + boxWidth - 12, y + 12, x + boxWidth + xGap, y + 12);
        }
        else
        {
            //Draw cdr diagonal line nil symbol.
            sg.drawLine(x + textWidth, y + height, x + boxWidth, y);
        }


        if (largestX < (int) (x + boxWidth + xGap)) {
            largestX = (int) (x + boxWidth + xGap);
        }


        if (currentNode.getCar() != null) {
            sg.drawLine(x + 13, y + 12, x + 13, currentNode.getCar().getY() * (height + yGap) + yGap);
        }
        else
        {
            //Draw car diagonal line nil symbol code goes here.
        }

        
        drawBox(currentNode.getCdr(), x + boxWidth, sg);

        drawBox(currentNode.getCar(), previousRightX, sg);


    }//end method.


    public Dimension getPreferredSize() {
        if (paintedOnce) {
            Bounds maxBounds = new Bounds(0, (largestY + 1) * (25 + 10) + 10, 0, largestX);

            Dimension scaledDimension = maxBounds.getScaledDimension(this.viewScale);

            return scaledDimension;
        } else {
            return new Dimension(700, 600);
        }

    }//end method.


    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
        this.revalidate();
        this.repaint();
    }

    private class ConsNode {

        private ConsNode car;
        private ConsNode cdr;
        private String name = "";
        private int y;


        public ConsNode() {
        }


        public ConsNode getCar() {
            return car;
        }


        public void setCar(ConsNode down) {
            this.car = down;
        }


        public ConsNode getCdr() {
            return cdr;
        }


        public void setCdr(ConsNode right) {
            this.cdr = right;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public int getY() {
            return y;
        }


        public void setY(int y) {
            this.y = y;
        }

    }//end class.

    private class ConsXHolder {

        private Cons cons;
        private ConsNode consNode;


        public ConsXHolder(Cons cons, ConsNode consNode) {
            this.cons = cons;

            this.consNode = consNode;
        }


        public Cons getCons() {
            return cons;
        }


        public void setCons(Cons cons) {
            this.cons = cons;
        }


        public ConsNode getConsNode() {
            return consNode;
        }


        public void setConsNode(ConsNode consNode) {
            this.consNode = consNode;
        }

    }//end class.


    /*
    Drawing Lists as Box Diagrams (from http://www.gnu.org/s/emacs/manual/html_node/elisp/Box-Diagrams.html)

    A list can be illustrated by a diagram in which the cons cells are shown as pairs of boxes, like dominoes. (The Lisp reader cannot read such an illustration; unlike the textual notation, which can be understood by both humans and computers, the box illustrations can be understood only by humans.) This picture represents the three-element list (rose violet buttercup):

    --- ---      --- ---      --- ---
    |   |   |--> |   |   |--> |   |   |--> nil
    --- ---      --- ---      --- ---
    |            |            |
    |            |            |
    --> rose     --> violet   --> buttercup

    In this diagram, each box represents a slot that can hold or refer to any Lisp object. Each pair of boxes represents a cons cell. Each arrow represents a reference to a Lisp object, either an atom or another cons cell.

    In this example, the first box, which holds the car of the first cons cell, refers to or holds rose (a symbol). The second box, holding the cdr of the first cons cell, refers to the next pair of boxes, the second cons cell. The car of the second cons cell is violet, and its cdr is the third cons cell. The cdr of the third (and last) cons cell is nil.

    Here is another diagram of the same list, (rose violet buttercup), sketched in a different manner:

    ---------------       ----------------       -------------------
    | car   | cdr   |     | car    | cdr   |     | car       | cdr   |
    | rose  |   o-------->| violet |   o-------->| buttercup |  nil  |
    |       |       |     |        |       |     |           |       |
    ---------------       ----------------       -------------------

    A list with no elements in it is the empty list; it is identical to the symbol nil. In other words, nil is both a symbol and a list.

    Here is the list (A ()), or equivalently (A nil), depicted with boxes and arrows:

    --- ---      --- ---
    |   |   |--> |   |   |--> nil
    --- ---      --- ---
    |            |
    |            |
    --> A        --> nil

    Here is a more complex illustration, showing the three-element list, ((pine needles) oak maple), the first element of which is a two-element list:

    --- ---      --- ---      --- ---
    |   |   |--> |   |   |--> |   |   |--> nil
    --- ---      --- ---      --- ---
    |            |            |
    |            |            |
    |             --> oak      --> maple
    |
    |     --- ---      --- ---
    --> |   |   |--> |   |   |--> nil
    --- ---      --- ---
    |            |
    |            |
    --> pine     --> needles

    The same list represented in the second box notation looks like this:

    --------------       --------------       --------------
    | car   | cdr  |     | car   | cdr  |     | car   | cdr  |
    |   o   |   o------->| oak   |   o------->| maple |  nil |
    |   |   |      |     |       |      |     |       |      |
    -- | ---------       --------------       --------------
    |
    |
    |        --------------       ----------------
    |       | car   | cdr  |     | car     | cdr  |
    ------>| pine  |   o------->| needles |  nil |
    |       |      |     |         |      |
    --------------       ----------------
     */
}//end class.

