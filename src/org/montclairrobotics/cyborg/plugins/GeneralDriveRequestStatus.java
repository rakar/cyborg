package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.DriveRequestStatus;
import org.montclairrobotics.cyborg.utils.*;

public class GeneralDriveRequestStatus extends DriveRequestStatus {

	public GeneralDriveRequestStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public DirectionVector direction;
	public double rotation;
	
	public boolean gyroLock;
	

}
