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

package zinger.bsheet.module;

import java.awt.*;
import java.util.*;

public class ComponentContextRegistry
{
	protected final Map componentContextMap;

	public ComponentContextRegistry(Map componentContextMap)
	{
		this.componentContextMap = componentContextMap;
	}

	public ComponentContextRegistry()
	{
		this(new HashMap());
	}

	public synchronized void registerComponent(Object component, ModuleContext context)
	{
		this.componentContextMap.put(component, context);
		Collection reverseRegistry = (Collection)this.componentContextMap.get(context);
		if(reverseRegistry == null)
		{
			reverseRegistry = new HashSet();
			this.componentContextMap.put(context, reverseRegistry);
		}
		reverseRegistry.add(component);
	}

	public synchronized void unregisterComponent(Object component)
	{
		ModuleContext context = (ModuleContext)this.componentContextMap.remove(component);
		if(context != null)
		{
			Collection reverseRegistry = (Collection)this.componentContextMap.get(context);
			if(reverseRegistry != null)
			{
				reverseRegistry.remove(component);
				if(reverseRegistry.isEmpty())
				{
					this.componentContextMap.remove(context);
				}
			}
		}
	}

	public synchronized void unregisterContext(ModuleContext context)
	{
		Collection reverseRegistry = (Collection)this.componentContextMap.remove(context);
		if(reverseRegistry != null)
		{
			for(Iterator iterator = reverseRegistry.iterator(); iterator.hasNext(); )
			{
				this.unregisterComponent(iterator.next());
			}
		}
	}

	public ModuleContext findContext(Component contextKey)
	{
		if(contextKey == null)
		{
			return null;
		}
		ModuleContext context = (ModuleContext)this.componentContextMap.get(contextKey);
		if(context == null)
		{
			context = this.findContext(contextKey.getParent());
		}
		return context;
	}
}
