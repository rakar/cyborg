package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipRequestMapper;
import org.montclairrobotics.cyborg.utils.CBJoystickIndex;
import org.usfirst.frc.team555.robot.Robot;

public class SHManipRequestMapper extends CBManipRequestMapper {

	Robot robot;
	CBJoystickIndex shootButton = new CBJoystickIndex(1, 1);
	CBJoystickIndex armDownButton = new CBJoystickIndex(1, 3);
	CBJoystickIndex armUpButton = new CBJoystickIndex(1, 5);
	CBJoystickIndex halfDownButton = new CBJoystickIndex(1, 4);
	CBJoystickIndex halfUpButton = new CBJoystickIndex(1, 6);
	CBJoystickIndex spinSwitch = new CBJoystickIndex(1, 0);
	
	public SHManipRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override
	public void update() {
		SHManipRequestStatus req = (SHManipRequestStatus)robot.manipRequestStatus;
		req.ShootOut.set(
				robot.hardwareAdapter.getButtonPress(shootButton),
				robot.hardwareAdapter.getButtonRelease(shootButton));
		req.ArmDown.set(
				robot.hardwareAdapter.getButtonPress(armDownButton),
				robot.hardwareAdapter.getButtonPress(armUpButton));
		req.HalfUp.set(
				robot.hardwareAdapter.getButtonPress(halfUpButton),
				robot.hardwareAdapter.getButtonPress(halfDownButton));
		
		int pov = robot.hardwareAdapter.getPOV(spinSwitch);
		req.SpinIn.set(180==pov,0==pov);
		
	}

}
