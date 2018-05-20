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
 * Harmonic Mean Filter
 * @author JLepere2
 * @date 05/20/2018
 */
public class HarmonicMeanFilter implements TransformationAlgorithm {
	
	/**
	 * Creates an object for harmonic mean filter
	 * @param m the Model for MVC.
	 */
	public HarmonicMeanFilter(Model m) {
		
		// frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: Medial Filter");
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
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// count number of true gray values in the mask
				int count = 0;
				
				// sum 1/gv for all gray values in the mask
				double sum = 0;
				
				// gray values in mask
				for (int yO = yN - maskOffset; yO <= yN + maskOffset; yO ++) {
					for (int xO = xN - maskOffset; xO <= xN + maskOffset; xO ++) {
						if (!(yO < 0 || yO >= size || xO < 0 || xO >= size)) {
							count ++;
							sum += (1.0/(image[yO][xO] & 0xff));
						}
					}
				}
				
				// set gv
				int gv;
				if (sum == 0.0) {
					gv = 0;
				} else {
					gv = (int) (count / sum);
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
		return "Harmonic Mean Filter";
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
