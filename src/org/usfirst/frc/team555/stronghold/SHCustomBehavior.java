package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

public class SHCustomBehavior extends CBBehavior {

	SHCustomRequestData crd = (SHCustomRequestData)Cyborg.requestData;
	SHCustomControlData ccd = (SHCustomControlData)Cyborg.controlData;
	CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.controlData.driveData;
	
	CBErrorCorrection xTracker;
	CBErrorCorrection yTracker;
	SHFireControlSM fireControl; 
	CBEdgeTrigger autoSteerEdgeTrigger;

	enum SHFireControlStates { Idle, SpinUp, AtSpeed, Fire, Intake };
	public class SHFireControlSM extends CBStateMachine<SHFireControlStates> {
		SHCustomRequestData grd;
		CBStdDriveRequestData drd;
		int cycleCheck=0;
		
		boolean fireReqPending;
		
		public SHFireControlSM() {
			super(SHFireControlStates.Idle);
			grd = (SHCustomRequestData) Cyborg.requestData;
			drd =(CBStdDriveRequestData) Cyborg.requestData.driveData;
		}
		
		@Override
		public void calcNextState() {
			
			cycleCheck++;
			
			if (grd.fireShooter.isHigh()){
				fireReqPending=true;
			}
			switch(currentState) {
			case Idle:
				if (grd.spinUpShooter || grd.fireShooter.isHigh()){
					nextState = SHFireControlStates.SpinUp;
				}
				break;
			case AtSpeed:
				if (fireReqPending) {
					nextState = SHFireControlStates.Fire;
				}
				break;
			case Fire:
				if(secondsInState>.4) {
					nextState = SHFireControlStates.Idle;
				}
				break;
			case Intake:
				if (grd.spinUpShooter || grd.fireShooter.isHigh()){
					nextState = SHFireControlStates.SpinUp;
				} else {
					if (!grd.intake) {
						nextState = SHFireControlStates.Idle;
					}
				}
				break;
			case SpinUp:
				if (secondsInState>.6) {
					nextState=SHFireControlStates.AtSpeed;
				}
				break;
			default:
				break;
			}
			if (grd.intake){
				nextState = SHFireControlStates.Intake;
			}
		}
		
		@Override
		public void doTransition() {
			if(isTransitionTo(SHFireControlStates.SpinUp)) {
				ccd.SpinSpeed = 0.6;
				cycleCheck=0;
				//exit();
			}
			if(isTransitionTo(SHFireControlStates.Idle)) {
				ccd.ShootOut.set(CBTriStateValue.low);
				ccd.SpinSpeed = 0.0;
				this.fireReqPending = false;
				//exit();
			}
			if(isTransitionTo(SHFireControlStates.Intake)) {
				ccd.SpinSpeed = -0.4;
				this.fireReqPending = false;
				//exit();
			}
			if(isTransitionTo(SHFireControlStates.Fire)) {
				ccd.ShootOut.set(CBTriStateValue.high);
				this.fireReqPending = false;
				//SmartDashboard.putNumber("CycleCheck", cycleCheck);
				//exit();
			}
		}
		
		@Override
		public void doCurrentState() {
		}
	}

	public SHCustomBehavior(SHRobot robot) {
		super(robot);
		fireControl = new SHFireControlSM();
		autoSteerEdgeTrigger = new CBEdgeTrigger();
	}
	
	public void update() {

		ccd.ArmDown.set(crd.ArmDown || crd.ArmHalfUp, crd.ArmUp);	
		ccd.HalfUp.set(crd.ArmHalfUp, crd.ArmDown);

		fireControl.update();
		
		autoSteerEdgeTrigger.update(crd.autoSteer);
		if(autoSteerEdgeTrigger.getRisingEdge()) {
			xTracker.setTarget(crd.autoSteerX);
			yTracker.setTarget(crd.autoSteerY);
		}
		if(autoSteerEdgeTrigger.getState()) {
			dcd.rotation = 0;
			dcd.direction.setXY(0, 0);
			dcd.active=true;
			if(crd.targetX>-1) {		
				dcd.rotation = xTracker.update(crd.targetX);
				//SmartDashboard.putNumber("autoSteerRotation", dcd.rotation);
			}
			if(crd.targetY>-1) {		
				dcd.direction.setXY(0, yTracker.update(crd.targetY));
				//SmartDashboard.putNumber("autoSteerForward", dcd.direction.getY());
			}
		}
	}
	
	public SHCustomBehavior setXTracker(CBErrorCorrection tracker) {
		xTracker = tracker;
		return this;
	}
	
	public SHCustomBehavior setYTracker(CBErrorCorrection tracker) {
		yTracker = tracker;
		return this;
	}
}
