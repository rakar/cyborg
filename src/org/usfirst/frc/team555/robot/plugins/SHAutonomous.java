package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBAutonomous;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;
import org.usfirst.frc.team555.robot.Robot;

public class SHAutonomous extends CBAutonomous {
	Robot robot;
	CBStateMachine<AutoAIState> sm;
	double fireTargetX;
	double fireTargetY;
	double fireRangeX;
	double fireRangeY;
	int fireCount = 0;

	enum AutoAIState {start,armdown,drive,turnToTower, alignToTarget, fire, done}
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
				if(stateDuration>3) nextState = AutoAIState.drive;
				break;
			case drive:
				if(stateDuration>3) nextState = AutoAIState.turnToTower;
				break;
			case turnToTower:
				if(crd.targetX>20 && crd.targetX<280) nextState = AutoAIState.alignToTarget;
				break;
			case alignToTarget:
				if(Math.abs(crd.targetX-fireTargetX)<fireRangeX 
						&& Math.abs(crd.targetY-fireTargetY)<fireRangeY) {
					fireCount++;
				} else {
					fireCount = 0;
				}
				if(fireCount>25) nextState = AutoAIState.fire;
				break;
			case fire:
				nextState = AutoAIState.done;
				break;
			case done:
				break;
			default:
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
			} else if(currentState==AutoAIState.drive && nextState==AutoAIState.turnToTower) {
				drd.direction.setXY(0.0, 0.0);
				drd.rotation = 0.0;
				if(crd.selectedSide==0) drd.rotation = -0.3;
				if(crd.selectedSide==2) drd.rotation =  0.3;
				drd.gyroLock = false;
				drd.active = true;
			} else if(nextState==AutoAIState.alignToTarget) {
				crd.autoSteerX = fireTargetX;
				crd.autoSteerY = fireTargetY;
				crd.autoSteer = true;
			} else if(nextState==AutoAIState.fire) {
				crd.autoSteer = false;
				crd.fireShooter.set(CBTriStateValue.high);
			} else if(nextState==AutoAIState.done) {
				crd.fireShooter.set(CBTriStateValue.nil);
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
	
	public SHAutonomous setFireTarget(double x, double xRange, double y, double yRange) {
		this.fireTargetX = x;
		this.fireTargetY = y;
		this.fireRangeX = xRange;
		this.fireRangeY = yRange;
		return this;
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
