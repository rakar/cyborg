package org.montclairrobotics.cyborg.assemblies;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;

/**
 * 
 * @author rich
 * 
 * Controls an array of speed controllers. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered 
 * to be the lead controller and all others will be considered followers.  
 *
 */
public class CBVictorArrayController implements CBSpeedControllerArrayController { 

	private ArrayList<CBSpeedController> speedControllers = new ArrayList<>();
	private CBEncoder encoder = null;
	private CBErrorCorrection errorCorrection = null;
	private CBDriveMode driveMode = CBDriveMode.Power;
	private boolean reversed=false;
	private int direction=1;

	public CBVictorArrayController() {
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#addSpeedController(org.montclairrobotics.cyborg.devices.CBSpeedController)
	 */
	@Override
	public CBSpeedControllerArrayController addSpeedController(CBDeviceID controllerId) {
		speedControllers.add(Cyborg.hardwareAdapter.getSpeedController(controllerId));
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#setEncoder(org.montclairrobotics.cyborg.devices.CBEncoder)
	 */
	@Override
	public CBSpeedControllerArrayController setEncoder(CBDeviceID encoderId) {
		this.encoder = Cyborg.hardwareAdapter.getEncoder(encoderId);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#setErrorCorrection(org.montclairrobotics.cyborg.utils.CBErrorCorrection)
	 */
	@Override
	public CBSpeedControllerArrayController setErrorCorrection(CBErrorCorrection errorCorrection) {
		this.errorCorrection = errorCorrection;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#setDriveMode(org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode)
	 */
	@Override
	public CBSpeedControllerArrayController setDriveMode(CBDriveMode driveMode) {
		this.driveMode = driveMode;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#setReversed(boolean)
	 */
	@Override
	public CBSpeedControllerArrayController setReversed(boolean reversed) {
		this.reversed = reversed;
		this.direction = reversed?-1:1;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#update(double)
	 */
	@Override
	public CBSpeedControllerArrayController update(double target) {
		switch(driveMode) {
		case Power:
			//target = target; //*direction;
			break;
		case Speed:
			if(errorCorrection==null || encoder==null){
				System.out.println("Error: Drive mode=Speed, but CBErrorCorrection or CBEncoder not set.");
				target = 0;
			} else {
				target  += errorCorrection.setTarget(target).update(encoder.getRate());
			}
			break;
		case Conflict:
		default:
			target = 0;
			break;
		}

		for(CBSpeedController l:speedControllers) l.set(target*direction);

		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#getReversed()
	 */
	@Override
	public boolean getReversed() {
		return reversed;
	}
	
	public CBDriveMode getDriveMode() {
		return driveMode;
	}
	
}
