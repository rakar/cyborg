package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBDriveRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveRequestData extends CBDriveRequestData {

	public CBGeneralDriveRequestData() {
		// TODO Auto-generated constructor stub
	}
	
	public CB2DVector direction = new CB2DVector();
	public double rotation;
	public boolean gyroLock;
}
