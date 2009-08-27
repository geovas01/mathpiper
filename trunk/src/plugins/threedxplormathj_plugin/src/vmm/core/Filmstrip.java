/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A Filmstrip is just a sequence of images.  It is possible for some of
 * the images in the Filmstrip to be null.  The images are numbered starting
 * at zero.  There is no preset limit on the number of images.
 */
public class Filmstrip {
	
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	/**
	 * Attempts to determine how many filmstrip frames an still be created with a specified size.  This
	 * is not necessarily going to give an exact value; the value is likely to be an underestimate.
	 * @see Util#availableMemory()
	 * @param width the width in pixels of the images
	 * @param height the height in pixels of the images
	 * @param color are the images to be colored (4 bytes per pixel) or black and white (1 byte per pixel)?
	 */
	public static int maxFrames(int width, int height, boolean color) {
		long mem = Util.availableMemory() - 3000000;
		long bytesPerFrame = 1000 + (width * height * (color? 4 : 1));
		int frames =(int) (mem / bytesPerFrame);
		if (frames < 2)
			frames = 0;
		return frames;
	}
	
	/**
	 *  Sets a specified frame in the filmstrip to a given image.
	 *  If frameNumber is less than the current filmstrip size, then
	 *  the current frame at position frameNumber is replaced. Otherwise
	 *  the size of the filmstrip is increased to (frameNumber+1) and
	 *  the image is then stored in position frameNumber as the last
	 *  frame in the filmstrip.  Any extra locations that are created
	 *  are filled with nulls.
	 *  @param frameNumber The position in the filmstrip where the image is to be added.
	 *  An error occurs if this is less than zero.
	 *  @param image The image that is to be put at postion frameNumber in the filmstrip.
	 *  This can be null.
	 */
	public void setFrame(int frameNumber, BufferedImage image) {
		if (frameNumber < images.size())
			images.set(frameNumber,image);
		else {
			while (images.size() < frameNumber-1)
				images.add(null);
			images.add(image);
		}
	}
	
	/**
	 * Returns the current number of frames in the filmstrip.
	 */
	public int getFrameCount() {
		return images.size();
	}
	
	/**
	 * Returns the image at at a specified position in the filmstrip.  The return value
	 * can be null.  
	 * @param frameNumber The number of the frame that is to be returned.  An error occurs
	 * if this is not greater than or equal to zero and less than {@link #getFrameCount()}.
	 */
	public BufferedImage getFrame(int frameNumber) {
		return images.get(frameNumber);
	}
	
	/**
	 * Removes any frames for which the image is null from the filmstrip.
	 */
	public void stripNullFrames() {
		for (int i = images.size() - 1; i >= 0; i--)
			if (images.get(i) == null)
				images.remove(i);
	}

}
