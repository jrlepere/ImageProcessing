package transformations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main_frame.slider_component.SliderComponent;
import main_frame.slider_component.ValueLabelConversion;
import model.Model;

/**
 * High Boost Filter
 * @author JLepere2
 * @date 04/20/2018
 */
public class HighBoostFilter implements TransformationAlgorithm {
	
	/**
	 * Creates an object for high boost filter
	 * @param m the Model for MVC.
	 */
	public HighBoostFilter(Model m) {
		
		// frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: High Boost Filter");
		parameterSelectionFrame.setSize(PARAMETER_FRAME_WIDTH, PARAMETER_FRAME_HEIGHT);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setAlwaysOnTop(true);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setLayout(new GridLayout(3, 1));
		
		
		// mask size for average calculation
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
				averageMaskOffset = maskSizeSlider.getValue();
				m.algorithmParametersChanged();
			}
		});
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		for (int i = minValue; i <= maxValue; i ++) tickLabels.put(i, new JLabel(""+getMaskSize(i)));
		maskSizeSlider.setTicks(tickLabels);
		
		// k input
		double initialK = 1.0;
		JPanel setKPanel = new JPanel();
		setKPanel.setLayout(new BorderLayout());
		JLabel kLabel = new JLabel("k = ");
		kLabel.setHorizontalAlignment(JLabel.CENTER);
		kLabel.setPreferredSize(new Dimension(50, 50));
		setKPanel.add(kLabel, BorderLayout.WEST);
		JTextField kTextField = new JTextField(""+initialK);
		kTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					k = Double.parseDouble(kTextField.getText().trim());
					m.algorithmParametersChanged();
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "Not acceptable input for k.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setKPanel.add(kTextField, BorderLayout.CENTER);
		
		// information label
		JLabel infoLabel = new JLabel("g(x,y) = f(x,y) + k*[f(x,y) - f_bar(x,y)]");
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// add components to frame
		parameterSelectionFrame.add(infoLabel);
		parameterSelectionFrame.add(maskSizeSlider);
		parameterSelectionFrame.add(setKPanel);
		parameterSelectionFrame.pack();
		
		// initialize variables
		averageMaskOffset = initialValue;
		k = initialK;
		
	}
	
	public int[][] transform(int[][] image) {
		
		// size of the image
		int size = image.length;
		
		// holds the original image - the average within the mask
		int[][] tempImage = new int[size][size];
		
		// initialize temp
		for (int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// sum and count variables for calculating the average
				int sum = 0;
				int count = 0;
				
				// sum and count within the mask
				for (int yO = yN - averageMaskOffset; yO <= yN + averageMaskOffset; yO ++) {
					for (int xO = xN - averageMaskOffset; xO <= xN + averageMaskOffset; xO ++) {
						if (!(yO < 0 || yO >= size || xO < 0 || xO >= size)) {
							sum += image[yO][xO] & 0xff;
							count++;
						}
					}
				}
				
				// set temp
				tempImage[yN][xN] = (image[yN][xN] & 0xff) - (sum / count);
			}
		}
		
		// initialize new image g(x,y)
		int[][] newImage = new int[size][size];
		
		// set new Image
		for(int yN = 0; yN < size; yN ++) {
			for (int xN = 0; xN < size; xN ++) {
				
				// gv for new image
				int gv = (image[yN][xN] & 0xff) + (int) (k * tempImage[yN][xN]);
				
				// set in range
				if (gv > 255) gv = 255;
				if (gv < 0) gv = 0;
				
				// set new image rgb
				newImage[yN][xN] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
				
			}
		}
		
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	public String toString() {
		return "High Boost Filter";
	}
	
	/**
	 * Convert the offset to the size for the mask.
	 * @param offset 2 x offset + 1
	 */
	private int getMaskSize(int offset) {
		return 2 * offset + 1;
	}
	
	private int averageMaskOffset;
	private double k;
	private JFrame parameterSelectionFrame;
	private static final int PARAMETER_FRAME_WIDTH = 600;
	private static final int PARAMETER_FRAME_HEIGHT = 200;
	
}
