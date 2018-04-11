package model;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main_frame.menu.BitPlaneMenu;
import transformations.TransformationAlgorithm;

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
		spatialResolution = 512;   // TODO: un hard code??
		bitPrecision = 8;   // TODO: un hard code??
		bitPlaneMask = BitPlaneMenu.INITIAL_PLANE_VALUE;
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
		if (algo != null) algo.showSelectionFrame(false);
		algo = newAlgo;
		algo.showSelectionFrame(true);
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
	 * Set the bit plane.
	 * @param newBitPlaneMask the bit plane.
	 */
	public void setBitPlane(int newBitPlaneMask) {
		bitPlaneMask = newBitPlaneMask;
		alert();
	}
	
	/**
	 * Alert the model that the algorithm specific parameters changed.
	 */
	public void algorithmParametersChanged() {
		alert();
	}
	
	/**
	 * Gets the transformed image.
	 * @return the transformed image.
	 */
	public int[][] getTransformation() {
		
		// temporary matrix for converting to selected bit precision
		int[][] temp = new int[LOADED_PICTURE_RESOLUTION][LOADED_PICTURE_RESOLUTION];
		
		// set new values
		int bitPrecisionBlockSize = (int) Math.pow(2, 8 - bitPrecision);
		double bitPrecisionValueMappingRatio = (255.0 / (Math.pow(2, bitPrecision) - 1));
		for (int y = 0; y < LOADED_PICTURE_RESOLUTION; y ++) {
			for (int x = 0; x < LOADED_PICTURE_RESOLUTION; x ++) {
				int oGrayScale = pixelMatrix[y][x] & 0xff;
				int nGrayScale = (int) (((int) (oGrayScale / bitPrecisionBlockSize)) * bitPrecisionValueMappingRatio);
				temp[y][x] = 0xff000000 + (nGrayScale << 16) + (nGrayScale << 8) + nGrayScale;
			}
		}
		
		// get transformation of new algorithm with bit precision applied
		temp = algo.transform(temp);
		
		// perform mask
		if (bitPlaneMask != BitPlaneMenu.BIT_PLANE_ALL_VALUE) {
			int newImageSize = temp.length;
			for (int y = 0; y < newImageSize; y ++) {
				for (int x = 0; x < newImageSize; x ++) {
					
					// original gray value
					int gv = (temp[y][x] & 0xff);
					
					// mask gv by selected bit plane
					gv = gv & (0x01 << (bitPlaneMask - 1));
					
					// set so white and black
					if (gv != 0) gv = 0xff;
					
					// set new gv
					temp[y][x] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
					
				}
			}
		}
		
		return temp;
	}
	
	/**
	 * Gets the current spatial resolution.
	 * @return the spatial resolution.
	 */
	public int getSpatialResolution() {
		return spatialResolution;
	}
	
	/**
	 * Gets the current bit precision.
	 * @return the bit precision.
	 */
	public int getBitPrecision() {
		return bitPrecision;
	}
	
	/**
	 * Gets the loaded pixel matrix.
	 * @return the loaded pixel matrix.
	 */
	public int[][] getPixelMatrix() {
		return pixelMatrix;
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
	private int bitPlaneMask;
	public static final int LOADED_PICTURE_RESOLUTION = 512;
	public static final int MAX_RESOLUTION = 512;
	public static final int MIN_RESOLUTION = 32;
	
}
