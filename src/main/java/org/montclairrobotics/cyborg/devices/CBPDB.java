package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.tables.ITable;

public class CBPDB implements CBDevice {
	int canID;
	PowerDistributionPanel pdb;

	public CBPDB() {
		canID=0;
		pdb = new PowerDistributionPanel();
	}

	public CBPDB(int CanID) {
		canID = CanID;		
		pdb = new PowerDistributionPanel(canID);
	}
	
	public CBPDB clearStickyFaults() {
		pdb.clearStickyFaults();
		return this;
	}
	
	public boolean equals(Object obj) {
		return pdb.equals(obj);
	}
	
	public CBPDB free() {
		pdb.free();
		return this;
	}
	
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
		// TODO Auto-generated method stub
	}

	@Override
	public void senseUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void configureSim() {

	}

	@Override
	public void senseUpdateSim() {

	}

	@Override
	public void controlUpdateSim() {

	}

}
