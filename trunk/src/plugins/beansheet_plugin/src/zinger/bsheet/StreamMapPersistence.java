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

import java.io.*;
import java.util.*;

public abstract class StreamMapPersistence extends StandardMapPersistence
{
	public StreamMapPersistence()
	{
	}

	public StreamMapPersistence(javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		super(fileFilter, extensions);
	}

	protected Map load(File file) throws IOException, UnsupportedOperationException
	{
		InputStream is = new FileInputStream(file);
		try
		{
			return this.load(is);
		}
		finally
		{
			is.close();
		}
	}

	protected abstract Map load(InputStream is) throws IOException, UnsupportedOperationException;

	protected void save(Map map, File file) throws IOException
	{
		OutputStream os = new FileOutputStream(file);
		try
		{
			this.save(map, os);
		}
		finally
		{
			os.close();
		}
	}

	protected abstract void save(Map map, OutputStream os) throws IOException;
}
