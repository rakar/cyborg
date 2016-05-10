package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBHardwareAdapter;
import org.montclairrobotics.cyborg.CBTeleOpMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.usfirst.frc.team555.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHOperatorMapper extends CBTeleOpMapper {
	Robot robot;
	CBHardwareAdapter ha;
	SHGeneralRequestData req;

	
	CBButton shootButton;
	CBButton armDownButton;
	CBButton armUpButton;
	CBButton halfDownButton;
	CBButton halfUpButton;
	CBButton autoSteerButton;
	CBPov spinPov;
	
	public SHOperatorMapper(Robot robot) {
		super(robot);
		this.robot = robot;
		ha = Cyborg.hardwareAdapter;
		req = (SHGeneralRequestData)Cyborg.generalRequestData;
		
		this.shootButton    = ha.getButton(robot.devices.shootButton);
		this.armDownButton  = ha.getButton(robot.devices.armDownButton);
		this.armUpButton    = ha.getButton(robot.devices.armUpButton);
		this.halfDownButton = ha.getButton(robot.devices.halfDownButton);
		this.halfUpButton   = ha.getButton(robot.devices.halfUpButton);
		this.spinPov     	= ha.getPOV(robot.devices.spinPov);
		this.autoSteerButton = ha.getButton(robot.devices.autoSteerButton);
	}
	
	@Override
	public void update() {

		req.ShootOut.set(shootButton.getButtonPress(),shootButton.getButtonRelease());
		req.ArmDown.set(armDownButton.getButtonPress(), armUpButton.getButtonPress());
		req.HalfUp.set(halfUpButton.getButtonPress(),halfDownButton.getButtonPress());
		req.autoSteer = autoSteerButton.getButtonState();
		//SmartDashboard.putBoolean("AutoSteerButton", req.autoSteer);

		int pov = spinPov.get();
		req.SpinIn.set(180==pov,0==pov);		
	}
}
