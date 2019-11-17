package interconnect.elements.passive;

import java.util.ArrayList;
import java.util.Map;

import ch.epfl.general_libraries.utils.SimpleMap;
import interconnect.elements.AbstractElement;
import interconnect.util.Wavelength;
import mathLib.numbers.Complex;
import mathLib.sfg.SFG;

public class Termination extends AbstractElement {

	Wavelength inputLambda = null ;

	Complex s11 = Complex.ZERO ;

	public void setName(String name) {
		this.name = name ;
	}

	@Override
	public void setWavelength(Wavelength inputLambda) {
		this.inputLambda = inputLambda ;
	}

	@Override
	public void buildElement() {

		if(inputLambda == null)
			throw new NullPointerException("wavelength is not set for " + name) ;

		String port1_in = name+".port1.in" ;
		String port1_out = name+".port1.out" ;

		nodes = new ArrayList<>() ;
		nodes.add(port1_in) ;
		nodes.add(port1_out) ;

		sfgElement = new SFG(nodes) ;
		sfgElement.addArrow(port1_in, port1_out, s11);
	}

	@Override
	public Map<String, String> getAllParameters() {
		Map<String, String> map = new SimpleMap<String, String>() ;
		return map ;
	}

}
