package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.hal.SolenoidJNI;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

import edu.wpi.first.wpilibj.Solenoid;

public class CBSolenoid implements CBDevice {
	String name, subsystem;
	Solenoid solenoid;

	public CBSolenoid(int channel) {
		solenoid = new Solenoid(channel);
	}

	public CBSolenoid(int moduleNumber, int channel) {
		solenoid = new Solenoid(moduleNumber,channel);
	}
	
	public CBSolenoid set(CBTriStateValue value) {
		if(value==CBTriStateValue.high) solenoid.set(true);
		else if(value==CBTriStateValue.low) solenoid.set(false);
		return this;
	}

	/**
	 * Set the value of a solenoid.
	 *
	 * @param on True will turn the solenoid output on. False will turn the solenoid output off.
	 */
	public void set(boolean on) {
		solenoid.set(on);
	}

	/**
	 * Read the current value of the solenoid.
	 *
	 * @return True if the solenoid output is on or false if the solenoid output is off.
	 */
	public boolean get() {
		return solenoid.get();
	}

	/**
	 * Check if solenoid is blacklisted. If a solenoid is shorted, it is added to the blacklist and
	 * disabled until power cycle, or until faults are cleared.
	 *
	 * @return If solenoid is disabled due to short.
	 * @see #clearAllPCMStickyFaults()
	 */
	public boolean isBlackListed() {
		return solenoid.isBlackListed();
	}

	/**
	 * Set the pulse duration in the PCM. This is used in conjunction with
	 * the startPulse method to allow the PCM to control the timing of a pulse.
	 * The timing can be controlled in 0.01 second increments.
	 *
	 * @param durationSeconds The duration of the pulse, from 0.01 to 2.55 seconds.
	 *
	 * @see #startPulse()
	 */
	public void setPulseDuration(double durationSeconds) {
		solenoid.setPulseDuration(durationSeconds);
	}

	/**
	 * Trigger the PCM to generate a pulse of the duration set in
	 * setPulseDuration.
	 *
	 * @see #setPulseDuration(double)
	 */
	public void startPulse() {
		solenoid.startPulse();
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
		builder.setSmartDashboardType("Solenoid");
		builder.setSafeState(() -> set(false));
		builder.addBooleanProperty("Value", this::get, this::set);
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
	public void configure() {
		// TODO Auto-generated method stub
	}
}
