package main_frame.slider_component;

/**
 * Concrete class for slider component value label where no transformation is done to the slider value.
 * @author JLepere2
 * @date 04/16/2018
 */
public class NoValueConversion implements ValueLabelConversion{
	public int convertForPresentation(int sliderValue) {
		return sliderValue;
	}
}
