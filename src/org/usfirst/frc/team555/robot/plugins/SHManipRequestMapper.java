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
		req.ShootOut.set(
				robot.driverStationState.getButtonPress(1, 1),
				robot.driverStationState.getButtonRelease(1, 1));
		req.ArmDown.set(
				robot.driverStationState.getButtonPress(1, 3),
				robot.driverStationState.getButtonState(1, 5));
		req.HalfUp.set(
				robot.driverStationState.getButtonPress(1, 6),
				robot.driverStationState.getButtonPress(1, 4));
		
		int pov = robot.driverStationState.getPOV(1, 0);
		req.SpinIn.set(180==pov,0==pov);
		
		//SmartDashboard.putNumber("POV", pov);
	}

}
