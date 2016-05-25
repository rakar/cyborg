package org.montclairrobotics.cyborg.utils;

/**
 * Generalized 2D Vector
 * Includes Cartesian and Polar conversions and utilities  
 */
public class CB2DVector {
	
	private double x,y;
	private double angle,mag;
	
	public CB2DVector() {
		
	}
	public CB2DVector( double x, double y) {
		setXY(x,y);
	}

	/**
	 * Sets the DirectionVector based on Cartesian coordinates 
	 * 
	 * @param x left(-)/right(+) direction
	 * @param y forward(+)/back(-) direction
	 * @return  current object
	 */
	public CB2DVector setXY(double x, double y) {
		this.x = x;
		this.y = y;
		// angle is based on 0 degrees being "forward" 
		// and + is counterclockwise.
		this.angle = Math.atan2(-x, y)*180.0/Math.PI;
		this.mag =  Math.sqrt(x*x+y*y);
		return this;
	}
		
	/**
	 * Returns the left(-)/right(+) direction of the Cartesian coordinates 
	 * 
	 * @return x left(-)/right(+) direction
	 * 
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the forward(+)/back(-) direction of the Cartesian coordinates 
	 * 
	 * @return y forward(+)/back(-) direction
	 * 
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the angle of the direction vector 
	 * 
	 * @return angle in degrees (0 = forward)
	 * 
	 */
	public double getAngleDeg() {
		return angle;
	}
	
	/**
	 * Returns the angle of the direction vector 
	 * 
	 * @return angle in radians (0 = forward)
	 * 
	 */
	public double getAngleRad() {
		return angle*Math.PI/180.0;
	}
	
	/**
	 * Returns the magnitude of the direction vector 
	 * 
	 * @return magnitude of direction vector
	 * 
	 */
	public double getMag() {
		return mag;
	}

	public CB2DVector copy(CB2DVector src) {
		return this.setXY(src.getX(), src.getY()); // , src.getRotDeg());
	}
}
