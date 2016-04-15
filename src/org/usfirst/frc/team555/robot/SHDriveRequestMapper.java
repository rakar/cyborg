package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.DriveRequestMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriverStationState;

public class SHDriveRequestMapper extends DriveRequestMapper {
	Robot robot;
	
	public SHDriveRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}

	@Override
	public void Update() {
		SHDriveRequestStatus drs = (SHDriveRequestStatus)robot.driveRequestStatus;
		//drs.DrivePower = robot.driverStationState.getJoystickAxis(0, 1);
		//drs.TurnPower = robot.driverStationState.getJoystickAxis(0, 0);

	}

}
