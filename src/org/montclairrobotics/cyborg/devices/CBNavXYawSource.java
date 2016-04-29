package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBSource;

public class CBNavXYawSource implements CBSource {
	private CBNavX navx;
	
	public CBNavXYawSource(Object navxDeviceID) {
		this.navx = Cyborg.getHA().getNavX(navxDeviceID);
	}
	
	public double get() {
		return navx.getYaw();
	}

}
