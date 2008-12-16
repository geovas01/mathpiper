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
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.ui.gui.applets;

public class SelectSymbol
{

	public int iheight;
	public int ispacing;
	public int iwidth;
	public int ix;
	public int iy;
	public int nr_symbols;
	String[] symbols = new String[64];

	public SelectSymbol(int width, int spacing)
	{
		ix = iy = 0;
		nr_symbols = 0;
		iwidth = width;
		ispacing = spacing;

		int i;
		iheight = 0;
		addSymbol("0");
		addSymbol("1");
		addSymbol("(");
		addSymbol(")");
		addSymbol("[");
		addSymbol("]");
		addSymbol("{");
		addSymbol("}");
		addSymbol("+");
		addSymbol("-");
		addSymbol("*");
		addSymbol("/");
		addSymbol(".");
		addSymbol(",");
		addSymbol(";");
		addSymbol(":");
		addSymbol("!");
		addSymbol("#");
		addSymbol("_");
		addSymbol("^");
		addSymbol("<");
		addSymbol(">");
		addSymbol("<=");
		addSymbol(">=");
		addSymbol(":=");
		addSymbol("=");
		addSymbol("!");
		addSymbol("@");
		iheight = (nr_symbols + iwidth - 1) / iwidth;
	}

	public void draw(int xpos, int ypos, MathPiperGraphicsContext aGraphicsContext)
	{
		aGraphicsContext.setFontSize(0, 12);
		aGraphicsContext.setColor(255, 255, 255);
		aGraphicsContext.fillRect(xpos, ypos, iwidth * ispacing, iheight * aGraphicsContext.fontHeight());
		aGraphicsContext.setColor(0, 0, 0);
		aGraphicsContext.drawRect(xpos, ypos, iwidth * ispacing, iheight * aGraphicsContext.fontHeight());

		int x;
		int y;
		aGraphicsContext.setColor(128, 128, 128);

		for (y = 0; y < iheight; y++)
		{
			aGraphicsContext.drawLine(xpos, ypos + y * aGraphicsContext.fontHeight(), xpos + iwidth * ispacing, ypos + y * aGraphicsContext.fontHeight());
		}

		for (x = 0; x < iwidth; x++)
		{
			aGraphicsContext.drawLine(xpos + x * ispacing, ypos, xpos + x * ispacing, ypos + iheight * aGraphicsContext.fontHeight());
		}

		aGraphicsContext.setColor(0, 0, 0);

		for (y = 0; y < iheight; y++)
		{

			for (x = 0; x < iwidth; x++)
			{

				if (ix == x && iy == y)
				{
					aGraphicsContext.setColor(128, 128, 128);
					aGraphicsContext.fillRect(xpos + x * ispacing, ypos + y * aGraphicsContext.fontHeight(), ispacing, aGraphicsContext.fontHeight());
					aGraphicsContext.setColor(0, 0, 0);
				}

				if (symbols[y * iwidth + x] != null)
				{
					aGraphicsContext.drawText(xpos + x * ispacing + (ispacing - aGraphicsContext.textWidthInPixels(symbols[y * iwidth + x])) / 2, ypos + (y + 1) * aGraphicsContext.fontHeight() - aGraphicsContext.fontDescent(), symbols[y * iwidth + x]);
				}
			}
		}
	}

	public void addSymbol(String sym)
	{
		symbols[nr_symbols++] = sym;
	}

	public int height(MathPiperGraphicsContext aGraphicsContext)
	{

		return iheight * aGraphicsContext.fontHeight();
	}
};
