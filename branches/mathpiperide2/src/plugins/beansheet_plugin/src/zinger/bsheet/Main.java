// Bean Sheet
// Copyright (C) 2004-2007 Alexey Zinger
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
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;

import zinger.bsheet.module.*;
import zinger.swing.*;
import zinger.util.*;

public class Main
{
	public static ModuleContext moduleContext; //Note:tk: Engineer this better.
    protected static class EditFieldTableSelectionListener implements ListSelectionListener, ActionListener
    {
        private final JTextComponent textComponent;
        private final JTable table;
	

        public EditFieldTableSelectionListener(JTextComponent textComponent, JTable table)
        {
            this.textComponent = textComponent;
            this.table = table;
        }

        public void valueChanged(ListSelectionEvent ev)
        {
            int row = this.table.getSelectedRow();
            int column = this.table.getSelectedColumn();
            if(row < 0 || column < 0)
            {
                return;
            }
            Object value = this.table.getValueAt(row, column);
            value = ((BSHTableModel)this.table.getModel()).formatValueAt(value, this.table.convertColumnIndexToModel(column), row);
            this.textComponent.setText(String.valueOf(value));
        }

        public void actionPerformed(ActionEvent ev)
        {
            int row = this.table.getSelectedRow();
            int column = this.table.getSelectedColumn();
            if(row < 0 || column < 0)
            {
                return;
            }
            this.table.setValueAt(this.textComponent.getText(), row, column);
        }
    }

    private static final ActionListener NEW_DOCUMENT_LISTENER = new ActionListener()
    {
		public void actionPerformed(ActionEvent ev)
		{
			Main.showNewFrame();
		}
	};

    private static final Properties CONSTANTS = new Properties();

    static
    {
		Main.CONSTANTS.setProperty(Util.IMPORT_PROPERTY, "zinger.bsheet.constants");
		Util.loadImports(Main.CONSTANTS, Locale.getDefault(), Main.class.getClassLoader());
		Util.evalAllProperties(Main.CONSTANTS, Main.CONSTANTS);
	}

    private static Font FIXED_WIDTH_FONT = null;
    public static Font getFixedWidthFont()
    {
		if(Main.FIXED_WIDTH_FONT == null)
		{
			Main.FIXED_WIDTH_FONT = Main.createFont("font.fixed_width");
		}
		return Main.FIXED_WIDTH_FONT;
	}

	private static Window splashScreen = null;

    public static String getConstant(String name)
    {
		return Main.CONSTANTS.getProperty(name);
	}

	public static Font createFont(String constantBase)
	{
		return SwingUtil.createFont(constantBase, Main.CONSTANTS);
	}

	/**
	 * @deprecated Use <code>SwingUtil.createFont(String, Properties)</code>
	 *
	 * @see zinger.swing.SwingUtil#createFont(java.lang.String, java.util.Properties)
	 */
	public static Font createFont(String constantBase, Properties config)
	{
		return SwingUtil.createFont(constantBase, config);
	}

	public static JMenuItem createMenuItem(String constantBase)
	{
		return SwingUtil.createMenuItem(constantBase, Main.CONSTANTS);
	}

	/**
	 * @deprecated Use <code>SwingUtil.createMenuItem(String, Properties)</code>
	 *
	 * @see zinger.swing.SwingUtil#createMenuItem(java.lang.String, java.util.Properties)
	 */
	public static JMenuItem createMenuItem(String constantBase, Properties config)
	{
		return SwingUtil.createMenuItem(constantBase, config);
	}

	public static JButton createButton(String constantBase)
	{
		return SwingUtil.createButton(constantBase, Main.CONSTANTS);
	}

    public static void main(String[] args)
    {
		System.setProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel");
		//Main.showNewFrame(true);
	}

	public static synchronized Window getSplashScreen(Window owner)
	{
		if(Main.splashScreen == null)
		{
			Main.splashScreen = new zinger.swing.SplashScreen(
				Main.class.getClassLoader().getResource(Main.getConstant("splashscreen.resource")),
				owner,
				Main.getConstant("splashscreen.text"),
				Integer.parseInt(Main.getConstant("splashscreen.text.x")),
				Integer.parseInt(Main.getConstant("splashscreen.text.y")),
				SwingUtil.createFont("splashscreen.text.font", Main.CONSTANTS));
		}

		return Main.splashScreen;
	}

	protected static void showSplashScreen(final Window owner)
	{
		(new Thread(new Runnable()
		{
			public void run()
			{
				Window win = Main.getSplashScreen(owner);

				win.setLocationRelativeTo(null);
				win.setVisible(true);

				long splashDelay = Long.parseLong(Main.getConstant("splashscreen.delay"));

				try
				{
					do
					{
						Thread.sleep(splashDelay);
					}
					while(!owner.isVisible());
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}

				win.dispose();
			}
		})).start();
	}

	public static void showNewFrame()
	{
//		Main.showNewFrame(false);
	}

