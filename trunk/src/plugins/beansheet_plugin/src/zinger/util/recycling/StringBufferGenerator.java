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

package zinger.util.recycling;

public class StringBufferGenerator implements ObjectGenerator
{
    protected final int length;

    public StringBufferGenerator(final int length)
    {
        this.length = length;
    }

    public StringBufferGenerator()
    {
        this(-1);
    }

    public Object newObject()
    {
        if(this.length >= 0)
        {
            return new StringBuffer(this.length);
        }
        else
        {
            return new StringBuffer();
        }
    }

    public boolean prepareObject(final Object obj, final Object arg) throws IllegalArgumentException
    {
        final StringBuffer sb;
        try
        {
            sb = (StringBuffer)obj;
        }
        catch(final ClassCastException ex)
        {
            return false;
        }
        sb.setLength(0);
        if(arg != null)
        {
            sb.append(arg);
        }
        return true;
    }
}
