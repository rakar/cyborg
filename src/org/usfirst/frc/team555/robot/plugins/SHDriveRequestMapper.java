package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.DriveRequestMapper;
import org.usfirst.frc.team555.robot.Robot;

public class SHDriveRequestMapper extends DriveRequestMapper {
	Robot robot;
	
	public SHDriveRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}

	@Override
	public void update() {
		//SHDriveRequestStatus drs = (SHDriveRequestStatus)robot.driveRequestStatus;
		//drs.DrivePower = robot.driverStationState.getJoystickAxis(0, 1);
		//drs.TurnPower = robot.driverStationState.getJoystickAxis(0, 0);

	}

}
