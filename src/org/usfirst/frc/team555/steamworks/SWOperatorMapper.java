package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;

public class SWOperatorMapper extends CBTeleOpMapper {
	SWRequestData rd = (SWRequestData)Cyborg.requestData;
	
	CBButton gearAutoOpenButton;
	CBButton gearAutoCloseButton;
	CBButton gearManualLeftOpenButton;
	CBButton gearManualLeftCloseButton;
	CBButton gearManualRightOpenButton;
	CBButton gearManualRightCloseButton;
	CBButton climbButton;


	public SWOperatorMapper(SWRobot robot) {
		super(robot);
	}

	@Override
	public void update() {
		rd.gearAutoClose = gearAutoCloseButton.getState();
		rd.gearAutoOpen = gearAutoOpenButton.getState();
		rd.gearManualLeftClose = gearManualLeftCloseButton.getState();
		rd.gearManualLeftOpen = gearManualLeftOpenButton.getState();
		rd.gearManualRightClose = gearManualRightCloseButton.getState();
		rd.gearManualRightOpen = gearManualRightOpenButton.getState();
		rd.climb = climbButton.getState();
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


