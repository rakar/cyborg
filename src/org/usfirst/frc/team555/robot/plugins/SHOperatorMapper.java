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
	CBButton pidTuneEnableButton = null;
	CBButton pidTuneCycleButton = null;
	
	CBPov spinPov;
	
	public SHOperatorMapper(Robot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override
	public void update() {

		crd.ArmUp = armUpButton.getRisingEdge();
		crd.ArmHalfUp = halfUpButton.getRisingEdge();
		crd.ArmDown = armDownButton.getRisingEdge(); 
		
		if (pidTuneEnableButton!=null){
			crd.pidTuneEnable = pidTuneEnableButton.getState(); 
			crd.pidTuneCycle = pidTuneCycleButton.getState(); 
		}

		crd.fireShooter.set(shootButton.getRisingEdge(),shootButton.getFallingEdge());
		
		crd.autoSteer = autoSteerButton.getState();
		if(crd.autoSteer) {
			crd.autoSteerX=160;
			crd.autoSteerY= 90;
		}

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
	
	public SHOperatorMapper setTuneButtons(CBDeviceId enableId, CBDeviceId cycleId) {
		this.pidTuneCycleButton = Cyborg.hardwareAdapter.getButton(cycleId);
		this.pidTuneEnableButton = Cyborg.hardwareAdapter.getButton(enableId);
		return this;
	}
}
