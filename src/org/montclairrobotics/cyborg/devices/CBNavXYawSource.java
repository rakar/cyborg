package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.CBSource;

public class CBNavXYawSource implements CBSource {
	private CBNavX navx;
	
	public CBNavXYawSource(CBNavX navx) {
		this.navx = navx;
	}
	
	public double get() {
		return navx.getYaw();
	}

}
