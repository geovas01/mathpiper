/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.ui.gui.worksheets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;

/**
 *
 * @author tkosan
 */
public class DynamicIcon implements Icon {

    Font font;
    private final static int DEFAULT_SIZE = 16;
    private int width = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;

    private String iconText;

    public DynamicIcon(String iconText, int fontSize) {
        this.iconText = iconText;
        
        font = new Font("SansSerif", Font.PLAIN, fontSize);

        recalculateIconWidth(iconText);
    }

    private void recalculateIconWidth(String iconText) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(iconText, frc);
        width = (int) bounds.getWidth();
        height = (int) bounds.getHeight();
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(font);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //FontColor
        g2d.setPaint(Color.BLACK);
        g2d.drawString(iconText, x, y);
    }

}
