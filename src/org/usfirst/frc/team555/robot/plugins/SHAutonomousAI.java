package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBAutonomousAI;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.plugins.CBGeneralDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.usfirst.frc.team555.robot.Robot;

public class SHAutonomousAI extends CBAutonomousAI {
	Robot robot;

	enum AutoAIState {start,armdown,drive,done}

	public class SHAutoAISM extends CBStateMachine<AutoAIState> {
		//private Robot robot;

		
		public SHAutoAISM(Robot robot) {
			super(AutoAIState.start);
			//this.robot = robot;
		}
		
		@Override
		public void calcNextState() {
			switch(currentState) {
			case start:
				nextState = AutoAIState.armdown;
				break;
			case armdown:
				if(cycles>175) nextState = AutoAIState.drive;
				break;
			case drive:
				if(cycles>175) nextState = AutoAIState.done;
				break;
			case done:
				break;			
			}
		}
		
		@Override
		public void doTransition() {

			SHManipulatorRequestData mrd = (SHManipulatorRequestData) Cyborg.manipulatorRequestData;
			CBGeneralDriveRequestData drd =(CBGeneralDriveRequestData) Cyborg.driveRequestData;
			if(currentState==AutoAIState.start && nextState==AutoAIState.armdown) {
				mrd.ArmDown.set(true, false);
				mrd.HalfUp.set(true, false);			
			}
			if(currentState==AutoAIState.armdown && nextState==AutoAIState.drive) {
				drd.direction.setXY(0.0, 0.5);
				drd.rotation = 0.0;
				drd.gyroLock = true;
				drd.active = true;
			}
			if(currentState==AutoAIState.drive && nextState==AutoAIState.done) {
				drd.direction.setXY(0.0, 0.0);
				drd.rotation = 0.0;
				drd.gyroLock = false;
				drd.active = true;
			}
		}
		
		@Override
		public void doCurrentState() {
			
		}
	}

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
