package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class CBTalonSRX implements CBSpeedController {
	TalonSRX talon;
	int channel;
	
	public CBTalonSRX(int channel) {
		this.talon = new TalonSRX(channel);
		this.channel = channel;
	}

	@Override
	public CBSpeedController pidWrite(double output) {
		talon.set(ControlMode.PercentOutput, output);
		return this;
	}

	@Override
	public double get() {
		return talon.getMotorOutputPercent();
	}

	@Override
	public CBSpeedController set(double speed) {
		talon.set(ControlMode.PercentOutput, speed);
		return this;
	}

	@Override
	public CBSpeedController setInverted(boolean isInverted) {
		talon.setInverted(isInverted);
		return this;
	}

	@Override
	public boolean getInverted() {
		return talon.getInverted();
	}

	@Override
	public CBSpeedController disable() {
		talon.neutralOutput();
		return this;
	}

	@Override
	public CBSpeedController stopMotor() {
		talon.neutralOutput();
		return null;
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

}
