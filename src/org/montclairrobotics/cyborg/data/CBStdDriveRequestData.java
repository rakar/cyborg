package org.montclairrobotics.cyborg.data;

import org.montclairrobotics.cyborg.CBDriveRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class CBStdDriveRequestData extends CBDriveRequestData {

	public CBStdDriveRequestData() {
		// TODO Auto-generated constructor stub
	}
	
	public CB2DVector direction = new CB2DVector();
	public double rotation;
	public boolean gyroLock;
	public double gyroLockSource;
}
