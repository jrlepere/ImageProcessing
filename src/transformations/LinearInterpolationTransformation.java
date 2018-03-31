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
		int[][] newImage = new int[image.length][image[0].length];
		for (int r = 0; r < image.length; r ++) {
			for (int c = 0; c < image[0].length; c ++) {
				newImage[r][c] = image[r][c];
			}
		}
		return newImage;
	}
	
	public String toString() {
		return "Linear Interpolation";
	}
	
}
