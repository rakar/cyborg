package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.SpeedController;

public class CBSpeedController implements CBDevice{

	private SpeedController controller; 
	
	public CBSpeedController(SpeedController controller) {
		this.controller = controller;
	}

	public CBSpeedController pidWrite(double output) {
		controller.pidWrite(output);
		return this;
	}

	public double get() {
		return controller.get();
	}

	public CBSpeedController set(double speed, byte syncGroup) {
		controller.set(speed,syncGroup);
		return this;
	}

	public CBSpeedController set(double speed) {
		controller.set(speed);
		return this;
	}

	public CBSpeedController setInverted(boolean isInverted) {
		controller.setInverted(isInverted);
		return this;
	}

	public boolean getInverted() {
		return controller.getInverted();
	}

	public CBSpeedController disable() {
		controller.disable();
		return this;
	}

	public CBSpeedController stopMotor() {
		controller.stopMotor();
		return this;
	}
	
	//public CBSpeedController setPID(CBPIDController pid) {
	//	this.pid = pid;
	//	return this;
	//}

	@Override
	public void senseUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub
		
	}
	
}
