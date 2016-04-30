package org.montclairrobotics.cyborg.utils;

public class CBTracker {
	private CBSource source;
	private CBPIDController controller;
	
	public CBTracker(CBSource source, CBPIDController controller) {
		this.source = source;
		this.controller = controller;
	}
	
	public void lock() {
		controller.setTarget(source.get());
	}
	
	public double update() {
		return controller.update(source.get());
	}
}
