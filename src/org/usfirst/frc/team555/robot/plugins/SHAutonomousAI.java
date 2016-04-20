package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.AutonomousAI;
import org.usfirst.frc.team555.robot.Robot;

enum AutoAIState {start,armdown,drive,done};

public class SHAutonomousAI extends AutonomousAI {
	Robot robot;
	SHAutoAISM sm;
	
	public SHAutonomousAI(Robot robot) {
		this.robot = robot;
		sm = new SHAutoAISM(robot);
	}
	
	@Override
	public void update() {
		sm.update();
	}

}
