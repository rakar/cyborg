package org.montclairrobotics.cyborg.assemblies;

import org.montclairrobotics.cyborg.utils.CB2DVector;

public class CBSwerveDriveModule extends CBDriveModule {
	boolean driveReversed;
	double minAngle, maxAngle;

	public CBSwerveDriveModule() {
		// TODO Auto-generated constructor stub
	}

	public CBSwerveDriveModule(CB2DVector position, double orientation) {
		super(position, orientation);
	}
	
	@Override
	public CBDriveModule update(double target) {
		System.out.println("Error: update(double taget) not valid for CBSwerveModule."); 
		return this;
	}
	/**
	 * Update the module with current control info.
	 * This routine is responsible for performing angle 
	 * optimization re: closest half-turn with reverse.
	 * @param angle
	 * @param speed
	 * @return this module
	 */
	public CBDriveModule update(double angle, double speed) {
		// TODO: Add angle optimization re: closest half-turn with reverse.
		// TODO: enforce rotation limits.
		controllerArrays.get(0).update(angle);
		controllerArrays.get(1).update(speed);
		return this;
	}
	
	public CBDriveModule setAngleLimits(double minAngle, double maxAngle) {
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		return this;
	}

	public double getAngle() {
		return controllerArrays.get(0).get();
	}
	
}
