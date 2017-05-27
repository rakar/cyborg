package org.montclairrobotics.cyborg.utils;

public class CBTarget2D extends CBEdgeTrigger {
	double xTarget;
	double yTarget;
	double xRange;
	double yRange;
	double xPosition;
	double yPosition;
	int countOnTarget = 0;
	int countOffTarget = 0;
	//CBEdgeTrigger onTarget;

	public CBTarget2D() {
		xPosition = 0;
		yPosition = 0;
		countOnTarget = 0;
		countOffTarget = 0;
	}

	public CBTarget2D setTarget(double xTarget, double yTarget, double xRange, double yRange) {
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.xRange = xRange;
		this.yRange = yRange;
		return this;
	}
	
	public CBTarget2D setPosition(double x, double y){
		xPosition = x;
		yPosition = y;
		return this;
	}
	
	public CBTarget2D update(double x, double y) {
		xPosition = x;
		yPosition = y;
		super.update(Math.abs(xTarget-xPosition)<xRange 
				&& Math.abs(yTarget-yPosition)<yRange);
		if(getState()) {
			countOnTarget++;
			countOffTarget = 0;
		} else {
			countOnTarget = 0;
			countOffTarget ++;
		}
		return this;
	}
	
	public double getXPosition() {
		return xPosition;
	}
	public double getYPosition() {
		return yPosition;
	}
	
}