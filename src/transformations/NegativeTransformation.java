package transformations;

/**
 * Negative image transformation.
 * @author JLepere2
 * @date 04/04/2018
 */
public class NegativeTransformation implements TransformationAlgorithm {
	
	public int[][] transform(int[][] image) {
		
		// the resolution of the image.
		int resolution = image.length;
		
		// the new image to invert the pixels
		int[][] newImage = new int[resolution][resolution];
		
		// set the new image
		for (int y = 0; y < resolution; y ++) {
			for (int x = 0; x < resolution; x ++) {
				int pixelValue = 255 - image[y][x] & 0xff;
				newImage[y][x] = 0xff000000 + (pixelValue << 16) + (pixelValue << 8) + pixelValue;
			}
		}
		
		return newImage;
	}
	
	public String toString() {
		return "Negative";
	}

	public void showSelectionFrame(boolean visible) {}

}
