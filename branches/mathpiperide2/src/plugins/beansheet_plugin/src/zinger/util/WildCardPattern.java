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

import java.util.*;

public class WildCardPattern
{
    private final List patternElements;
    private Boolean rigidStart;
    private Boolean rigidEnd;

    public WildCardPattern(final List patternElements, final boolean rigidStart, final boolean rigidEnd)
    {
        this.patternElements = patternElements;
        this.rigidStart = rigidStart ? Boolean.TRUE : Boolean.FALSE;
        this.rigidEnd = rigidEnd ? Boolean.TRUE : Boolean.FALSE;
    }

    public WildCardPattern()
    {
        this(new LinkedList(), false, false);
    }

    public WildCardPattern(final String pattern, final String wildCard)
    {
        this();
        this.setPattern(pattern, wildCard);
    }

    public void addPatternElement(final String patternElement)
    {
        if(this.rigidStart == null)
        {
            this.rigidStart = Boolean.TRUE;
        }
        this.patternElements.add(patternElement);
        this.rigidEnd = Boolean.TRUE;
    }

    public void addWildCard()
    {
        if(this.rigidStart == null)
        {
            this.rigidStart = Boolean.FALSE;
        }
        this.rigidEnd = Boolean.FALSE;
    }

    public void setPattern(final String pattern, final String wildCard)
    {
        this.patternElements.clear();
        this.rigidStart = null;
        this.rigidEnd = null;

        String token;
        for(final StringTokenizer tokenizer = new StringTokenizer(pattern, wildCard, true); tokenizer.hasMoreElements();)
        {
            token = tokenizer.nextToken();
            if(token.length() == 1 && wildCard.indexOf(token.charAt(0)) >= 0)
            {
                this.addWildCard();
            }
            else
            {
                this.addPatternElement(token);
            }
        }
    }

    public boolean match(final String string)
    {
        int lastIndex = 0;
        int currentIndex;
        String patternElement;
        for(final Iterator iterator = this.patternElements.iterator(); iterator.hasNext();)
        {
            patternElement = (String)iterator.next();
            currentIndex = string.indexOf(patternElement, lastIndex);
            if(currentIndex < lastIndex)
            {
                return false;
            }
            lastIndex = currentIndex + patternElement.length();
        }
        if(this.rigidStart.booleanValue() && !string.startsWith((String)patternElements.get(0)))
        {
            return false;
        }
        if(this.rigidEnd.booleanValue() && !string.endsWith((String)patternElements.get(patternElements.size() - 1)))
        {
            return false;
        }
        return true;
    }
}
