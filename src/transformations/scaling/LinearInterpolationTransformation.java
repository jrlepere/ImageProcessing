package transformations.scaling;

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
		
		// scale down the image before scaling back up
		image = scaleDown(image);
		
		// new image pixel matrix
		int[][] newImage = new int[zoomOutResolution][zoomOutResolution];
		
		// set the new pixel matrix
		for (int y = 0; y < zoomOutResolution; y ++) {
			for (int x = 0; x < zoomOutResolution; x ++) {
				newImage[y][x] = getNewPixelValue(x, y, zoomOutResolution, image);
			}
		}
		
		return newImage;
	}
	

	private int getNewPixelValue(int newX, int newY, int zoomOutResolution, int[][] image) {
		
		/*
		 * https://en.wikipedia.org/wiki/Bilinear_interpolation
		 * 
		 *       X1                 X2
		 *       |                  | 
		 * y2 - Q12 ----- R2 ----- Q22
		 *       |        |         |
		 *       |        |         |
		 *       -------- P --------
		 *       |        |         |
		 *       |        |         |
		 * y1 - Q11 ----- R1 ----- Q21
		 * 
		 * P = (x,y)
		 */
		
		int zoomInResolution = image.length;
		double zoomRatio = zoomInResolution / ((double) zoomOutResolution);
		
		double x = (newX * zoomRatio);
		double y = (newY * zoomRatio);
		
		int x1 = (int) (x);
		int x2 = (x1 + 1 == zoomInResolution) ? x1 : x1 + 1;
		
		int y1 = (int) (y);
		int y2 = (y1 + 1 == zoomInResolution) ? y1 : y1 + 1;
		
		int newPixelValue;
		
		if (respX && x1 != x2) {
			// Only interpolate in X
			
			double q11_f = (image[y1][x1] & 0xff) * (x2 - x);
			double q21_f = (image[y1][x2] & 0xff) * (x - x1);
			
			newPixelValue = (int) (1.0/((x2 - x1)) * (q11_f + q21_f));
			
		} else if (!respX && y1 != y2) {
			// Only interpolate in y
			
			double q11_f = (image[y1][x1] & 0xff) * (y2 - y);
			double q12_f = (image[y2][x1] & 0xff) * (y - y1);
			
			newPixelValue = (int) (1.0/((y2 - y1)) * (q11_f + q12_f));
			
		} else {
			// No interpolation
			
			newPixelValue = image[y1][x1] & 0xff;
			
		}
		
		return 0xff000000 + (newPixelValue << 16) + (newPixelValue << 8) + newPixelValue;
		
	}
	
	public String toString() {
		String s = "Linear Interpolation ";
		if (respX) s += "X";
		else s += "Y";
		return s;
	}
	
	private boolean respX;
	
}
