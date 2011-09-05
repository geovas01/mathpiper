// Bean Sheet
// Copyright (C) 2004-2005 Alexey Zinger
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

package zinger.bsheet;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;

public class HTMLPersistence extends AbstractPersistence
{
	public static final HTMLPersistence INSTANCE = new HTMLPersistence();

	protected static final Pattern HTML_VAL_PATTERN = Pattern.compile("\\<html\\>(.*)\\</html\\>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	protected final StandardMapPersistence mapPersistence = new StandardMapPersistence()
	{
		public javax.swing.filechooser.FileFilter getFileFilter()
		{
			throw new UnsupportedOperationException();
		}

		protected Map load(File file) throws IOException
		{
			throw new UnsupportedOperationException();
		}

		protected void save(Map map, File file) throws IOException
		{
			throw new UnsupportedOperationException();
		}
	};

	protected HTMLPersistence()
	{
		super(
			new FilenameFileFilter(Pattern.compile("^.*\\.html?$", Pattern.CASE_INSENSITIVE), "HTML (*.html, *.htm)"),
			new String[] {"html", "htm"}
		);
	}

	public boolean isLoadCapable()
	{
		return false;
	}

	public boolean load(JTable table, File file) throws IOException, UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	public boolean save(JTable table, File file) throws IOException
	{
		OutputStream os = new FileOutputStream(file);
		try
		{
			this.save(table, os);
			return false;
		}
		finally
		{
			os.close();
		}
	}

	public void save(JTable table, OutputStream os) throws IOException
	{
		Writer writer = new OutputStreamWriter(os);
		try
		{
			this.save(table, writer);
		}
		finally
		{
			writer.close();
		}
	}

	public void save(JTable table, Writer writer) throws IOException
	{
		BSHTableModel model = (BSHTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();
		BufferedWriter bwriter = new BufferedWriter(writer);
		try
		{
			bwriter.write("<html>\n<body>\n<table cellspacing=\"0\">\n");

			Map map = this.mapPersistence.save(table);

			Vector dataVector = (Vector)map.get(StandardMapPersistence.DATA_VECTOR_KEY);
			Vector rowVector;
			int rowLength;
			int rowIndex = -1;
			Object val;
			Font font;
			for(Iterator rowIterator = dataVector.iterator(); rowIterator.hasNext();)
			{
				++rowIndex;
				rowVector = (Vector)rowIterator.next();
				bwriter.write("<tr height=\"");
				bwriter.write(String.valueOf(table.getRowHeight(rowIndex)));
				bwriter.write("\">");
				if(rowVector != null)
				{
					rowLength = rowVector.size();
					for(int colIndex = 1; colIndex < rowLength; ++colIndex)
					{
						bwriter.write("<td width=\"");
						bwriter.write(String.valueOf(columnModel.getColumn(colIndex).getWidth()));
						bwriter.write('\"');

						Appearance appearance = model.getAppearance(colIndex, rowIndex, false);
						if(appearance == null)
						{
							appearance = model.getAppearance(colIndex, -1, false);
						}
						font = null;
						if(appearance != null)
						{
							Color bgColor = appearance.getBgColor();
							if(bgColor != null)
							{
								bwriter.write(" bgcolor=\"");
								this.writeHtmlRGB(bgColor, bwriter);
								bwriter.write('\"');
							}

							Color fgColor = appearance.getFgColor();
							if(fgColor != null)
							{
								bwriter.write(" style=\"color:");
								this.writeHtmlRGB(fgColor, bwriter);
								bwriter.write('\"');
							}

							font = appearance.getFont();
							if(font != null)
							{
								bwriter.write(" style=\"font-family:");
								bwriter.write(font.getName());
								bwriter.write(";font-size:");
								bwriter.write(String.valueOf(font.getSize()));
								bwriter.write('\"');
							}
						}
						bwriter.write(">");
						if(font != null)
						{
							if(font.isBold())
							{
								bwriter.write("<b>");
							}
							if(font.isItalic())
							{
								bwriter.write("<i>");
							}
						}

						val = model.evalValueAt(colIndex, rowIndex);
						val = model.formatValueAt(val, colIndex, rowIndex);
						if(val != null)
						{
							if(val instanceof String)
							{
								Matcher m = HTMLPersistence.HTML_VAL_PATTERN.matcher((String)val);
								if(m.matches())
								{
									val = m.group(1);
								}
								else
								{
									val = ((String)val).replaceAll("\\\"", "&quot;");
									val = ((String)val).replaceAll("\\<", "&lt;");
									val = ((String)val).replaceAll("\\>", "&gt;");
								}
							}
							bwriter.write(val.toString());
						}

						if(font != null)
						{
							if(font.isItalic())
							{
								bwriter.write("</i>");
							}
							if(font.isBold())
							{
								bwriter.write("</b>");
							}
						}
						bwriter.write("</td>");
					}
				}
				bwriter.write("</tr>\n");
			}

			bwriter.write("</table>\n</body>\n</html>");
		}
		finally
		{
			bwriter.close();
		}
	}

	protected void writeHtmlRGB(Color color, Writer writer) throws IOException
	{
		writer.write('#');

		int component = color.getRed();
		writer.write(this.hexDigit(component >> 4));
		writer.write(this.hexDigit(component & 0xF));

		component = color.getGreen();
		writer.write(this.hexDigit(component >> 4));
		writer.write(this.hexDigit(component & 0xF));

		component = color.getBlue();
		writer.write(this.hexDigit(component >> 4));
		writer.write(this.hexDigit(component & 0xF));
	}

	protected char hexDigit(int n)
	{
		return n < 10 ?
			(char)('0' + n) :
			(char)(55 + n);
	}
}
