package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBDriveControlStatus;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveControlStatus extends CBDriveControlStatus {

	public CBGeneralDriveControlStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public CBDirectionVector direction = new CBDirectionVector();
	public double rotation;

}
