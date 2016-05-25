package org.montclairrobotics.assemblies;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.utils.CB2DVector;

public class CBDriveModule {

	private ArrayList<CBSpeedControllerArrayController> controllerArrays = new ArrayList<>();
	private CB2DVector position = new CB2DVector();
	private double orientation;
	
	public CBDriveModule() {
	}
	
	public CBDriveModule(CB2DVector position, double orientation) {
		setPlacement(position, orientation);
	}
	
	public CBDriveModule setPlacement(CB2DVector position, double orientation) {
		this.position = position;
		this.orientation = orientation;
		return this;
	}
	
	public CBDriveModule addSpeedControllerArray(CBSpeedControllerArrayController controllerArray) {
		controllerArrays.add(controllerArray);
		return this;
	}

	/**
	 * @return the controllerArrays
	 */
	public ArrayList<CBSpeedControllerArrayController> getControllerArrays() {
		return controllerArrays;
	}

	/**
	 * @return the position
	 */
	public CB2DVector getPosition() {
		return position;
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return orientation;
	}
}
