import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The Model component for MVC design pattern.
 * @author JLepere2
 * @date 03/29/2018
 */
public class Model {

	/**
	 * Creates a Model object for MVC design pattern.
	 * Contains:
	 *  - Transformation algorithm.
	 *  - Spatial resolution.
	 *  - Bit precision.
	 */
	public Model() {
		listeners = new LinkedList<>();
		pixelMatrix = new int[512][512];
		algo = new DefaultAlgorithm();
		spatialResolution = 512;   // TODO: un hard code??
		bitPrecision = 8;   // TODO: un hard code??
	}
	
	/**
	 * Attaches a listener to the model.
	 * @param l the listener.
	 */
	public void attachListener(ChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 * Sets the pixel matrix.
	 * @param newPixelMatrix the new pixel matrix.
	 */
	public void setPixelMatrix(int[][] newPixelMatrix) {
		pixelMatrix = newPixelMatrix;
		alert();
	}
	
	/**
	 * Sets the transformation algorithm.
	 * @param newAlgo the new transformation algorithm.
	 */
	public void setAlgorithm(TransformationAlgorithm newAlgo) {
		algo = newAlgo;
		alert();
	}
	
	/**
	 * Sets the spatial resolution.
	 * @param newSpatialResolution the new spatial resolution.
	 */
	public void setSpatialResolution(int newSpatialResolution) {
		spatialResolution = newSpatialResolution;
		alert();
	}
	
	/**
	 * Sets the bit precision.
	 * @param newBitPrecision the new bit precision.
	 */
	public void setBitPrecision(int newBitPrecision) {
		bitPrecision = newBitPrecision;
		alert();
	}
	
	/**
	 * Gets the transformed image.
	 * @return the transformed image.
	 */
	public int[][] getTransformation() {
		
		//int[][] temp = new int[pixelMatrix.length][pixelMatrix[0].length];
		int[][] temp = new int[spatialResolution][spatialResolution];
		
		// Contization
		int bitPrecisionBlockSize = (int) Math.pow(2, 8 - bitPrecision);
		int bitPrecisionValueMappingRatio = (255 / ((int) Math.pow(2, bitPrecision) - 1));
		double spatialResolutionRatio = 512.0 / spatialResolution;
		for (int y = 0; y < temp.length; y ++) {
			for (int x = 0; x < temp[0].length; x ++) {
				int oRGB = pixelMatrix[(int) (y * spatialResolutionRatio)][(int) (x * spatialResolutionRatio)];
				int oGrayScale = oRGB & 0xff;
				int nGrayScale = (oGrayScale / bitPrecisionBlockSize) * bitPrecisionValueMappingRatio;
				temp[y][x] = 0xff000000 + (nGrayScale << 16) + (nGrayScale << 8) + nGrayScale;
			}
		}
		
		return algo.transform(temp);
	}
	
	/**
	 * Alert the listeners of a change.
	 */
	public void alert() {
		ChangeEvent e = new ChangeEvent(this);
		for (ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	private List<ChangeListener> listeners;
	private int[][] pixelMatrix;
	private TransformationAlgorithm algo;
	private int spatialResolution;
	private int bitPrecision;
	
}
