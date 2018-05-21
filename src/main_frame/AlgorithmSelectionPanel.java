package main_frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Model;
import transformations.AlphaTrimmedMeanFilter;
import transformations.ArithmeticMeanFilter;
import transformations.BitPlaneRemoval;
import transformations.ContraHarmonicMeanFilter;
import transformations.GeometricMeanFilter;
import transformations.HarmonicMeanFilter;
import transformations.HighBoostFilter;
import transformations.Laplacian;
import transformations.MaxFilter;
import transformations.MedianFilter;
import transformations.MidpointFilter;
import transformations.MinFilter;
import transformations.NegativeTransformation;
import transformations.Smoothing;
import transformations.TransformationAlgorithm;
import transformations.histogram_equalization.GlobalHistogramEqualization;
import transformations.histogram_equalization.LocalHistogramEqualization;
import transformations.scaling.BiLinearInterpolationTransformation;
import transformations.scaling.LinearInterpolationTransformation;
import transformations.scaling.NearestNeighborTransformation;

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
		
		TransformationAlgorithm initialAlgo = new NegativeTransformation();
		m.setAlgorithm(initialAlgo);
		
		TransformationAlgorithm[] algos = new TransformationAlgorithm[] {
			initialAlgo,
			new NearestNeighborTransformation(m),
			new LinearInterpolationTransformation(m, true),
			new LinearInterpolationTransformation(m, false),
			new BiLinearInterpolationTransformation(m),
			new GlobalHistogramEqualization(),
			new LocalHistogramEqualization(m),
			new Smoothing(m),
			new MedianFilter(m),
			new Laplacian(m),
			new HighBoostFilter(m),
			new BitPlaneRemoval(m),
			new ArithmeticMeanFilter(m),
			new GeometricMeanFilter(m),
			new HarmonicMeanFilter(m),
			new ContraHarmonicMeanFilter(m),
			new MaxFilter(m),
			new MinFilter(m),
			new MidpointFilter(m),
			new AlphaTrimmedMeanFilter(m)
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
