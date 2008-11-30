/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.ui.gui.applets;

import org.mathpiper.lisp.parsers.TexParser;
import org.mathpiper.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class FormulaViewApplet extends Applet
{
	Image offImage = null;
	Graphics offGraphics = null;
	Dimension offDimension = null;

	SBox expression = null;

	public void init()
	{
		setBackground(Color.white);
		setLayout(null);
	}

	public void start()
	{
		repaint();
	}

	public void stop()
	{
		offImage = null;
		offGraphics = null;
	}



	public void update(Graphics g)
	{
		Dimension d = getSize();
		// Create the offscreen graphics context
		if ((offGraphics == null)
		                || (d.width != offDimension.width)
		                || (d.height != offDimension.height))
		{
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
			// Paint the frame into the image
			paintFrame(offGraphics);
		}

		// Paint the image onto the screen
		g.drawImage(offImage, 0, 0, null);
	}

	/**
	  * Paint the previous frame (if any).
	  */
	public void paint(Graphics g)
	{
		//System.out.println("paint");
		Dimension d = getSize();
		if ((offGraphics == null)
		                || (d.width != offDimension.width)
		                || (d.height != offDimension.height))
		{
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();

			// Paint the frame into the image
			paintFrame(offGraphics);
		}
		if (offImage != null)
		{
			g.drawImage(offImage, 0, 0, null);
		}
	}

	void paintFrame(Graphics g)
	{
		//System.out.println("paintFrame");
		// Tell the rendering system we'd like to have anti-aliasing please
		if (g instanceof Graphics2D)
		{
			Graphics2D g2d = null;
			g2d = (Graphics2D)g;
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			                      RenderingHints.VALUE_ANTIALIAS_ON));
		}

		// Clear Background
		Dimension d = getSize();
		//    g.setColor(Color.white);
		//   g.fillRect(0, 0, d.width, d.height);

		// All graphics should be black from now on
		g.setColor(Color.black);

		GraphicsPrimitives gp = new GraphicsPrimitives(g);

		gp.SetLineThickness(0);

		if (expression == null)
		{
			String s = getParameter("expression");
			if (s != null)
			{
				System.out.println("re-rendering the whole formula!");
				TexParser parser = new TexParser();
				expression = parser.parse(s);
			}
		}
		if (expression != null)
		{
			expression.calculatePositions(gp, 3, new java.awt.Point(1, d.height/2));
			expression.render(gp);
		}
	}

}


