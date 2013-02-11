package org.mathpiper.ui.gui.help;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;

public class RenderedLatex extends JLabel {
    
    private double zoomScale = 1.0;
    
    TeXFormula texFormula;

    public RenderedLatex() {
        super();
        //this.setText("Hello.");


    }


    public void setLatex(String latexString) {
        
        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
	DefaultTeXFont.registerAlphabet(new GreekRegistration());
	texFormula = new TeXFormula(latexString);
	TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 17);
	icon.setInsets(new Insets(1, 1, 1, 1));
        this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        this.setAlignmentY(icon.getBaseLine());
        this.setIcon(icon);

    }
    
    public void setZoomScale(double zoomScale)
    {
	this.zoomScale = zoomScale;
	
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float)(12 * zoomScale));
        icon.setInsets(new Insets(1, 1, 1, 1));
        this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        this.setAlignmentY(icon.getBaseLine());
        this.setIcon(icon);

	JLabel jl = new JLabel();

	jl.setForeground(new Color(0, 0, 0));
	
	icon.paintIcon(jl, this.getGraphics(), (int)(this.getX() * zoomScale), (int)(this.getY() * zoomScale));
    }
    
/*
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                AffineTransform old = g2d.getTransform();
                g2d.scale(zoomScale, zoomScale);
                super.paint(g2d);
                g2d.setTransform(old);
            }

            public Dimension getPreferredSize() {
        
        	int x = (int) (this.getX() * zoomScale);
        	int y = (int) (this.getY() * zoomScale);
        	
        	
        	
        	return (new Dimension(x,y));
        
            }//end method.
        
        */
        
}
