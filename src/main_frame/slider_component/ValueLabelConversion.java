package main_frame.slider_component;

/**
 * An interface to define the conversion between slider value and value presented on right of component.
 * @author JLepere2
 * @date 04/16/2018
 */
public interface ValueLabelConversion {
	/**
	 * Convert slider value to a value for presentation.
	 * @param sliderValue the slider value.
	 */
	public int convertForPresentation(int sliderValue);
}
