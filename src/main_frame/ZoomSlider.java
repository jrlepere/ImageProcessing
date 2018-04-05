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
public class ZoomSlider extends JPanel {

	/**
	 * Creates the slider for zooming in on the image.
	 * @param m the Model for MVC.
	 * @param minZoom the minimum zoom value.
	 * @param maxZoom the maximum zoom value.
	 * @param description the description of zooming.
	 */
	public ZoomSlider(Model m, String description) {
		
		this.setLayout(new BorderLayout());
		
		slider = new JSlider(Model.MIN_RESOLUTION, Model.MAX_RESOLUTION, Model.MAX_RESOLUTION);
		
		slider.setMajorTickSpacing(Model.MAX_RESOLUTION - Model.MIN_RESOLUTION);
		slider.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		tickLabels.put(Model.MIN_RESOLUTION, new JLabel(""+Model.MIN_RESOLUTION));
		tickLabels.put(Model.MAX_RESOLUTION, new JLabel(""+Model.MAX_RESOLUTION));
		slider.setLabelTable(tickLabels);
		slider.setPaintLabels(true);
		
		// TODO: Discrete??
		
		JLabel sliderLabel = new JLabel(""+Model.MAX_RESOLUTION);
		sliderLabel.setHorizontalTextPosition(JLabel.CENTER);
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		sliderLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.DESCRIPTION_HEIGHT,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sliderLabel.setText(""+slider.getValue());
			}
		});
		
		JLabel descriptionLabel = new JLabel(description);
		descriptionLabel.setHorizontalTextPosition(JLabel.CENTER);
		descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		descriptionLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.WEST_DESCRIPTION_WIDTH,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
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
	
	public void setMinimum(int minZoom) {
		slider.setMinimum(minZoom);
		
		Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
		
		sliderLabels.put(minZoom, new JLabel(""+minZoom));
		sliderLabels.put(Model.MAX_RESOLUTION, new JLabel(""+Model.MAX_RESOLUTION));
		slider.setLabelTable(sliderLabels);
		
		slider.setMajorTickSpacing(Model.MAX_RESOLUTION - minZoom);
	}
	
	private JSlider slider;

	private static final long serialVersionUID = 11328442L;
	
}
