package org.montclairrobotics.cyborg.devices;

/**
 * Represents a generic speed controller.
 */
public interface CBSpeedController extends CBDevice {
 
	CBSpeedController pidWrite(double output);

	double get();

	//CBSpeedController set(double speed, byte syncGroup);

	CBSpeedController set(double speed);

	CBSpeedController setInverted(boolean isInverted);

	boolean getInverted();

	CBSpeedController disable();

	CBSpeedController stopMotor();

	void senseUpdate();

	void controlUpdate();

	void configure();

}