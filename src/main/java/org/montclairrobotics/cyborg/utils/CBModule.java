package org.montclairrobotics.cyborg.utils;

import org.montclairrobotics.cyborg.Cyborg;

public abstract class CBModule {
	public Cyborg robot;
	private boolean isActive;

	public boolean IsActive() {
		return isActive;
	}

	public void SetActive(boolean active) {
		isActive = active;
	}

	public CBModule(Cyborg robot) {
		this.robot = robot;
	}

}
