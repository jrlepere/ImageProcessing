package main_frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Model;
import transformations.BiLinearInterpolationTransformation;
import transformations.LinearInterpolationTransformation;
import transformations.NearestNeighborTransformation;
import transformations.NegativeTransformation;
import transformations.TransformationAlgorithm;

/**
 * A JPanel for selecting the algorithm to apply to the loaded image.
 * @author JLepere2
 * @date 03/31/3018
 */
public class AlgorithmSelectionPanel extends JPanel {

	/**
	 * Creates an algorithm selection panel object for selecting the transformation algorithm.
	 * @param m the Model for MVC.
	 */
	public AlgorithmSelectionPanel(Model m) {
		
		this.setLayout(new GridLayout(1, 1));
		
		TransformationAlgorithm[] algos = new TransformationAlgorithm[] {
			new NearestNeighborTransformation(m),
			new LinearInterpolationTransformation(m, true),
			new LinearInterpolationTransformation(m, false),
			new BiLinearInterpolationTransformation(m),
			new NegativeTransformation()
		};
		
		JComboBox<TransformationAlgorithm> algoSelectionBox = new JComboBox<>(algos);
		algoSelectionBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.setAlgorithm(algos[algoSelectionBox.getSelectedIndex()]);
			}
		});
		
		this.add(algoSelectionBox);
		
	}
	
	private static final long serialVersionUID = 1254609071L;	
	
}
