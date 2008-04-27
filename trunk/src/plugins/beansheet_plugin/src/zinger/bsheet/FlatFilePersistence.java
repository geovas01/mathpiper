// Bean Sheet
// Copyright (C) 2004-2007 Alexey Zinger
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

public class FlatFilePersistence extends StreamMapPersistence
{
	public static final FlatFilePersistence INSTANCE = new FlatFilePersistence();

	protected int[] commentChars;
	protected int[] quoteChars;
	protected boolean eolIsSignificant;
	protected int[] ordinaryChars;
	protected boolean slashSlashComments;
	protected boolean slashStarComments;

	protected int[] rowDelimChars;
	protected int[] colDelimChars;

	protected FlatFilePersistence()
	{
		this(new FilenameFileFilter(Pattern.compile(".*"), "Delimited Text Files"), null);
	}

	protected FlatFilePersistence(javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		super(fileFilter, extensions);
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence getCommentChars()
	{
		return this.writeEscapedChars(this.commentChars);
	}

	/**
	 * @since 1.0.5
	 */
	public void setCommentChars(CharSequence commentChars)
	{
		this.commentChars = this.readEscapedChars(commentChars);
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence getQuoteChars()
	{
		return this.writeEscapedChars(this.quoteChars);
	}

	/**
	 * @since 1.0.5
	 */
	public void setQuoteChars(CharSequence quoteChars)
	{
		this.quoteChars = this.readEscapedChars(quoteChars);
	}

	/**
	 * @since 1.0.5
	 */
	public boolean isEOLSignificant()
	{
		return this.eolIsSignificant;
	}

	/**
	 * @since 1.0.5
	 */
	public void setEOLSignificant(boolean eolSignificant)
	{
		this.eolIsSignificant = eolSignificant;
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence getOrdinaryChars()
	{
		return this.writeEscapedChars(this.ordinaryChars);
	}

	/**
	 * @since 1.0.5
	 */
	public void setOrdinaryChars(CharSequence ordinaryChars)
	{
		this.ordinaryChars = this.readEscapedChars(ordinaryChars);
	}

	/**
	 * @since 1.0.5
	 */
	public boolean getSlashSlashComments()
	{
		return this.slashSlashComments;
	}

	/**
	 * @since 1.0.5
	 */
	public void setSlashSlashComments(boolean slashSlashComments)
	{
		this.slashSlashComments = slashSlashComments;
	}

	/**
	 * @since 1.0.5
	 */
	public boolean getSlashStarComments()
	{
		return this.slashStarComments;
	}

	/**
	 * @since 1.0.5
	 */
	public void setSlashStarComments(boolean slashStarComments)
	{
		this.slashStarComments = slashStarComments;
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence getRowDelimChars()
	{
		return this.writeEscapedChars(this.rowDelimChars);
	}

	/**
	 * @since 1.0.5
	 */
	public void setRowDelimChars(CharSequence rowDelimChars)
	{
		this.rowDelimChars = this.readEscapedChars(rowDelimChars);
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence getColDelimChars()
	{
		return this.writeEscapedChars(this.colDelimChars);
	}

	/**
	 * @since 1.0.5
	 */
	public void setColDelimChars(CharSequence colDelimChars)
	{
		this.colDelimChars = this.readEscapedChars(colDelimChars);
	}

	/**
	 * @since 1.0.5
	 */
	public CharSequence writeEscapedChars(int[] chars)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < chars.length; ++i)
		{
			char ch = (char)chars[i];
			switch(ch)
			{
				case '\t':
				sb.append("\\t");
				break;

				case '\n':
				sb.append("\\n");
				break;

				case '\r':
				sb.append("\\r");
				break;

				case '\\':
				sb.append("\\\\");
				break;

				default:
				sb.append(ch);
				break;
			}
		}
		return sb;
	}

	public int[] readEscapedChars(CharSequence escapedChars)
	{
		int[] chars = new int[escapedChars.length()];
		int arrayIndex = -1;
		char ch;
		for(int i = 0; i < chars.length; ++i)
		{
			ch = escapedChars.charAt(i);
			if(ch == '\\')
			{
				ch = escapedChars.charAt(++i);
				switch(ch)
				{
					case 't':
					ch = '\t';
					break;

					case 'n':
					ch = '\n';
					break;

					case 'r':
					ch = '\r';
					break;

					case '\\':
					ch = '\\';
					break;
				}
			}
			chars[++arrayIndex] = (int)ch;
		}
		int[] result = new int[++arrayIndex];
		if(result.length == 0)
		{
			return null;
		}
		System.arraycopy(chars, 0, result, 0, result.length);
		return result;
	}

	protected synchronized boolean doPromptForParseSettings(Component parent)
	{
		if(parent != null)
		{
			JOptionPane optionPane = new JOptionPane(null, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			JDialog dialog = optionPane.createDialog(parent, Main.getConstant("dialog.flat_file.parse.title"));
			JTextField quoteCharsTextField = new JTextField("\"\'");
			quoteCharsTextField.setFont(Main.getFixedWidthFont());
			JTextField rowDelimCharsTextField = new JTextField("\\n\\r");
			rowDelimCharsTextField.setFont(Main.getFixedWidthFont());
			JTextField colDelimCharsTextField = new JTextField(",\\t");
			colDelimCharsTextField.setFont(Main.getFixedWidthFont());
			JPanel panel = new JPanel(new GridLayout(3, 2));
			panel.add(new JLabel(Main.getConstant("dialog.flat_file.parse.quote.label")));
			panel.add(quoteCharsTextField);
			panel.add(new JLabel(Main.getConstant("dialog.flat_file.parse.row_delim.label")));
			panel.add(rowDelimCharsTextField);
			panel.add(new JLabel(Main.getConstant("dialog.flat_file.parse.col_delim.label")));
			panel.add(colDelimCharsTextField);
			dialog.getContentPane().add(panel, BorderLayout.NORTH);
			dialog.pack();
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
			Object value = optionPane.getValue();
			if(value instanceof Integer && ((Integer)value).intValue() == JOptionPane.OK_OPTION)
			{
				this.quoteChars = this.readEscapedChars(quoteCharsTextField.getText());
				this.rowDelimChars = this.readEscapedChars(rowDelimCharsTextField.getText());
				this.colDelimChars = this.readEscapedChars(colDelimCharsTextField.getText());
			}
			else
			{
				return false;
			}
		}
		this.eolIsSignificant = true;
		this.slashSlashComments = false;
		this.slashStarComments = false;

		return true;
	}

	protected synchronized StreamTokenizer createTokenizer(Reader r, int[] quoteChars, int[] whitespaceChars)
	{
		StreamTokenizer tokenizer = new StreamTokenizer(r);
		tokenizer.resetSyntax();
		BitSet bs = new BitSet(256);
		bs.set(255);
		if(this.commentChars != null)
		{
			for(int i = 0; i < this.commentChars.length; ++i)
			{
				tokenizer.commentChar(this.commentChars[i]);
				bs.set(this.commentChars[i]);
			}
		}
		if(quoteChars != null)
		{
			for(int i = 0; i < quoteChars.length; ++i)
			{
				tokenizer.quoteChar(quoteChars[i]);
				bs.set(quoteChars[i]);
			}
		}
		tokenizer.eolIsSignificant(this.eolIsSignificant);
		if(this.ordinaryChars != null)
		{
			for(int i = 0; i < this.ordinaryChars.length; ++i)
			{
				tokenizer.ordinaryChar(this.ordinaryChars[i]);
			}
		}
		tokenizer.slashSlashComments(this.slashSlashComments);
		tokenizer.slashStarComments(this.slashStarComments);
		if(whitespaceChars != null)
		{
			for(int i = 0; i < whitespaceChars.length; ++i)
			{
				tokenizer.whitespaceChars(whitespaceChars[i], whitespaceChars[i]);
				tokenizer.ordinaryChar(whitespaceChars[i]);
				bs.set(whitespaceChars[i]);
			}
		}
		int firstWord = -1;
		for(int i = 0; i < 256; ++i)
		{
			if(bs.get(i))
			{
				if(firstWord > -1)
				{
					tokenizer.wordChars(firstWord, i - 1);
					firstWord = -1;
				}
			}
			else if(firstWord < 0)
			{
				firstWord = i;
			}
		}
		return tokenizer;
	}

	protected synchronized StreamTokenizer createRowTokenizer(Reader r)
	{
		return this.createTokenizer(r, null, this.rowDelimChars);
	}

	protected synchronized StreamTokenizer createColumnTokenizer(Reader r)
	{
		return this.createTokenizer(r, this.quoteChars, this.colDelimChars);
	}

	/**
	 * @since 0.9.7
	 */
	protected boolean isQuote(int ttype)
	{
		if(this.quoteChars != null)
		{
			for(int i = 0; i < this.quoteChars.length; ++i)
			{
				if(ttype == this.quoteChars[i])
				{
					return true;
				}
			}
		}
		return false;
	}

	protected void save(Map map, OutputStream os) throws IOException
	{
		Writer w = new OutputStreamWriter(os);
		Writer bw = new BufferedWriter(w);
		try
		{
			Vector row;
			Iterator colIterator;
			Object val;
			String sval;
			int quoteCharIndex;
			for(Iterator rowIterator = ((Vector)map.get(StandardMapPersistence.DATA_VECTOR_KEY)).iterator(); rowIterator.hasNext(); )
			{
				row = (Vector)rowIterator.next();
				if(row == null)
				{
					continue;
				}
				colIterator = row.iterator();
				if(colIterator.hasNext())
				{
					colIterator.next();
				}
				while(colIterator.hasNext())
				{
					val = colIterator.next();
					if(val != null)
					{
						sval = val.toString();
						quoteCharIndex = -1;
						if(this.quoteChars != null)
						{
							for(int i = 0; i < this.quoteChars.length; ++i)
							{
								if(sval.indexOf((char)this.quoteChars[i]) < 0)
								{
									quoteCharIndex = i;
									break;
								}
							}
						}
						if(quoteCharIndex > -1)
						{
							bw.write((char)this.quoteChars[quoteCharIndex]);
						}
						bw.write(val.toString());
						if(quoteCharIndex > -1)
						{
							bw.write((char)this.quoteChars[quoteCharIndex]);
						}
					}
					bw.write(this.colDelimChars[0]);
				}
				bw.write(rowDelimChars[0]);
			}
		}
		finally
		{
			bw.close();
			w.close();
			os.close();
		}
	}

	protected Map load(InputStream is) throws IOException
	{
		Reader r = new InputStreamReader(is);
		Reader br = new BufferedReader(r);
		try
		{
			Vector rows = new Vector();
			Vector row;
			int colCount = Integer.parseInt(Main.getConstant("table.cols.count"));
			boolean delimLast;
			for(StreamTokenizer rowTokenizer = this.createRowTokenizer(br); rowTokenizer.nextToken() != StreamTokenizer.TT_EOF; )
			{
				if(rowTokenizer.sval == null)
				{
					continue;
				}
				row = new Vector();
				row.add(null);
				delimLast = false;
				for(StreamTokenizer colTokenizer = this.createColumnTokenizer(new StringReader(rowTokenizer.sval)); colTokenizer.nextToken() != StreamTokenizer.TT_EOF; )
				{
					if(colTokenizer.ttype == StreamTokenizer.TT_WORD || this.isQuote(colTokenizer.ttype))
					{
						row.add(colTokenizer.sval);
						delimLast = false;
					}
					else
					{
						if(delimLast)
						{
							row.add(null);
						}
						delimLast = true;
					}
				}
				rows.add(row);
				colCount = Math.max(colCount, row.size());
			}
			this.decompressDataVector(rows, Integer.parseInt(Main.getConstant("table.rows.count")));

			Map map = new HashMap();
			map.put(StandardMapPersistence.DATA_VECTOR_KEY, rows);
			Vector colIDs = new Vector(colCount);
			colIDs.setSize(colCount);
			map.put(StandardMapPersistence.COLUMN_IDENTIFIERS_KEY, colIDs);

			return map;
		}
		finally
		{
			br.close();
			r.close();
			is.close();
		}
	}

	/**
	 * Prompts for parsing settings before calling superclass' implementation.
	 * If the parse settings are canceled by the user returns <code>false</code>
	 */
	public boolean save(JTable table, File file) throws IOException
	{
		return this.doPromptForParseSettings(table.getTopLevelAncestor()) && super.save(table, file);
	}

	/**
	 * Prompts for parsing settings before calling superclass' implementation.
	 * If the parse settings are canceled by the user returns <code>false</code>
	 */
	public boolean load(JTable table, File file) throws IOException, UnsupportedOperationException
	{
		return this.doPromptForParseSettings(table.getTopLevelAncestor()) && super.load(table, file);
	}

	protected Map save(JTable table)
	{
		Map map = super.save(table);
		this.evalValues(table, map);
		return map;
	}
}
