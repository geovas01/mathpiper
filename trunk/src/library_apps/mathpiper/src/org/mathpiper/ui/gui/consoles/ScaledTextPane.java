package org.mathpiper.ui.gui.consoles;

/**
 * @author Stanislav Lapitsky
 * @version 1.0
 */
import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

public class ScaledTextPane extends JTextPane {

    JComboBox zoomCombo = new JComboBox(new String[]{"50%", "75%",
                "100%", "150%", "200%"});

    JButton button;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Scaled example with custom GlyphPainter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScaledTextPane scaledTextPane = new ScaledTextPane();
        scaledTextPane.getDocument().putProperty("ZOOM_FACTOR", new Double(1.0));
        JScrollPane scroll = new JScrollPane(scaledTextPane);
        frame.getContentPane().add(scroll);
        frame.getContentPane().add(scaledTextPane.zoomCombo, BorderLayout.NORTH);

        frame.setSize(600, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public ScaledTextPane() {
        super();
        final SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, 16);
        setEditorKit(new ScaledEditorKit());

        setDocument(new MathPiperDocument());
        //javax.swing.text.DefaultStyledDocument e;
        StyledDocument doc = (StyledDocument) ScaledTextPane.this.getDocument();
        doc.setCharacterAttributes(0, 1, attrs, true);
        try {
            /*StyleConstants.setFontFamily(attrs, "Lucida Sans");
            doc.insertString(0, "Lusida Sans font test\n", attrs);

            StyleConstants.setFontFamily(attrs, "Lucida Bright");
            doc.insertString(0, "Lucida Bright font test\n", attrs);

            StyleConstants.setFontFamily(attrs, "Lucida Sans Typewriter");
            doc.insertString(0, "Lucida Sans Typewriter font test\n", attrs);*/

            doc.insertString(0, "01", attrs);

            button = new JButton("TEST");


            Style style = doc.addStyle("StyleName", null);
            StyleConstants.setComponent(style,button );
            doc.insertString(1, "ignored text", style);



        } catch (BadLocationException ex) {
        }

        zoomCombo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String s = (String) zoomCombo.getSelectedItem();
                s = s.substring(0, s.length() - 1);
                double scale = new Double(s).doubleValue() / 100;
                ScaledTextPane.this.getDocument().putProperty("ZOOM_FACTOR", new Double(scale));        
                try {
                    StyledDocument doc = (StyledDocument) ScaledTextPane.this.getDocument();
                    doc.setCharacterAttributes(0, 1, attrs, true);
                    doc.insertString(0, "", null); //refresh
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        zoomCombo.setSelectedItem("100%");
    }


    public void repaint(int x, int y, int width, int height) {
        super.repaint(0, 0, getWidth(), getHeight());
    }

}

class ScaledEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new StyledViewFactory();
    }

    class StyledViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new ScaledLableView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new ScaledView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ScaledComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }

    }

}

//-----------------------------------------------------------------
class ScaledView extends BoxView {

    public ScaledView(Element elem, int axis) {
        super(elem, axis);
    }


    public double getZoomFactor() {
        Double scale = (Double) getDocument().getProperty("ZOOM_FACTOR");
        if (scale != null) {
            return scale.doubleValue();
        }

        return 1;
    }


    public void paint(Graphics g, Shape allocation) {
        Graphics2D g2d = (Graphics2D) g;
        double zoomFactor = getZoomFactor();
        AffineTransform old = g2d.getTransform();
        g2d.scale(zoomFactor, zoomFactor);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        super.paint(g2d, allocation);
        g2d.setTransform(old);
    }


    public float getMinimumSpan(int axis) {
        float f = super.getMinimumSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    public float getMaximumSpan(int axis) {
        float f = super.getMaximumSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    public float getPreferredSpan(int axis) {
        float f = super.getPreferredSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    protected void layout(int width, int height) {
        super.layout(new Double(width / getZoomFactor()).intValue(),
                new Double(height *
                getZoomFactor()).intValue());
    }


    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        double zoomFactor = getZoomFactor();
        Rectangle alloc;
        alloc = a.getBounds();
        Shape s = super.modelToView(pos, alloc, b);
        alloc = s.getBounds();
        alloc.x *= zoomFactor;
        alloc.y *= zoomFactor;
        alloc.width *= zoomFactor;
        alloc.height *= zoomFactor;

        return alloc;
    }


    public int viewToModel(float x, float y, Shape a,
            Position.Bias[] bias) {
        double zoomFactor = getZoomFactor();
        Rectangle alloc = a.getBounds();
        x /= zoomFactor;
        y /= zoomFactor;
        alloc.x /= zoomFactor;
        alloc.y /= zoomFactor;
        alloc.width /= zoomFactor;
        alloc.height /= zoomFactor;

        return super.viewToModel(x, y, alloc, bias);
    }

}

class ScaledLableView extends LabelView {

    static GlyphPainter defaultPainter;


    public ScaledLableView(Element elem) {
        super(elem);
    }


    protected void checkPainter() {
        if (getGlyphPainter() == null) {
            if (defaultPainter == null) {
                defaultPainter = new ScaledGlyphPainter();
            }
            setGlyphPainter(defaultPainter.getPainter(this, getStartOffset(), getEndOffset()));
        }
    }

}




class ScaledComponentView extends ComponentView {

    public ScaledComponentView(Element elem) {
        super(elem);
    }


    public double getZoomFactor() {
        Double scale = (Double) getDocument().getProperty("ZOOM_FACTOR");
        if (scale != null) {
            return scale.doubleValue();
        }

        return 1;
    }


    public void paint(Graphics g, Shape allocation) {
        Graphics2D g2d = (Graphics2D) g;
        double zoomFactor = getZoomFactor();
        AffineTransform old = g2d.getTransform();
        g2d.scale(zoomFactor, zoomFactor);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        Rectangle alloc;
        alloc = allocation.getBounds();
        /*
        alloc.x *= zoomFactor;
        alloc.y *= zoomFactor;
        alloc.width *= zoomFactor;
        alloc.height *= zoomFactor;*/

        super.paint(g2d, alloc);
        g2d.setTransform(old);
    }


/*
public float getAlignment(int axis)
{
    return .7f;
}*/


    public float getMinimumSpan(int axis) {
        float f = super.getMinimumSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    public float getMaximumSpan(int axis) {
        float f = super.getMaximumSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    public float getPreferredSpan(int axis) {
        float f = super.getPreferredSpan(axis);
        f *= getZoomFactor();
        return f;
    }


    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        double zoomFactor = getZoomFactor();
        Rectangle alloc;
        alloc = a.getBounds();
        Shape s = super.modelToView(pos, alloc, b);
        alloc = s.getBounds();
        alloc.x *= zoomFactor;
        alloc.y *= zoomFactor;
        alloc.width *= zoomFactor;
        alloc.height *= zoomFactor;

        return alloc;
    }


    public int viewToModel(float x, float y, Shape a,
            Position.Bias[] bias) {
        double zoomFactor = getZoomFactor();
        Rectangle alloc = a.getBounds();
        x /= zoomFactor;
        y /= zoomFactor;
        alloc.x /= zoomFactor;
        alloc.y /= zoomFactor;
        alloc.width /= zoomFactor;
        alloc.height /= zoomFactor;

        return super.viewToModel(x, y, alloc, bias);
    }

}//end class.
