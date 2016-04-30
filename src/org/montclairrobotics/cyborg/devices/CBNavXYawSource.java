package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBSource;

public class CBNavXYawSource implements CBSource {
	private CBNavX navx;
	
	public CBNavXYawSource(CBDeviceID navxDeviceID) {
		this.navx = Cyborg.hardwareAdapter.getNavX(navxDeviceID);
	}
	
	public double get() {
		return navx.getYaw();
	}

}
