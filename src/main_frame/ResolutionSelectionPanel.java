package main_frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

/**
 * Panel for selecting the transformation to perform on the loaded image.
 * @author JLepere2
 * @date 03/29/2018
 */
public class ResolutionSelectionPanel extends JPanel {

	/**
	 * Creates the selection panel for making transformations on the loaded image.
	 * @param m the Model for MVC.
	 */
	public ResolutionSelectionPanel(Model m) {
		
		// layout
		this.setLayout(new GridLayout(1, 1));
		
		// bit precision slider range values
		int bPrecMinValue = 1;
		int bPrecMaxValue = 8;
		int bPrecInitialValue = bPrecMaxValue;
		
		// create bit resolution slider object
		SliderComponent resolutionSlider = new SliderComponent(m, " Bit Precision: ", bPrecMinValue, bPrecMaxValue, bPrecInitialValue);
		
		// add change listener
		resolutionSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				m.setBitPrecision(resolutionSlider.getValue());
			}
		});
		
		// show ticks all
		resolutionSlider.setTicksAll();
		
		// add to jpanel
		this.add(resolutionSlider);
		
	}
	
	private static final long serialVersionUID = 2232421L;

}
