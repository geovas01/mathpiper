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

import bsh.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import zinger.bsheet.commands.*;
import zinger.util.recycling.*;


/**
 * Core Bean Sheet functionality.
 * This class provides integration between <a href="http://www.beanshell.org" target="_blank">Bean Shell</a>
 * and Swing.  Also contains some parsing and formatting logic.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class BSHTableModel extends DefaultTableModel implements CellEditorListener
{
	/**
	 * Extensions to default cell rendering.
	 * This class invokes tool tips, cell evaluation as well as formatting, when available.
	 *
	 * @see zinger.bsheet.BSHTableModel#evalValueAt(int, int)
	 * @see zinger.bsheet.BSHTableModel#formatValueAt(java.lang.Object, int, int)
	 */
    public class BSHCellRenderer extends DefaultTableCellRenderer
    {
		protected final Appearance NULL_APPEARANCE = new Appearance();

		protected final MessageFormat toolTipFormat = new MessageFormat("<html><pre>{0}</pre></html>");
		private final Object[] toolTipFormatParams = new Object[1];

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column)
        {
            int modelColumnIndex = table.convertColumnIndexToModel(column);
            Object formattedValue = this.getFormattedValue(modelColumnIndex, row);
            Component renderer = super.getTableCellRendererComponent(table,
                                                                     formattedValue,
                                                                     isSelected,
                                                                     hasFocus,
                                                                     row,
                                                                     column);
			if(renderer instanceof JComponent)
			{
				synchronized(this.toolTipFormatParams)
				{
					this.toolTipFormatParams[0] = value;
					((JComponent)renderer).setToolTipText(this.toolTipFormat.format(this.toolTipFormatParams));
				}
			}

			Appearance presentation = BSHTableModel.this.getAppearance(column, row, false);
			if(presentation == null)
			{
				presentation = BSHTableModel.this.getAppearance(column, -1, false);
			}
			if(presentation == null)
			{
				presentation = this.NULL_APPEARANCE;
			}

			Font font = presentation.getFont();
			renderer.setFont(font);

			if(renderer instanceof JLabel)
			{
				Number alignmentX = presentation.getAlignmentX();
				if(alignmentX != null)
				{
					((JLabel)renderer).setHorizontalAlignment(alignmentX.intValue());
				}

				Number alignmentY = presentation.getAlignmentY();
				if(alignmentY != null)
				{
					((JLabel)renderer).setVerticalAlignment(alignmentY.intValue());
				}
			}
			else if(renderer instanceof JComponent)
			{
				Number alignmentX = presentation.getAlignmentX();
				if(alignmentX != null)
				{
					((JComponent)renderer).setAlignmentX(alignmentX.floatValue());
				}

				Number alignmentY = presentation.getAlignmentY();
				if(alignmentY != null)
				{
					((JComponent)renderer).setAlignmentY(alignmentY.floatValue());
				}
			}

			if(!isSelected || hasFocus)
			{
				Color bgColor = presentation.getBgColor();
				renderer.setBackground(bgColor);

				Color fgColor = presentation.getFgColor();
				renderer.setForeground(fgColor);
			}

			return renderer;
        }

        protected Object getEvaluatedValue(int col, int row)
        {
			return BSHTableModel.this.evalValueAt(col, row);
		}

		protected Object getFormattedValue(int col, int row)
		{
			return BSHTableModel.this.formatValueAt(this.getEvaluatedValue(col, row), col, row);
		}
    }

	/**
	 * Extentions to default cell editing.
	 * This class adds formatting to default cell editing.
	 *
	 * @see zinger.bsheet.BSHTableModel#formatValueAt(java.lang.Object, int, int)
	 */
    public class BSHCellEditor implements TableCellEditor
    {
        private final TableCellEditor editor;

        public BSHCellEditor(TableCellEditor editor)
        {
            this.editor = editor;
        }

        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column)
        {
            BSHTableModel.this.editingRow = row;
            BSHTableModel.this.editingColumn = column;
            return this.editor.getTableCellEditorComponent(table,
                                                           BSHTableModel.this.formatValueAt(value, table.convertColumnIndexToModel(column), row),
                                                           isSelected,
                                                           row,
                                                           column);
        }

        public Object getCellEditorValue()
        {
            return this.editor.getCellEditorValue();
        }

        public boolean isCellEditable(EventObject anEvent)
        {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent)anEvent).getClickCount() >= 2;
            }
            return true;
        }

        public boolean shouldSelectCell(EventObject anEvent)
        {
            return this.editor.shouldSelectCell(anEvent);
        }

        public boolean stopCellEditing()
        {
            return this.editor.stopCellEditing();
        }

        public void cancelCellEditing()
        {
            this.editor.cancelCellEditing();
        }

        public void addCellEditorListener(CellEditorListener l)
        {
            this.editor.addCellEditorListener(l);
        }

        public void removeCellEditorListener(CellEditorListener l)
        {
            this.editor.removeCellEditorListener(l);
        }
    }

	/**
	 * Current row being edited.
	 */
    protected int editingRow;

    /**
     * Current column being edited.
     */
    protected int editingColumn;

	/**
	 * Instance of Bean Shell interpreter.
	 */
    protected final Interpreter bsh;

    /**
     * Regular expression object representing a cell address reference (e.g. <code>[B10]</code>).
     */
    protected final Pattern cellRefPattern;

	/**
	 * Regular expression object representing a cell range iterator (e.g. <code>[B2:C15]</code>).
	 */
    protected final Pattern cellRangePattern;

	/**
	 * Regular expression object representing a relative cell reference (e.g. <code>[-1+2]</code>).
	 */
    protected final Pattern cellRelativeRefPattern;

    /**
     * Object pool for <code>StringBuffer</code> objects.
     * This object is thread safe.
     */
    protected final ObjectPool stringBufferRecycler = new SynchronizedObjectPool(new ObjectRecycler(new StringBufferGenerator()));

    /**
     * Object pool for <code>Set</code> objects.
     * This object is thread safe.
     */
    protected final ObjectPool setRecycler = new SynchronizedObjectPool(new ObjectRecycler(new CollectionGenerator(HashSet.class)));

    /**
     * Two-way dependencies tracker.
     */
    protected transient Dependencies dependencies;

    /**
     * Evaluation cache.
     * This two-dimensional array is used by <code>evalValueAt(int, int)</code> method.
     * As of 0.8, the model accomodates cached <code>null</code>
	 * values by means of explicity cached status flags.
     *
     * @see #evalValueAt(int, int)
     * @see #isValueCached(int, int)
     * @see #setValueCached(boolean, int, int)
     * @see #valuesCached
     */
    protected transient Object[][] cache;

	/**
     * @see #evalValueAt(int, int)
     * @see #isValueCached(int, int)
     * @see #setValueCached(boolean, int, int)
	 * @see #dependencies
     *
	 * @since Bean Sheet 0.8
	 */
    protected transient BitSet[] valuesCached;

	/**
	 * A map of column format arrays.
	 * This object maps column name to an array of formats.
	 *
	 * @see #getFormat(int, int)
	 * @see #setFormat(java.text.Format, int, int)
	 */
    protected Map columnFormats = new HashMap();

    protected Map appearanceMap = new HashMap();

    private transient int lastNonEmptyRow;
    private transient int lastNonEmptyColumn;

	/**
	 * Controls whether the left-most column (index 0) shows row index.
	 * This is a bit of a band-aid measure meant to help with
	 * <code>TableSort.ComparatorTableModel</code> and other future extensions of this class.
	 * May become deprecated in favor of cleaner API.
	 *
	 * @since 0.9.4
	 */
    protected boolean rowIndexes = true;

	/**
	 * @since 1.0.3
	 */
    protected boolean suppressDependencies = false;

	/**
	 * Instantiates the model using initial row and column counts.
	 */
    public BSHTableModel(int rowCount, int columnCount)
    {
        super(rowCount, columnCount);
        this.cellRefPattern = Pattern.compile("([A-Za-z]+)(\\d+)");
        this.cellRangePattern = range.INSTANCE.rangePattern;
        this.cellRelativeRefPattern = Pattern.compile("([\\+\\-]\\d+)([\\+\\-]\\d+)");
        this.bsh = new Interpreter();
        try
        {
			this.bsh.getNameSpace().getClassManager().setClassLoader(this.getClass().getClassLoader());
			this.bsh.getNameSpace().importCommands("zinger.bsheet.commands");
            this.bsh.set("model", this);
        }
        catch(EvalError ex)
        {
            ex.printStackTrace(System.err);
        }
    }

	/**
	 * Sets a value at the given address.
	 * If the value is a string, not a formula, and a <code>Format</code> object is available for
	 * the given address, parsing the value is attempted using the <code>Format</code>object.
	 * If the parsing is successful, the object stored in the model itself is the result
	 * of the parsing.  For example, if the format is an instance of <code>SimpleDateFormat</code>,
	 * and its pattern is set to &quot;<code>yyyy-MM-dd</code>&quot;, then the value string
	 * &quot;<code>1977-9-6</code>&quot; would be parsed and stored as an instance of <code>Date</code>
	 * representing September 6, 1977.
	 *
	 * @see #getFormat(int, int)
	 */
    public void setValueAt(Object value, int row, int column)
    {
        if(value instanceof String && !((String)value).startsWith("="))
        {
            Format format = this.getFormat(column, row);
            if(format != null)
            {
                try
                {
                    value = format.parseObject((String)value);
                }
                catch(java.text.ParseException ex)
                {
                    //ex.printStackTrace();
                }
            }
        }
        super.setValueAt(value, row, column);
        if(row > this.lastNonEmptyRow)
        {
			this.lastNonEmptyRow = row;
		}
		if(column > this.lastNonEmptyColumn)
		{
			this.lastNonEmptyColumn = column;
		}
        this.followDependencies(column, row);
    }

	/**
	 * Formats the given value using formatting rules for the given address.
	 * If no formatting rules are available for the given address or if it fails for the given value,
	 * the value itself is returned without any formatting applied.
	 *
	 * @see #getFormat(int, int)
	 */
    public Object formatValueAt(Object value, int column, int row)
    {
        if(value != null && !(value instanceof String && ((String)value).length() > 0 && ((String)value).charAt(0) == '='))
        {
            Format format = this.getFormat(column, row);
            if(format != null)
            {
                try
                {
                    value = format.format(value);
                }
                catch(IllegalArgumentException ex)
                {
                    //ex.printStackTrace();
                }
                catch(ClassCastException ex)
                {
					//ex.printStackTrace();
				}
            }
        }
        return value;
    }

    /**
	 * Extends default behavior to always return row index for column 0.
	 * Column 0 is a special column that is used as a row header.
	 */
    public Object getValueAt(int row, int column)
    {
        return this.rowIndexes && column == 0 ? new Integer(row) : super.getValueAt(row, column);
    }

	/**
	 * Extends default behavior to return <code>null</code> for column 0.
	 * Column 0 is a special column that is used as a row header.
	 */
    public String getColumnName(int column)
    {
        return column == 0 ? null : super.getColumnName(column - 1);
    }

    /**
     * Extends default behavior to return a class object representing <code>JTableHeader</code>
     * for column 0.
	 * Column 0 is a special column that is used as a row header.
     */
    public Class getColumnClass(int column)
    {
        return column == 0 ? JTableHeader.class : super.getColumnClass(column);
    }

	/**
	 * Extends default behavior to return <code>false</code> for column 0.
	 * Column 0 is a special column that is used as a row header.
	 */
    public boolean isCellEditable(int row, int column)
    {
        return column > 0 && super.isCellEditable(row, column);
    }

    public Appearance removeAppearance(int column, int row)
    {
		Appearance[] appearances = (Appearance[])this.appearanceMap.get(this.getColumnName(column));
		if(appearances == null || appearances.length <= row)
		{
			return null;
		}
		Appearance appearance = appearances[row + 1];
		appearances[row + 1] = null;
		return appearance;
	}

    public Appearance getAppearance(int column, int row, boolean mutable)
    {
		Appearance[] appearances = (Appearance[])this.appearanceMap.get(this.getColumnName(column));
		if(appearances == null)
		{
			if(mutable)
			{
				appearances = new Appearance[this.getRowCount() + 1];
				this.appearanceMap.put(this.getColumnName(column), appearances);
			}
			else
			{
				return null;
			}
		}
		else if(row + 1 >= appearances.length)
		{
			if(mutable)
			{
				Appearance[] tempAppearances = appearances;
				appearances = new Appearance[this.getRowCount() + 1];
				System.arraycopy(tempAppearances, 0, appearances, 0, tempAppearances.length);
				this.appearanceMap.put(this.getColumnName(column), appearances);
			}
			else
			{
				return null;
			}
		}

		if(appearances[row + 1] == null && mutable)
		{
			appearances[row + 1] = new Appearance();
		}
		return appearances[row + 1];
	}

	/**
	 * Associates the given format object with the given cell or column.
	 * If <code>row</code> is <code>0</code>, the format is associated with the entire column.
	 *
	 * @see #getFormat(int, int)
	 */
    public void setFormat(Format format, int column, int row)
    {
        Format[] formats = (Format[])this.columnFormats.get(this.getColumnName(column));
        if(formats == null)
        {
            formats = new Format[this.getRowCount() + 1];
            this.columnFormats.put(this.getColumnName(column), formats);
        }
        else if(row >= formats.length)
        {
			Format[] tempFormats = formats;
			formats = new Format[this.getRowCount() + 1];
			System.arraycopy(tempFormats, 0, formats, 0, tempFormats.length);
			this.columnFormats.put(this.getColumnName(column), formats);
		}
        formats[row + 1] = format;

        if(row < 0)
        {
			for(int i = this.getRowCount() - 1; i > -1; --i)
			{
				this.followDependencies(column, i);
			}
		}
		else
		{
			this.followDependencies(column, row);
		}
    }

	/**
	 * Returns the format object associated with the given cell or column.
	 * If <code>row</code> is <code>0</code>, the returned format is for the entire column.
	 * Also if there is no format available for the given cell, the format for the column is then
	 * returned.
	 *
	 * @see #setFormat(java.text.Format, int, int)
	 */
    public Format getFormat(int column, int row)
    {
        Format[] formats = (Format[])this.columnFormats.get(this.getColumnName(column));
        Format format;
        if(formats == null)
        {
            format = null;
        }
        else
        {
			if(formats.length > row + 1)
			{
				format = formats[row + 1];
			}
			else
			{
				format = null;
			}
			if(format == null)
			{
				format = formats[0];
			}
        }
        return format;
    }

	/**
	 * Checks for cache state for a specific cell.
	 * As of 0.8, the model maintains an array of bit sets that maintain these states.
	 *
	 * @see #setValueCached(boolean, int, int)
	 *
	 * @since Bean Sheet 0.8
	 */
    protected boolean isValueCached(int column, int row)
    {
		return this.valuesCached[column] != null && this.valuesCached[column].get(row);
	}

	/**
	 * Sets cache state for a specific cell.
	 * As of 0.8, the model maintains an array of bit sets that maintain these states.
	 *
	 * @see #isValueCached(int, int)
	 *
	 * @since Bean Sheet 0.8
	 */
	protected void setValueCached(boolean cached, int column, int row)
	{
		if(this.valuesCached[column] == null)
		{
			this.valuesCached[column] = new BitSet(row);
		}
		this.valuesCached[column].set(row, cached);
	}

    /**
     * Evaluates a value at the given cell address.
     * If the object is a formula string (starts with <code>=</code>, then
     * the formula is processed using internal preprocessing rules and then passed on
     * to the Bean Shell interpreter (<code>bsh</code>).
     * <p>
     * The preprocessing rules encompass the following steps:
     * <ol>
     * <li>setting cell-specific variables in Bean Shell context (<code>col</code>, <code>row</code>);
     * <li>setting cell dependencies;
     * <li>replacing cell address references.
     * </ol>
     *
     * @see #isValueCached(int, int)
     * @see #setValueCached(boolean, int, int)
     * @see #replaceReferences(java.lang.String, java.util.Set)
	 * @see #bsh
     */
    public Object evalValueAt(int column, int row)
    {
        Object value = this.cache[column][row];
        if(this.isValueCached(column, row))
        {
            return value;
        }
        try
        {
			// marking value as cached before attempting to evaluate
			// the result prevents StackOverflowError in a cirular dependency
			this.setValueCached(true, column, row);

            value = this.getValueAt(row, column);
            if(value instanceof String && ((String)value).length() > 0 && ((String)value).charAt(0) == '=')
            {
                Set dependencies = null;
                Point point;
                try
                {
                    dependencies = this.dependencies.getDependencies(column, row, true);
                    if(dependencies != null)
                    {
                        Point[] depArray = (Point[])dependencies.toArray(new Point[dependencies.size()]);
                        for(int i = 0; i < depArray.length; ++i)
                        {
                            this.dependencies.removeDependency(depArray[i].x, depArray[i].y, column, row);
                        }
                    }
                    dependencies = (Set)this.setRecycler.getObject();
                    this.bsh.set("col", column);
                    this.bsh.set("row", row);
                    value = this.bsh.eval(this.replaceReferences(((String)value).substring(1), dependencies));
                }
                catch(EvalError ex)
                {
					ex.printStackTrace();
                    value = ex;
                }
                finally
                {
                    if(dependencies != null)
                    {
                        for(Iterator iterator = dependencies.iterator(); iterator.hasNext(); )
                        {
                            point = (Point)iterator.next();
                            this.dependencies.addDependency(point.x, point.y, column, row);
                        }
                        this.setRecycler.recycleObject(dependencies);
                    }
                }
            }
			return value;
        }
        finally
        {
            this.cache[column][row] = value;
        }
    }

	/**
	 * Replaces cell address references with Bean Shell-compatible statements.
	 * There are 3 kinds of cell references allowed:
	 * <table>
	 * <tr><th>Type</th><th>Syntax</th><th>Example</th><th>Equivalent</th></tr>
	 * <tr>
	 * 	<td>Explicit</td>
	 *	<td>[&lt;column name&gt;&lt;row index&gt;]</code></td>
	 *	<td><code>[B10]</td>
	 *	<td><code>model.evalValueAt(model.findColumn("B"), 10)</code></td>
	 * </tr>
	 * <tr>
	 *	<td>Relative</td>
	 *	<td>[&lt;column index delta&gt;&lt;row index delta&gt;]</td>
	 *	<td><code>[-1+2]</code></td>
	 *	<td><code>model.evalValueAt(col - 1, row + 2)</code></td>
	 * </tr>
	 * <tr>
	 *	<td>Range</td>
	 *	<td>[&lt;start column name&gt;&lt;start row index&gt;:&lt;end column name&gt;&lt;end row index&gt;]</td>
	 *	<td><code>[B2:C15]</code></td>
	 *	<td><code>range("[B2:C15]")</code></td>
	 * </tr>
	 * </table>
	 * Dependencies are not automatically created for range type references.  Typically the user of a range
	 * will do that in the course of iterating.
	 */
    public String replaceReferences(String evalString, Set dependencies)
    {
        StringBuffer sb = (StringBuffer)this.stringBufferRecycler.getObject();
        try
        {
            int length = evalString.length();
            Matcher matcher;
            int startIndex;
            int endIndex;
            int i;
            int column;
            String refString;
            for(i = 0; i < length; ++i)
            {
                startIndex = evalString.indexOf('[', i);
                if(startIndex < 0)
                {
                    break;
                }
                if(startIndex > 0 && evalString.charAt(startIndex - 1) == '\\')
                {
                    sb.append(evalString.substring(i, startIndex - 1)).append('[');
                    i = startIndex;
                    continue;
                }
                endIndex = evalString.indexOf(']', startIndex);
                if(endIndex < 0)
                {
                    break;
                }
                refString = evalString.substring(startIndex + 1, endIndex);
                if((matcher = this.cellRefPattern.matcher(refString)).matches())
                {
                    column = this.findColumn(matcher.group(1).toUpperCase());
                    dependencies.add(new Point(column, Integer.parseInt(matcher.group(2))));
                    sb.append(evalString.substring(i, startIndex))
                      .append("model.evalValueAt(")
                      .append(column)
                      .append(',')
                      .append(matcher.group(2))
                      .append(')');
                }
                else if((matcher = this.cellRangePattern.matcher(refString)).matches())
                {
					sb.append(evalString.substring(i, startIndex))
					  .append("range(\"")
					  .append(refString)
					  .append("\")");
				}
                else if((matcher = this.cellRelativeRefPattern.matcher(refString)).matches())
                {
					try
					{
						dependencies.add(new Point(
							((Integer)this.bsh.eval("col" + matcher.group(1))).intValue(),
							((Integer)this.bsh.eval("row" + matcher.group(2))).intValue()));
					}
					catch(EvalError ex)
					{
						ex.printStackTrace();
					}
					sb.append(evalString.substring(i, startIndex))
					  .append("model.evalValueAt(col")
					  .append(matcher.group(1))
					  .append(",row")
					  .append(matcher.group(2))
					  .append(')');
				}
                else
                {
					sb.append(evalString.substring(i, endIndex + 1));
                }
                i = endIndex;
            }
            if(i < length)
            {
                sb.append(evalString.substring(i));
            }
            return sb.toString();
        }
        finally
        {
            this.stringBufferRecycler.recycleObject(sb);
        }
    }

    public void editingCanceled(ChangeEvent ev)
    {
    }

    public void editingStopped(ChangeEvent ev)
    {
    }

	/**
	 * Calls recursive <code>followDependencies(Point, Set).
	 * @see #followDependencies(java.awt.Point, java.util.Set)
	 */
    public void followDependencies(int column, int row)
    {
		if(this.suppressDependencies)
		{
			return;
		}
		this.followDependencies(new Point(column, row), new HashSet());
    }

	/**
	 * Recursively runs according to cell's dependencies.
	 * @since 1.0.3
	 */
    protected void followDependencies(Point coord, Set processed)
    {
		if(processed.contains(coord))
		{
			return;
		}
		this.setValueCached(false, coord.x, coord.y);
		processed.add(coord);
        Set dependencies = this.dependencies.getDependencies(coord.x, coord.y, false);
        if(dependencies != null)
        {
            Point point;
            for(Iterator iterator = dependencies.iterator(); iterator.hasNext(); )
            {
                point = (Point)iterator.next();
                this.followDependencies(point, processed);
            }
        }
		this.fireTableCellUpdated(coord.y, coord.x);
	}

	/**
	 * Requests a dependency from one cell to another.
	 * This method exposes API, primarily meant for ability to establish dependencies
	 * without the use of the standard <code>[]</code> cell address notation.  This is especially
	 * useful when defining Bean Shell commands or processing logic within a spreadsheet,
	 * such as operations on ranges defined by their start and end points.
	 */
    public void requestDependency(int causeCol, int causeRow, int effectCol, int effectRow)
    {
		this.dependencies.addDependency(causeCol, causeRow, effectCol, effectRow);
	}

	/**
	 * Returns column identifiers.
	 */
    Vector getColumnIdentifiers()
    {
        return this.columnIdentifiers;
    }

    /**
     * Recreates internal value cache and dependencies objects (<code>cache</code> and <code>dependencies</code>).
     * This is useful when the model structure (dimensions) changes.
     *
     * @see #cache
     * @see #dependencies
     */
    protected void refreshCacheAndDependencies()
    {
		this.cache = new Object[columnIdentifiers.size()][dataVector.size()];
		this.valuesCached = new BitSet[this.columnIdentifiers.size()];
		this.dependencies = new Dependencies(columnIdentifiers.size(), dataVector.size(), true);
		this.refreshLastNonEmpty();
	}

	/**
	 * @since 0.9.2
	 */
	protected synchronized void refreshLastNonEmpty()
	{
		int columnCount = this.getColumnCount();
		int rowCount = this.getRowCount();
		boolean go;

		go = true;;
		for(this.lastNonEmptyRow = rowCount - 1; go && this.lastNonEmptyRow > -1; --this.lastNonEmptyRow)
		{
			for(int column = 1; go && column < columnCount; ++column)
			{
				go = (this.getValueAt(this.lastNonEmptyRow, column) == null);
			}
		}
		++this.lastNonEmptyRow;

		go = true;
		for(this.lastNonEmptyColumn = columnCount - 1; go && this.lastNonEmptyColumn > 0; --this.lastNonEmptyColumn)
		{
			for(int row = 0; go && row < rowCount; ++row)
			{
				go = (this.getValueAt(row, this.lastNonEmptyColumn) == null);
			}
		}
		++this.lastNonEmptyColumn;
	}

	/**
	 * @since 0.9.2
	 */
	public int getLastNonEmptyRow()
	{
		return this.lastNonEmptyRow;
	}

	/**
	 * @since 0.9.2
	 */
	public int getLastNonEmptyColumn()
	{
		return this.lastNonEmptyColumn;
	}

	public void fireTableStructureChanged()
	{
		this.refreshCacheAndDependencies();
		super.fireTableStructureChanged();
	}

    public void fireTableChanged(TableModelEvent ev)
    {
		switch(ev.getType())
		{
			case TableModelEvent.INSERT:
			case TableModelEvent.DELETE:
			this.refreshCacheAndDependencies();
			break;
		}
		super.fireTableChanged(ev);
	}

	/**
	 * Returns internal format map.
	 *
	 * @see #columnFormats
	 * @see #setFormats(java.util.Map)
	 */
    public Map getFormats()
    {
        return this.columnFormats;
    }

	/**
	 * Sets internal format map.
	 *
	 * @see #columnFormats
	 * @see #getFormats()
	 */
    public void setFormats(Map formats)
    {
        this.columnFormats = formats;
    }

    public Map getAppearanceMap()
    {
		return this.appearanceMap;
	}

	public void setAppearanceMap(Map appearanceMap)
	{
		this.appearanceMap = appearanceMap;
	}

	/**
	 * Sorts rows of data in the model in the specified columns spanning <code>nRows</code> rows
	 * starting with <code>startRow</code>.
	 * Custom comparators can be provided in an array.  Provided comparators are matched
	 * against their corresponding columns using array indexes.  If comparator array is shorter
	 * than the column index array, the rest of the columns are sorted in their natural order.
	 * Each element in the comparator array represents a comparator used for that particular column's
	 * sorting.  A <code>null</code> comparator represents natural data order.  It may be assumed that
	 * no <code>null</code> column values will be passed to the custom comparators for comparison.
	 * <code>null</code> values are handled entirely by <code>BSHTableModelComparable</code>.
	 *
	 * @param startRow
	 * starting row
	 *
	 * @param nRows
	 * number of rows
	 *
	 * @param cols
	 * column indexes
	 *
	 * @param comps
	 * custom comparators.  Must not be <code>null</code>.
	 *
	 * @see zinger.bsheet.BSHTableModelComparable
	 */
	public void sort(int startRow, int nRows, int[] cols, Comparator[] comps)
	{
		this.suppressDependencies = true;
		try
		{
			BSHTableModelComparable[] data = new BSHTableModelComparable[nRows];
			for(int r = 0; r < data.length; ++r)
			{
				data[r] = new BSHTableModelComparable(this, r + startRow, cols, comps);
			}

			Arrays.sort(data);

			for(int r = 0; r < data.length; ++r)
			{
				for(int c = 0; c < cols.length; ++c)
				{
					this.setValueAt(data[r].getDataValue(c), startRow + r, cols[c]);
				}
			}
		}
		finally
		{
			this.suppressDependencies = false;
			for(int r = 0; r < nRows; ++r)
			{
				for(int c = 0; c < cols.length; ++c)
				{
					this.followDependencies(cols[c], startRow + r);
				}
			}
		}
	}
}
