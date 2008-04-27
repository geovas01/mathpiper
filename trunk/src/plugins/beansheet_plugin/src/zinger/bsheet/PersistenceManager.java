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

package zinger.bsheet;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import zinger.bsheet.event.*;

/**
 * Persistence manager for a single table model.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class PersistenceManager extends WindowAdapter implements ActionListener, TableModelListener
{
	protected static Set instances = new HashSet();

    protected final JTable table;

    protected final Map persistenceMap = new HashMap();

	/**
	 * File the model currently persists to.
	 * If <code>null</code>, a file must be chosen or created prior to saving.
	 */
    protected File dataFile;

    private JFileChooser fileChooser;

    protected final Set listeners = new HashSet();

    protected boolean documentChanged = false;

    public PersistenceManager(JTable table)
    {
        this.table = table;
        this.table.getModel().addTableModelListener(this);

        PersistenceManager.opened(this);
    }

	/**
	 * Handles menu item events.
	 */
    public void actionPerformed(ActionEvent ev)
    {
        String actionCommand = ev.getActionCommand();
        if(actionCommand.equals(Main.getConstant("action.open")))
        {
            this.load();
        }
        else if(actionCommand.equals(Main.getConstant("action.save")))
        {
            this.save();
        }
        else if(actionCommand.equals(Main.getConstant("action.save_as")))
        {
            this.dataFile = null;
            this.save();
        }
        else if(actionCommand.equals(Main.getConstant("action.close")))
        {
            this.saveOnClose();
        }
        else if(actionCommand.equals(Main.getConstant("action.exit")))
        {
			this.closeAll();
		}
    }

	/**
	 * @since 1.0.4
	 */
    public void tableChanged(TableModelEvent ev)
    {
		this.documentChanged = true;
	}

	/**
	 * Sets the title of the frame that contains the table to the name of the persistence file.
	 */
    protected void updateFrameTitle()
    {
		if(this.dataFile != null)
		{
			Container cont = this.table.getTopLevelAncestor();
			if(cont instanceof Frame)
			{
				((Frame)cont).setTitle(this.dataFile.getName());
			}
		}
	}

	protected Persistence getChosenPersistence()
	{
		if(this.fileChooser == null)
		{
			return null;
		}
		return (Persistence)this.persistenceMap.get(this.fileChooser.getFileFilter());
	}

	/**
	 * Loads table model data from a picked file.
	 * Prompts the user for a file and passes the call to <code>load(File)</code>.
	 *
	 * @see zinger.bsheet.Persistence#load(javax.swing.JTable, java.io.File)
	 */
    protected synchronized boolean load()
    {
        switch(this.needsSave())
        {
			// Yes: save and close if saved
            case JOptionPane.YES_OPTION:
            if(!this.save())
            {
                return false;
            }
            break;

			// No: close without saving
            case JOptionPane.NO_OPTION:
            break;

            // Cancel or ESC: do nothing
            default:
            return false;
        }

		File oldDataFile = this.dataFile;
        JFileChooser fileChooser = this.getFileChooser();
        if(fileChooser.showOpenDialog(this.table.getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION)
        {
			this.dataFile = fileChooser.getSelectedFile();

			Persistence persistence = this.getChosenPersistence();
			if(persistence != null && this.dataFile != null)
			{
				if(persistence.isLoadCapable())
				{
					try
					{
						if(!this.dataFile.exists())
						{
							this.dataFile = persistence.autocorrectFile(this.dataFile);
						}
						this.fireEvent(new PersistenceEvent(this, PersistenceEvent.DOCUMENT_LOADING, persistence, this.dataFile));
						if(persistence.load(this.table, this.dataFile))
						{
							this.fireEvent(new PersistenceEvent(this, PersistenceEvent.DOCUMENT_LOADED, persistence, this.dataFile));
							this.updateFrameTitle();
							this.documentChanged = false;
							return true;
						}
					}
					catch(IOException ex)
					{
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this.table.getTopLevelAncestor(),
						                              ex,
						                              "Cannot load.",
						                              JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this.table.getTopLevelAncestor(),
					                              persistence.getFileFilter().getDescription() + " is write-only type persistence.  Please choose another.",
					                              "Cannot load.",
					                              JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		this.dataFile = oldDataFile;
		return false;
    }

	/**
	 * Persists table model data into a picked file.
	 * Ensures a persistence file is chosen and passes the call to <code>save(File)</code>.
	 *
	 * @see zinger.bsheet.Persistence#save(javax.swing.JTable, java.io.File)
	 */
    protected synchronized boolean save()
    {
		File oldDataFile = this.dataFile;
        if(this.dataFile == null)
        {
            JFileChooser fileChooser = this.getFileChooser();
            if(fileChooser.showSaveDialog(this.table.getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION)
            {
            	this.dataFile = fileChooser.getSelectedFile();
			}
			else
			{
				return false;
			}
        }

        Persistence persistence = this.getChosenPersistence();
        if(persistence != null && this.dataFile != null)
        {
			try
			{
				this.dataFile = persistence.autocorrectFile(this.dataFile);
				this.fireEvent(new PersistenceEvent(this, PersistenceEvent.DOCUMENT_SAVING, persistence, this.dataFile));
				if(persistence.save(this.table, this.dataFile))
				{
					this.fireEvent(new PersistenceEvent(this, PersistenceEvent.DOCUMENT_SAVED, persistence, this.dataFile));
					this.updateFrameTitle();
					this.documentChanged = false;
					return true;
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this.table.getTopLevelAncestor(),
				                              ex,
				                              "Cannot save.",
				                              JOptionPane.ERROR_MESSAGE);
			}
		}
		this.dataFile = oldDataFile;
		return false;
    }

	/**
	 * Determines if the data needs to be saved.
	 * Part of the determination can be prompting the user to see if they want to save the changes.
	 */
    protected int needsSave()
    {
		if(!this.documentChanged)
		{
			return JOptionPane.NO_OPTION;
		}
        return JOptionPane.showConfirmDialog(this.table.getTopLevelAncestor(), "Do you want to save this spreadsheet?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);
    }

	/**
	 * Handles window closing events.
	 */
    public void windowClosing(WindowEvent ev)
    {
        this.saveOnClose();
    }

	/**
	 * Determines if the data needs to be saved and saves it if it does.
	 *
	 * @see #needsSave()
	 * @see #save()
	 */
    public void saveOnClose()
    {
        switch(this.needsSave())
        {
			// Yes: save and close if saved
            case JOptionPane.YES_OPTION:
            if(this.save())
            {
                this.disposeWindow();
            }
            break;

			// No: close without saving
            case JOptionPane.NO_OPTION:
            this.disposeWindow();
            break;

            // Cancel or ESC: do nothing
            default:
            break;
        }
    }

	/**
	 * Steps through all instances and calls <code>saveOnClose()</code> on each.
	 */
    public static synchronized void closeAll()
    {
		PersistenceManager[] instances = (PersistenceManager[])PersistenceManager.instances.toArray(new PersistenceManager[PersistenceManager.instances.size()]);
		for(int i = 0; i < instances.length; ++i)
		{
			instances[i].saveOnClose();
		}
	}

	/**
	 * Convenience method for finding the window component containing the table and disposing of that window.
	 * This operation is necessary to ensure disposal of native window peer objects and related threads.
	 *
	 * @see #saveOnClose()
	 */
    protected void disposeWindow()
    {
		Container cont = this.table.getTopLevelAncestor();
		if(cont instanceof Window)
		{
			((Window)cont).dispose();
			PersistenceManager.disposed(this);
		}
    }

	/**
	 * Removes specified instance from non-disposed instance set.
	 * If the set reaches an empty state (no more non-disposed instances),
	 * <code>System.exit(0)</code> is called.
	 *
	 * @see #opened(zinger.bsheet.PersistenceManager)
	 */
    protected static synchronized void disposed(PersistenceManager impexp)
    {
		PersistenceManager.instances.remove(impexp);
		if(PersistenceManager.instances.isEmpty())
		{
			System.exit(0);
		}
	}

	/**
	 * Adds specified instance to the non-disposed instance set.
	 *
	 * @see #disposed(zinger.bsheet.PersistenceManager)
	 */
	protected static synchronized void opened(PersistenceManager impexp)
	{
		PersistenceManager.instances.add(impexp);
	}

    protected synchronized JFileChooser getFileChooser()
    {
		if(this.fileChooser == null)
		{
			this.fileChooser = new JFileChooser();
			this.fileChooser.setAcceptAllFileFilterUsed(false);
			for(Iterator iterator = this.persistenceMap.keySet().iterator(); iterator.hasNext();)
			{
				this.fileChooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter)iterator.next());
			}
			this.fileChooser.setFileFilter(CompressedXMLPersistence.INSTANCE.getFileFilter());
		}
		return this.fileChooser;
	}

	public void addPersistence(Persistence persistence)
	{
		this.persistenceMap.put(persistence.getFileFilter(), persistence);
	}

	public void removePersistence(Persistence persistence)
	{
		this.persistenceMap.remove(persistence.getFileFilter());
	}

	public void addPersistenceListener(PersistenceListener listener)
	{
		synchronized(this.listeners)
		{
			this.listeners.add(listener);
		}
	}

	public void removePersistenceListener(PersistenceListener listener)
	{
		synchronized(this.listeners)
		{
			this.listeners.remove(listener);
		}
	}

	protected void fireEvent(PersistenceEvent ev)
	{
		synchronized(this.listeners)
		{
			for(Iterator iterator = listeners.iterator(); iterator.hasNext(); )
			{
				((PersistenceListener)iterator.next()).handlePersistenceEvent(ev);
			}
		}
	}
}
