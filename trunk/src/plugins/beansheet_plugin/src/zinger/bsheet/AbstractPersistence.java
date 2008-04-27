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

/**
 * Base class for <code>Persistence</code> implementations.  Provides basic <code>autocorrectFile(File)</code>
 * implementation based on supported file extensions.
 *
 * @see #autocorrectFile(java.io.File)
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public abstract class AbstractPersistence implements Persistence
{
	protected final javax.swing.filechooser.FileFilter fileFilter;
	protected final String[] extensions;

	public AbstractPersistence(javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		this.fileFilter = fileFilter;
		this.extensions = extensions;
	}

	public javax.swing.filechooser.FileFilter getFileFilter()
	{
		return this.fileFilter;
	}

	public File autocorrectFile(File file)
	{
		if(this.fileFilter != null && this.extensions != null)
		{
			String fileName = file.getName();
			for(int i = 0; i < this.extensions.length && !this.fileFilter.accept(file); ++i)
			{
				file = new File(file.getParentFile(), fileName + '.' + this.extensions[i]);
			}
		}
		return file;
	}
}
