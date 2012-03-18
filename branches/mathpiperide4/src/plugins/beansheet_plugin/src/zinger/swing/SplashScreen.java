// Bean Sheet
// Copyright (C) 2004 Alexey Zinger
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package zinger.swing;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.*;

import javax.swing.*;

/**
 * Generic splash screen that displays text overlayed over an image.
 * The main splash screen window responds to a mouse click by hiding itself.
 *
 * @since 0.9
 */
public class SplashScreen extends Window
{
	public static class SplashCanvas extends Canvas
	{
		private Image image;
		private final Dimension preferredSize;

		protected final String text;
		protected final int textX;
		protected final int textY;
		protected final Font textFont;

		private boolean textRendered = false;

		public SplashCanvas(URL imageURL, String text, int textX, int textY, Font textFont)
		{
			this.image = this.getToolkit().createImage(imageURL);
			this.text = text;
			this.textX = textX;
			this.textY = textY;
			this.textFont = textFont;

			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(this.image, 0);
			try
			{
				tracker.waitForAll();
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
			this.preferredSize = new Dimension(this.image.getWidth(this), this.image.getHeight(this));
		}

		/**
		 * Not thread safe.
		 */
		protected void renderText()
		{
			if(this.textRendered)
			{
				return;
			}

			Image buffer = this.createImage(this.image.getWidth(this), this.image.getHeight(this));

			buffer.getGraphics().drawImage(this.image, 0, 0, this);

			int x = this.textX;
			int y = this.textY;
			int w = buffer.getWidth(this) - x;
			int h = buffer.getHeight(this) - y;

			JLabel label = new JLabel(text);
			label.setSize(w, h);
			label.setLocation(x, y);
			label.setFont(textFont);
			label.paint(buffer.getGraphics());

			this.image = buffer;
			this.textRendered = true;
		}

		protected synchronized Image getImage()
		{
			this.renderText();
			return this.image;
		}

		public void paint(Graphics g)
		{
			g.drawImage(this.getImage(), 0, 0, this);
		}

		public Dimension getPreferredSize()
		{
			return this.preferredSize;
		}
	}

	public SplashScreen(URL imageURL, Window owner, String text, int textX, int textY, Font textFont)
	{
		super(owner);
		SplashScreen.SplashCanvas can = new SplashScreen.SplashCanvas(imageURL, text, textX, textY, textFont);
		can.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent ev)
			{
				SplashScreen.this.setVisible(false);
			}
		});
		this.add(can);
		this.pack();
	}
}
