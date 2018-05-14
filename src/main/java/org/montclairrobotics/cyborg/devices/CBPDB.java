package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.tables.ITable;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIPDB;
import org.montclairrobotics.cyborg.simulation.CBSimPDB;
import org.montclairrobotics.cyborg.simulation.CBWPIPDB;

public class CBPDB implements CBDevice {
	int canID;
	CBIPDB pdb;

	public CBPDB() {
		canID=0;
		if(Cyborg.simulationActive) {
			pdb = new CBSimPDB();
		} else {
			pdb = new CBWPIPDB();
		}
	}

	public CBPDB(int CanID) {
		canID = CanID;
		if(Cyborg.simulationActive) {
			pdb = new CBSimPDB(CanID);
		} else {
			pdb = new CBWPIPDB(CanID);
		}
	}
	
	public CBPDB clearStickyFaults() {
		pdb.clearStickyFaults();
		return this;
	}
	
	public boolean equals(Object obj) {
		return pdb.equals(obj);
	}
	
	//public CBPDB free() {
	//	pdb.free();
	//	return this;
	//}
	
	public double getCurrent(int channel) {
		return pdb.getCurrent(channel);
	}
	
	public double getTemperature() {
		return pdb.getTemperature();
	}
	
	public double getTotalCurrent() {
		return pdb.getTotalCurrent();
	}
	
	public double getTotalEnergy() {
		return pdb.getTotalEnergy();
	}
	
	@Override
	public void configure() {
	}

	@Override
	public void senseUpdate() {
	}

	@Override
	public void controlUpdate() {
	}
}
