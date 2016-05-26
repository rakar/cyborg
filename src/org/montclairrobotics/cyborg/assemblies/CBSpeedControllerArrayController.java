package org.montclairrobotics.cyborg.assemblies;

import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;

public interface CBSpeedControllerArrayController {

	CBSpeedControllerArrayController setErrorCorrection(CBErrorCorrection errorCorrection);

	CBSpeedControllerArrayController setDriveMode(CBDriveMode driveMode);

	CBSpeedControllerArrayController setReversed(boolean reversed);

	CBSpeedControllerArrayController update(double target);

	boolean getReversed();

	CBSpeedControllerArrayController setEncoder(CBDeviceID encoderId);

	CBSpeedControllerArrayController addSpeedController(CBDeviceID controllerId);
	
	CBDriveMode getDriveMode();

}