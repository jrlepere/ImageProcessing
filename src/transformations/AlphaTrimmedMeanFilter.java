package transformations;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main_frame.slider_component.NoValueConversion;
import main_frame.slider_component.SliderComponent;
import main_frame.slider_component.ValueLabelConversion;
import model.Model;

/**
 * Alpha Trimmed Mean Filter
 * @author JLepere2
 * @date 05/20/2018
 */
public class AlphaTrimmedMeanFilter implements TransformationAlgorithm {
	
	/**
	 * Creates an object for alpha trimmed mean filter
	 * @param m the Model for MVC.
	 */
	public AlphaTrimmedMeanFilter(Model m) {
		
		// frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: Medial Filter");
		parameterSelectionFrame.setSize(PARAMETER_FRAME_WIDTH, PARAMETER_FRAME_HEIGHT);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setAlwaysOnTop(true);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setLayout(new GridLayout(2, 1));
		
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
		
		// trimmed
		SliderComponent trimSlider = new SliderComponent(m, " Trim: ", 0, 20, 0, new NoValueConversion());
		trimSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				trim = trimSlider.getValue();
				m.algorithmParametersChanged();
			}
		});
		
		// add components to frame
		parameterSelectionFrame.add(maskSizeSlider);
		parameterSelectionFrame.add(trimSlider);
		parameterSelectionFrame.pack();
		
		// initial mask offset
		maskOffset = initialValue;
		trim = 0;
		
	}
	
	public int[][] transform(int[][] image) {
		
		// size of the image
		int size = image.length;
		// holder for gray values
		int[] grayValuesHolder = new int[getMaskSize(maskOffset) * getMaskSize(maskOffset)];
		
		// new image matrix declaration
		int[][] newImage = new int[size][size];
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// count number of true gray values in the mask
				int count = 0;
				
				// add gray values in mask to list
				for (int yO = yN - maskOffset; yO <= yN + maskOffset; yO ++) {
					for (int xO = xN - maskOffset; xO <= xN + maskOffset; xO ++) {
						if (!(yO < 0 || yO >= size || xO < 0 || xO >= size)) {
							grayValuesHolder[count] = image[yO][xO] & 0xff;
							count++;
						}
					}
				}
				
				// extract gray values
				int[] grayValues = Arrays.copyOfRange(grayValuesHolder, 0, count);
				
				// sort
				Arrays.sort(grayValues);
				
				// sum
				int sum = 0;
				for (int i = trim/2; i < grayValues.length-trim/2; i ++) {
					sum += grayValues[i];
				}
				
				// get new gv
				int gv;
				if (grayValues.length-trim <= 0) {
					gv = 0;
				} else {
					gv = sum/(grayValues.length-trim);
				}
				
				// set new image
				newImage[yN][xN] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	public String toString() {
		return "Alpha Trimmed Mean Filter";
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
	private int trim;
	private static final int PARAMETER_FRAME_WIDTH = 600;
	private static final int PARAMETER_FRAME_HEIGHT = 200;
	
}
