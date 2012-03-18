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

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public abstract class StandardMapPersistence extends AbstractPersistence
{
	public static final String VERSION_KEY = "version";
	public static final String COLUMN_IDENTIFIERS_KEY = "column_ids";
	public static final String DATA_VECTOR_KEY = "data_vector";
	public static final String DATA_VECTOR_LENGTH_KEY = "data_vector_length";
	public static final String COLUMNS_KEY = "columns";
	public static final String FORMATS_KEY = "formats";
	public static final String ROW_HEIGHTS_KEY = "row_heights";
	public static final String APPEARANCE_MAP_KEY = "appearance_map";

	public StandardMapPersistence(javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		super(fileFilter, extensions);
	}

	public StandardMapPersistence()
	{
		this(null, null);
	}

	public boolean isLoadCapable()
	{
		return true;
	}

	public boolean save(JTable table, File file) throws IOException
	{
		this.save(this.save(table), file);
		return true;
	}

	public boolean load(JTable table, File file) throws IOException, UnsupportedOperationException
	{
		this.load(this.load(file), table);
		return true;
	}

	protected Map save(JTable table)
	{
		BSHTableModel model = (BSHTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();

		Map objectMap = new HashMap();
		objectMap.put(StandardMapPersistence.COLUMN_IDENTIFIERS_KEY, model.getColumnIdentifiers());
		Vector dataVector = model.getDataVector();
		objectMap.put(StandardMapPersistence.DATA_VECTOR_LENGTH_KEY, new Integer(dataVector.size()));
		dataVector = this.getCompressedDataVector(dataVector);
		objectMap.put(StandardMapPersistence.DATA_VECTOR_KEY, dataVector);
		objectMap.put(StandardMapPersistence.COLUMNS_KEY, this.getColumns(columnModel));
		objectMap.put(StandardMapPersistence.FORMATS_KEY, model.getFormats());

		Map rowHeightsMap = new HashMap();
		int defaultHeight = table.getRowHeight();
		int rowHeight;
		for(int i = table.getRowCount() - 1; i > -1; --i)
		{
			rowHeight = table.getRowHeight(i);
			if(rowHeight != defaultHeight)
			{
				rowHeightsMap.put(new Integer(i), new Integer(rowHeight));
			}
		}
		objectMap.put(StandardMapPersistence.ROW_HEIGHTS_KEY, rowHeightsMap);

		objectMap.put(StandardMapPersistence.APPEARANCE_MAP_KEY, model.getAppearanceMap());

		return objectMap;
	}

	/**
	 * Loads table model data from the specified object map.
	 * Data model is loaded from the map using the following keys:
	 * <table>
	 * <tr><th>Constant Variable</th><th>Constant Value (* = required)</th><th>Description</th></tr>
	 *
	 * <tr bgcolor="#eeeeee"><td><code>COLUMN_IDENTIFIERS_KEY</code></td>
	 * <td><code>column_ids</code> *</td>
	 * <td>A <code>Vector</code> containing column identifiers.</td></tr>
	 *
	 * <tr><td><code>DATA_VECTOR_KEY</td>
	 * <td><code>data_vector</code> *</td>
	 * <td>A compressed two-dimensional data vector.
	 * A data vector is compressed by removing all trailing empty row elements
	 * and trailing <code>null</code> from each ramaining row vector.</td></tr>
	 *
	 * <tr bgcolor="#eeeeee"><td><code>DATA_VECTOR_LENGTH_KEY</code></td>
	 * <td><code>data_vector_length</code></td>
	 * <td>An <code>Integer</code> containing the row count (the length of the data vector).</td></tr>
	 *
	 * <tr><td><code>COLUMNS_KEY</code></td>
	 * <td><code>columns</code></td>
	 * <td>A <code>List</code> of <code>Column</code> objects.</td></tr>
	 *
	 * <tr bgcolor="#eeeeee"><td><code>FORMATS_KEY</code></td>
	 * <td><code>formats</code></td>
	 * <td>A formats <code>Map</code> used in <code>BSHTableModel</code> for column and cell formatting.</td></tr>
	 *
	 * <tr><td><code>ROW_HEIGHTS_KEY</code></td>
	 * <td><code>row_heights</code></td>
	 * <td>A <code>Map</code> mapping row indices to row heights for non-default row height values.</td></tr>
	 *
	 * <tr bgcolor="#eeeeee"><td><code>APPEARANCE_MAP_KEY</code></td>
	 * <td><code>appearance_map</code></td>
	 * <td>A cell appearance <code>Map</code> used for column and cell presentation setting.</td></tr>
	 * </table>
	 *
	 * @see #COLUMN_IDENTIFIERS_KEY
	 * @see #DATA_VECTOR_KEY
	 * @see #DATA_VECTOR_LENGTH_KEY
	 * @see #COLUMNS_KEY
	 * @see #FORMATS_KEY
	 * @see #ROW_HEIGHTS_KEY
	 * @see #APPEARANCE_MAP_KEY
	 * @see javax.swing.table.DefaultTableModel#setDataVector(java.util.Vector, java.util.Vector)
	 * @see #setColumns(java.util.List, javax.swing.table.TableColumnModel)
	 * @see zinger.bsheet.BSHTableModel#setFormats(java.util.Map)
	 * @see zinger.bsheet.BSHTableModel#setAppearanceMap(java.util.Map)
	 * @see #decompressDataVector(java.util.Vector, int)
	 */
	protected void load(Map map, JTable table)
	{
		BSHTableModel model = (BSHTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();

		Vector columnIdentifiers = (Vector)map.get(StandardMapPersistence.COLUMN_IDENTIFIERS_KEY);
		Vector dataVector = (Vector)map.get(StandardMapPersistence.DATA_VECTOR_KEY);
		Integer dataVectorLength = (Integer)map.get(StandardMapPersistence.DATA_VECTOR_LENGTH_KEY);
		if(dataVectorLength != null)
		{
			this.decompressDataVector(dataVector, dataVectorLength.intValue());
		}
		model.setDataVector(dataVector, columnIdentifiers);

		java.util.List columns = (java.util.List)map.get(StandardMapPersistence.COLUMNS_KEY);
		if(columns != null)
		{
			this.setColumns(columns, columnModel);
		}

		Map formats = (Map)map.get(StandardMapPersistence.FORMATS_KEY);
		if(formats == null)
		{
			formats = new HashMap();
		}
		model.setFormats(formats);

		Map rowHeightsMap = (Map)map.get(StandardMapPersistence.ROW_HEIGHTS_KEY);
		Integer rowHeight;
		int defaultHeight = table.getRowHeight();
		for(int i = table.getRowCount() - 1; i > -1; --i)
		{
			rowHeight = rowHeightsMap == null ? null : (Integer)rowHeightsMap.get(new Integer(i));
			table.setRowHeight(i, rowHeight == null ? defaultHeight : rowHeight.intValue());
		}

		Map appearanceMap = (Map)map.get(StandardMapPersistence.APPEARANCE_MAP_KEY);
		if(appearanceMap == null)
		{
			appearanceMap = new HashMap();
		}
		model.setAppearanceMap(appearanceMap);
	}

	/**
	 * Decompresses given two-dimensional data vector.
	 * A data vector is decompressed by setting size of the vector to specified row count
	 * (<code>originalLength</code>).  <strong>Note:</strong> This method is not a true inverse
	 * function of <code>getCompressedDataVector(Vector)</code> because trailing empty rows
	 * are represented by <code>null</code> elements rather than empty <code>Vector</code> objects
	 * and trailing <code>null</code> elements of the rows are not restored.  Also note that this method
	 * operates directly on the passed in vector and mutates it.
	 *
	 * @see #load(java.util.Map, javax.swing.JTable)
	 * @see #getCompressedDataVector(java.util.Vector)
	 */
	protected void decompressDataVector(Vector dataVector, int originalLength)
	{
		if(dataVector.size() < originalLength)
		{
			dataVector.setSize(originalLength);
		}
	}

	/**
	 * Creates a compressed version of the given two-dimensional data vector.
	 * A data vector is compressed by removing all trailing empty row elements and
	 * trailing <code>null</code> elements in remaining rows.  This method does not
	 * mutate passed in <code>Vector</code>.
	 *
	 * @see #save(javax.swing.JTable)
	 * @see #decompressDataVector(java.util.Vector, int)
	 */
	protected Vector getCompressedDataVector(Vector dataVector)
	{
		Vector compressedDataVector = new Vector();
		boolean trailingTrim = true;
		Vector rowVector;
		int j;
		int originalSize = dataVector.size();
		for(int i = 0; i < originalSize; ++i)
		{
			rowVector = (Vector)dataVector.get(i);
			if(rowVector != null)
			{
				rowVector = new Vector(rowVector);
				for(j = rowVector.size() - 1; j >= 0 && rowVector.get(j) == null; --j)
				{
				}
				rowVector.setSize(j + 1);
				if(rowVector.size() > 1)
				{
					compressedDataVector.setSize(i + 1);
					compressedDataVector.set(i, rowVector);
				}
			}
		}
		return compressedDataVector;
	}

	/**
	 * Creates a <code>List</code> of <code>Column</code> objects from a table model's column enumerator.
	 *
	 * @see #save(javax.swing.JTable)
	 * @see #setColumns(java.util.List, javax.swing.table.TableColumnModel)
	 */
	protected java.util.List getColumns(TableColumnModel columnModel)
	{
		java.util.List columns = new LinkedList();
		for(Enumeration en = columnModel.getColumns(); en.hasMoreElements(); )
		{
			columns.add(en.nextElement());
		}
		return columns;
	}

	/**
	 * Sets the columns in the table model based on the <code>List</code> containing <code>Column</code> objects.
	 *
	 * @see #load(java.util.Map, javax.swing.JTable)
	 * @see #getColumns(javax.swing.table.TableColumnModel)
	 */
	protected void setColumns(java.util.List columns, TableColumnModel columnModel)
	{
		for(Iterator iterator = this.getColumns(columnModel).iterator(); iterator.hasNext(); )
		{
			columnModel.removeColumn((TableColumn)iterator.next());
		}
		for(Iterator iterator = columns.iterator(); iterator.hasNext(); )
		{
			columnModel.addColumn((TableColumn)iterator.next());
		}
	}

	protected abstract Map load(File file) throws IOException;
	protected abstract void save(Map map, File file) throws IOException;

	public boolean evalValues(JTable table, Map map)
	{
		TableModel m = table.getModel();
		if(!(m instanceof BSHTableModel))
		{
			return false;
		}
		BSHTableModel model = (BSHTableModel)m;
		int rowIndex = 0;
		Vector row;
		int rowSize;
		for(Iterator rowIterator = ((Vector)map.get(StandardMapPersistence.DATA_VECTOR_KEY)).iterator(); rowIterator.hasNext(); ++rowIndex)
		{
			row = (Vector)rowIterator.next();
			if(row == null)
			{
				continue;
			}
			rowSize = row.size();
			for(int colIndex = 0; colIndex < rowSize; ++colIndex)
			{
				row.set(colIndex, model.formatValueAt(model.evalValueAt(colIndex, rowIndex), colIndex, rowIndex));
			}
		}
		return true;
	}
}
