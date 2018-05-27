package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIDigitalOutput;
import org.montclairrobotics.cyborg.simulation.CBSimDigitalOutput;
import org.montclairrobotics.cyborg.simulation.CBWPIDigitalOutput;

public class CBDigitalOutput implements CBDevice {
	CBIDigitalOutput digitalOutput;

	public CBDigitalOutput(int channel) {
		if(Cyborg.simulationActive) {
			digitalOutput = new CBSimDigitalOutput(channel);
		} else {
			digitalOutput = new CBWPIDigitalOutput(channel);
		}
	}

	@Override
	public void senseUpdate() {
		
	}

	@Override
	public void controlUpdate() {
		
	}

	@Override
	public void configure() {
		
	}

	@Override
	public String getName() {
		return digitalOutput.getName();
	}

	@Override
	public void setName(String name) {
		digitalOutput.setName(name);
	}

	@Override
	public void setName(String subsystem, String name) {
		setName(name);
		setSubsystem(subsystem);
	}

	@Override
	public String getSubsystem() {
		return digitalOutput.getSubsystem();
	}

	@Override
	public void setSubsystem(String subsystem) {
		digitalOutput.setSubsystem(subsystem);
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		digitalOutput.initSendable(builder);
	}

	public CBDigitalOutput setDeviceName(String name) {
		setName(name);
		return this;
	}

	public CBDigitalOutput setDeviceName(String subsystem, String name) {
		setName(subsystem, name);
		return this;
	}

}
