package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBDriveRequestStatus;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveRequestStatus extends CBDriveRequestStatus {

	public CBGeneralDriveRequestStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public CBDirectionVector direction = new CBDirectionVector();
	public double rotation;
	public boolean gyroLock;
	
}
