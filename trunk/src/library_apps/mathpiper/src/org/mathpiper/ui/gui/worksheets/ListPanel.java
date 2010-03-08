package org.mathpiper.ui.gui.worksheets; // ViewList({{a},{b}})

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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;

public class ListPanel extends JPanel implements ViewPanel {

    private ConsNode headNode;

    protected double viewScale = 1;
    
    private Queue<ConsNode> levelQueue = new LinkedList();
    
    private Stack<ConsXHolder> sequenceStack = new Stack();


    public ListPanel(Environment aEnvironment, int aStackTop, ConsPointer consPointer, double viewScale) {
        super();
        this.setOpaque(true);
        this.viewScale = viewScale;
        this.setBackground(Color.white);

        int startX = 10;
        int xStep = 60;

        int startY = 10;
        int yStep = 40;

        try {

            Cons headCons = consPointer.getCons();

            if(headCons == null)
            {
                throw new Exception("Null cons.");
            }


            if(!(headCons instanceof SublistCons))
            {
                //Display a single non-list cons.
                headNode = new ConsNode();
                headNode.setName(headCons.car().toString());
                headNode.setX(startX);
                headNode.setY(startY);
            }
            else
            {
                ConsXHolder consXHolder = new ConsXHolder(headCons, startX);
                sequenceStack.push(consXHolder);

                ConsNode currentNode = null;

                while(!sequenceStack.empty())
                {
                    consXHolder = sequenceStack.pop();
                    
                    //Remove rest because it has already been processed.
                    consXHolder.getCons().cdr().setCons(null);

                    ConsPointer currentConsPointer = new ConsPointer(consXHolder.getCons());

                    int currentX = consXHolder.getX();

                    if(currentConsPointer.getCons() == headCons)
                    {
                        //If this is the head cons, create the head node.
                        headNode = new ConsNode();
                        currentNode = headNode;
                        currentNode.setX(startX);
                        currentNode.setY(startY);
                    }//end if.

                    while(currentConsPointer.cdr().getCons() != null || (currentConsPointer.car() instanceof ConsPointer && ((ConsPointer)currentConsPointer.car()).getCons() != null))
                    {
                        if(currentConsPointer.cdr().getCons() != null)
                        {
                            //Go next.
                            currentConsPointer.goNext(aStackTop, aEnvironment);
                            ConsNode newNode = new ConsNode();
                            if(! (currentConsPointer.getCons() instanceof SublistCons))
                            {
                                String name = currentConsPointer.getCons().toString();
                                newNode.setName(name);
                            }
                            else
                            {
                                newNode.setName("Sl_a");
                            }
                            newNode.setX(currentNode.getX() + xStep);

                            currentNode.setCdr(newNode);
                            currentNode = newNode;

                            if(currentConsPointer.getCons() instanceof SublistCons)
                            {
                                sequenceStack.push(new ConsXHolder(currentConsPointer.getCons(), currentNode.getX()));

                                if(currentConsPointer.getCons().cdr().getCons() == null)
                                {
                                    break;
                                }//end if.

                            }//end if.

                        }
                        else
                        {
                            if((currentConsPointer.car() instanceof ConsPointer && ((ConsPointer)currentConsPointer.car()).getCons() == null))//! (currentConsPointer.getCons() instanceof SublistCons)) //(ConsPointer)currentConsPointer.car()).getCons() == null
                            {
                                break;
                            }

                            //GoSub.
                            currentConsPointer.goSub(aStackTop, aEnvironment);

                            if(currentConsPointer.getCons() instanceof SublistCons)
                            {
                                sequenceStack.push(new ConsXHolder(currentConsPointer.getCons(), currentX)); //currentNode.getX()));
                            }//end if.

                            ConsNode newNode = new ConsNode();

                            if(! (currentConsPointer.getCons() instanceof SublistCons))
                            {
                                String name = currentConsPointer.getCons().toString();
                                newNode.setName(name);
                            }
                            else
                            {
                                newNode.setName("Sl_b");
                            }

                            newNode.setX(currentX); //currentNode.getX());

                            currentNode.setCar(newNode);

                            currentNode = newNode;

                            levelQueue.add(currentNode);

                        }//end else.

                    }//end goNext while.

                }//end while.

                
                int y = startY;

                while(levelQueue.peek() != null)
                {
                    ConsNode consNode = levelQueue.poll();
                    consNode.setY(y += yStep);
                }

            }//end else.


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


        int x;

        int y;

        if(headNode != null)
        {   
            drawBox(headNode, sg);


        }//end if


    }//end method.

    private void drawBox(ConsNode currentNode, ScaledGraphics sg)
    {

        if(currentNode == null)
        {
            return;
        }

        int x = currentNode.getX();

        int y = currentNode.getY();

        sg.drawRectangle(x, y, 50, 25);

        sg.drawLine(x + 25, y, x + 25, y + 25);

        String name = currentNode.getName();

        if(name != null)
        {
            sg.drawscaledText(name, x + 3, y + 16, 1.3);
        }

        if(currentNode.getCdr() != null)
        {
            currentNode.getCdr().setY(currentNode.getY());

            sg.drawLine(x + 37, y + 12, x + 50 + 10 , y + 12);
        }


        if(currentNode.getCar() != null)
        {
            sg.drawLine(x + 12, y + 12, x + 12 , y + 25 + 15);
        }

        drawBox(currentNode.getCdr(), sg);

        drawBox(currentNode.getCar(), sg);


    }//end method.

    public Dimension getPreferredSize() {
        return new Dimension(700, 600);
    }

    public void setViewScale(double viewScale) {
        this.viewScale = viewScale;
    }


    private class ConsNode {

        private ConsNode car;
        private ConsNode cdr;
        private String name = "";
        private int x;
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

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
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
        private int x;

        public ConsXHolder(Cons cons, int x){
            this.cons = cons;

            this.x = x;
        }

        public Cons getCons() {
            return cons;
        }

        public void setCons(Cons cons) {
            this.cons = cons;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
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

