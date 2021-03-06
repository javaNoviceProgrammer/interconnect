package interconnect.tutorial;

import interconnect.elements.passive.StraightWg;
import interconnect.solver.PhotonicCircuit;
import interconnect.util.Wavelength;
import mathLib.numbers.Complex;
import mathLib.plot.MatlabChart;
import mathLib.util.MathUtils;

public class Tutorial01 {

	/*
	 * Simulating a simple straight waveguide
	 */

	public static void main(String[] args) {

		double[] lambdaNm = MathUtils.linspace(1500, 1600, 1000) ;
		Complex[] transfer = new Complex[lambdaNm.length] ;
		// perform wavelength sweep
		for(int i=0; i<lambdaNm.length; i++) {
			Wavelength lambda = new Wavelength(lambdaNm[i]) ;
			// create the circuit
			PhotonicCircuit pc = new PhotonicCircuit() ;
			pc.setWavelength(lambda) ;
			// create circuit elements
			StraightWg wg1 = new StraightWg("wg1", s -> 2.23, 20, 100) ;
			pc.addElement(wg1);
			// calculate transfer
			transfer[i] = pc.getTransfer("wg1.port1", "wg1.port2") ;
		}

		MatlabChart fig = new MatlabChart() ;
		fig.plot(lambdaNm, transfer);
		fig.renderPlot();
		fig.xlabel("Wavelength (nm)");
		fig.ylabel("Transfer");
		fig.run(true);
	}
}
