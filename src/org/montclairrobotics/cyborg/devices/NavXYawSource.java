package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.ISource;

public class NavXYawSource implements ISource {
	private NavX navx;
	
	public NavXYawSource(NavX navx) {
		this.navx = navx;
	}
	
	public double get() {
		return navx.getYaw();
	}

}
