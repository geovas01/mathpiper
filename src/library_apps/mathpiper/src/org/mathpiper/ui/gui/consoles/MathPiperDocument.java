package org.mathpiper.ui.gui.consoles;

import java.util.Stack;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.AbstractDocument.ElementEdit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

//Code from http://java-sl.com/tip_refresh_view.html. Stanislav Lapitsky
public class MathPiperDocument extends DefaultStyledDocument {

    public void refresh() {
        refresh(0, getLength());
    }


    public void refresh(int offset, int len) {
        DefaultDocumentEvent changes = new DefaultDocumentEvent(offset, len,
                DocumentEvent.EventType.CHANGE);
        Element root = getDefaultRootElement();
        Element[] removed = new Element[0];
        Element[] added = new Element[0];
        changes.addEdit(new ElementEdit(root, 0, removed, added));
        changes.end();
        fireChangedUpdate(changes);
    }


    public void scanTree(int fontSize) {
        Element root = this.getDefaultRootElement();
        Stack<Element> nodes = new Stack();
        nodes.push(root);
        Element currentNode;
        while (!nodes.isEmpty()) {
            currentNode = nodes.pop();

            int numberOfChildren = currentNode.getElementCount();

            for (int i = 0; i < numberOfChildren; i++) {
                Element child = currentNode.getElement(i);
                nodes.push(child);
            }

            //System.out.print(currentNode.getName() + "   " + currentNode.isLeaf() + "    " + currentNode.toString());
            //System.out.println();

            if (currentNode.isLeaf()) {


                SimpleAttributeSet attrs = new SimpleAttributeSet(currentNode.getAttributes());
                StyleConstants.setFontSize(attrs, fontSize);

                this.setCharacterAttributes(currentNode.getStartOffset(), 1, attrs, true);

            }

        }//end while.
    }//end method.

}


    
