import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		tickLabels.put(32, new JLabel("32"));
		tickLabels.put(512, new JLabel("512"));
		zoomInSlider.setLabelTable(tickLabels);
		zoomInSlider.setPaintLabels(true);
		
		// TODO: Discrete??
		
		zoomInSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				m.setSpatialResolution(zoomInSlider.getValue());
			}
		});
		
		this.add(zoomInSlider, BorderLayout.CENTER);
		this.add(new JLabel(" Zoom in: "), BorderLayout.WEST);
	}

	private static final long serialVersionUID = 11328442L;
	
}
