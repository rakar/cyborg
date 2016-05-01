package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBAutonomous;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.plugins.CBGeneralDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.usfirst.frc.team555.robot.Robot;

public class SHAutonomous extends CBAutonomous {
	Robot robot;

	enum AutoAIState {start,armdown,drive,done}

	public class SHAutoAISM extends CBStateMachine<AutoAIState> {
		SHGeneralRequestData mrd;
		CBGeneralDriveRequestData drd;
		
		public SHAutoAISM() {
			super(AutoAIState.start);
			mrd = (SHGeneralRequestData) Cyborg.generalRequestData;
			drd =(CBGeneralDriveRequestData) Cyborg.driveRequestData;
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


			if(currentState==AutoAIState.start && nextState==AutoAIState.armdown) {
				mrd.ArmDown.set(true, false);
				mrd.HalfUp.set(mrd.selectedAuto==0, mrd.selectedAuto!=0);			
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

	public SHAutonomous(Robot robot) {
		this.robot = robot;
		sm = new SHAutoAISM();
	}

	@Override
	public void init() {
		sm.setState(AutoAIState.start);
	}

	@Override
	public void update() {
		sm.update();
	}
	
}
