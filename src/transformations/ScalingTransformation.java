package transformations;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

/**
 * Abstract class for definition of scaling algorithms.
 * @author JLepere2
 * @date 03/31/3018
 */
public abstract class ScalingTransformation implements TransformationAlgorithm {

	/**
	 * Abstract scaling transformation algorithm definition.
	 */
	public ScalingTransformation(Model m) {
		
		// Initial resolution
		// TODO: Not hard code
		zoomOutResolution = 512;
		
		// Frame Initialization
		parameterSelectionFrame = new JFrame("Parameter Selection: " + toString());
		parameterSelectionFrame.setLayout(new BorderLayout());
		parameterSelectionFrame.setLocation(PARAMETER_FRAME_WIDTH, PARAMETER_FRAME_HEIGHT);
		parameterSelectionFrame.setAlwaysOnTop(true);
		parameterSelectionFrame.setResizable(false);
		parameterSelectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Value Ranges
		int minValue = 32;
		int maxValue = 512;
		int initialValue = maxValue;
		
		// Slider
		JSlider zoomOutSlider = new JSlider(minValue, maxValue, initialValue);
		
		zoomOutSlider.setMajorTickSpacing(maxValue - minValue);
		zoomOutSlider.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
		sliderLabels.put(minValue, new JLabel(""+minValue));
		sliderLabels.put(maxValue, new JLabel(""+maxValue));
		zoomOutSlider.setLabelTable(sliderLabels);
		zoomOutSlider.setPaintLabels(true);
		
		// Current Value Label
		JLabel sliderLabel = new JLabel(""+initialValue);
		sliderLabel.setHorizontalTextPosition(JLabel.CENTER);
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		final int labelWidth = 50;
		final int labelHeight = 50;
		sliderLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
		
		zoomOutSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				zoomOutResolution = zoomOutSlider.getValue();
				sliderLabel.setText(zoomOutResolution+"");
				m.algorithmParametersChanged();
			}
		});
		
		parameterSelectionFrame.add(new JLabel(" Zoom Out: "), BorderLayout.WEST);
		parameterSelectionFrame.add(zoomOutSlider, BorderLayout.CENTER);
		parameterSelectionFrame.add(sliderLabel, BorderLayout.EAST);
		
		parameterSelectionFrame.pack();
		
		/*
		 * Listen for a zoom in change from the model.
		 * Modify the zoom out slider to reflect this change.
		 * (Minimum zoom out cannot be smaller than the current zoom in.
		 *   this would imply we are zooming in more.)
		 */
		m.attachListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int zoomInResolution = m.getSpatialResolution();
				
				zoomOutSlider.setMinimum(zoomInResolution);
				
				sliderLabels.clear();
				sliderLabels.put(zoomInResolution, new JLabel(""+zoomInResolution));
				sliderLabels.put(maxValue, new JLabel(""+maxValue));
				zoomOutSlider.setLabelTable(sliderLabels);
				
				zoomOutSlider.setMajorTickSpacing(maxValue - zoomInResolution);
			}
		});
		
	}
	
	public void showSelectionFrame(boolean visible) {
		parameterSelectionFrame.setVisible(visible);
	}
	
	protected int zoomOutResolution;
	protected JFrame parameterSelectionFrame;
	private static final int PARAMETER_FRAME_WIDTH = 600;
	private static final int PARAMETER_FRAME_HEIGHT = 200;
	
}
