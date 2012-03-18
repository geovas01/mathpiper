/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vmm.core.Display;
import vmm.core.Filmstrip;
import vmm.core.I18n;
import vmm.core.TimerAnimation;

class SavedAnimationSupport {
	
	private static class ProgressDialog extends JDialog implements ActionListener {
		JLabel message = new JLabel();
		volatile boolean canceled;
		ProgressDialog(Frame parent, String initialMessage) {
			super(parent, I18n.tr("3dxm.AnimationSupport.SavingAnimation"), true);
			JPanel panel = new JPanel();
			message = new JLabel(initialMessage + "   ");
			panel.add(message);
			JButton button = new JButton(I18n.tr("buttonNames.Cancel"));
			button.addActionListener(this);
			panel.add(button);
			setContentPane(panel);
			pack();
		}
		void setNote(String str) {
			message.setText(str);
		}
		public void actionPerformed(ActionEvent e) {
			canceled = true;
			dispose();
		}
	}
	
	
	private static class FrameSaver extends Thread {
		String format;
		BufferedImage[] frames;
		ZipOutputStream out;
		ProgressDialog progressDialog;
		volatile boolean canceled = true;
		volatile String errorMessage;
		FrameSaver(String format, BufferedImage[] frames, ZipOutputStream out, ProgressDialog dialog) {
			this.format = format;
			this.frames = frames;
			this.out = out;
			this.progressDialog = dialog;
		}
		public void run() {
			try {
				for (int i = 0; i < frames.length; i++) {
					try {
						Thread.sleep(50);
					}
					catch (InterruptedException e) {
					}
					ZipEntry frameEntry = new ZipEntry("frame" + i + "." + format.toLowerCase());
					try {
						out.putNextEntry(frameEntry);
						if (ImageIO.write(frames[i], format, out) == false)
							throw new IOException();
						out.closeEntry();
					}
					catch (IOException e) {
						errorMessage = I18n.tr("3dxm.AnimationSupport.CantWriteFile");
						canceled = false;
						return;
					}
					if (progressDialog.canceled) {
						return;
					}
					if (i < frames.length-1)
						progressDialog.setNote(I18n.tr("3dxm.AnimationSupport.SavingFrameNumber",""+(i+2),""+frames.length));
				}
				try {
					Thread.sleep(50);
				} 
				catch (InterruptedException e1) {
				}
				try {
					out.close();
				}
				catch (IOException e) {
					errorMessage = I18n.tr("3dxm.AnimationSupport.CantWriteFile");
				}
				canceled = false;
			}
			finally {
				if (!progressDialog.canceled)
					progressDialog.dispose();
 			}
		}
	}
	
	
	static boolean saveAnimation(Component dialogParent, OutputStream outputStream, Filmstrip filmstrip, 
			boolean cyclic, String format) throws IOException {
		filmstrip.stripNullFrames();
		if (filmstrip.getFrameCount() < 2)
			throw new IOException(I18n.tr("3dxm.AnimationSupport.NoFrames"));
		BufferedImage[] frames = new BufferedImage[filmstrip.getFrameCount()];
		for (int i = 0; i < filmstrip.getFrameCount(); i++)
			frames[i] = filmstrip.getFrame(i);
		ZipOutputStream out = new ZipOutputStream( outputStream );
		StringBuffer buffer = new StringBuffer();
		buffer.append("3dxm_animation_file_format_version=1\n");
		buffer.append("format=" + format + "\n");
		buffer.append("frames=" + filmstrip.getFrameCount() + "\n");
		buffer.append("width=" + frames[0].getWidth() + "\n");
		buffer.append("height=" + frames[0].getHeight() + "\n");
		buffer.append("cyclic=" + cyclic + "\n");
		String metaString = buffer.toString();
		ZipEntry metaData = new ZipEntry("metadata.txt");
		out.putNextEntry(metaData);
		PrintWriter writer = new PrintWriter(out);
		writer.print(metaString);
		writer.flush();
		out.closeEntry();
		while (dialogParent != null && !(dialogParent instanceof Frame))
			dialogParent = dialogParent.getParent();
		ProgressDialog progressDialog = new ProgressDialog((Frame)dialogParent,
				I18n.tr("3dxm.AnimationSupport.SavingFrameNumber","1",""+frames.length));
		progressDialog.setLocation( dialogParent.getX() + 50, dialogParent.getY() + 50 );
		FrameSaver saver = new FrameSaver(format,frames,out,progressDialog);
		saver.start();
		progressDialog.setVisible(true);
		if (saver.errorMessage != null)
			throw new IOException(saver.errorMessage);
		return !saver.canceled;
	}
	
	
	static void readAndPlay(Display display, ZipFile zipFile) throws IOException {
		display.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String format;
		int frames, width, height, looping;
		try {
			ZipEntry meta = zipFile.getEntry("metadata.txt");
			if (meta == null)
				throw new Exception();
			Properties metaData = new Properties();
			metaData.load( zipFile.getInputStream(meta) );
			if (metaData.get("3dxm_animation_file_format_version") == null) 
				throw new Exception();
			if ("JPEG".equalsIgnoreCase((String)metaData.get("format")))
				format = "JPEG";
			else if ("PNG".equalsIgnoreCase((String)metaData.get("format")))
				format = "PNG";
			else
				throw new Exception();
			frames = Integer.parseInt((String)metaData.get("frames"));
			if (frames < 2)
				throw new Exception();
			width = Integer.parseInt((String)metaData.get("width"));
			if (width <= 0)
				throw new Exception();
			height = Integer.parseInt((String)metaData.get("height"));
			if (height <= 0)
				throw new Exception();
			if (metaData.get("cyclic").equals("true"))
				looping = TimerAnimation.LOOP;
			else
				looping = TimerAnimation.OSCILLATE;
		}
		catch (Exception e) {
			display.setCursor(Cursor.getDefaultCursor());
			throw new IOException(I18n.tr("3dxm.AnimationSupport.BadAnimationFileFormat"));
		}
		display.discardFilmstrip();
		Filmstrip filmstrip = new Filmstrip();
		Component parent = display;
		while (parent != null && !(parent instanceof Frame))
			parent = parent.getParent();
		try {
			for (int i = 0; i < frames; i++) {
				display.setStatusText(I18n.tr("3dxm.AnimationSupport.status.ReadingFrameFromFile",i+1));
				ZipEntry frameEntry = zipFile.getEntry("frame" + i + "." + format.toLowerCase());
				if (frameEntry == null)
					throw new Exception();
				InputStream in = zipFile.getInputStream(frameEntry);
				try {
					BufferedImage frame = ImageIO.read(in);
					if (frame == null)
						throw new Exception();
					filmstrip.setFrame(i,frame);
				}
				catch (OutOfMemoryError e) {
					if (i < 4) {
						filmstrip = null;
						JOptionPane.showMessageDialog(display,I18n.tr("3dxm.AnimationSupport.OutOfMemoryCantPlay"),
								I18n.tr("3dxm.dialog.errormessage.title"),JOptionPane.ERROR_MESSAGE);
						display.setCursor(Cursor.getDefaultCursor());
						display.setStatusText();
						return;
					}
					filmstrip.setFrame(i-1,null);  // discard previous frame to free up some memory!
					if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(display, 
							I18n.tr("3dxm.AnimationSupport.OutOfMemoryDialogQuestion",i-1,frames), 
							I18n.tr("3dxm.AnimationSupport.OutOfMemoryDialogTitle"), 
							JOptionPane.YES_NO_OPTION)) {
						filmstrip.stripNullFrames();
						break;
					}
					else {
						display.setCursor(Cursor.getDefaultCursor());
						display.setStatusText();
						return;
					}
				}
				in.close();
			}
		}
		catch (Exception e) {
			display.setCursor(Cursor.getDefaultCursor());
			display.setStatusText();
			throw new IOException(I18n.tr("3dxm.AnimationSupport.ErrorWhileReadingAnimationFrames"));
		}
		display.playFilmstrip(filmstrip, looping, new Dimension(width,height));
	}

}
