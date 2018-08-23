package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

public class CBPov extends CBJoystickIndex implements CBDevice {
	String name, subsystem;
	private int state;
	private CBJoystick joystick;

	protected boolean initialized;
	public void deviceInit() {
		if (!initialized) {
			init();
			initialized = true;
		}
	}

	public CBPov(int stickID, int index) {
		super(stickID, index);
		joystick = Cyborg.hardwareAdapter.getJoystick(stickID);
	}

	public int get() {
		return state;
	}

	@Override
	public void senseUpdate() {
		state = joystick.getPOV(index);
	}

	@Override
	public void controlUpdate() {
	}

	@Override
	public void init() {
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

	public CBPov setDeviceName(String name) {
		setName(name);
		return this;
	}

	public CBPov setDeviceName(String subsystem, String name) {
		setName(subsystem, name);
		return this;
	}

}
