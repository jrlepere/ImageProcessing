package transformations;

import model.Model;

/**
 * Linear interpolation transform with respect to X and Y. 
 * @author JLepere2
 * @date 03/29/2018
 */
public class LinearInterpolationTransformation extends ScalingTransformation {

	/**
	 * Creates an object that defines the linear transformation.
	 * @param m the Model for MVC.
	 * @param respectX true to interpolate with respect to x, false with respect to y.
	 */
	public LinearInterpolationTransformation(Model m, boolean respectX) {
		super(m);
		respX = respectX;
	}
	
	public int[][] transform(int[][] image) {
		int[][] newImage = new int[zoomOutResolution][zoomOutResolution];
		
		double ratio = ((double) zoomOutResolution) / image.length;
		for (int y = 0; y < zoomOutResolution; y ++) {
			for (int x = 0; x < zoomOutResolution; x ++) {
				
				int newPixelValue;
				
				if (respX) {
					// Interpolate with respect to x
					
					double helper = x/ratio;
					int x1 = (int) helper;
					int x2 = getNextClosest(helper);
					if (x2 == -1 || x2 == image.length) x2 = x1;
					
					double weight = helper - x1;
					
					int oldY = (int) (y/ratio);
					int x1PixelValue = image[oldY][x1] & 0xff;
					int x2PixelValue = image[oldY][x2] & 0xff;
					newPixelValue = (int) ((x1PixelValue * (1.0 - weight)) + (x2PixelValue * weight));
					
				} else {
					// Interpolate with respect to y
					
					double helper = y/ratio;
					int y1 = (int) helper;
					int y2 = getNextClosest(helper);
					if (y2 == -1 || y2 == image.length) y2 = y1;
					
					double weight = helper - y1;
					
					int oldX = (int) (x/ratio);
					int y1PixelValue = image[y1][oldX] & 0xff;
					int y2PixelValue = image[y2][oldX] & 0xff;
					newPixelValue = (int) ((y1PixelValue * (1.0 - weight)) + (y2PixelValue * weight));
					
					}
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
		String s = "Linear Interpolation ";
		if (respX) s += "X";
		else s += "Y";
		return s;
	}
	
	private boolean respX;
	
}
