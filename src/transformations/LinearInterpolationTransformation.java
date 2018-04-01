package transformations;

import model.Model;

/**
 * A holder transformation algorithm that simply transforms the image to itself. 
 * @author JLepere2
 * @date 03/29/2018
 */
public class LinearInterpolationTransformation extends ScalingTransformation {

	public LinearInterpolationTransformation(Model m) {
		super(m);
	}
	
	public int[][] transform(int[][] image) {
		int[][] newImage = new int[zoomOutResolution][zoomOutResolution];
		
		double ratio = ((double) zoomOutResolution) / image.length;
		for (int y = 0; y < zoomOutResolution; y ++) {
			for (int x = 0; x < zoomOutResolution; x ++) {
				
				double helper = y/ratio;
				int y1 = (int) helper;
				int y2 = getNextClosest(helper);
				if (y2 == -1 || y2 == image.length) y2 = y1;
				
				/*
				 * Weight to apply to y1.
				 * (1 - weight) to apply to y2
				 */
				double weight = helper - y1;
				
				int oldX = (int) (x/ratio);
				int y1PixelValue = image[y1][oldX] & 0xff;
				int y2PixelValue = image[y2][oldX] & 0xff;
				int newPixelValue = (int) ((y1PixelValue * (1.0 - weight)) + (y2PixelValue * weight));
				newImage[y][x] = 0xff000000 + (newPixelValue << 16) + (newPixelValue << 8) + newPixelValue;
				
			}
		}
		
		return newImage;
	}
	
	private int getNextClosest(double p) {
		int pInt = (int) p;
		if ((p - pInt) >= 0.5) return pInt + 1;
		else return pInt - 1;
	}
	
	public String toString() {
		return "Linear Interpolation";
	}
	
}
