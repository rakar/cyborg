package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBDriveControlData;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveControlData extends CBDriveControlData {

	public CBGeneralDriveControlData() {
		// TODO Auto-generated constructor stub
	}
	
	public CBDirectionVector direction = new CBDirectionVector();	
	public double rotation;

}
