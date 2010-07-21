package org.mathpiper.ui.gui.consoles;

import java.util.Stack;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.AbstractDocument.ElementEdit;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.View;

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

            //System.out.print(currentNode.getName() + "   " + currentNode.isLeaf() + "    " + currentNode.toString() );

            /*Enumeration attributeNames = currentNode.getAttributes().getAttributeNames();
            while(attributeNames.hasMoreElements())
            {
            System.out.println("   " + attributeNames.nextElement().toString());
            }*/




            /*if (currentNode.isLeaf()) {
            SimpleAttributeSet attrs = new SimpleAttributeSet(currentNode.getAttributes());
            StyleConstants.setFontSize(attrs, fontSize);
            this.setCharacterAttributes(currentNode.getStartOffset(), 1, attrs, true);
            }*/

            if (currentNode instanceof ComponentView) {
                ComponentView componentView = (ComponentView) currentNode;
                ResultHolder resultHolder = (ResultHolder) componentView.getComponent();

                resultHolder.setScale(fontSize);


            }

        }//end while.




    }//end method.


    public void scanViews(JTextPane textPane, int fontSize) {

        View root = textPane.getUI().getRootView(textPane);
        Stack<View> nodes = new Stack();
        nodes.push(root);
        View currentNode;
        while (!nodes.isEmpty()) {
            currentNode = nodes.pop();

            for (int i = 0; i < currentNode.getViewCount(); i++) {
                View child = currentNode.getView(i);

                nodes.push(child);

            }//end for.

            if (currentNode instanceof ComponentView) {
                ComponentView componentView = (ComponentView) currentNode;

                ResultHolder resultHolder = (ResultHolder) componentView.getComponent();

                resultHolder.setScale(fontSize);
            }

        }//end while.

    }//end method.

}//end class.


    
