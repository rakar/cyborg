package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBAutonomous;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.usfirst.frc.team555.robot.Robot;

public class SHAutonomous extends CBAutonomous {
	Robot robot;
	CBStateMachine<AutoAIState> sm;

	enum AutoAIState {start,armdown,drive,done}
	public class SHAutoAISM extends CBStateMachine<AutoAIState> {
		SHCustomRequestData   crd = (SHCustomRequestData)Cyborg.customRequestData;
		CBStdDriveRequestData drd = (CBStdDriveRequestData)Cyborg.driveRequestData;;
		
		public SHAutoAISM() {
			super(AutoAIState.start);
		}
		
		@Override
		public void calcNextState() {
			switch(currentState) {
			case start:
				if (crd.selectedAuto>0) {
					nextState = AutoAIState.armdown;
				}
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
				if (crd.selectedAuto==1) {
					crd.ArmHalfUp = true;
				} else {
					crd.ArmDown = true;
				}
			} else if(currentState==AutoAIState.armdown && nextState==AutoAIState.drive) {
				drd.direction.setXY(0.0, 0.5);
				drd.rotation = 0.0;
				drd.gyroLock = true;
				drd.active = true;
			} else if(currentState==AutoAIState.drive && nextState==AutoAIState.done) {
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
