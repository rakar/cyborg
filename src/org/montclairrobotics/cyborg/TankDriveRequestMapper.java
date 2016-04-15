package org.montclairrobotics.cyborg;

public class TankDriveRequestMapper extends DriveRequestMapper {

	public TankDriveRequestMapper(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Update() {
		TankDriveRequestStatus rs = (TankDriveRequestStatus)robot.driveRequestStatus;
		rs.leftPower = robot.driverStationState.getJoystickAxis(0, 1);
		rs.rightPower = robot.driverStationState.getJoystickAxis(0, 2);
		rs.active = true;
	}

}
