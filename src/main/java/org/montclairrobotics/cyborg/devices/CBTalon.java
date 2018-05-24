package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBITalon;
import org.montclairrobotics.cyborg.simulation.CBSimTalon;
import org.montclairrobotics.cyborg.simulation.CBWPITalon;

public class CBTalon extends CBSpeedController implements CBDevice {
	CBITalon talon;
	int pwmPort;

	public CBTalon(int pwmPort) {
		this.pwmPort = pwmPort;
		if(Cyborg.simulationActive) {
			talon = new CBSimTalon(pwmPort);
		} else {
			talon = new CBWPITalon(pwmPort);
		}
	}

	public CBTalon pidWrite(double output) {
		talon.pidWrite(output);
		return this;
	}

	public double get() {
		return talon.get();
	}

	public CBTalon set(double speed) {
		talon.set(speed);
		return this;
	}

	public CBTalon setInverted(boolean isInverted) {
		talon.setInverted(isInverted);
		return this;
	}

	public CBTalon stopMotor() {
		talon.stopMotor();
		return this;
	}

	public boolean getInverted() {
		return talon.getInverted();
	}

	public CBTalon disable() {
		talon.disable();
		return this;
	}

	public String getDescription() {
		return talon.getDescription();
	}
	
	public double getExpiration() {
		return talon.getExpiration();
	}
	
	public double getPosition() {
		return talon.getPosition();
	}
	
	public double getSpeed() {
		return talon.getSpeed();
	}
	
	public boolean isAlive() {
		return talon.isAlive();
	}
	
	public boolean isSafetyEnabled() {
		return talon.isSafetyEnabled();
	}
	
	public CBTalon setExpiration(double timeout) {
		talon.setExpiration(timeout);
		return this;
	}

	public CBTalon setPosition(double pos) {
		talon.setPosition(pos);
		return this;
	}
	
	public CBTalon setSafetyEnabled(boolean enabled) {
		talon.setSafetyEnabled(enabled);
		return this;
	}
	
	public String toString()  {
		return talon.toString();
	}

	public void configure() {
	}

	public void senseUpdate() {
	}

	public void controlUpdate() {
	}

	@Override
	public String getName() {
		String name = super.getName();
		if(name=="") {
			name = "PWM:"+Integer.toString(pwmPort)+" PDB:"+Integer.toString(pdbChannel);
		}
		return name;
	}

}
