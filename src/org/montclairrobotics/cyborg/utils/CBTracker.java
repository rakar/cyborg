package org.montclairrobotics.cyborg.utils;

public class CBTracker {
	private CBSource source;
	private CBPID controller;
	
	public CBTracker(CBSource source, CBPID controller) {
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
