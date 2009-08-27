/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.Prefs;



public class Galleries {
	
	
	private GalleryItemList defaultGallery;

	private ArrayList<GalleryItemList> items;  // GalleryItemLists, and nulls to indicate separators
	
	private GalleryItemList currentGallery;
	private String currentGalleryName;
	
	static class GalleryItem {
		String name;
		String title;
	}
	
	static class ExhibitItem extends GalleryItem {
		boolean isXML;
		String classname;  // for xml, this is the resource file name
	}
	
	private static class GalleryItemList extends GalleryItem{
		String nodeType; // "gallery", "gallery_group", "exhibit_group"
		ArrayList<GalleryItem> items; // nulls indicate separators
	}
	
	private  class SelectGalleryAction extends AbstractAction {
		JMenu exhibitMenu;
		GalleryItemList gallery;
		Menus menus;
		SelectGalleryAction(Menus menus, GalleryItemList gallery, JMenu exhibitMenu) {
			super(gallery.title);
			this.menus = menus;
			this.gallery = gallery;
			this.exhibitMenu = exhibitMenu;
		}
		public void actionPerformed(ActionEvent evt) {
			currentGalleryName = gallery.name;
			menus.installExhibit(null);
			setUpExhibitMenu(menus, exhibitMenu, gallery);
		}
	}
	
	private  class SelectExhibitAction extends AbstractAction {
		Menus menus;
		ExhibitItem exhibitItem;
		SelectExhibitAction(Menus menus, ExhibitItem exhibitItem) {
			super(exhibitItem.title);
			this.menus = menus;
			this.exhibitItem = exhibitItem;
		}
		public void actionPerformed(ActionEvent evt) {
			menus.installExhibit(exhibitItem);
		}
	}
	
	public Galleries() throws IOException {
		this("vmm/xm3d/galleries.xml");
	}
	
