package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBHardwareAdapter;
import org.montclairrobotics.cyborg.CBManipulatorRequestMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.usfirst.frc.team555.robot.Robot;

public class SHManipulatorRequestMapper extends CBManipulatorRequestMapper {

	Robot robot;
	CBButton shootButton;
	CBButton armDownButton;
	CBButton armUpButton;
	CBButton halfDownButton;
	CBButton halfUpButton;
	CBPov spinPov;
	
	public SHManipulatorRequestMapper(Robot robot) {
		super(robot);
		this.robot = robot;
		CBHardwareAdapter ha = Cyborg.hardwareAdapter;
		
		this.shootButton    = ha.getButton(robot.devices.shootButton);
		this.armDownButton  = ha.getButton(robot.devices.armDownButton);
		this.armUpButton    = ha.getButton(robot.devices.armUpButton);
		this.halfDownButton = ha.getButton(robot.devices.halfDownButton);
		this.halfUpButton   = ha.getButton(robot.devices.halfUpButton);
		this.spinPov     	= ha.getPOV(robot.devices.spinPov);
	}
	
	@Override
	public void update() {
		SHManipulatorRequestData req = (SHManipulatorRequestData)Cyborg.manipulatorRequestData;

		req.ShootOut.set(shootButton.getButtonPress(),shootButton.getButtonRelease());
		req.ArmDown.set(armDownButton.getButtonPress(), armUpButton.getButtonPress());
		req.HalfUp.set(halfUpButton.getButtonPress(),halfDownButton.getButtonPress());
		
		int pov = spinPov.get();
		req.SpinIn.set(180==pov,0==pov);		
	}
}
