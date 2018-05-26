package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIJoystick;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;

import edu.wpi.first.wpilibj.Joystick;

public class CBButton extends CBEdgeTrigger implements CBDevice {
	String name,subsystem;
	private CBJoystickIndex stickIndex;
	private CBJoystick joystick;


	public CBButton(CBJoystickIndex joystickIndex) {
		this(joystickIndex.stickID, joystickIndex.index, "", "");
	}

	public CBButton(CBJoystickIndex joystickIndex, String name) {
		this(joystickIndex.stickID, joystickIndex.index, "", name);
	}

	public CBButton(CBJoystickIndex joystickIndex, String subsystem, String name) {
		this(joystickIndex.stickID, joystickIndex.index, subsystem, name);
	}

    public CBButton(int stickID, int index) {
        this(stickID, index, "", "");
    }

    public CBButton(int stickID, int index, String name) {
        this(stickID, index, "", name);
    }

    public CBButton(int stickID, int index, String subsystem, String name) {
		super();
		stickIndex = new CBJoystickIndex(stickID, index);
		if(stickID>=0) {
			joystick = Cyborg.hardwareAdapter.getJoystick(stickIndex.stickID);
		} else {
			joystick = null;
		}
		setName(subsystem, name);
	}
	
	public static CBButton getDefaulted(CBButton button) {
		return (button!=null)?button:new CBButton(CBJoystickIndex.undefined());
	}

	public boolean isDefined() {
		return stickIndex.isDefined();
	}

	@Override
	public void configure() {
	}

	@Override
	public void senseUpdate() {
		if (isDefined()) {
			update(joystick.getRawButton(stickIndex.index));
		}
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
		this.name=name;
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

}
