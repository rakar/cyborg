package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBSource;

public class CBNavXYawSource implements CBSource {
	private CBNavX navx;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CBNavXYawSource(Enum navx) {
		this.navx = Cyborg.getHA().getNavX(navx);
	}
	
	public double get() {
		return navx.getYaw();
	}

}
