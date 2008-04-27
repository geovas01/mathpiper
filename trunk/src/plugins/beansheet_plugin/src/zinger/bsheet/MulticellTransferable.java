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

package zinger.bsheet;

import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;

public class MulticellTransferable implements Transferable
{
	protected static final DataFlavor[] SELECTION_DATA_FLAVORS =
    new DataFlavor[]
    {
		new DataFlavor(Map.class, "Enhanced 2D Java object array."),
        new DataFlavor(Object[][].class, "2D Java object array."),
        DataFlavor.stringFlavor,
        DataFlavor.plainTextFlavor // deprecated
    };

	/**
	 * @since Bean Sheet 0.9.1
	 */
	public static final int ENHANCED_JAVA_2D_ARRAY_FLAVOR_INDEX = 0;
	public static final int JAVA_2D_ARRAY_FLAVOR_INDEX = 1;
    public static final int STRING_FLAVOR_INDEX = 2;
    public static final int PLAIN_TEXT_FLAVOR_INDEX = 3;

    public static final String ENHANCED_SELECTION_KEY = "selection";
    public static final String ENHANCED_APPEARANCES_KEY = "appearances";

    protected final Object[][] selection;
    private String selectionAsString;

	/**
	 * @since Bean Sheet 0.9.1
	 */
    protected final Appearance[][] appearances;

    public MulticellTransferable(Object[][] selection)
    {
		this(selection, null);
	}

	/**
	 * @since Bean Sheet 0.9.1
	 */
    public MulticellTransferable(Object[][] selection, Appearance[][] appearances)
    {
		this.selection = selection;
        this.appearances = appearances;
    }

    public DataFlavor[] getTransferDataFlavors()
    {
		return MulticellTransferable.getSupportedFlavors();
	}

    public static DataFlavor[] getSupportedFlavors()
    {
        DataFlavor[] flavors = new DataFlavor[MulticellTransferable.SELECTION_DATA_FLAVORS.length];
        System.arraycopy(MulticellTransferable.SELECTION_DATA_FLAVORS, 0, flavors, 0, flavors.length);
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        for(int i = 0; i < MulticellTransferable.SELECTION_DATA_FLAVORS.length; ++i)
        {
            if(MulticellTransferable.SELECTION_DATA_FLAVORS[i].equals(flavor))
            {
                return true;
            }
        }
        return false;
    }

    public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
    {
		if(MulticellTransferable.SELECTION_DATA_FLAVORS[MulticellTransferable.ENHANCED_JAVA_2D_ARRAY_FLAVOR_INDEX].equals(flavor))
		{
			return this.getEnhancedSelection();
		}
		if(MulticellTransferable.SELECTION_DATA_FLAVORS[MulticellTransferable.JAVA_2D_ARRAY_FLAVOR_INDEX].equals(flavor))
        {
            return this.selection;
        }
        if(MulticellTransferable.SELECTION_DATA_FLAVORS[MulticellTransferable.STRING_FLAVOR_INDEX].equals(flavor))
        {
            return this.getSelectionAsString();
        }
        if(MulticellTransferable.SELECTION_DATA_FLAVORS[MulticellTransferable.PLAIN_TEXT_FLAVOR_INDEX].equals(flavor))
        {
            return this.getSelectionAsInputStream();
        }
        throw new UnsupportedFlavorException(flavor);
    }

	/**
	 * @since Bean Sheet 0.9.1
	 */
    public Object getEnhancedSelection()
    {
		Map enhancedSelection = new HashMap(2);
		enhancedSelection.put(MulticellTransferable.ENHANCED_SELECTION_KEY, this.selection);
		if(this.appearances != null)
		{
			enhancedSelection.put(MulticellTransferable.ENHANCED_APPEARANCES_KEY, this.appearances);
		}
		return enhancedSelection;
	}

    public String getSelectionAsString()
    {
        if(this.selectionAsString == null)
        {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < this.selection.length; ++i)
            {
                if(i > 0)
                {
                    sb.append('\n');
                }
                for(int j = 0; j < this.selection[0].length; ++j)
                {
                    if(j > 0)
                    {
                        sb.append('\t');
                    }
                    if(selection[i][j] != null)
                    {
                    	sb.append(String.valueOf(selection[i][j]));
					}
                }
            }
            this.selectionAsString = sb.toString();
        }
        return this.selectionAsString;
    }

    public InputStream getSelectionAsInputStream()
    {
        return new ByteArrayInputStream(this.getSelectionAsString().getBytes());
    }

    protected static String[][] parse2DArray(String[] rows)
    {
        String[][] result = new String[rows.length][];
        for(int i = 0; i < result.length; ++i)
        {
            result[i] = rows[i].split("\t");
        }
        return result;
	}

    public static String[][] parse2DArrayString(String s)
    {
        String[] rows = s.split("\n");
        return MulticellTransferable.parse2DArray(rows);
    }

    public static String[][] parseReader(Reader r) throws IOException
    {
		List rows = new ArrayList();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(r);
			for(String line = br.readLine(); line != null; line = br.readLine())
			{
				rows.add(line);
			}
		}
		finally
		{
			try
			{
				br.close();
				r.close();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return MulticellTransferable.parse2DArray((String[])rows.toArray(new String[rows.size()]));
	}
}
