package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBTeleOpMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.usfirst.frc.team555.robot.Robot;

public class SHOperatorMapper extends CBTeleOpMapper {
	Robot robot;
	SHCustomRequestData grd;
	
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
		grd = (SHCustomRequestData)Cyborg.customRequestData;
	}
	
	@Override
	public void update() {

		grd.ArmUp = armUpButton.getButtonPress();
		grd.ArmHalfUp = halfUpButton.getButtonPress();
		grd.ArmDown = armDownButton.getButtonPress(); 

		grd.fireShooter.set(shootButton.getButtonPress(),shootButton.getButtonRelease());
		grd.autoSteer = autoSteerButton.getButtonState();
		//grd.autoSteerLock = autoSteerLockTrigger.setState(grd.autoSteer).getRisingEdge();

		int pov = spinPov.get();
		grd.intake = (180==pov);
		grd.spinUpShooter = (0==pov);		
	}
	
	public SHOperatorMapper setShootButton(CBDeviceID buttonId) {
		shootButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmDownButton(CBDeviceID buttonId) {
		armDownButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmUpButton(CBDeviceID buttonId) {
		armUpButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setAutoSteerButton(CBDeviceID buttonId) {
		autoSteerButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmHalfUpButton(CBDeviceID buttonId) {
		halfUpButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setSpinPOV(CBDeviceID povId) {
		spinPov = Cyborg.hardwareAdapter.getPOV(povId);
		return this;
	}
}
