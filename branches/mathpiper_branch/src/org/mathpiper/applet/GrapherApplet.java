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

package org.mathpiper.applet;

import java.net.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class GrapherApplet extends java.applet.Applet implements KeyListener
{
	Dimension offDimension;
	Image offImage;
	Graphics offGraphics;

	Grapher grapher;

	String iRenderOperations;

	public void init()
	{
		iRenderOperations = getParameter("CallList");
		if (iRenderOperations == null)
			iRenderOperations = "";
		grapher = new Grapher(iRenderOperations);
		addKeyListener(this);
		repaint();
	}

	public void	keyReleased(KeyEvent e) {}
	public void	keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e)
	{
		double scf = 1.05;
		switch (e.getKeyChar())
		{
		case 'o':
		case 'O':
			grapher.xmin *= scf;
			grapher.xmax *= scf;
			offImage = null; offGraphics = null; repaint();
			break;
		case 'p':
		case 'P':
			grapher.xmin /= scf;
			grapher.xmax /= scf;
			offImage = null; offGraphics = null; repaint();
			break;

		case 'a':
		case 'A':
			grapher.ymin *= scf;
			grapher.ymax *= scf;
			offImage = null; offGraphics = null; repaint();
			break;
		case 'z':
		case 'Z':
			grapher.ymin /= scf;
			grapher.ymax /= scf;
			offImage = null; offGraphics = null; repaint();
			break;
		}
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

	void drawToOffscreen()
	{
		// Create the offscreen graphics context
		Dimension d = getSize();
		if ((offGraphics == null)
		                || (d.width != offDimension.width)
		                || (d.height != offDimension.height))
		{
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
			paintFrame(offGraphics);
		}
	}
	public void update(Graphics g)
	{
		drawToOffscreen();

		// Paint the frame into the image
		synchronized(offImage)
		{
			// Paint the image onto the screen
			g.drawImage(offImage, 0, 0, null);
		}
	}

	public void paint(Graphics g)
	{
		drawToOffscreen();
		if (offImage != null)
		{
			synchronized(offImage)
			{
				g.drawImage(offImage, 0, 0, null);
			}
		}
	}

	synchronized public void paintFrame(Graphics g)
	{
		Dimension d = getSize();
		grapher.paint(g, 0, 0, d);
	}
};

