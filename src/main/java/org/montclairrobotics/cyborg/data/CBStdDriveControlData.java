package org.montclairrobotics.cyborg.data;

import org.montclairrobotics.cyborg.utils.*;

public class CBStdDriveControlData extends CBDriveControlData {

	public CB2DVector direction = new CB2DVector();	
	public double rotation;
	public boolean steerOnly; 
	public CB2DVector orbitOffset = new CB2DVector(); 
	/* To turn off orbitMode, set offset to (0,0) 
	 * or simply never use it. 
	 */
	//public boolean orbitMode;

}
