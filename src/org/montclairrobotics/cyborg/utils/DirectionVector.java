package org.montclairrobotics.cyborg.utils;

public class DirectionVector {
	
	private double x,y;
	private double angle,mag;
	//private double rotation;
	
	//public DirectionVector setXYRotDeg(double x, double y, double rot) {
	//	return setXY(x,y).setRotDeg(rot);
	//}
	
	public DirectionVector setXY(double x, double y) {
		this.x = x;
		this.y = y;
		this.angle = Math.atan2(y, x)*180.0/Math.PI;
		this.mag =  Math.sqrt(x*x+y*y);
		return this;
	}
	
	//public DirectionVector setRotDeg(double rot) {
	//	this.rotation = rot;
	//	return this;
	//}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getAngleDeg() {
		return angle;
	}
	
	public double getAngleRad() {
		return angle*Math.PI/180.0;
	}
	
	public double getMag() {
		return mag;
	}

	//public double getRotDeg() {
	//	return rotation;
	//}

	public DirectionVector copy(DirectionVector src) {
		return this.setXY(src.getX(), src.getY()); // , src.getRotDeg());
	}
}
