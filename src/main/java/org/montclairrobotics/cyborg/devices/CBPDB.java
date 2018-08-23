package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.tables.ITable;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIPDB;
import org.montclairrobotics.cyborg.simulation.CBSimPDB;
import org.montclairrobotics.cyborg.simulation.CBWPIPDB;

public class CBPDB extends CBDeviceInit {
	String name, subsystem;

	int canID;
	CBIPDB pdb;

	public CBPDB() {
		this(0);
	}

	public CBPDB(int CanID) {
		canID = CanID;
		if (Cyborg.simulationActive) {
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
	public void init() {
	}

	@Override
	public void senseUpdate() {
	}

	@Override
	public void controlUpdate() {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSubsystem() {
		return subsystem;
	}

	@Override
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	@Override
	public void initSendable(SendableBuilder builder) {

	}

	public CBPDB setDeviceName(String name) {
		setName(name);
		return this;
	}

	public CBPDB setDeviceName(String subsystem, String name) {
		setName(subsystem, name);
		return this;
	}

}