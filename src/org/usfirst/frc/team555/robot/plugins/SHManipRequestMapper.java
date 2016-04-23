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
		req.ShootOut = robot.driverStationState.getButtonPress(1, 1);
		req.ShootIn  = robot.driverStationState.getButtonRelease(1, 1);
		req.ArmUp = robot.driverStationState.getButtonPress(1, 6);
		req.ArmDown = robot.driverStationState.getButtonState(1, 3);
		req.HalfUp = robot.driverStationState.getButtonPress(1, 5);
		req.HalfDown = robot.driverStationState.getButtonPress(1, 4);
		
		int pov = robot.driverStationState.getPOV(1, 0);
		req.SpinIn   = 180==pov;
		req.SpinOut  =   0==pov;
		
		//SmartDashboard.putNumber("POV", pov);
	}

}
