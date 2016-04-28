package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBSource;
import org.usfirst.frc.team555.robot.Robot.Device;

public class CBNavXYawSource implements CBSource {
	private CBNavX navx;
	
	public CBNavXYawSource(Device navx) {
		this.navx = Cyborg.getHA().getNavX(navx);
	}
	
	public double get() {
		return navx.getYaw();
	}

}
