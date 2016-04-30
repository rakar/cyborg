package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBTeleOpMapper extends CBModule {
	
public CBTeleOpMapper(Cyborg robot) {
	super(robot);
	// TODO Auto-generated constructor stub
}

	public abstract void update();
}
