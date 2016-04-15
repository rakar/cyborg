package org.montclairrobotics.cyborg;

public class TankDriveBehaviorProcessor extends BehaviorProcessor {

	public TankDriveBehaviorProcessor(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Update() {
		super.Update();
		TankDriveRequestStatus rs = (TankDriveRequestStatus)robot.driveRequestStatus;
		TankDriveControlStatus cs = (TankDriveControlStatus)robot.driveControlStatus;
		cs.leftPower = rs.leftPower;
		cs.rightPower = rs.rightPower;
		cs.active = rs.active;
	}
}
