package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.PID;

import edu.wpi.first.wpilibj.SpeedController;

public class MotorController implements SpeedController{

	SpeedController controller; 
	PID pid;
	
	public MotorController(SpeedController controller) {
		this.controller = controller;
	}

	@Override
	public void pidWrite(double output) {
		controller.pidWrite(output);
	}

	@Override
	public double get() {
		return controller.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		controller.set(speed,syncGroup);
	}

	@Override
	public void set(double speed) {
		controller.set(speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		controller.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return controller.getInverted();
	}

	@Override
	public void disable() {
		controller.disable();
	}

	@Override
	public void stopMotor() {
		controller.stopMotor();
	}
	
	public MotorController setPID(PID pid) {
		this.pid = pid;
		return this;
	}

	public void update() {
		
	}
	
}
