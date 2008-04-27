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

package zinger.bsheet.module;

import bsh.*;

import java.io.*;
import java.util.*;

import javax.swing.*;

import zinger.bsheet.*;

public class ModuleContext
{
	protected final JTable table;
	protected final JMenuBar menuBar;
	protected final PersistenceManager impexp;
	protected final TableModifier tableModifier;
	protected final CellFormatEditor formatEditor;
	protected final Interpreter bsh;

	public ModuleContext(JTable table,
	                     JMenuBar menuBar,
	                     PersistenceManager impexp,
	                     TableModifier tableModifier,
	                     CellFormatEditor formatEditor,
	                     Interpreter bsh)
	{
		this.table = table;
		this.menuBar = menuBar;
		this.impexp = impexp;
		this.tableModifier = tableModifier;
		this.formatEditor = formatEditor;
		this.bsh = bsh;

		try
		{
			this.bsh.set("context", this);
		}
		catch(EvalError ex)
		{
			ex.printStackTrace();
		}
	}

	public JTable getTable()
	{
		return this.table;
	}

	public JMenuBar getMenuBar()
	{
		return this.menuBar;
	}

	public PersistenceManager getImpexp()
	{
		return this.impexp;
	}

	public TableModifier getTableModifier()
	{
		return this.tableModifier;
	}

	public CellFormatEditor getFormatEditor()
	{
		return this.formatEditor;
	}

	/**
	 * @since 0.9.3
	 */
	public Interpreter getBSH()
	{
		return this.bsh;
	}

	public void init()
	{
		for(Iterator iterator = ModuleFactory.INSTANCE.getModules().iterator(); iterator.hasNext(); )
		{
			((Module)iterator.next()).addContext(this);
		}
	}
}
