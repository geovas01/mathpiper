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
import java.util.regex.*;

import javax.swing.filechooser.*;

public class FilenameFileFilter extends javax.swing.filechooser.FileFilter
{
	protected final String description;

	protected final Pattern[] filenamePatterns;

	public FilenameFileFilter(Pattern[] filenamePatterns, String description)
	{
		this.filenamePatterns = filenamePatterns;
		this.description = description;
	}

	public FilenameFileFilter(Pattern filenamePattern, String description)
	{
		this(new Pattern[] {filenamePattern}, description);
	}

	public boolean accept(File file)
	{
		boolean result = file.isDirectory();
		for(int i = 0; !result && i < this.filenamePatterns.length; ++i)
		{
			result = this.filenamePatterns[i].matcher(file.getName()).matches();
		}
		return result;
	}

	public String getDescription()
	{
		return this.description;
	}
}
