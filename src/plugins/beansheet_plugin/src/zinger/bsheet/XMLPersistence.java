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

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class XMLPersistence extends StreamMapPersistence
{
	public static final XMLPersistence INSTANCE = new XMLPersistence();

	protected XMLPersistence()
	{
		this(new FilenameFileFilter(new Pattern[]
			{
				Pattern.compile("^.*\\.xml$", Pattern.CASE_INSENSITIVE),
				Pattern.compile("^.*\\.bsx$", Pattern.CASE_INSENSITIVE)
			}, "Bean Sheet XML (*.bsx, *.xml)"),
			new String[]
			{
				"bsx",
				"xml"
			}
		);
	}

	protected XMLPersistence(javax.swing.filechooser.FileFilter fileFilter, String[] extensions)
	{
		super(fileFilter, extensions);
	}

	protected Map load(InputStream is) throws IOException
	{
		InputStream bis = new BufferedInputStream(is);
		XMLDecoder xmlDec = new XMLDecoder(bis);
		try
		{
			return (Map)xmlDec.readObject();
		}
        catch(Throwable ex)
        {
			ex.printStackTrace();
			throw new IOException("Could not load document.");
        }
        finally
        {
            xmlDec.close();
            bis.close();
        }
	}

	protected void save(Map map, OutputStream os) throws IOException
	{
        OutputStream bos = new BufferedOutputStream(os);
        XMLEncoder xmlEnc = new XMLEncoder(bos);
        try
        {
            xmlEnc.writeObject(map);
        }
        finally
        {
            xmlEnc.close();
            bos.close();
        }
	}
}
