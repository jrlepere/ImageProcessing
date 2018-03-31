package transformations;
/**
 * Transformation Algorithm that takes an image in and outputs a transformed image.
 * @author JLepere2
 * @date 03/29/2018
 */
public interface TransformationAlgorithm {

	/**
	 * Transforms an integer matrix representing an image.
	 * @param image the image to transform.
	 * @return the new image.
	 */
	public int[][] transform(int[][] image);
	
	/**
	 * Shows the selection frame for the algorithm.
	 * @param visible true to set the frame visible, false otherwise.
	 */
	public void showSelectionFrame(boolean visible);
	
}
