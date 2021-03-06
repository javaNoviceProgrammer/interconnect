package interconnect.tutorial;

import interconnect.elements.passive.StraightWg;
import interconnect.elements.passive.Yjunction;
import interconnect.modes.Neff450X220Strip;
import interconnect.solver.PhotonicCircuit;
import interconnect.util.Utils;
import interconnect.util.Wavelength;
import mathLib.plot.MatlabChart;
import mathLib.util.MathUtils;

public class Tutorial08 {

	/*
	 * Simulating a Mach-Zehnder
	 */

	public static void main(String[] args) {
		double L1 = 100 ;
		double L2 = 120 ;
		double[] LambdaNm = MathUtils.linspace(1500, 1600, 10000) ;
		double delta = 0 ;
		double[] transfer = new double[LambdaNm.length] ;
		for(int i=0; i<LambdaNm.length; i++) {
			Wavelength lambda = new Wavelength(LambdaNm[i]) ;
			PhotonicCircuit pc = new PhotonicCircuit() ;
			pc.setWavelength(lambda) ;
			Yjunction y1 = new Yjunction("y1", delta) ;
			Yjunction y2 = new Yjunction("y2", delta) ;
			StraightWg wg1 = new StraightWg("wg1", new Neff450X220Strip(), 3, L1) ;
			StraightWg wg2 = new StraightWg("wg2", new Neff450X220Strip(), 3, L2) ;
			pc.addElement(y1);
			pc.addElement(y2);
			pc.addElement(wg1);
			pc.addElement(wg2);
			pc.connectPorts("y1.port2", "wg1.port1");
			pc.connectPorts("y1.port3", "wg2.port1");
			pc.connectPorts("y2.port2", "wg1.port2");
			pc.connectPorts("y2.port3", "wg2.port2");
			transfer[i] = Utils.todB(pc.getTransfer("y1.port1", "y2.port1").absSquared()) ;

			if(i==0) {
				pc.printDetails();
			}
		}

		MatlabChart fig = new MatlabChart() ;
		fig.plot(LambdaNm, transfer);
		fig.renderPlot();
		fig.xlabel("Wavelength (nm)");
		fig.ylabel("Transfer (dB)");
		fig.run(true);
	}
}
