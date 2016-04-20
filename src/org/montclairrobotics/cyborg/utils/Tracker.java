package org.montclairrobotics.cyborg.utils;

public class Tracker {
	private ISource source;
	private PID controller;
	
	public Tracker(ISource source, PID controller) {
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
