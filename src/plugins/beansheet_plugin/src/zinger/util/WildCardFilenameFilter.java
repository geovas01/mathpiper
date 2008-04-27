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

package zinger.util;

import java.io.*;
import java.util.*;

public class WildCardFilenameFilter implements FilenameFilter
{
    public static final String WILD_CARD = "*";

    protected final WildCardPattern pattern = new WildCardPattern();

    public WildCardFilenameFilter()
    {
    }

    public WildCardFilenameFilter(final String pattern)
    {
        this();
        this.setPattern(pattern);
    }

    public void setPattern(final String pattern)
    {
        this.pattern.setPattern(pattern, WildCardFilenameFilter.WILD_CARD);
    }

    public boolean accept(final File dir, final String name)
    {
        return pattern.match(name);
    }
}
