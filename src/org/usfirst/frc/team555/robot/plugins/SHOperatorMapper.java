package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import org.usfirst.frc.team555.robot.Robot;

public class SHOperatorMapper extends CBTeleOpMapper {
	Robot robot;
	SHCustomRequestData crd = (SHCustomRequestData)Cyborg.customRequestData;
	
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
	}
	
	@Override
	public void update() {

		crd.ArmUp = armUpButton.getButtonPress();
		crd.ArmHalfUp = halfUpButton.getButtonPress();
		crd.ArmDown = armDownButton.getButtonPress(); 

		crd.fireShooter.set(shootButton.getButtonPress(),shootButton.getButtonRelease());
		crd.autoSteer = autoSteerButton.getButtonState();
		//grd.autoSteerLock = autoSteerLockTrigger.setState(grd.autoSteer).getRisingEdge();

		int pov = spinPov.get();
		crd.intake = (180==pov);
		crd.spinUpShooter = (0==pov);		
	}
	
	public SHOperatorMapper setShootButton(CBDeviceId buttonId) {
		shootButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmDownButton(CBDeviceId buttonId) {
		armDownButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmUpButton(CBDeviceId buttonId) {
		armUpButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setAutoSteerButton(CBDeviceId buttonId) {
		autoSteerButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setArmHalfUpButton(CBDeviceId buttonId) {
		halfUpButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}
	
	public SHOperatorMapper setSpinPOV(CBDeviceId povId) {
		spinPov = Cyborg.hardwareAdapter.getPOV(povId);
		return this;
	}
}
