package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.ManipRequestMapper;
import org.usfirst.frc.team555.robot.Robot;

public class SHManipRequestMapper extends ManipRequestMapper {

	Robot robot;
	
	public SHManipRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override
	public void update() {
		//SHDrivetrainControlRequest req = (SHDrivetrainControlRequest)robot.inputRequest;
		//req.Shoot = robot.driverStationState.getButtonPress(1, 1);
		//req.ArmUp = robot.driverStationState.getButtonPress(1, 2);
		//req.ArmDown = robot.driverStationState.getButtonPress(1, 3);
		//req.HalfDown = robot.driverStationState.getButtonPress(1, 4);
		//req.HalfDown = robot.driverStationState.getButtonPress(1, 5);
	}

}
