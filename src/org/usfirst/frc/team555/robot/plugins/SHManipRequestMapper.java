package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.ManipRequestMapper;
import org.usfirst.frc.team555.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHManipRequestMapper extends ManipRequestMapper {

	Robot robot;
	
	public SHManipRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override
	public void update() {
		SHManipRequestStatus req = (SHManipRequestStatus)robot.manipRequestStatus;
		req.Shoot = robot.driverStationState.getButtonPress(1, 1);
		req.ArmUp = robot.driverStationState.getButtonPress(1, 2);
		req.ArmDown = robot.driverStationState.getButtonPress(1, 3);
		req.HalfDown = robot.driverStationState.getButtonPress(1, 4);
		req.HalfDown = robot.driverStationState.getButtonPress(1, 5);
		req.SpinIn   = 180==robot.driverStationState.getPOV(1, 0);
		req.SpinOut  =   0==robot.driverStationState.getPOV(1, 0);
		
		SmartDashboard.putNumber("POV", robot.driverStationState.getPOV(1, 0));
	}

}
