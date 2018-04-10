package transformations;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main_frame.SliderComponent;
import model.Model;

/**
 * Global histogram equalization algorithm for contrast enhancement.
 * @author JLepere2
 * @date 04/10/2018
 */
public class LocalHistogramEqualization implements TransformationAlgorithm {
	
	/**
	 * Creates an object for performing local histogram equalization
	 * @param m the Model for MVC.
	 */
	public LocalHistogramEqualization(Model m) {
		
		// frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: Local Histogram Equalization");
		parameterSelectionFrame.setSize(PARAMETER_FRAME_WIDTH, PARAMETER_FRAME_HEIGHT);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setAlwaysOnTop(true);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setLayout(new GridLayout(1, 1));
		
		// slider to set mask size
		int minValue = 1;
		int maxValue = 5;
		int initialValue = 1;
		SliderComponent maskSizeSlider = new SliderComponent(m, " Mask Offset: ", minValue, maxValue, initialValue);
		maskSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maskOffset = maskSizeSlider.getValue();
				m.algorithmParametersChanged();
			}
		});
		maskSizeSlider.setTicksAll();
		
		// add components to frame
		parameterSelectionFrame.add(maskSizeSlider);
		parameterSelectionFrame.pack();
		
		// initial mask offset
		maskOffset = initialValue;
		
	}
	
	public int[][] transform(int[][] image) {
		
		/*
		 * Variables:
		 * size - size of the image (width and height)
		 * gvCount - the gray scale [0, gvCount)
		 * maskOffset - the offset of the mask in the North, South, East and West direction from the center point
		 * maskSize - the size of the mask
		 */
		int size = image.length;
		int gvCount = (int) Math.pow(2, 8);
		//int maskOffset = 1;
		int maskSize = maskOffset * 2 + 1;
		
		// new image matrix declaration
		int[][] newImage = new int[size][size];
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// original gray value
				int oGV = image[yN][xN] & 0xff;
				
				// count of pixels less than or equals to original gray value
				int count = 0;   
				for (int yO = yN - maskOffset; yO <= yN + maskOffset; yO ++) {
					for (int xO = xN - maskOffset; xO <= xN + maskOffset; xO ++) {
						if (!(yO < 0 || yO >= size || xO < 0 || xO >= size))
							if ((image[yO][xO] & 0xff) <= oGV) count ++;
					}
				}
				
				// set new gray value
				int gv = ((gvCount-1) * count) / (maskSize * maskSize);
				newImage[yN][xN] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	public String toString() {
		return "Local Histrogram Equalization";
	}
	
	private JFrame parameterSelectionFrame;
	private int maskOffset;
	private static final int PARAMETER_FRAME_WIDTH = 600;
	private static final int PARAMETER_FRAME_HEIGHT = 200;
	
}
