/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * This class provides support for "Anaglyph Stereo" rendering.  Left- and right-eye views are drawn to separate
 * grayscale images.  Then these images are composed into a single RGB image in which the left-eye view becomes
 * the green color component, the right-eye view becomes the red color component, and the blue color component is
 * zero.  The resulting composite image can be viewed with red/green or red/blue stereo glasses.
 * (There should really be some way of doing all this directly in Java graphics, but support for this
 * type of composition does not seem to exist.)
 */
public class StereoComposite {
	
	private int width, height;
	
	private BufferedImage view;  // the composite image
	private BufferedImage leftEyeView;
	private BufferedImage rightEyeView;
	
	private int chunkSize; /* When the composition is done, this is how many pixels are processed in a batch.
							A large chunk size uses more memory, but a very small chunk size would be inefficient.
							The chunk size is set to a multiple of the width of the image, with a value not greater 
							than 10000*/
	
	private int[] leftSamples;   // These three arrays are used for doing the compositing.
	private int[] rightSamples;  // leftSamples and rightSamples are used to read pixels from the left and right views.

	private int[] viewInts;  // This is the actual memory for the composed view's WritableRaster.

	/**
	 * Sets the size of the image that is to be composed.  Buffered images are allocated for the left, right,
	 * and composed views as well as for some arrays that are used to do the compostion.  This method
	 * must be called before any drawing or compositing can be done.
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		chunkSize = 10000 / width;
		if (chunkSize < 1)
			chunkSize = 1;
		view = leftEyeView = rightEyeView = null;
		leftSamples = rightSamples = viewInts = null;
		try {
			leftEyeView = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
			rightEyeView = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
			view = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			leftSamples = new int[chunkSize*width];
			rightSamples = new int[chunkSize*width];
		}
		catch (OutOfMemoryError e) {
			view = leftEyeView = rightEyeView = null;
			leftSamples = rightSamples = null;
			throw e;
		}
		WritableRaster viewRaster = view.getRaster();
		DataBufferInt viewData = (DataBufferInt)viewRaster.getDataBuffer();
		viewInts = viewData.getData();
	}
	
	/**
	 * Returns the image width, as specified in {@link #setSize(int, int)}.  If <code>setSize</code>
	 * has not yet been called, the return value is 0.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the image height, as specified in {@link #setSize(int, int)}.  If <code>setSize</code>
	 * has not yet been called, the return value is 0.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Releases the memory allocated by {@link #setSize(int, int)}.  The width and the height are also reset to 0.
	 */
	public void releaseMemory() {
		view = leftEyeView = rightEyeView = null;
		leftSamples = rightSamples = viewInts = null;
		width = height = 0;
	}
	
	/**
	 * Returns a newly created graphics context for drawing into the left eye view.  The {@link #setSize(int, int)} method
	 * must already have been called to allocate memory for the views.  If <code>setSize</code> has not been
	 * called, the return value is null.
	 */
	public Graphics2D getLeftEyeGraphics() {
		return leftEyeView == null? null : (Graphics2D)leftEyeView.getGraphics(); 
	}
	
	/**
	 * Returns a newly created graphics context for drawing into the right eye view.  The {@link #setSize(int, int)} method
	 * must already have been called to allocate memory for the views.  If <code>setSize</code> has not been
	 * called, the return value is null.
	 */
	public Graphics2D getRightEyeGraphics() {
		return rightEyeView == null? null : (Graphics2D)rightEyeView.getGraphics(); 
	}
	
	/**
	 * Returns the BufferedImage where the left-eye view is rendered.  If <code>setSize</code> has not been
	 * called, the return value is null.
	 */
	public BufferedImage getLeftEyeImage() {
		return leftEyeView;
	}
	
	/**
	 * Returns the BufferedImage where the right view is rendered.  If <code>setSize</code> has not been
	 * called, the return value is null.
	 */
	public BufferedImage getRightEyeImage() {
		return rightEyeView;
	}
	
	/**
	 * Returns the BufferedImage that contains the composed view.  Note that {@link #compose()} must
	 * be called in order to combine the left- and righ-eye views into the composite image -- this is
	 * <b>not</b> done automatically.  The return value can be null if {@link #setSize(int, int)} has
	 * not been called to allocate memory.
	 */
	public BufferedImage getImage() {  // does not do compose -- call compose() before using the image.
		return view;  // can be null
	}
	
	public void compose() {
//		long start = System.currentTimeMillis();
		Raster leftRaster = leftEyeView.getData();
		Raster rightRaster = rightEyeView.getData();
		int row = 0;
		while (row < height) {
			int rowCount = chunkSize;
			if (row + rowCount > height)
				rowCount = height - row;
			int sampleCount = rowCount*width;
			int firstIndex = row*width;
			leftRaster.getSamples(0,row,width,rowCount,0,leftSamples);
			rightRaster.getSamples(0,row,width,rowCount,0,rightSamples);
			for (int i = 0; i < sampleCount; i++)
				viewInts[i + firstIndex] = (rightSamples[i] << 16) + (leftSamples[i] << 8);
			row += chunkSize;
		}
//		long time = System.currentTimeMillis() - start;
//		System.out.println("Compsition took " + time / 1000.0 + " seconds for " + (width*height) + " pixels");
	}
	

	
//	public void compose_old() {
//		long start = System.currentTimeMillis();
//		Raster leftRaster = leftEyeView.getData();
//		DataBufferByte leftData = (DataBufferByte)leftRaster.getDataBuffer();
//		byte[] leftBytes = leftData.getData();
//		Raster rightRaster = rightEyeView.getData();
//		DataBufferByte rightData = (DataBufferByte)rightRaster.getDataBuffer();
//		byte[] rightBytes = rightData.getData();
//		WritableRaster viewRaster = view.getRaster();
//		DataBufferInt viewData = (DataBufferInt)viewRaster.getDataBuffer();
//		int[] viewInts = viewData.getData();
//		for (int i = 0; i < viewInts.length; i++) {
//			int a = leftBytes[i];
//			int b = rightBytes[i];
//			a &= 0xFF;
//			b &= 0xFF;
//			viewInts[i] = (a << 16) + (b << 8);
//		}
//		long time = System.currentTimeMillis() - start;
//		System.out.println("Compsition took " + time / 1000.0 + " seconds for " + (width*height) + " pixels");
//	}
	
	

}
