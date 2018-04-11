package main_frame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButton;

import model.Model;

/**
 * The Bit Plane Selection Menu for the View component of the menu bar.
 * @author JLepere2
 *
 */
public class BitPlaneMenu extends JMenu {

	public BitPlaneMenu(Model m) {
		super("Bit Plane");
		
		// button group
		ButtonGroup bitPlaneSelectionButtons = new ButtonGroup();
		
		// bit plane all button
		AbstractButton buttonAll = new JRadioButton("All");
		buttonAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.setBitPlane(BIT_PLANE_ALL_VALUE);
			}
		});
		buttonAll.setSelected(true);
		bitPlaneSelectionButtons.add(buttonAll);
		this.add(buttonAll);
		
		// Bit planes
		for (int i = 8; i >= 1; i --) {
			AbstractButton buttonPlane = new JRadioButton("" + i);
			buttonPlane.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					m.setBitPlane(Integer.parseInt(buttonPlane.getText()));
				}
			});
			bitPlaneSelectionButtons.add(buttonPlane);
			this.add(buttonPlane);
		}
		
	}
	
	public static final int BIT_PLANE_ALL_VALUE = 9;
	public static final int INITIAL_PLANE_VALUE = BIT_PLANE_ALL_VALUE;
	private static final long serialVersionUID = 3624631L;
	
}
