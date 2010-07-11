package org.mathpiper.ui.gui.consoles;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.AbstractDocument.ElementEdit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;


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
    }


    
