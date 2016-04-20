package org.montclairrobotics.cyborg.utils;

public class NavXYawSource implements ISource {
	private NavX	navx;
	
	public NavXYawSource(NavX navx) {
		this.navx = navx;
	}
	
	public double get() {
		return navx.getYaw();
	}

}
