package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.CBAutonomous;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBStateMachine;

/*
 * Our Steamworks Autonomous modes have 5 variants of:
 * driving forward some distance, 
 * turning in place, 
 * driving forward again,
 * opening the gear mechanism,
 * driving back some
 * 
 * If create variables for our drive distances and turn angles most of them become the same.
 * And we allow for the turn and second drive to be 0 degrees and 0 distance then the special case Center Auto becomes the same too. 
 * 
 * This will be implemented with a simple linear state machine. 
 * 
 */
public class SWAutonomous extends CBAutonomous {
	SWRequestData rd = (SWRequestData)Cyborg.requestData;
	SWControlData cd = (SWControlData)Cyborg.controlData;
	CBStdDriveRequestData drd = (CBStdDriveRequestData)rd.driveData;
	double autoDriveSpeed = 0.5;
	
	private enum AutoStates {start, drive1, turn, drive2, release, waitAfterRelease, backoff};
	private class AutoStateMachine extends CBStateMachine<AutoStates> {

		protected AutoStateMachine(AutoStates start) {
			super(start);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void calcNextState() {
			switch(currentState) {
			case backoff:
				// if backoff distance reached
				break;
			case drive1:
				// if distance1 attained
				break;
			case drive2:
				// if distance2 reached
				break;
			case release:
				if(rd.leftOpenSwitch && rd.rightOpenSwitch) nextState = AutoStates.waitAfterRelease;
				break;
			case start:
				nextState = AutoStates.drive1;
				break;
			case turn:
				// if angle attained 
				break;
			case waitAfterRelease:
				if(secondsInState>1) nextState=AutoStates.backoff;
				break;
			}
			
		}

		@Override
		protected void doTransition() {
			switch(nextState) {
			case backoff:
				break;
			case drive1:
				break;
			case drive2:
				break;
			case release:
				break;
			case start:
				break;
			case turn:
				break;
			case waitAfterRelease:
				break;
			}
		}

		@Override
		protected void doCurrentState() {
		}
		
	}
	
	
	@Override
	public void init() {
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
