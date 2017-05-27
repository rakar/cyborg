package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.CBAutonomous;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTarget2D;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

public class SHAutonomous extends CBAutonomous {
	SHRobot robot;
	SHCustomRequestData   crd; // = (SHCustomRequestData)Cyborg.customRequestData;
	CBStdDriveRequestData drd; // = (CBStdDriveRequestData)Cyborg.driveRequestData;;
	CBStateMachine<AutoAIState> sm;
	CBTarget2D fireTarget;

	enum AutoAIState {start,armdown,drive,turnToTower, alignToTarget, fire, done}
	public class SHAutoAISM extends CBStateMachine<AutoAIState> {
		SHCustomRequestData   crd = (SHCustomRequestData)Cyborg.requestData;
		CBStdDriveRequestData drd = (CBStdDriveRequestData)Cyborg.requestData.driveData;
		
		public SHAutoAISM() {
			super(AutoAIState.start);
		}
		
		@Override
		public void calcNextState() {
			switch(currentState) {
			case start:
				if (crd.selectedAuto>0) {
					setState(AutoAIState.armdown);
				}
				break;
			case armdown:
				if(secondsInState>3) setState(AutoAIState.drive);
				break;
			case drive:
				if(secondsInState>10) setState(AutoAIState.turnToTower);
				break;
			case turnToTower:
				if(crd.targetX>20 && crd.targetX<280) {
					fireTarget.update(false);
					setState(AutoAIState.alignToTarget);
				}
				break;
			case alignToTarget:
				//if(Math.abs(crd.targetX-fireTargetX)<fireRangeX 
				//		&& Math.abs(crd.targetY-fireTargetY)<fireRangeY) {
				//	fireCount++;
				//} else {
				//	fireCount = 0;
				//}
				if(fireTarget.getTrueCount()>25) setState(AutoAIState.fire);
				break;
			case fire:
				setState(AutoAIState.done);
				break;
			case done:
				break;
			default:
				break;			
			}
		}
		
		@Override
		public void doTransition() {
			if(isTransition(AutoAIState.start, AutoAIState.armdown)) {
				if (crd.selectedAuto==1) {
					crd.ArmHalfUp = true;
				} else {
					crd.ArmDown = true;
				}
			} else if(isTransition(AutoAIState.armdown, AutoAIState.drive)) {
				drd.direction.setXY(0.0, 30); // forward at 30 frames per second
				drd.rotation = 0.0;
				drd.gyroLockActive = true;
				drd.active = true;
			} else if(isTransition(AutoAIState.drive, AutoAIState.turnToTower)) {
				drd.direction.setXY(0.0, 0.0);
				drd.rotation = 0.0;
				if(crd.selectedSide==0) drd.rotation = -30;
				if(crd.selectedSide==2) drd.rotation =  30;
				drd.gyroLockActive = false;
				drd.active = true;
			} else if(isTransitionTo(AutoAIState.alignToTarget)) {
				crd.autoSteerX = fireTarget.getXPosition();
				crd.autoSteerY = fireTarget.getYPosition();
				crd.autoSteer = true;
			} else if(isTransitionTo(AutoAIState.fire)) {
				crd.autoSteer = false;
				crd.fireShooter.set(CBTriStateValue.high);
			} else if(isTransitionTo(AutoAIState.done)) {
				crd.fireShooter.set(CBTriStateValue.nil);
			}
		}
		
		@Override
		public void doCurrentState() {
		}
	}

	public SHAutonomous(SHRobot robot) {
		this.robot = robot;
		crd = (SHCustomRequestData)Cyborg.requestData;
		drd = (CBStdDriveRequestData)Cyborg.requestData.driveData;
		sm = new SHAutoAISM();
		fireTarget = new CBTarget2D();
	}
	
	public SHAutonomous setFireTarget(double x, double y, double xRange, double yRange) {
		fireTarget.setTarget(x, y, xRange, yRange);
		return this;
	}

	@Override
	public void init() {
		sm.setState(AutoAIState.start);
	}

	@Override
	public void update() {
		fireTarget.update(crd.targetX,crd.targetY);
		sm.update();
	}	
}
