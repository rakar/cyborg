package org.montclairrobotics.cyborg.assemblies;

import org.montclairrobotics.cyborg.devices.CBSpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author rich
 * 
 * Controls an array of speed controllers. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered 
 * to be the lead controller and all others will be considered followers.  
 *
 */
public class CBVictorArrayController extends CBSpeedControllerArrayController { 
	double currentPower;

	public CBVictorArrayController() {
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
				double encoderRate = encoder.getRate();
				currentPower += errorCorrection.setTarget(target).update(encoderRate);
				SmartDashboard.putNumber("currentPower::", currentPower);
				target = currentPower;
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
	
	public double get() {
		return encoder.get();
	}
	
	
}
