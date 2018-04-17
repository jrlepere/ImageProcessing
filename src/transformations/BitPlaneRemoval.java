package transformations;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;

/**
 * A bit plane removal transformation.
 * @author JLepere2
 * @date 04/17/2018
 */
public class BitPlaneRemoval implements TransformationAlgorithm {

	/**
	 * Creates an object for removing bit planes.
	 * @param m the Model for MVC.
	 */
	public BitPlaneRemoval(Model m) {
		
		// parameter selection frame initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: Bit Plane Removal");
		parameterSelectionFrame.setLayout(new GridLayout(1, 1));
		parameterSelectionFrame.setSize(PARAMETER_SELECTION_WIDTH, PARAMETER_SELECTION_HEIGHT);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setLocationRelativeTo(null);
		parameterSelectionFrame.setAlwaysOnTop(true);
		
		// byte for defining if the bit plane is visible, all initially visible.
		planeMask = 0xff;
		
		// button panel
		JPanel buttonPanel = new JPanel(new GridLayout(NUMBER_OF_BITS,1));
		for (int i = 0; i < NUMBER_OF_BITS; i ++) {
			
			// create a check box
			JCheckBox checkBox = new JCheckBox(""+(NUMBER_OF_BITS-i), true);
			
			// add an action listener to recalculate transformed image if changed
			checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					// get the bit plane selected (1-8)
					int bitPlane = Integer.parseInt(checkBox.getText());
					
					// get a mask for only this bit plane
					int bitPlaneMask = 0x01 << (bitPlane - 1);
					
					// set global mask
					planeMask ^= bitPlaneMask;
					
					// alert model of a change
					m.algorithmParametersChanged();
				}
			});
			buttonPanel.add(checkBox);
		}
		
		parameterSelectionFrame.add(buttonPanel);
		parameterSelectionFrame.pack();
		
	}
	
	public int[][] transform(int[][] image) {
		
		// size of the image
		int N = image.length;
		
		// create and initialize transformed image
		int[][] newImage = new int[N][N];
		for (int y = 0; y < N; y ++) {
			for (int x = 0; x < N; x ++) {
				int grayValue = image[y][x] & 0xff;
				int newGrayValue = grayValue & planeMask;
				newImage[y][x] = 0xff000000 + (newGrayValue << 16) + (newGrayValue << 8) + newGrayValue;
			}
		}
		
		return newImage;
	}

	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(true);
	}
	
	public String toString() {
		return "Bit Plane Removal";
	}

	private static final int NUMBER_OF_BITS = 8;
	private int planeMask;
	private JFrame parameterSelectionFrame;
	private static final int PARAMETER_SELECTION_WIDTH = 600;
	private static final int PARAMETER_SELECTION_HEIGHT = 200;
	
}
