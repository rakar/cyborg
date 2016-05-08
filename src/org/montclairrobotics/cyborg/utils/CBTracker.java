package org.montclairrobotics.cyborg.utils;

public class CBTracker {
	private CBSource source;
	private CBPIDController controller;
	
	public CBTracker(CBSource source, CBPIDController controller) {
		this.source = source;
		this.controller = controller;
	}
	
	public CBTracker lock() {
		controller.setTarget(source.get());
		return this;
	}
	
	public CBTracker setTarget(double target) {
		setTarget(target, true);
		return this;
	}
	
	public CBTracker setTarget(double target, boolean reset) {
		controller.setTarget(target, reset);
		return this;
	}
	
	public double update() {
		return controller.update(source.get());
	}
}
