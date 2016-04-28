package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBDriveRequestMapper extends CBModule {
	
public CBDriveRequestMapper(Cyborg robot) {
	super(robot);
	// TODO Auto-generated constructor stub
}

	public abstract void update();
}
