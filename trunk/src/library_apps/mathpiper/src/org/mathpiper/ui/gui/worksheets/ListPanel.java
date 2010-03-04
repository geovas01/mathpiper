package org.mathpiper.ui.gui.worksheets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;
import javax.swing.JPanel;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;

public class ListPanel extends JPanel implements ViewPanel {

    private ConsNode headNode;
    private ConsNode currentNode;
    protected double viewScale = 1;
    
    private Stack<ConsNode> levelStack = new Stack();
    
    private Stack<ConsNode> sequenceStack = new Stack();


    public ListPanel(Environment aEnvironment, int aStackTop, ConsPointer consPointer, double viewScale) {
        super();
        this.setOpaque(true);
        this.viewScale = viewScale;
        this.setBackground(Color.white);


        boolean head = true;



        try {
            consPointer.goSub(aStackTop, aEnvironment);

            ConsNode currentNode = null;
            
            while (consPointer.getCons() != null) {
                
                Cons cons = consPointer.getCons();
                
                String carName = "";
                if(cons.car() != null)
                {
                    carName = cons.car().toString();
                }


                ConsNode newNode = new ConsNode();

                newNode.setName(carName);

                if (head) {
                    currentNode = newNode;
                    headNode = currentNode;
                    head = false;
                }
                else
                {
                    currentNode.setRight(newNode);

                    currentNode = newNode;
                }
                

                consPointer.goNext(aStackTop, aEnvironment);
            }
        } catch (Exception e) {
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

        int x = 10;
        int y = 30;
        int iIndent = 0;

        if(headNode != null)
        {   
            currentNode = headNode;

            do
            {
                sg.drawRectangle(x, y, 50, 25);

                sg.drawLine(x + 25, y, x + 25, y + 25);

                sg.drawscaledText(currentNode.getName(), x + 3, y + 16, 1.3);

                x += 60;

                currentNode = currentNode.getRight();
                
            } while(currentNode != null);

        }//end if


    }//end method.

    public Dimension getPreferredSize() {
        return new Dimension(700, 600);
    }

    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
    }

    private class ConsNode {

        private ConsNode down;
        private ConsNode right;
        private String name;

        public ConsNode() {
        }


        public ConsNode getDown() {
            return down;
        }

        public void setDown(ConsNode down) {
            this.down = down;
        }

        public ConsNode getRight() {
            return right;
        }

        public void setRight(ConsNode right) {
            this.right = right;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    In this example, the first box, which holds the car of the first cons cell, refers to or “holds” rose (a symbol). The second box, holding the cdr of the first cons cell, refers to the next pair of boxes, the second cons cell. The car of the second cons cell is violet, and its cdr is the third cons cell. The cdr of the third (and last) cons cell is nil.

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