	public Galleries(String resourceName) throws IOException {
		InputStream input;
		try {
			ClassLoader cl = Galleries.class.getClassLoader();
			URL resourceURL = cl.getResource(resourceName);
			input = resourceURL.openStream();
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("3dxm.galleries.error.CantRead"));
		}
        readStream(input);
	}

	public Galleries(InputStream xmlStream) throws IOException {
		readStream(xmlStream);
	}
	
	public String currentGalleryName() {
		return currentGalleryName;
	}
	
	public void selectGalleryByName(String name, Menus menus, JMenu exhibitMenu) {
		if (name == null) {
			currentGalleryName = null;
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			GalleryItemList item = items.get(i);
			if (item.nodeType.equals("gallery")) {
				if (item.name.equals(name)) {
					setUpExhibitMenu(menus,exhibitMenu,item);
					return;
				}
			}
			else {
				for (int j = 0; j < item.items.size(); j++) {
					GalleryItemList subitem = (GalleryItemList)item.items.get(j);
					if (subitem.name.equals(name)) {
						setUpExhibitMenu(menus,exhibitMenu,subitem);
						return;
					}
				}
			}
		}
		currentGalleryName = null;
	}
	
	String findGalleryForExhibit(Exhibit exhibit) { 
		String classname = exhibit.getClass().getName();
		for (GalleryItemList gallery : items) {
			if (gallery == null)
				continue;
			if (gallery.nodeType.equals("gallery_group")) {
				for (GalleryItem gg : gallery.items) {
					if (gg == null)
						continue;
					assert gg instanceof GalleryItemList && ((GalleryItemList)gg).nodeType.equals("gallery");
					if (findGalleryHelper(classname,(GalleryItemList)gg))  // must be a gallery
						return gg.name;
				}
			}
			else {
				if (findGalleryHelper(classname,gallery))
					return gallery.name;
			}
		}
		return null;
	}
	private boolean findGalleryHelper(String classname, GalleryItemList group) {
		for (GalleryItem item : group.items) {
			if (item == null)
				continue;
			assert item instanceof ExhibitItem || (item instanceof GalleryItemList && ((GalleryItemList)item).nodeType.equals("exhbit_group"));
			if (item instanceof GalleryItemList) {  // must be an exhbit_group
				for (GalleryItem exhibitItem : ((GalleryItemList)item).items)
					if (exhibitItem != null && classname.equals(((ExhibitItem)exhibitItem).classname))
						return true;
			}
			else  { // must be an ExhibitItem
				if (classname.equals(((ExhibitItem)item).classname))
					return true;
			}
		}
		return false;
	}
	
	public void setUpGalleryMenu(final Menus menus, JMenu galleryMenu, final JMenu exhibitMenu) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == null)
				galleryMenu.addSeparator();
			else if ( items.get(i).nodeType.equals("gallery") ) {
				GalleryItemList gallery = items.get(i);
				galleryMenu.add( new SelectGalleryAction(menus,gallery,exhibitMenu) );
			}
			else {   // must be a gallery_group
				GalleryItemList gallery_group = items.get(i);
				ArrayList subgallery = gallery_group.items;
				JMenu submenu = new JMenu(gallery_group.title);
				for (int j = 0; j < subgallery.size(); j++) {
					if (subgallery.get(j) == null)
						submenu.addSeparator();
					else {
						GalleryItemList gallery = (GalleryItemList)subgallery.get(j);
						submenu.add( new SelectGalleryAction(menus,gallery,exhibitMenu) );
					}
				}
				galleryMenu.add(submenu);
			}
		}
		setUpExhibitMenu(menus, exhibitMenu, defaultGallery);
	}

	
	void setUpExhibitMenu(Menus menus, JMenu exhibitMenu) {
		setUpExhibitMenu(menus,exhibitMenu,null);
	}
		
	private void setUpExhibitMenu(final Menus menus, JMenu exhibitMenu, GalleryItemList gallery) {
		if (gallery == null)
			gallery = currentGallery;
		if (gallery == null)
			gallery = defaultGallery;
		currentGallery = gallery;
		currentGalleryName = gallery.name;
		exhibitMenu.removeAll();
		exhibitMenu.setText(gallery.title);
		for (int i = 0; i < gallery.items.size(); i++) {
			if (gallery.items.get(i) == null)
				exhibitMenu.addSeparator();
			else if ( gallery.items.get(i) instanceof ExhibitItem ) {
				exhibitMenu.add( new SelectExhibitAction(menus,(ExhibitItem)gallery.items.get(i)) );
			}
			else {  // an array of strings, representing a submenu
				GalleryItemList exhibitGroup = (GalleryItemList)gallery.items.get(i);
				ArrayList items = exhibitGroup.items;
				JMenu submenu = new JMenu(exhibitGroup.title);
				for (int j = 0; j < items.size(); j++) {
					if (items.get(j) == null)
						submenu.addSeparator();
					else
						submenu.add( new SelectExhibitAction(menus,(ExhibitItem)items.get(j)));
				}
				exhibitMenu.add(submenu);
			}
		}
	}


	
	/*
	 * Syntax for galleries.xml document:  
	 *    -- Main element is <galleries>
	 *    -- <galleries> can contain <gallery>, <gallery_group>, and <separator>
	 *    -- <gallery> can contain <exhibit>, <xml_exhibit>, <exhibit_group>, and <separator>
	 *    -- <gallery_group> can contain <gallery> and <separator>
	 *    -- <exhibit_group> can contain <exhibit> and <separator>
	 *    -- <exhibit> and <separator> are empty elements
	 *    -- <gallery>, <gallery_group> and <exhibit_group> require a "name" attribute and have
	 *          an optional "title" attribute giving a human-readable name for use in a menu.  If not
	 *          title is given, the title is obtained by applying I18n.tr() to the  name.
	 *    -- <exhibit> requires a "class" attribute whose value is the name of the class for the exhibit.
	 *         Optional "name" attribute whose value defaults to the same as the class.
	 *         Optional "title" attribute whose value to defaults to I18n.tr() applied to the name.
	 *    -- <xml_exhibit> requires a "file" attribue whose value is the resource file name
	 *                   of a saved settings file.
	 *         Optional "name" attribute whose value defaults to the same as the resource file name.
	 *         Optional "title" attribute whose value to defaults to I18n.tr() applied to the name.
	 *    -- <gallery> can have the attribute  default="yes"  to mark the default gallery
	 */
	private void readStream(InputStream xmlStream) throws IOException {
		DocumentBuilder reader;
		Document doc;
		Element galleriesElement;
		try {
			reader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("3dxm.galleries.error.CantDoXML"));
		}
		try {
			doc = reader.parse(xmlStream);
		}
		catch (SAXException e) {
			throw new IOException(I18n.tr("3dxm.galleries.error.NotXML",e.getMessage()));
		}
		catch (IOException e) {
			throw new IOException(I18n.tr("3dxm.galleries.error.CantRead",e.getMessage()));
		}
		galleriesElement = doc.getDocumentElement();
		if (! galleriesElement.getTagName().equals("galleries"))
			throw new IOException(I18n.tr("3dxm.galleries.error.WrongXML",galleriesElement.getTagName()));
		NodeList children = galleriesElement.getChildNodes();
		items = new ArrayList<GalleryItemList>();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				Element itemElement = (Element)children.item(i);
				if (itemElement.getTagName().equals("gallery")) {
					items.add(buildGallery(itemElement));
				}
				else if (itemElement.getTagName().equals("gallery_group")) {
					items.add(buildGalleryGroup(itemElement));
				}
				else
					throw new IOException(I18n.tr("3dxm.galleries.error.WrongXML",galleriesElement.getTagName()));
			}
		}
	}
	
	private GalleryItemList buildGallery(Element galleryElement) throws IOException {
		String name = galleryElement.getAttribute("name").trim();
		if (name.length() == 0)
			throw new IOException(I18n.tr("3dxm.galleries.error.MissingAttribute", "name", "gallery"));
		String title = galleryElement.getAttribute("title").trim();
		if (title.length() == 0)
			title = I18n.tr(name);
		NodeList itemElements = galleryElement.getChildNodes();
		int itemCount = itemElements.getLength();
		ArrayList<GalleryItem> items = new ArrayList<GalleryItem>();
		for (int i = 0; i < itemCount; i++) {
			if (itemElements.item(i) instanceof Element) {
				Element itemElement = (Element)itemElements.item(i);
				String tagName = itemElement.getTagName();
				if (tagName.equals("separator"))
					items.add(null);
				else if (tagName.equals("exhibit"))
					items.add(buildGalleryItem(itemElement));
				else if (tagName.equals("xml_exhibit"))
					items.add(buildXMLGalleryItem(itemElement));
				else if (tagName.equals("exhibit_group"))
					items.add(buildGalleryItemGroup(itemElement));
				else
					throw new IOException(I18n.tr("3dxm.galleries.error.WrongXML",tagName));
			}
		}
		GalleryItemList g = new GalleryItemList();
		g.nodeType = "gallery";
		g.name = name;
		g.title = title;
		g.items = items;
		String isDefault = galleryElement.getAttribute("default").trim();
		if (isDefault.equals("yes") || defaultGallery == null)
			defaultGallery = g;
		return g;
	}
	
	private GalleryItemList buildGalleryGroup(Element galleryGroupElement) throws IOException {
		String name = galleryGroupElement.getAttribute("name").trim();
		if (name.length() == 0)
			throw new IOException(I18n.tr("3dxm.galleries.error.MissingAttribute", "name", "gallery_group"));
		String title = galleryGroupElement.getAttribute("title").trim();
		if (title.length() == 0)
			title = I18n.tr(name);
		NodeList itemElements = galleryGroupElement.getChildNodes();
		int itemCount = itemElements.getLength();
		ArrayList<GalleryItem> galleries = new ArrayList<GalleryItem>();
		for (int i = 0; i < itemCount; i++) {
			if (itemElements.item(i) instanceof Element) {
				Element itemElement = (Element)itemElements.item(i);
				String tagName = itemElement.getTagName();
				if (tagName.equals("gallery"))
					galleries.add(buildGallery(itemElement));
				else if (tagName.equals("separator"))
					galleries.add(null);
				else
					throw new IOException(I18n.tr("3dxm.galleries.error.WrongXML",tagName));
			}
		}
		GalleryItemList g = new GalleryItemList();
		g.nodeType = "gallery_group";
		g.name = name;
		g.title = title;
		g.items = galleries;
		return g;
	}
	
	private ExhibitItem buildGalleryItem(Element exhibitElement) throws IOException {
		String classname = exhibitElement.getAttribute("class").trim();
		if (classname.length() == 0)
			throw new IOException(I18n.tr("3dxm.galleries.error.MissingAttribute", "class", "exhibit"));
		String name = exhibitElement.getAttribute("name").trim();
		if (name.length() == 0)
			name = classname;
		String title = exhibitElement.getAttribute("title").trim();
		if (title.length() == 0)
			title = I18n.tr(name);
		ExhibitItem g = new ExhibitItem();
		g.classname = classname;
		g.name = name;
		g.title = title;
		g.isXML = false;
		return g;
	}

	private ExhibitItem buildXMLGalleryItem(Element exhibitElement) throws IOException {
		String classname = exhibitElement.getAttribute("file").trim();
		if (classname.length() == 0)
			throw new IOException(I18n.tr("3dxm.galleries.error.MissingAttribute", "file", "xml_exhibit"));
		String name = exhibitElement.getAttribute("name").trim();
		if (name.length() == 0)
			name = classname;
		String title = exhibitElement.getAttribute("title").trim();
		if (title.length() == 0)
			title = I18n.tr(name);
		ExhibitItem g = new ExhibitItem();
		g.classname = classname;
		g.name = name;
		g.title = title;
		g.isXML = true;
		return g;
	}
	
	private GalleryItemList buildGalleryItemGroup(Element exhibitGroupElement) throws IOException {
		String name = exhibitGroupElement.getAttribute("name").trim();
		if (name.length() == 0)
			throw new IOException(I18n.tr("3dxm.galleries.error.MissingAttribute", "name", "exhibit_group"));
		String title = exhibitGroupElement.getAttribute("title").trim();
		if (title.length() == 0)
			title = I18n.tr(name);
		NodeList itemElements = exhibitGroupElement.getChildNodes();
		int itemCount = itemElements.getLength();
		ArrayList<GalleryItem> items = new ArrayList<GalleryItem>();
		for (int i = 0; i < itemCount; i++) {
			if (itemElements.item(i) instanceof Element) {
				Element itemElement = (Element)itemElements.item(i);
				String tagName = itemElement.getTagName();
				if (tagName.equals("exhibit"))
					items.add(buildGalleryItem(itemElement));
				else if (tagName.equals("separator"))
					items.add(null);
				else
					throw new IOException(I18n.tr("3dxm.galleries.error.WrongXML",tagName));
			}
		}
		GalleryItemList g = new GalleryItemList();
		g.nodeType = "exhibit_group";
		g.name = name;
		g.title = title;
		g.items = items;
		return g;		
	}
	
	//----- static methods for managing the documentation location -------
	
	private static String documentationLocation;
	private static TreeSet<String> documentationFileList;
	private static Menus waitingForDocInfo;
	private static int setDocLocationCount;
	
	synchronized static void setDocumentationLocation() {
		final String docLoc = Prefs.get("3dxm.docprefs.documentationBaseURL", 
				I18n.tr("3dxm.DocPrefsDialog.DefaultDocumentationLocation"));
		if (docLoc.equals(documentationLocation))
			return;
		documentationLocation = docLoc;
		documentationFileList = null;
		setDocLocationCount++;
		final int ct = setDocLocationCount;
		Thread reader = new Thread() {
			public void run() {
				TreeSet<String> files = new TreeSet<String>();
				try {
					URL url;
					if (docLoc.startsWith("http://")) {
						String doc;
						if (docLoc.endsWith("/"))
							doc = docLoc + "doclist.txt";
						else
							doc = docLoc + "/" + "doclist.txt";
						url = new URL(doc);
					}
					else {
						File docFile = new File(docLoc, "doclist.txt");
						url = docFile.getAbsoluteFile().toURI().toURL();
					}
					BufferedReader in = new BufferedReader(
							new InputStreamReader(url.openStream(), "ISO-8859-1"));
					while (true) {
						String line = in.readLine();
						if (line == null)
							break;
						files.add(line);
					}
					in.close();
				}
				catch (Exception e) {
					System.out.println("Error reading documentation file list.\n" + e);
				}
				finally {
					gotDocInfo(files,ct);
				}
			}
		};
		reader.start();
	}
	
	synchronized private static void gotDocInfo(TreeSet<String> files, int idCount) {
		if (idCount != setDocLocationCount)
			return;
		documentationFileList = files;
		if (waitingForDocInfo != null) {
			waitingForDocInfo.gotDocumnetationFileList(files);
			waitingForDocInfo = null;
		}
	}
	
	synchronized static boolean documentationFileExists(Menus requester, String fileName) {
		if (documentationFileList == null) {
			waitingForDocInfo = requester;
			return false;
		}
		return documentationFileList.contains(fileName);
	}
	
	static {
		setDocumentationLocation();
	}

}
