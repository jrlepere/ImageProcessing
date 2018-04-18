package transformations;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
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
 * A class for defining smoothing transformations on an image.
 * @author JLepere2
 * @date 04/18/2017
 */
public class Smoothing implements TransformationAlgorithm {

	public Smoothing(Model m) {
		
		// parameter selection frame initialization
		final int FRAME_WIDTH = 350;
		final int FRAME_HEIGHT = 450;
		parameterSelectionFrame = new JFrame("Smoothing");
		parameterSelectionFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		parameterSelectionFrame.setLayout(new BorderLayout());
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setAlwaysOnTop(true);
		
		// smoothing matrix
		final int DEFAULT_MASK_OFFSET = 1;
		setSmoothingMatrixInput(offsetSizeConversion(DEFAULT_MASK_OFFSET));
		
		// mask table
		maskTable = new JPanel();
		setMaskTable();
		
		// mask slider
		final int MIN_MASK_OFFSET = 1;
		final int MAX_MASK_OFFSET = 4;
		SliderComponent maskSlider = new SliderComponent(m, "Mask Size", MIN_MASK_OFFSET, MAX_MASK_OFFSET, DEFAULT_MASK_OFFSET, new ValueLabelConversion() {
			public int convertForPresentation(int sliderValue) {
				return offsetSizeConversion(sliderValue);
			}
		});
		maskSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int maskSize = offsetSizeConversion(maskSlider.getValue());
				setSmoothingMatrixInput(maskSize);
				setMaskTable();
			}
		});
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		for (int i = MIN_MASK_OFFSET; i <= MAX_MASK_OFFSET; i ++) tickLabels.put(i, new JLabel(""+offsetSizeConversion(i)));
		maskSlider.setTicks(tickLabels);
		
		// transform button
		JButton transformButton = new JButton("Transform");
		transformButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// validate that the inputs in the smoothing text field matrix are acceptable
				try {
					int maskSize = smoothingMatrixInput.length;
					int sum = 0;
					for (int y = 0; y < maskSize; y ++) {
						for (int x = 0; x < maskSize; x ++) {
							int v = Integer.parseInt(smoothingMatrixInput[y][x].getText().trim());
							if (v < 0) throw new Exception();
							sum += v;
						}
					}
					if (sum == 0) throw new Exception();
					m.algorithmParametersChanged();
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "There is a negative integer or the sum == 0!", "MASK ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// add components to frame
		parameterSelectionFrame.add(maskSlider, BorderLayout.NORTH);
		parameterSelectionFrame.add(maskTable, BorderLayout.CENTER);
		parameterSelectionFrame.add(transformButton, BorderLayout.SOUTH);
		
	}
	
	public int[][] transform(int[][] image) {
		
		// size and of the mask
		int maskSize = smoothingMatrixInput.length;
		int maskOffset = sizeOffsetConversion(maskSize);
		
		// smoothing mask to apply to the image
		int[][] smoothingMask = new int[maskSize][maskSize];
		for (int y = 0; y < maskSize; y ++) {
			for (int x = 0; x < maskSize; x ++) {
				smoothingMask[y][x] = Integer.parseInt(smoothingMatrixInput[y][x].getText().trim());
			}
		}
		
		// size of the image
		int N = image.length;
		
		// new image
		int[][] newImage = new int[N][N];
		for (int y = 0; y < N; y ++) {
			for (int x = 0; x < N; x ++) {
				// for each pixel
				int numerator = 0;
				int denominator = 0;
				for (int s = -1*maskOffset; s <= maskOffset; s ++) {
					for (int t = -1*maskOffset; t <= maskOffset; t ++) {
						int yOffset = y + s;
						int xOffset = x + t;
						if (yOffset < 0 || yOffset >= N || xOffset < 0 || xOffset >= N) continue;
						int w = smoothingMask[s + maskOffset][t + maskOffset];
						numerator += w * (image[yOffset][xOffset] & 0xff);
						denominator += w;
					}
				}
				int gv = numerator / denominator;
				newImage[y][x] = 0xff000000 + (gv << 16) + (gv << 8) + gv;
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	/**
	 * Sets the smoothing matrix input to [maskSize x maskSize] of all 1s.
	 * @param maskSize the size of the matrix.
	 */
	private void setSmoothingMatrixInput(int maskSize) {
		smoothingMatrixInput = new JTextField[maskSize][maskSize];
		for (int y = 0; y < maskSize; y ++) {
			for (int x = 0; x < maskSize; x ++) {
				JTextField maskElement = new JTextField("1");
				maskElement.setHorizontalAlignment(JTextField.CENTER);
				smoothingMatrixInput[y][x] = maskElement;
			}
		}
	}
	
	private void setMaskTable() {
		
		// get the size of the table
		int maskSize = smoothingMatrixInput.length;
		
		// reset mask layout size
		maskTable.removeAll();
		maskTable.setLayout(new GridLayout(maskSize, maskSize));
		
		for (int y = 0; y < maskSize; y ++) {
			for (int x = 0; x < maskSize; x ++) {
				maskTable.add(smoothingMatrixInput[y][x]);
			}
		}
		
		parameterSelectionFrame.revalidate();
		parameterSelectionFrame.repaint();
	}
	
	/**
	 * Converts the offset to the size of the mask.
	 * @param offset the offset.
	 * @return the size of the mask
	 */
	private int offsetSizeConversion(int offset) {
		return 2 * offset + 1;
	}
	
	/**
	 * Converts the size to the offset of the mask.
	 * @param size the size of the mask.
	 * @return the mask offset.
	 */
	private int sizeOffsetConversion(int size) {
		return size / 2;
	}
	
	public String toString() {
		return "Smoothing";
	}

	private JTextField[][] smoothingMatrixInput;
	private JFrame parameterSelectionFrame;
	private JPanel maskTable;
	
}
