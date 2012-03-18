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
import java.util.regex.*;
import java.util.zip.*;

public class CompressedXMLPersistence extends XMLPersistence
{
	public static final CompressedXMLPersistence INSTANCE = new CompressedXMLPersistence();

	protected final String contentEntryName;

	protected CompressedXMLPersistence()
	{
		this("content.bsx",
			new FilenameFileFilter(new Pattern[]
			{
				Pattern.compile("^.*\\.bsz$", Pattern.CASE_INSENSITIVE)
			}, "Compressed Bean Sheet XML (*.bsz)"),
			new String[]
			{
				"bsz"
			}
		);
	}

	protected CompressedXMLPersistence(String contentEntryName, javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		super(fileFilter, extensions);
		this.contentEntryName = contentEntryName;
	}

	protected Map load(InputStream is) throws IOException
	{
		ZipInputStream zis = new ZipInputStream(is);
		try
		{
			while(zis.available() > 0 && !zis.getNextEntry().getName().equals(this.contentEntryName));
			return super.load(zis);
		}
		finally
		{
			is.close();
		}
	}

	protected void save(Map map, OutputStream os) throws IOException
	{
		ZipOutputStream zos = new ZipOutputStream(os);
		try
		{
			zos.putNextEntry(new ZipEntry(this.contentEntryName));
			super.save(map, zos);
		}
		finally
		{
			os.close();
		}
	}
}
