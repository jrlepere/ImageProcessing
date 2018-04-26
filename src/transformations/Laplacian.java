package transformations;

/**
 * Laplacian smoothing filer.
 * @author JLepere2
 * @date 04/26/2018
 */
public class Laplacian implements TransformationAlgorithm {
	
	/**
	 * Creates an object for performing Laplacian smoothing.
	 */
	public Laplacian() {}
	
	public int[][] transform(int[][] image) {
		
		// size of the image
		int size = image.length;
		
		// new image matrix declaration
		int[][] newImage = new int[size][size];
		
		// min and max value holders for resizing
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		// set the newImage, before rescaling.
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// center
				int newGV = (-4) * (image[yN][xN] & 0xff);
				
				// west
				if (xN - 1 >= 0) {
					newGV += image[yN][xN-1];
				}
				
				// east
				if (xN + 1 < size) {
					newGV += image[yN][xN+1];
				}
				
				// north
				if (yN - 1 >= 0) {
					newGV += image[yN-1][xN];
				}
				
				// south
				if (yN + 1 < size) {
					newGV += image[yN+1][xN];
				}
				
				// set min and max
				if (newGV < min) min = newGV;
				if (newGV > max) max = newGV;
				
				// set new image
				// note that these images need to be normalized to 8 bit.
				newImage[yN][xN] = newGV;
			}
		}
		
		// max after subtracting min from each value, used for scaling
		int newMax = max - min;
		
		// scaling multiplication factor
		double scalingFactor = 255.0 / newMax;
		
		// scale the new image to 8 bit
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// get gray value subtract min
				int gv = newImage[yN][xN] - min;
				
				// scale to 8 bit
				int newGV = (int) (gv * scalingFactor);
				
				// set rgb*
				newImage[yN][xN] = 0xff000000 + (newGV << 16) + (newGV << 8) + newGV;
				
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {}
	
	public String toString() {
		return "Laplacian Smoothing";
	}
	
}
