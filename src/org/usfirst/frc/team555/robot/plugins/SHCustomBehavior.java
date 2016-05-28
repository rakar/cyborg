package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

public class SHCustomBehavior extends CBBehavior {

	SHCustomRequestData crd;
	SHCustomControlData ccd;
	CBStdDriveControlData dcd;
	
	CBErrorCorrection xTracker;
	CBErrorCorrection yTracker;
	SHFireControlSM fireControl; 

	enum SHFireControlStates { Idle, SpinUp, AtSpeed, Fire, Intake };
	public class SHFireControlSM extends CBStateMachine<SHFireControlStates> {
		SHCustomRequestData grd;
		CBStdDriveRequestData drd;
		
		boolean fireReqPending;
		
		public SHFireControlSM() {
			super(SHFireControlStates.Idle);
			grd = (SHCustomRequestData) Cyborg.customRequestData;
			drd =(CBStdDriveRequestData) Cyborg.driveRequestData;
		}
		
		@Override
		public void calcNextState() {
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
				if(cycles>50) {
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
				if (cycles>150) {
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
			if(nextState == SHFireControlStates.SpinUp) {
				ccd.SpinSpeed = 0.6;
				exit();
			}
			if(nextState==SHFireControlStates.Idle) {
				ccd.ShootOut.set(CBTriStateValue.low);
				ccd.SpinSpeed = 0.0;
				exit();
			}
			if(nextState==SHFireControlStates.Intake) {
				ccd.SpinSpeed = -0.4;
				exit();
			}
			if(nextState==SHFireControlStates.Fire) {
				ccd.ShootOut.set(CBTriStateValue.high);
				exit();
			}
		}
		
		@Override
		public void doCurrentState() {
		}
	}

	public SHCustomBehavior(Cyborg robot) {
		super(robot);

		crd = (SHCustomRequestData)Cyborg.customRequestData;
		ccd = (SHCustomControlData)Cyborg.customControlData;
		dcd = (CBStdDriveControlData)Cyborg.driveControlData;
		fireControl = new SHFireControlSM();
	}
	
	public void update() {

		ccd.ArmDown.set(crd.ArmDown || crd.ArmHalfUp, crd.ArmUp);	
		ccd.HalfUp.set(crd.ArmHalfUp, crd.ArmDown);

		fireControl.update();
		
		if(crd.autoSteer) {
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
