package org.montclairrobotics.cyborg.utils;

public class CBEnums {

	public enum CBDriveMode { Power, Speed };
	
	public enum CBSpeedControllerScheme {
		Basic,		// simple controllers used without internal intelligence 
		Advanced	// make use of SRX style control by off-loading correction to speed controller 
		};

	public enum CBEncoderScheme {
		None,			// No encoder used
		RoboRio,		// Encoder wired directly to roboRio
		SpeedController	// Encoder wired to an SRX style controller
		};
	

	public CBEnums() {
	}

}
