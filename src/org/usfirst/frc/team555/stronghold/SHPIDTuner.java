package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBPIDErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHPIDTuner extends CBBehavior {
	SHCustomRequestData crd = (SHCustomRequestData)Cyborg.requestData;
	SHCustomControlData ccd = (SHCustomControlData)Cyborg.controlData;
	CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.controlData.driveData;
	
	CBEdgeTrigger tuneEnableEdgeTrigger;
	CBEdgeTrigger tuneCycleEdgeTrigger;

	SHPIDTuneSM tunerSM = new SHPIDTuneSM();

	CBErrorCorrection pidEC;
	
	boolean shutdown = false;
	double moveDuration = 0.5;
	double moveSpeed = 20.0; //inches per second
	double testSpeed;
	double testConstants[]= {0,0,0};
	double testSteps[] = {0.1,0.1,0.1};
	double currCycleError;
	double lastCycleError;
	double currPower = 0;

	enum SHPIDTuneStates { Idle, SpinUp, SpinDown, ReverseUp, ReverseDown, CycleComplete };
	public class SHPIDTuneSM extends CBStateMachine<SHPIDTuneStates> {
		SHCustomRequestData grd;
		CBStdDriveRequestData drd;		
		
		public SHPIDTuneSM() {
			super(SHPIDTuneStates.Idle);
		}
		
		@Override
		public void calcNextState() {
			if(!tuneEnableEdgeTrigger.getState()) {
				nextState = SHPIDTuneStates.Idle;
				if(tuneEnableEdgeTrigger.getFallingEdge()) {
					shutdown = true;
				}
			} else {
				switch(currentState) {
				case Idle:
					if (tuneCycleEdgeTrigger.getRisingEdge()) nextState = SHPIDTuneStates.SpinUp;
					break;
				case ReverseDown:	
					if (this.secondsInState>=moveDuration) nextState=SHPIDTuneStates.CycleComplete;
					break;
				case ReverseUp:
					if (this.secondsInState>=moveDuration) nextState=SHPIDTuneStates.ReverseDown;
					break;
				case SpinDown:
					if (this.secondsInState>=moveDuration) nextState=SHPIDTuneStates.ReverseUp;
					break;
				case SpinUp:
					if (this.secondsInState>=moveDuration) nextState=SHPIDTuneStates.SpinDown;
					break;
				case CycleComplete:
					nextState = SHPIDTuneStates.Idle;
					break;
				default:
					break;
				}
			}
		}
		
		@Override
		public void doTransition() {
			if(nextState==SHPIDTuneStates.Idle) {
				testSpeed=0;
			}
			if(nextState==SHPIDTuneStates.SpinUp) {
				testSpeed=moveSpeed;
			}
			if(nextState==SHPIDTuneStates.SpinDown) {
				testSpeed=0;
			}
			if(nextState==SHPIDTuneStates.ReverseUp) {
				testSpeed=-moveSpeed;
			}
			if(nextState==SHPIDTuneStates.ReverseDown) {
				testSpeed=0;
			}
			if(nextState==SHPIDTuneStates.CycleComplete) {
				testSpeed=0;
				shutdown = true;
				recalcConstants();
			}
		}
		
		@Override
		public void doCurrentState() {
			if(currentState!=SHPIDTuneStates.Idle) {
				currCycleError += pidEC.getError();
			}
		}
	}

	public SHPIDTuner(Cyborg robot) {
		super(robot);
		pidEC = new CBPIDErrorCorrection()
				.setConstants(testConstants)
				.reset();
	}
	
	public void update() {
		/*
		this.tuneEnableEdgeTrigger.update(crd.pidTuneEnable);
		this.tuneCycleEdgeTrigger.update(crd.pidTuneCycle);

		tunerSM.update();

		if (tuneEnableEdgeTrigger.getState()) {
			dcd.active = true;
			dcd.direction.setXY(0, 0);
			currPower += pidEC.update(testSpeed);
			dcd.rotation = currPower;
		}

		if (shutdown) {
			dcd.active = true;
			dcd.direction.setXY(0, 0);
			dcd.rotation = 0;
			shutdown = false;
		}
		*/
	}
	
	public void recalcConstants() {
		lastCycleError = currCycleError;
		SmartDashboard.putNumber("LastCycleError:", lastCycleError);
		SmartDashboard.putNumber("CurrCycleError:", currCycleError);
		
		// Do stuff
		
		pidEC.setConstants(testConstants).reset();
	}
}
