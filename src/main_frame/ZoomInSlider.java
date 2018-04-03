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
public class ZoomInSlider extends JPanel {

	/**
	 * Creates the slider for zooming in on the image.
	 * @param m the Model for MVC.
	 */
	public ZoomInSlider(Model m) {
		
		this.setLayout(new BorderLayout());
		
		int minZoom = 32;
		int maxZoom = 512;
		int initialZoom = maxZoom;
		
		JSlider zoomInSlider = new JSlider(minZoom, maxZoom, initialZoom);
		
		zoomInSlider.setMajorTickSpacing(480);
		zoomInSlider.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		tickLabels.put(minZoom, new JLabel(""+minZoom));
		tickLabels.put(maxZoom, new JLabel(""+maxZoom));
		zoomInSlider.setLabelTable(tickLabels);
		zoomInSlider.setPaintLabels(true);
		
		// TODO: Discrete??
		
		JLabel sliderLabel = new JLabel(""+initialZoom);
		sliderLabel.setHorizontalTextPosition(JLabel.CENTER);
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		sliderLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.DESCRIPTION_HEIGHT,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
		zoomInSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int zoomInValue = zoomInSlider.getValue();
				sliderLabel.setText(""+zoomInValue);
				m.setSpatialResolution(zoomInValue);
			}
		});
		
		JLabel descriptionLabel = new JLabel(" Zoom in: ");
		descriptionLabel.setHorizontalTextPosition(JLabel.CENTER);
		descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		descriptionLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.WEST_DESCRIPTION_WIDTH,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
		this.add(zoomInSlider, BorderLayout.CENTER);
		this.add(descriptionLabel, BorderLayout.WEST);
		this.add(sliderLabel, BorderLayout.EAST);
	}

	private static final long serialVersionUID = 11328442L;
	
}
