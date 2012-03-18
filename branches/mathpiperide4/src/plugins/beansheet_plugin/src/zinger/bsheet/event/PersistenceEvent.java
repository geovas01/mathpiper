// Bean Sheet
// Copyright (C) 2005 Alexey Zinger
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

package zinger.bsheet.event;

import java.io.*;
import java.util.*;

import zinger.bsheet.*;

public class PersistenceEvent extends EventObject
{
	public static final int DOCUMENT_LOADING = 0;
	public static final int DOCUMENT_LOADED = 1;
	public static final int DOCUMENT_SAVING = 2;
	public static final int DOCUMENT_SAVED = 3;

	protected final int type;
	protected final Persistence persistence;
	protected final File dataFile;

	public PersistenceEvent(PersistenceManager source, int type, Persistence persistence, File dataFile)
	{
		super(source);
		this.type = type;
		this.persistence = persistence;
		this.dataFile = dataFile;
	}

	public PersistenceEvent(PersistenceManager source, int type)
	{
		this(source, type, null, null);
	}

	public PersistenceManager getPersistenceManager()
	{
		return (PersistenceManager)this.getSource();
	}

	public int getType()
	{
		return this.type;
	}

	public Persistence getPersistence()
	{
		return this.persistence;
	}

	public File getDataFile()
	{
		return this.dataFile;
	}
}
