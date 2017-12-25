package org.montclairrobotics.cyborg.assemblies;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;
import org.montclairrobotics.cyborg.utils.CBEnums.CBEncoderScheme;

public abstract class CBSpeedControllerArrayController {
	protected ArrayList<CBSpeedController> speedControllers = new ArrayList<>();
	
	protected CBEncoder encoder = null;
	protected CBErrorCorrection errorCorrection = null;
	protected CBEncoderScheme encoderScheme = CBEncoderScheme.None;
	
	protected CBDriveMode driveMode = CBDriveMode.Power;

	protected boolean reversed=false;
	protected int direction=1;

	public CBSpeedControllerArrayController setErrorCorrection(CBErrorCorrection errorCorrection){
		this.errorCorrection = errorCorrection;
		return this;
	};

	public CBSpeedControllerArrayController setDriveMode(CBDriveMode driveMode) {
		this.driveMode = driveMode;
		return this;
	}
	
	public CBSpeedControllerArrayController setReversed(boolean reversed) {
		this.reversed = reversed;
		this.direction = reversed?-1:1;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#addSpeedController(org.montclairrobotics.cyborg.devices.CBSpeedController)
	 */
	public CBSpeedControllerArrayController addSpeedController(CBDeviceId controllerId) {
		speedControllers.add(Cyborg.hardwareAdapter.getSpeedController(controllerId));
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#addSpeedController(org.montclairrobotics.cyborg.devices.CBSpeedController)
	 */
	public CBSpeedControllerArrayController addSpeedController(CBSpeedController speedController) {
		speedControllers.add(speedController);
		return this;
	}

	public abstract CBSpeedControllerArrayController update(double target);

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#getReversed()
	 */
	public boolean getReversed() {
		return reversed;
	}
	

	public CBSpeedControllerArrayController setEncoder(CBDeviceId encoderId) {
		this.encoder = Cyborg.hardwareAdapter.getEncoder(encoderId);
		return this;
	}
	
	public CBSpeedControllerArrayController setEncoder(CBEncoder encoder) {
		this.encoder = encoder;
		return this;
	}
	
	public CBSpeedControllerArrayController setEncoderScheme(CBEncoderScheme encoderScheme) {
		this.encoderScheme = encoderScheme;
		return this;
	}

	public CBDriveMode getDriveMode() {
		return driveMode;
	}

	public abstract double get();

	public boolean canProvideFeedback() {
		return encoder!=null;
	}
	
	public double getFeedbackDistance() {
		return encoder==null?0:encoder.getDistance();
	}
}