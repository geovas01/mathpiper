/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface;

import java.awt.event.ActionEvent;

import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.VMMSave;
import vmm.core3D.View3DLit;


public class SurfaceView extends View3DLit {


	
	/**
	 * A command that allows the user to turn off grid lines in the u direction alltogether.
	 * This is created in the {@link #getActions()} method.
	 */
	protected ToggleAction omitULineAction = new ToggleAction(I18n.tr("vmm.surface.Surface.command.OmitUGridLines")) {
		public void actionPerformed(ActionEvent evt) {
			setShowUGridLines(!showUGridLines);
		}
	};

	/**
	 * A command that allows the user to turn off grid lines in the v direction alltogether.
	 * This is created in the {@link #getActions()} method.
	 */
	protected ToggleAction omitVLineAction = new ToggleAction(I18n.tr("vmm.surface.Surface.command.OmitVGridLines")) {
		public void actionPerformed(ActionEvent evt) {
			setShowVGridLines(!showVGridLines);
		}
	};
	
	@VMMSave
	private boolean showUGridLines = true;
	
	@VMMSave
	private boolean showVGridLines = true;
	
	/**
	 * An array of 6 commmands that allow the user to select what fraction of grid lines are actually drawn.
	 * The default number is that a grid line is shown every 6 subpatches; that is, the boundaries of the patches
	 * are shown.  These commands are created in the {@link #getActions()} method.
	 */
	protected ActionRadioGroup gridSpacingSelect  = new ActionRadioGroup(
			new String[] {
					I18n.tr("vmm.surface.Surface.command.GridSpacing.0"),
					I18n.tr("vmm.surface.Surface.command.GridSpacing.1"),
					I18n.tr("vmm.surface.Surface.command.GridSpacing.2"),
					I18n.tr("vmm.surface.Surface.command.GridSpacing.3"),
					I18n.tr("vmm.surface.Surface.command.GridSpacing.4"),
					I18n.tr("vmm.surface.Surface.command.GridSpacing.5")
			}) {
				public void optionSelected(int index) {
					setGridSpacing(standardGridSpacings[index]);
				}
			};
	
	@VMMSave
	private int gridSpacing;

	private int standardGridSpacings[] = { 1, 2, 3, 6, 12, 0 };
	
	public SurfaceView() {
		setGridSpacing(6);
		setAntialiased(true);
	}

	/**
	 * Determines how many grid lines are actually drawn.  A grid line is drawn every gridSpacing subpatches in
	 * each direction of the grid, except that the final grid line is always drawn.  If the value of
	 * gridSpacing is set to 0, then no grid lines are drawn along the surface; an exception is that when
	 * the surface is drawn in wireframe and gridSpacing is 0, grid lines are drawn.
	 */
	public void setGridSpacing(int gridSpacing) {
		if (gridSpacing < 0)
			gridSpacing = 0;
		if (this.gridSpacing == gridSpacing)
			return;
		this.gridSpacing = gridSpacing;
		for (int i = 0; i < standardGridSpacings.length; i++) {
			if (gridSpacing == standardGridSpacings[i]) {
				gridSpacingSelect.setSelectedIndex(i);
				break;
			}
		}
		forceRedraw();
	}

	/**
	 * Set the spacing between grid lines that are drawn.
	 * @see #setGridSpacing(int)
	 */
	public int getGridSpacing() {
		return gridSpacing;
	}
	
	/**
	 * Tells whether grid lines are drawn in the U direction.
	 * @see #setShowUGridLines(boolean)
	 */
	public boolean getShowUGridLines() {
		return showUGridLines;
	}

	/**
	 * Set whether grid lines are drawn in the U direction.  If the value is set to false,
	 * no U grid lines are drawn.  If it is set to true, grid lines are drawn, with the spacing
	 * between grid lines depending on the value set by {@link #setGridSpacing(int)}.
	 * (Wireframe mode is a special case in that some grid lines are drawn in wireframe
	 * mode, even if grid lines are turned off in both directions.)
	 */
	public void setShowUGridLines(boolean showUGridLines) {
		if (this.showUGridLines != showUGridLines) {
			this.showUGridLines = showUGridLines;
			omitULineAction.putValue("ToggleStatus",new Boolean(!showUGridLines));
			forceRedraw();
		}
	}

	/**
	 * Tells whether grid lines are drawn in the V direction.
	 * @see #setShowVGridLines(boolean)
	 */
	public boolean getShowVGridLines() {
		return showVGridLines;
	}

	/**
	 * Set whether grid lines are drawn in the V direction.  If the value is set to false,
	 * no V grid lines are drawn.  If it is set to true, grid lines are drawn, with the spacing
	 * between grid lines depending on the value set by {@link #setGridSpacing(int)}.
	 * (Wireframe mode is a special case in that some grid lines are drawn in wireframe
	 * mode, even if grid lines are turned off in both directions.)
	 */
	public void setShowVGridLines(boolean showVGridLines) {
		if (this.showVGridLines != showVGridLines) {
			this.showVGridLines = showVGridLines;
			omitVLineAction.putValue("ToggleStatus",new Boolean(!showVGridLines));
			forceRedraw();
		}
	}
	
	public ActionList getActions() {
		ActionList commands = super.getActions();
		ActionList list = new ActionList(I18n.tr("vmm.surface.Surface.command.GridSpacing"));
		list.add(gridSpacingSelect);
		list.add(null);
		list.add(omitULineAction);
		list.add(omitVLineAction);
		commands.add(list);
		return commands;
	}
	
	/**
	 * This method is overridden to set the orientation of a Surface exhibit from
	 * the defaultOrientation property of the Surface.
	 * @see Surface#setDefaultOrientation(int)
	 */
	public void setExhibit(Exhibit exhibit) {
		super.setExhibit(exhibit);
		if (exhibit != null && exhibit instanceof Surface) 
			setOrientation(((Surface)exhibit).getDefaultOrientation());
	}


}