	public static JPanel getInstance()
	{
		// frame
        //final JFrame frame = new JFrame(Main.getConstant("frame.title"));
       // frame.setIconImage(frame.getToolkit().createImage(Main.class.getClassLoader().getResource(Main.getConstant("frame.icon"))));
       
       
        // splash screen
//        if(showSplashScreen)
//        {
//        	Main.showSplashScreen(frame);
//	}

		// table model
        BSHTableModel model = new BSHTableModel(Integer.parseInt(Main.getConstant("table.rows.count")), Integer.parseInt(Main.getConstant("table.cols.count")));
        ZTable table = new ZTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setDefaultRenderer(Object.class, model.new BSHCellRenderer());
        table.setDefaultEditor(Object.class, model.new BSHCellEditor(table.getDefaultEditor(Object.class)));
        table.setDefaultRenderer(JTableHeader.class, table.getTableHeader().getDefaultRenderer());
        table.getDefaultEditor(Object.class).addCellEditorListener(model);
        table.setCellSelectionEnabled(true);

        // row resize renderer
        RowResizeRenderer rowHeaderRenderer = new RowResizeRenderer(table);
        //table.addMouseMotionListener(rowHeaderRenderer);
        Object rowHeaderRendererKey = RowResizeRenderer.class.getName();
        table.addExtraRenderer(rowHeaderRendererKey, rowHeaderRenderer);
        table.setExtraRendererOn(rowHeaderRendererKey, true);

		// dependencies renderer
        table.addExtraRenderer(Dependencies.class.getName(), new DependenciesRenderer(table));

		// edit text field
        JTextField editField = new JTextField();
        Main.EditFieldTableSelectionListener editFieldTableSelectionListener = new Main.EditFieldTableSelectionListener(editField, table);
        table.getColumnModel().getSelectionModel().addListSelectionListener(editFieldTableSelectionListener);
        table.getSelectionModel().addListSelectionListener(editFieldTableSelectionListener);
        editField.addActionListener(editFieldTableSelectionListener);

        // content pane
        JPanel contentPane = new JPanel(new BorderLayout());

        // table pane
        JPanel tablePane = new JPanel(new BorderLayout());
        tablePane.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePane.add(new JScrollPane(table), BorderLayout.CENTER);

        contentPane.add(tablePane, BorderLayout.CENTER);

		// file menu items
		JMenuItem newItem = Main.createMenuItem("menu.item.new");
        JMenuItem openItem = Main.createMenuItem("menu.item.open");
        JMenuItem saveItem = Main.createMenuItem("menu.item.save");
        JMenuItem saveAsItem = Main.createMenuItem("menu.item.save_as");
        JMenuItem closeItem = Main.createMenuItem("menu.item.close");
        JMenuItem exitItem = Main.createMenuItem("menu.item.exit");

		// file menu
        JMenu fileMenu = new JMenu(Main.getConstant("menu.file.label"));
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

		// edit menu items
		JMenuItem cellEditorItem = Main.createMenuItem("menu.item.cell_edit");
		JMenuItem searchItem = Main.createMenuItem("menu.item.search");
        JMenuItem copyItem = Main.createMenuItem("menu.item.copy");
        JMenuItem copyObjectsItem = Main.createMenuItem("menu.item.copy_objects");
        JMenuItem cutItem = Main.createMenuItem("menu.item.cut");
        JMenuItem cutObjectsItem = Main.createMenuItem("menu.item.cut_objects");
        JMenuItem deleteItem = Main.createMenuItem("menu.item.delete");
        JMenuItem pasteItem = Main.createMenuItem("menu.item.paste");
        JMenuItem pasteFillItem = Main.createMenuItem("menu.item.paste_fill");
        JMenuItem colSortItem = Main.createMenuItem("menu.item.col_sort");
        JMenuItem cellSortItem = Main.createMenuItem("menu.item.cell_sort");

		// edit menu
        JMenu editMenu = new JMenu(Main.getConstant("menu.edit.label"));
        editMenu.add(cellEditorItem);
        editMenu.addSeparator();
        editMenu.add(searchItem);
        editMenu.add(colSortItem);
        editMenu.add(cellSortItem);
        editMenu.addSeparator();
        editMenu.add(cutObjectsItem);
        editMenu.add(copyObjectsItem);
        editMenu.add(pasteItem);
        editMenu.add(pasteFillItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.addSeparator();
        editMenu.add(deleteItem);


		// table menu items
        JMenuItem delColsItem = Main.createMenuItem("menu.item.del_cols");
        JMenuItem addColItem = Main.createMenuItem("menu.item.add_col");
        JMenuItem delRowsItem = Main.createMenuItem("menu.item.del_rows");
        JMenuItem addRowItem = Main.createMenuItem("menu.item.add_row");
        JMenuItem columnFormatItem = Main.createMenuItem("menu.item.col_format");
        JMenuItem cellFormatItem = Main.createMenuItem("menu.item.cell_format");
        JMenuItem columnAppearanceItem = Main.createMenuItem("menu.item.col_appearance");
        JMenuItem cellAppearanceItem = Main.createMenuItem("menu.item.cell_appearance");
        JMenuItem showDependenciesItem = new JCheckBoxMenuItem(Main.getConstant("menu.item.show_dependencies.label"));
        showDependenciesItem.setActionCommand(Main.getConstant("menu.item.show_dependencies.action"));
        JMenuItem flipItem = Main.createMenuItem("menu.item.flip");

		// table menu
        JMenu tableMenu = new JMenu(Main.getConstant("menu.table.label"));
        tableMenu.add(flipItem);
        tableMenu.addSeparator();
        tableMenu.add(addColItem);
        tableMenu.add(delColsItem);
        tableMenu.addSeparator();
        tableMenu.add(addRowItem);
        tableMenu.add(delRowsItem);
        tableMenu.addSeparator();
        tableMenu.add(columnFormatItem);
        tableMenu.add(cellFormatItem);
        tableMenu.addSeparator();
        tableMenu.add(columnAppearanceItem);
        tableMenu.add(cellAppearanceItem);
        tableMenu.addSeparator();
        tableMenu.add(showDependenciesItem);

		// menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(tableMenu);

		// frame setup
//        frame.setContentPane(contentPane);
//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//
//        GraphicsEnvironment grenv = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        int w;
//        String wStr = Main.getConstant("frame.width");
//        if(wStr == null)
//        {
//			w = (int)((float)grenv.getMaximumWindowBounds().getWidth() * Float.parseFloat(Main.getConstant("frame.width.fraction")));
//		}
//		else
//		{
//			w = Integer.parseInt(wStr);
//		}
//
//		int h;
//		String hStr = Main.getConstant("frame.height");
//		if(hStr == null)
//		{
//			h = (int)((float)grenv.getMaximumWindowBounds().getHeight() * Float.parseFloat(Main.getConstant("frame.height.fraction")));
//		}
//		else
//		{
//			h = Integer.parseInt(hStr);
//		}
//
//        frame.setSize(w, h);
//        frame.setJMenuBar(menuBar);
//        frame.setLocationRelativeTo(null);

        // persistence manager
        PersistenceManager impexp = new PersistenceManager(table);
        impexp.addPersistence(CompressedXMLPersistence.INSTANCE);
        impexp.addPersistence(XMLPersistence.INSTANCE);
        impexp.addPersistence(HTMLPersistence.INSTANCE);
        impexp.addPersistence(FlatFilePersistence.INSTANCE);
        openItem.addActionListener(impexp);
        saveItem.addActionListener(impexp);
        saveAsItem.addActionListener(impexp);
        closeItem.addActionListener(impexp);
        exitItem.addActionListener(impexp);

        newItem.addActionListener(Main.NEW_DOCUMENT_LISTENER);

        //frame.addWindowListener(impexp);  
	

        // popup cell editor
        PopUpCellEditor cellEditor = new PopUpCellEditor(table);
        cellEditorItem.addActionListener(cellEditor);

        // table modifier
        TableModifier tableModifier = new TableModifier(table);
        delColsItem.addActionListener(tableModifier);
        addColItem.addActionListener(tableModifier);
        delRowsItem.addActionListener(tableModifier);
        addRowItem.addActionListener(tableModifier);
        copyItem.addActionListener(tableModifier);
        copyObjectsItem.addActionListener(tableModifier);
        cutItem.addActionListener(tableModifier);
        cutObjectsItem.addActionListener(tableModifier);
        deleteItem.addActionListener(tableModifier);
        pasteItem.addActionListener(tableModifier);
        pasteFillItem.addActionListener(tableModifier);
        showDependenciesItem.addActionListener(tableModifier);
        flipItem.addActionListener(tableModifier);

        // table search
        TableSearch search = new TableSearch(table);
        searchItem.addActionListener(search);

        // cell format editor
        CellFormatEditor formatEditor = new CellFormatEditor(table);
        columnFormatItem.addActionListener(formatEditor);
        cellFormatItem.addActionListener(formatEditor);

        // appearance editor
        AppearanceEditor appearanceEditor = new AppearanceEditor(table);
        columnAppearanceItem.addActionListener(appearanceEditor);
        cellAppearanceItem.addActionListener(appearanceEditor);

        contentPane.add(editField, BorderLayout.NORTH);
        editField.setFont(Main.getFixedWidthFont());

        // table sort
        TableSort sort = new TableSort(table);
        colSortItem.addActionListener(sort);
        cellSortItem.addActionListener(sort);

		// module context
		Main.moduleContext = new ModuleContext(table, menuBar, impexp, tableModifier, formatEditor, model.bsh);
		Main.moduleContext.init();

		table.requestFocus();
//Note: tk.
//        EventQueue.invokeLater(new Runnable()
//        {
//			public void run()
//			{
//        		frame.setVisible(true);
//			}
//		});
	return contentPane;
    }//end method.
}//end class.
