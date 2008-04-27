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

import javax.swing.*;

/**
 * Interface for Bean Sheet document persistance.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public interface Persistence
{
	/**
	 * @return <code>true</code> if the implementation can load a document as well as persist it
	 */
	public boolean isLoadCapable();

	/**
	 * @return an object that knows how to accept and reject files in accordance with the implementation
	 */
	public javax.swing.filechooser.FileFilter getFileFilter();

	/**
	 * Loads a document into the model behind the table.
	 *
	 * @throws java.lang.UnsupportedOperationException if the implementation is not capable of loading
	 * documents as per <code>isLoadCapable()</code> method
	 *
	 * @throws java.io.IOException if an IO problem occurred during loading the document
	 *
	 * @return <code>true</code> if the document was loaded successfully
	 *
	 * @see #isLoadCapable()
	 */
	public boolean load(JTable table, File file) throws IOException, UnsupportedOperationException;

	/**
	 * Persists the document from the model behind the table.
	 *
	 * @throws java.io.IOException if an IO problem occurred during persisting the document
	 *
	 * @return <code>true</code> if the document was persisted successfully
	 */
	public boolean save(JTable table, File file) throws IOException;

	/**
	 * Attempts to find a file based on the provided file if the implementation does not accept
	 * the file as it's passed in.  A typical implementation of this would autocomplete a file extension.
	 * Acceptance of a file is tested by <code>getFileFilter().accept(file)</code>.
	 *
	 * @see #getFileFilter()
	 */
	public File autocorrectFile(File file);
}
