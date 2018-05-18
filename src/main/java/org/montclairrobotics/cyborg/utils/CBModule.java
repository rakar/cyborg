package org.montclairrobotics.cyborg.utils;

import org.montclairrobotics.cyborg.Cyborg;

public abstract class CBModule {
	public Cyborg robot;
	
	public CBModule(Cyborg robot) {
		this.robot = robot;
	}

}
