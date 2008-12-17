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

package org.mathpiper.ui.gui.consoles;


public class HintWindow
{
	public  String[] iText = new String[64];
	public  String[] iDescription = new String[64];

	public  boolean iAllowSelection = true;
	public  int iNrDescriptions;
	public  int iMaxWidth;
	public  int iTextSize;
	public  int iCurrentPos;
	public  int iNrLines;

	public  HintWindow(int aTextSize)
	{
		iNrLines = 0;
		iNrDescriptions = 0;
		iMaxWidth = 0;
		iTextSize = aTextSize;
		iCurrentPos = 0;
	}
	public  void addLine(String aText)
	{
		if (iNrLines >= 20) return;
		iText[iNrLines] = aText;
		iNrLines++;
		iMaxWidth = 0;
	}
	public  void addDescription(String aText)
	{
		if (iNrDescriptions >= 20) return;
		iDescription[iNrDescriptions] = aText;
		iNrDescriptions++;
		iMaxWidth = 0;
	}
	public  void draw(int x, int y, MathPiperGraphicsContext  aGraphicsContext)
	{
		aGraphicsContext.setFontSize(0,iTextSize);
		if (iMaxWidth == 0)
		{
			int i;
			for (i=0;i<iNrLines;i++)
			{
				int width = aGraphicsContext.textWidthInPixels(iText[i]);
				if (width>iMaxWidth)
					iMaxWidth = width;
			}
			for (i=0;i<iNrDescriptions;i++)
			{
				int width = aGraphicsContext.textWidthInPixels(iDescription[i]);
				if (width>iMaxWidth)
					iMaxWidth = width;
			}
			iMaxWidth = iMaxWidth + 8;
		}

		//System.out.println("iNrLines = "+iNrLines);
		//System.out.println("iMaxWidth = "+iMaxWidth);

		int ix = x;
		int iy = y;
		int w = 5+iMaxWidth;
		int h = height(aGraphicsContext);
		iy -= (h+4);

		if (!iAllowSelection)
			aGraphicsContext.setColor(221,221,238);
		else
			aGraphicsContext.setColor(221,221,238);
		aGraphicsContext.fillRect(ix,iy,w,h);
		aGraphicsContext.setColor(0,0,0);
		aGraphicsContext.drawRect(ix,iy,w,h);

		int i;

		//System.out.println("iTextSize = "+iTextSize);
		//System.out.println("aGraphicsContext.FontHeight() = "+aGraphicsContext.FontHeight());

		for (i=0;i<iNrLines;i++)
		{
			if (!iAllowSelection)
			{
				aGraphicsContext.setColor(0,0,0);
			}
			else
			{
				if (i == iCurrentPos)
				{
					aGraphicsContext.setColor(190,190,200);
					aGraphicsContext.fillRect(1+ix,1+iy+(i)*aGraphicsContext.fontHeight(),w-2,aGraphicsContext.fontHeight()-2);
					aGraphicsContext.setColor(0,0,0);
				}
				else
				{
					aGraphicsContext.setColor(0,0,0);
				}
			}
			aGraphicsContext.drawText(ix+2,iy+(i+1)*aGraphicsContext.fontHeight()-aGraphicsContext.fontDescent(),iText[i]);
		}

		if (iNrDescriptions>0)
		{
			int offset = (iNrLines+1)*aGraphicsContext.fontHeight()+7;

			aGraphicsContext.drawLine(ix+6,iy+offset-4-aGraphicsContext.fontHeight(),ix+w-6,iy+offset-4-aGraphicsContext.fontHeight());

			aGraphicsContext.setFontSize(1,iTextSize);
			for (i=0;i<iNrDescriptions;i++)
			{
				aGraphicsContext.drawText(ix+2,iy+offset+(i)*aGraphicsContext.fontHeight()-aGraphicsContext.fontDescent(),iDescription[i]);
			}
		}
	}



	public  int height(MathPiperGraphicsContext  aGraphicsContext)
	{
		int h;
		aGraphicsContext.setFontSize(0,iTextSize);
		h = iNrLines*aGraphicsContext.fontHeight()+2;

		if (iNrDescriptions>0)
		{
			aGraphicsContext.setFontSize(1,iTextSize);
			h += iNrDescriptions*aGraphicsContext.fontHeight()+2;
			// space for line
			h+=7;
		}
		return h;
	}

}



