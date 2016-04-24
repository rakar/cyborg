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
				robot.hardwareAdapter.getButtonPress(1, 1),
				robot.hardwareAdapter.getButtonRelease(1, 1));
		req.ArmDown.set(
				robot.hardwareAdapter.getButtonPress(1, 3),
				robot.hardwareAdapter.getButtonState(1, 5));
		req.HalfUp.set(
				robot.hardwareAdapter.getButtonPress(1, 6),
				robot.hardwareAdapter.getButtonPress(1, 4));
		
		int pov = robot.hardwareAdapter.getPOV(1, 0);
		req.SpinIn.set(180==pov,0==pov);
		
		//SmartDashboard.putNumber("POV", pov);
	}

}
