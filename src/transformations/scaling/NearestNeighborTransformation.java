package transformations.scaling;

import model.Model;

/**
 * Scales the image using the nearest neighbor technique.
 * @author JLepere2
 * @date 30/31/2018
 */
public class NearestNeighborTransformation extends ScalingTransformation {

	/**
	 * Creates a nearest neighbor transformation object for scaling an image.
	 */
	public NearestNeighborTransformation(Model m) {
		super(m);
	}

	public int[][] transform(int[][] image) {
		
		// scale down the image before scaling back up
		image = scaleDown(image);
		
		// new image pixel matrix
		int[][] newImage = new int[zoomOutResolution][zoomOutResolution];
		
		// ratio to scale up
		double ratio = ((double) zoomOutResolution) / image.length;
		
		// set new pixel matrix
		for (int y = 0; y < zoomOutResolution; y ++) {
			for (int x = 0; x < zoomOutResolution; x ++) {
				newImage[y][x] = image[(int) (y/ratio)][(int) (x/ratio)];
			}
		}
		
		return newImage;
	}
	
	public String toString() {
		return "Nearest Neighbor";
	}
	
}
