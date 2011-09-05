// Bean Sheet
// Copyright (C) 2004-2006 Alexey Zinger
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

package zinger.swing;

import java.awt.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/**
 * Utility and factory class for graphics (mostly Swing-related).
 */
public class SwingUtil
{
	private SwingUtil()
	{
	}

	/**
	 * Factory method for <code>Font</code> objects.
	 * Constructs a <code>Font</code> object based on specified properties as follows:
	 * <table>
	 * <tr>
	 *   <th>Property</th>
	 *   <th>Description</th>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.name</code></td>
	 *   <td>Font name.</td>
	 * </tr>
	 * <tr>
	 *   <td><code>&lt;propertyBase&gt;.size</code></td>
	 *   <td>Font size.  Must be <code>Integer</code>-parsable.</td>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.style</code></td>
	 *   <td>Comma-separated list of applied font styles.  Spaces are allowed, but do not separate the values.
	 *       Possible allowed values are <code>bold</code> and <code>italic</code> (case insensitive).</td>
	 * </tr>
	 * </table>
	 *
	 * @since 0.8
	 */
	public static Font createFont(String propertyBase, Properties config)
	{
		int style = Font.PLAIN;
		String[] styles = config.getProperty(propertyBase + ".style").split(",");
		for(int i = 0; i < styles.length; ++i)
		{
			styles[i] = styles[i].trim().toLowerCase();
			if(styles[i].equals("bold"))
			{
				style |= Font.BOLD;
			}
			else if(styles[i].equals("italic"))
			{
				style |= Font.ITALIC;
			}
		}

		return new Font(config.getProperty(propertyBase + ".name"),
		                style,
		                Integer.parseInt(config.getProperty(propertyBase + ".size")));
	}

	/**
	 * Factory method for <code>JMenuItem</code> objects.
	 * Constructs a <code>JMenuItem</code> obejct and passes it to
	 * <code>configButton(AbstractButton, String, Properties)</code>
	 * method.
	 *
	 * @see #configButton(javax.swing.AbstractButton, java.lang.String, java.util.Properties)
	 */
	public static JMenuItem createMenuItem(String propertyBase, Properties config)
	{
		JMenuItem menuItem = new JMenuItem();
		SwingUtil.configMenuItem(menuItem, propertyBase, config);
		return menuItem;
	}

	/**
	 * Factory method for <code>JButton</code> objects.
	 * Constructs a <code>JButton</code> obejct and passes it to
	 * <code>configButton(AbstractButton, String, Properties)</code>
	 * method.
	 *
	 * @see #configButton(javax.swing.AbstractButton, java.lang.String, java.util.Properties)
	 */
	public static JButton createButton(String propertyBase, Properties config)
	{
		JButton button = new JButton();
		SwingUtil.configButton(button, propertyBase, config);
		return button;
	}

	/**
	 * Factory utility method for configuring <code>JMenuItem</code> objects.
	 * Configures a <code>JMenuItem</code> object and passes it to
	 * <code>configButton(AbstractButton, String, Properties)</code> method.
	 * The menu item is configured as follows:
	 * <table>
	 * <tr>
	 *   <th>Property</th>
	 *   <th>Description</th>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.accelerator</code></td>
	 *   <td>Keyboard accelerator string (as per <code>javax.swing.KeyStroke</code>).
	 *       May be <code>null</code>.</td>
	 * </tr>
	 * </table>
	 *
	 * @see #configButton(javax.swing.AbstractButton, java.lang.String, java.util.Properties)
	 *
	 * @since 1.0.3
	 */
	public static void configMenuItem(JMenuItem menuItem, String propertyBase, Properties config)
	{
		SwingUtil.configButton(menuItem, propertyBase, config);
		KeyStroke accelerator = KeyStroke.getKeyStroke(config.getProperty(propertyBase + ".accelerator"));
		if(accelerator != null)
		{
			menuItem.setAccelerator(accelerator);
		}
	}

	/**
	 * Factory utility method for configuring <code>AbstractButton</code> objects.
	 * Configures an <code>AbstractButton</code> object and passes it to
	 * <code>configComponent(JComponent, String, Properties)</code> method.
	 * The button is configured as follows:
	 * <table>
	 * <tr>
	 *   <th>Property</th>
	 *   <th>Description</th>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.label</code></td>
	 *   <td>Button text.</td>
	 * </tr>
	 * <tr>
	 *   <td><code>&lt;propertyBase&gt;.action</code></td>
	 *   <td>Action command.</td>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.icon</code></td>
	 *   <td>Icon base property that's given to <code>createIcon(String, Properties)</code> method.
	 *       May be <code>null</code>.</td>
	 * </tr>
	 * </table>
	 *
	 * @see #createIcon(java.lang.String, java.util.Properties)
	 * @see #configComponent(javax.swing.JComponent, java.lang.String, java.util.Properties)
	 */
	public static void configButton(AbstractButton button, String propertyBase, Properties config)
	{
		String text = config.getProperty(propertyBase + ".label");
		if(text != null)
		{
			button.setText(text);
		}

		String action = config.getProperty(propertyBase + ".action");
		if(action != null)
		{
			button.setActionCommand(action);
		}

		Icon icon = SwingUtil.createIcon(propertyBase + ".icon", config);
		if(icon != null)
		{
			button.setIcon(icon);
		}

		SwingUtil.configComponent(button, propertyBase, config);
	}

	/**
	 * Factory utility method for configuring <code>JComponent</code> objects.
	 * Configures a <code>JComponent</code> object based on specified properties as follows:
	 * <table>
	 * <tr>
	 *   <th>Property</th>
	 *   <th>Description</th>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.tool_tip</code></td>
	 *   <td>Tool tip text.  May be <code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static void configComponent(JComponent comp, String propertyBase, Properties config)
	{
		String toolTip = config.getProperty(propertyBase + ".tool_tip");
		if(toolTip != null)
		{
			comp.setToolTipText(toolTip);
		}
	}

	/**
	 * Factory method for <code>Icon</code> objects.
	 * Constructs an <code>Icon</code> object based on specified properties as follows:
	 * <table>
	 * <tr>
	 *   <th>Property</th>
	 *   <th>Description</th>
	 * </tr>
	 * <tr bgcolor="#eeeeff">
	 *   <td><code>&lt;propertyBase&gt;.url</code></td>
	 *   <td>Icon image URL.</td>
	 *   <td rowspan="2" bgcolor="#eeeeee"><strong>Note:</strong> mutually exclusive.</td>
	 * </tr>
	 * <tr>
	 *   <td><code>&lt;propertyBase&gt;.resource</code></td>
	 *   <td>Icon image resource name to be loaded by the class loader that loaded this class.</td>
	 * </tr>
	 * </table>
	 */
	public static Icon createIcon(String propertyBase, Properties config)
	{
		URL url = null;
		String urlText = config.getProperty(propertyBase + ".url");
		if(urlText != null)
		{
			try
			{
				url = new URL(urlText);
			}
			catch(MalformedURLException ex)
			{
				ex.printStackTrace();
			}
		}
		if(url == null)
		{
			String resource = config.getProperty(propertyBase + ".resource");
			if(resource != null)
			{
				url = SwingUtil.class.getClassLoader().getResource(resource);
			}
		}

		if(url != null)
		{
			return new ImageIcon(url);
		}

		return null;
	}
}
