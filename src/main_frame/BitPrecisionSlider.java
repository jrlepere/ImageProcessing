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
 * A Slider for selecting the bit precision of the transformed image.
 * @author JLepere2
 * @date 03/30/2018
 */
public class BitPrecisionSlider extends JPanel {

	/**
	 * Creates a Bit Precision Slider.
	 * @param m the Model for MVC.
	 */
	public BitPrecisionSlider(Model m) {
		
		this.setLayout(new BorderLayout());
		
		int minBitPrecision = 1;
		int maxBitPrecision = 8;
		int initialBitPrecision = maxBitPrecision;
		
		JSlider bitPrecisionSlider = new JSlider(minBitPrecision, maxBitPrecision, initialBitPrecision);
		
		bitPrecisionSlider.setMajorTickSpacing(1);
		bitPrecisionSlider.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> tickLabels = new Hashtable<>();
		for (int i = 1; i <= 8; i ++) tickLabels.put(i, new JLabel(""+i));
		bitPrecisionSlider.setLabelTable(tickLabels);
		bitPrecisionSlider.setPaintLabels(true);
		
		// TODO: Discrete??
		
		JLabel sliderLabel = new JLabel(""+initialBitPrecision);
		sliderLabel.setHorizontalTextPosition(JLabel.CENTER);
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		sliderLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.DESCRIPTION_HEIGHT,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
		bitPrecisionSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int bitPrecision = bitPrecisionSlider.getValue();
				sliderLabel.setText(""+bitPrecision);
				m.setBitPrecision(bitPrecision);
			}
		});
		
		JLabel descriptionLabel = new JLabel(" Bit Precision: ");
		descriptionLabel.setHorizontalTextPosition(JLabel.CENTER);
		descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		descriptionLabel.setPreferredSize(new Dimension(ResolutionSelectionPanel.WEST_DESCRIPTION_WIDTH,
				ResolutionSelectionPanel.DESCRIPTION_HEIGHT));
		
		this.add(bitPrecisionSlider, BorderLayout.CENTER);
		this.add(descriptionLabel, BorderLayout.WEST);
		this.add(sliderLabel, BorderLayout.EAST);
		
	}
	
	private static final long serialVersionUID = 143646L;
	
}
