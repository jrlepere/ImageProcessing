package main_frame.menu;

import javax.swing.JMenu;

import model.Model;

/**
 * View component in the main menu bar.
 * @author JLepere2
 * @date 04/11/2018
 */
public class ViewMenu extends JMenu {

	/**
	 * Creates the View component for the menu bar.
	 * @param m the Model for MVC.
	 */
	public ViewMenu(Model m) {
		super("View");
		
		this.add(new BitPlaneMenu(m));
		
	}
	
	private static final long serialVersionUID = 10908897L;
	
}
