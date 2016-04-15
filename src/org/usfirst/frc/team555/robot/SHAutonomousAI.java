package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.AutonomousAI;

enum AutoAIState {start,armdown,drive,done};

public class SHAutonomousAI extends AutonomousAI {
	Robot robot;
	SHAutoAISM sm;
	
	public SHAutonomousAI(Robot robot) {
		this.robot = robot;
		sm = new SHAutoAISM(robot);
	}
	
	@Override
	public void Update() {
		sm.Update();
	}

}
