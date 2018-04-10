package main_frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

/**
 * The slider to zoom in on the loaded image.
 * @author JLepere2
 * @date 03/30/2018
 */
public class SliderComponent extends JPanel {

	/**
	 * Creates the slider for zooming in on the image.
	 * @param m the Model for MVC.
	 * @param description the description of zooming.
	 * @param minValue the minimum value.
	 * @param maxValue the maximum value.
	 * @param initialValue the initial value.
	 */
	public SliderComponent(Model m, String description, int minValue, int maxValue, int initialValue) {
		
		// component parameters
		this.setLayout(new BorderLayout());
		
		// slider variables
		max = maxValue;
		min = minValue;
		
		// initialize slider
		slider = new JSlider(min, max, initialValue);
		slider.setMajorTickSpacing(max - min);
		slider.setPaintTicks(true);
		
		// slider labels
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		tickLabels.put(min, new JLabel(""+min));
		tickLabels.put(max, new JLabel(""+max));
		slider.setLabelTable(tickLabels);
		slider.setPaintLabels(true);
		
		// current value label
		JLabel sliderLabel = new JLabel(""+initialValue);
		sliderLabel.setHorizontalTextPosition(JLabel.CENTER);
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		sliderLabel.setPreferredSize(new Dimension(COMPONENT_HEIGHT, COMPONENT_HEIGHT));
		
		// update value label on change
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sliderLabel.setText(""+slider.getValue());
			}
		});
		
		// label describing purpose of slider
		JLabel descriptionLabel = new JLabel(description);
		descriptionLabel.setHorizontalTextPosition(JLabel.CENTER);
		descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		descriptionLabel.setPreferredSize(new Dimension(DESCRIPTION_WIDTH, COMPONENT_HEIGHT));
		
		// add components to panel
		this.add(slider, BorderLayout.CENTER);
		this.add(descriptionLabel, BorderLayout.WEST);
		this.add(sliderLabel, BorderLayout.EAST);
		
	}
	
	/**
	 * Add a listener to execute when this slider changed.
	 * @param l the change listener.
	 */
	public void addChangeListener(ChangeListener l) {
		slider.addChangeListener(l);
	}
	
	/**
	 * Gets the value of the slider.
	 * @return the value of the slider;
	 */
	public int getValue() {
		return slider.getValue();
	}
	
	/**
	 * Sets the minimum value of this slider.
	 * @param minZoom the minimum value
	 */
	public void setMinimum(int minValue) {
		
		// minValue must be >= global min
		if (minValue < min) minValue = min;
		
		// update minimum
		slider.setMinimum(minValue);
		
		// reset labels
		Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
		sliderLabels.put(minValue, new JLabel(""+minValue));
		sliderLabels.put(max, new JLabel(""+max));
		slider.setLabelTable(sliderLabels);
		slider.setMajorTickSpacing(max - minValue);
	}
	
	/**
	 * Sets the tick spacing to all from min to max.
	 */
	public void setTicksAll() {
		// set major tick spacing
		slider.setMajorTickSpacing(1);
		
		// set labels
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		for (int i = min; i <= max; i ++) tickLabels.put(i, new JLabel(""+i));
		slider.setLabelTable(tickLabels);
	}
	
	private JSlider slider;
	private int max;
	private int min;
	private static final long serialVersionUID = 11328442L;
	public static final int DESCRIPTION_WIDTH = 100;
	public static final int COMPONENT_HEIGHT = 50;
	
}
