package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import org.usfirst.frc.team555.stronghold.SHOperatorMapper;

public class SWOperatorMapper extends CBTeleOpMapper {
	SWRobot robot;
	SWCustomRequestData crd = (SWCustomRequestData)Cyborg.customRequestData;
	
	CBButton gearAutoOpenButton;
	CBButton gearAutoCloseButton;
	CBButton gearManualLeftOpenButton;
	CBButton gearManualLeftCloseButton;
	CBButton gearManualRightOpenButton;
	CBButton gearManualRightCloseButton;
	CBButton climbButton;


	public SWOperatorMapper(SWRobot robot) {
		super(robot);
		this.robot = robot;
	}

	@Override
	public void update() {
		crd.gearAutoClose = gearAutoCloseButton.getState();
		crd.gearAutoOpen = gearAutoOpenButton.getState();
		crd.gearManualLeftClose = gearManualLeftCloseButton.getState();
		crd.gearManualLeftOpen = gearManualLeftOpenButton.getState();
		crd.gearManualRightClose = gearManualRightCloseButton.getState();
		crd.gearManualRightOpen = gearManualRightOpenButton.getState();
		crd.climb = climbButton.getState();
	}
	
	public SWOperatorMapper setGearAutoOpenButton(CBDeviceId buttonId) {
		gearAutoOpenButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setGearAutoCloseButton(CBDeviceId buttonId) {
		gearAutoCloseButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setGearManualLeftOpenButton(CBDeviceId buttonId) {
		gearManualLeftOpenButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setGearManualLeftCloseButton(CBDeviceId buttonId) {
		gearManualLeftCloseButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setGearManualRightOpenButton(CBDeviceId buttonId) {
		gearManualRightOpenButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setGearManualRightCloseButton(CBDeviceId buttonId) {
		gearManualRightCloseButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

	public SWOperatorMapper setClimbButton(CBDeviceId buttonId) {
		climbButton = Cyborg.hardwareAdapter.getButton(buttonId);
		return this;
	}

}


