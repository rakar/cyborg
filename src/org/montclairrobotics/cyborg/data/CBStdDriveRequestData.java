package org.montclairrobotics.cyborg.data;

import org.montclairrobotics.cyborg.utils.*;

public class CBStdDriveRequestData extends CBDriveRequestData {
	
	public CB2DVector direction = new CB2DVector();
	public double rotation;
	public boolean gyroLock;
	public double gyroLockSource;
	public boolean steerOnly; 
	public CB2DVector orbitOffset = new CB2DVector(); 
	public boolean orbitMode;

}
