package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.plugins.CBStdDriveControlData;
import org.montclairrobotics.cyborg.plugins.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHCustomBehavior extends CBBehavior {

	SHCustomRequestData grd;
	SHCustomControlData gcd;
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
				gcd.SpinSpeed = 0.6;
			}
			if(nextState==SHFireControlStates.Idle) {
				gcd.ShootOut.set(CBTriStateValue.low);
				gcd.SpinSpeed = 0.0;
			}
			if(nextState==SHFireControlStates.Intake) {
				gcd.SpinSpeed = -0.4;
			}
			if(nextState==SHFireControlStates.Fire) {
				gcd.ShootOut.set(CBTriStateValue.high);
			}
		}
		
		@Override
		public void doCurrentState() {
		}
	}

	public SHCustomBehavior(Cyborg robot) {
		super(robot);

		grd = (SHCustomRequestData)Cyborg.customRequestData;
		gcd = (SHCustomControlData)Cyborg.customControlData;
		dcd = (CBStdDriveControlData)Cyborg.driveControlData;
		fireControl = new SHFireControlSM();
	}
	
	public void update() {

		gcd.ArmDown.set(grd.ArmDown || grd.ArmHalfUp, grd.ArmUp);	
		gcd.HalfUp.set(grd.ArmHalfUp, grd.ArmDown);

		fireControl.update();
		
		if(grd.autoSteer) {
			dcd.rotation = 0;
			dcd.direction.setXY(0, 0);
			dcd.active=true;
			if(grd.targetX>-1) {		
				dcd.rotation = xTracker.update(grd.targetX);
				SmartDashboard.putNumber("autoSteerRotation", dcd.rotation);
			}
			if(grd.targetY>-1) {		
				dcd.direction.setXY(0, yTracker.update(grd.targetY));
				SmartDashboard.putNumber("autoSteerForward", dcd.direction.getY());
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
