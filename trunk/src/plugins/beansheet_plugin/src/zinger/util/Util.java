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

package zinger.util;

import zinger.util.recycling.*;

import java.io.*;
import java.util.*;

/**
 * A general utility class.
 */
public final class Util
{
    /**
     * Used for property reference evaluation.
     * Set to "${".
     *
     * @see #PROPERTY_REFERENCE_POSTFIX
     */
    public static final String PROPERTY_REFERENCE_PREFIX = "${";

	/**
	 * A constant that enables code dealing with parsing property references
	 * not to compute the length of the reference prefix every time.
	 *
	 * @see #PROPERTY_REFERENCE_PREFIX
	 */
    protected static final int PROPERTY_REFERENCE_PREFIX_LENGTH = Util.PROPERTY_REFERENCE_PREFIX.length();

    /**
     * Used for property reference evaluation.
     * Set to "}".
     *
     * @see #PROPERTY_REFERENCE_PREFIX
     */
    public static final String PROPERTY_REFERENCE_POSTFIX = "}";

	/**
	 * A constant that enables code dealing with parsing property references
	 * not to compute the length of the reference postfix every time.
	 *
	 * @see #PROPERTY_REFERENCE_POSTFIX
	 */
    protected static final int PROPERTY_REFERENCE_POSTFIX_LENGTH = Util.PROPERTY_REFERENCE_POSTFIX.length();

    public static final String IMPORT_PROPERTY = "import";

    public static final String IMPORT_DELIMITER = ",";

    private static final ObjectRecycler BYTE_BUFFER_RECYCLER = new ObjectRecycler(new ArrayGenerator(byte.class, 1 << 10));

    /**
     * This class is not meant to be instantiated.
     */
    private Util()
    {
    }

    /**
     * Evaluates a property in a <code>Properties</code> object.
     * Property values can include references to other properties.  In such cases, those references
     * are evaluated.  References are indicated by enclosing the name of the referred-to property
     * in "${" and "}".
     *
     * @see #PROPERTY_REFERENCE_PREFIX
     * @see #PROPERTY_REFERENCE_POSTFIX
     * @see java.util.Properties
     *
     * @param propertyName
     * the name of the property to be evaluated
     *
     * @param in
     * the <code>Properties</code> object to evaluate the property in question and all its references
     *
     * @param out
     * if not <code>null</code>, the result of the evaluation is written into this <code>Properties</code>
     * object.  It is okay to pass the same <code>Properties</code> object as <code>in</code> and
     * <code>out</code>.  In that case, the property will be written over with the resulting value.
     */
    public static String evalProperty(final String propertyName, final Properties in, final Properties out)
    {
        final String value = in.getProperty(propertyName);
        final StringBuffer buffer = new StringBuffer();
        final int length = value.length();
        int referenceStart;
        int referenceEnd;
        String referenceValue;

        for(int i = 0; i >= 0 && i < length; )
        {
            referenceStart = value.indexOf(Util.PROPERTY_REFERENCE_PREFIX, i);
            if(referenceStart >= 0)
            {
                buffer.append(value.substring(i, referenceStart));
                referenceEnd = value.indexOf(Util.PROPERTY_REFERENCE_POSTFIX, referenceStart);
                if(referenceEnd >= 0)
                {
                    referenceValue = Util.evalProperty(value.substring(referenceStart + Util.PROPERTY_REFERENCE_PREFIX_LENGTH, referenceEnd), in, out);
                    buffer.append(referenceValue);
                }
                else
                {
                    buffer.append(value.substring(referenceStart));
                }
                i = referenceEnd + Util.PROPERTY_REFERENCE_POSTFIX_LENGTH;
            }
            else
            {
                buffer.append(value.substring(i));
                i = -1;
            }
        }

        final String result = buffer.toString();
        if(out != null)
        {
            out.setProperty(propertyName, result);
        }
        return result;
    }

    /**
     * Evaluates all properties in a <code>Properties</code> object.
     *
     * @see #evalProperty(java.lang.String, java.util.Properties, java.util.Properties)
     */
    public static void evalAllProperties(final Properties in, final Properties out)
    {
        for(final Enumeration en = in.propertyNames(); en.hasMoreElements(); )
        {
            Util.evalProperty((String)en.nextElement(), in, out);
        }
    }

    public static void loadImports(Properties p, Locale locale, ClassLoader loader)
    {
		String imports = p.getProperty(Util.IMPORT_PROPERTY);
		if(imports != null)
		{
			p.remove(Util.IMPORT_PROPERTY);
			//String[] importArray = imports.split(Util.IMPORT_DELIMITER);
			ResourceBundle bundle;
			String key;
			//for(int i = 0; i < importArray.length; ++i)
			for(StringTokenizer tokenizer = new StringTokenizer(imports, Util.IMPORT_DELIMITER); tokenizer.hasMoreTokens(); )
			{
				try
				{
					//bundle = ResourceBundle.getBundle(importArray[i].trim(), locale, loader);
					bundle = ResourceBundle.getBundle(tokenizer.nextToken(), locale, loader);
					for(Enumeration en = bundle.getKeys(); en.hasMoreElements(); )
					{
						key = (String)en.nextElement();
						p.setProperty(key, bundle.getString(key));
					}
					Util.loadImports(p, locale, loader);
				}
				catch(NullPointerException ex)
				{
					ex.printStackTrace();
				}
				catch(MissingResourceException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	public static void loadImports(Properties p)
	{
		Util.loadImports(p, Locale.getDefault(), Util.class.getClassLoader());
	}

    public static void pipeStream(final InputStream in, final OutputStream out) throws IOException
    {
        final byte[] buffer = (byte[])Util.BYTE_BUFFER_RECYCLER.getObject();
        try
        {
            int i;
            while((i = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, i);
            }
        }
        finally
        {
            Util.BYTE_BUFFER_RECYCLER.recycleObject(buffer);
        }
    }
}
