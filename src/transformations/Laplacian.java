package transformations;

import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main_frame.slider_component.SliderComponent;
import main_frame.slider_component.ValueLabelConversion;
import model.Model;

/**
 * Laplacian smoothing filer.
 * @author JLepere2
 * @date 04/26/2018
 */
public class Laplacian implements TransformationAlgorithm {
	
	/**
	 * Creates an object for performing Laplacian smoothing.
	 * @param m the model for MVC
	 */
	public Laplacian(Model m) {
		
		// frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: Laplacian Filter");
		parameterSelectionFrame.setSize(PARAMETER_FRAME_WIDTH, PARAMETER_FRAME_HEIGHT);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setAlwaysOnTop(true);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setLayout(new GridLayout(1, 1));
		
		// slider to set mask size
		int minValue = 1;
		int maxValue = 4;
		int initialValue = 1;
		SliderComponent maskSizeSlider = new SliderComponent(m, " Mask Size (NxN): ", minValue, maxValue, initialValue, new ValueLabelConversion() {
			public int convertForPresentation(int sliderValue) {
				return getMaskSize(sliderValue);
			}
		});
		maskSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maskOffset = maskSizeSlider.getValue();
				m.algorithmParametersChanged();
			}
		});
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		for (int i = minValue; i <= maxValue; i ++) tickLabels.put(i, new JLabel(""+getMaskSize(i)));
		maskSizeSlider.setTicks(tickLabels);
		
		// add components to frame
		parameterSelectionFrame.add(maskSizeSlider);
		parameterSelectionFrame.pack();
		
		// initial mask offset
		maskOffset = initialValue;
		
	}
	
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
				
				// new gv
				int newGV = 0;
				
				// get center multiple
				int centerMultiple = -1 * ((int) Math.pow(2 * maskOffset + 1, 2) - 1);
				
				// calculate the new gv
				for (int yO = yN - maskOffset; yO <= yN + maskOffset; yO ++) {
					for (int xO = xN - maskOffset; xO <= xN + maskOffset; xO ++) {
						if (!(yO < 0 || yO >= size || xO < 0 || xO >= size)) {
							int gv = (image[yO][xO] & 0xff);
							if ((yO == yN) && (xO == xN)) {
								newGV += centerMultiple * gv;
							} else {
								newGV += gv;
							}
						}
					}
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

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	public String toString() {
		return "Laplacian Smoothing";
	}
	
	/**
	 * Convert the offset to the size for the mask.
	 * @param offset 2 x offset + 1
	 */
	private int getMaskSize(int offset) {
		return 2 * offset + 1;
	}
	
	private JFrame parameterSelectionFrame;
	private int maskOffset;
	private static final int PARAMETER_FRAME_WIDTH = 600;
	private static final int PARAMETER_FRAME_HEIGHT = 200;
	
}
