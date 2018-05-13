package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.tables.ITable;

public class CBTalon implements CBSpeedController {
	Talon talon;
	int pwmPort;

	public CBTalon(int pwmPort) {
		this.pwmPort = pwmPort;
		this.talon = new Talon(pwmPort);
	}

	@Override
	public CBTalon pidWrite(double output) {
		talon.pidWrite(output);
		return this;
	}

	@Override
	public double get() {
		return talon.get();
	}

	@Override
	public CBTalon set(double speed) {
		talon.set(speed);
		return this;
	}

	@Override
	public CBTalon setInverted(boolean isInverted) {
		talon.setInverted(isInverted);
		return this;
	}

	@Override
	public CBTalon stopMotor() {
		talon.stopMotor();
		return this;
	}

	@Override
	public boolean getInverted() {
		return talon.getInverted();
	}

	@Override
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
