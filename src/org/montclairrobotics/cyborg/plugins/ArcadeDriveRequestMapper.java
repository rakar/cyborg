package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriveRequestMapper;

public class ArcadeDriveRequestMapper extends DriveRequestMapper {
	private int joystick;
	private int velJoystickAxis;
	private int rotJoystickAxis;
	private double deadzone = 0.0;
	private int gyroLockStick = -1;
	private int gyroLockButton = -1;

	public ArcadeDriveRequestMapper(Cyborg robot, int joystick, int velJoystickAxis, int rotJoystickAxis) {
		super(robot);
		this.joystick = joystick;
		this.velJoystickAxis = velJoystickAxis;
		this.rotJoystickAxis = rotJoystickAxis;
	}

	public ArcadeDriveRequestMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	public ArcadeDriveRequestMapper setGyroLockButton(int stick, int button) {
		this.gyroLockStick = stick;
		this.gyroLockButton = button;
		return this;
	}

	@Override
	public void update() {
		double velocity = -robot.driverStationState.getJoystickAxis(joystick, velJoystickAxis);  // y-axis of first stick
		double rotation =  robot.driverStationState.getJoystickAxis(joystick, rotJoystickAxis);  // x-axis of first stick;
		
		// Implement dead zone
		if(Math.abs(velocity)<deadzone) velocity=0.0;
		if(Math.abs(rotation)<deadzone) rotation=0.0;
		
		if(robot.driveRequestStatus instanceof GeneralDriveRequestStatus) {
			GeneralDriveRequestStatus rs = (GeneralDriveRequestStatus)robot.driveRequestStatus;

			rs.active = true;
			rs.direction.setLocation(0, velocity); 
			rs.rotation = rotation; 
			
			if(gyroLockStick>=0) {
				rs.gyroLock = robot.driverStationState.getButtonState(gyroLockStick, gyroLockButton);
			}			
		} else {
			robot.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}

}
