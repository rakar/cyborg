package org.montclairrobotics.cyborg.assemblies;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.utils.CB2DVector;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;

public class CBDriveModule {

	protected ArrayList<CBSpeedControllerArrayController> controllerArrays = new ArrayList<>();
	private CB2DVector position = new CB2DVector();
	private double orientation;
	private double orientationRadians;
	private CB2DVector orientationVector;
	private CBDriveMode driveMode=null;
	
	public CBDriveModule() {
	}
	
	public CBDriveModule(CB2DVector position, double orientation) {
		setPlacement(position, orientation);
	}
	
	public CBDriveModule setPlacement(CB2DVector position, double orientation) {
		this.position = position;
		this.orientation = orientation;
		this.orientationRadians = Math.PI*orientation/180.0;
		this.orientationVector = new CB2DVector(-Math.sin(orientationRadians),Math.cos(orientationRadians));
		return this;
	}
	
	public CBDriveModule addSpeedControllerArray(CBSpeedControllerArrayController controllerArray) {
		if (driveMode==null) {
			driveMode = controllerArray.getDriveMode();
		} else {
			if (controllerArray.getDriveMode()!=driveMode) {
				driveMode = CBDriveMode.Conflict;
			}
		}
		controllerArrays.add(controllerArray);
		return this;
	}

	/**
	 * @return the controllerArrays
	 */
	public ArrayList<CBSpeedControllerArrayController> getControllerArrays() {
		return controllerArrays;
	}
	
	public CBDriveModule update(double target) {
		for(CBSpeedControllerArrayController c:controllerArrays) {
			c.update(target);
		}
		return this;
	}

	/**
	 * @return the position
	 */
	public CB2DVector getPosition() {
		return position.copy();
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * @return the orientation in radians
	 */
	public double getOrientationRadians() {
		return orientationRadians;
	}

	/**
	 * @return the orientation vector
	 */
	public CB2DVector getOrientationVector() {
		return orientationVector;
	}

	/**
	 * @return the driveMode
	 */
	public CBDriveMode getDriveMode() {
		return driveMode;
	}
}
