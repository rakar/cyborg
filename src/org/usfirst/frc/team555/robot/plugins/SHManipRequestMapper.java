package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipRequestMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.usfirst.frc.team555.robot.Robot;
import org.usfirst.frc.team555.robot.Robot.Device;

public class SHManipRequestMapper extends CBManipRequestMapper {

	Robot robot;
	CBButton shootButton;
	CBButton armDownButton;
	CBButton armUpButton;
	CBButton halfDownButton;
	CBButton halfUpButton;
	CBPov spinPov;
	
	public SHManipRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
		
		this.shootButton    = Cyborg.getHA().getButton(Device.SHOOT_BUTTON);
		this.armDownButton  = Cyborg.getHA().getButton(Device.ARMUP_BUTTON);
		this.armUpButton    = Cyborg.getHA().getButton(Device.ARMUP_BUTTON);
		this.halfDownButton = Cyborg.getHA().getButton(Device.HALFDOWN_BUTTON);
		this.halfUpButton   = Cyborg.getHA().getButton(Device.HALFUP_BUTTON);
		this.spinPov     = Cyborg.getHA().getPOV(Device.SPIN_POV);
	}
	
	@Override
	public void update() {
		SHManipRequestStatus req = (SHManipRequestStatus)Cyborg.manipRequestStatus;

		req.ShootOut.set(shootButton.getButtonPress(),shootButton.getButtonRelease());
		req.ArmDown.set(armDownButton.getButtonPress(), armUpButton.getButtonPress());
		req.HalfUp.set(halfUpButton.getButtonPress(),halfDownButton.getButtonPress());
		
		int pov = spinPov.get();
		req.SpinIn.set(180==pov,0==pov);		
	}
}
