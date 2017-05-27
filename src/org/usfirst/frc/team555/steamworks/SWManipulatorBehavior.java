package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.utils.CBStateMachine;


/*
 * 
 * In Steamworks our manipulator package has two functions. 
 * 1 - Handle Gears (two arms on window motors)
 * 2 - Climb a rope (rotate a shaft with a "comb" on it using two ganged motors)
 * 
 * The Gear system is implemented in a state machine due to the "auto" functions and its use in autonomous.
 * The Climber is simply on/off under driver control so its implementation is trivial.
 * 
 */
public class SWManipulatorBehavior extends CBBehavior {

	private SWRequestData rd;
	private SWControlData cd; 
	
	/*
	 * Gear Controller State machine
	 */
	
	private enum GearStates { manual, autoOpen, autoClose };
	private class GearStateMachine extends CBStateMachine<GearStates> {

		protected GearStateMachine(GearStates start) {
			super(start);
		}
		
		// The one and ONLY thing this routine does is calculate the next state.
		// It should produce no side effects.
		@Override
		protected void calcNextState() {
			boolean manualPress = rd.gearManualLeftClose||rd.gearManualLeftOpen||rd.gearManualRightClose||rd.gearManualRightOpen;
			
			// if we complete an autoOpen/autoClose then switch to manual which is also like idle
			if (currentState==GearStates.autoOpen && rd.leftOpenSwitch && rd.rightOpenSwitch) {
				nextState = GearStates.manual;
			}
			if (currentState==GearStates.autoClose && rd.leftCloseSwitch && rd.rightCloseSwitch) {
				nextState = GearStates.manual;
			}

			// if the operator hits a button go to that state - manual overrides auto
			if(manualPress) nextState = GearStates.manual;
			else if (rd.gearAutoOpen) nextState = GearStates.autoOpen;
			else if (rd.gearAutoClose) nextState = GearStates.autoClose;
			
			// timeout - there is no way to timeout from manual operations, they're manual.
			if(secondsInState>3) nextState = GearStates.manual;
		}
		
		// This routine executes what ever needs to be done given the current state. 
		@Override
		protected void doCurrentState() {
			switch(currentState) {
			
			// follow operator commands, but respect limit switches
			case manual:
				cd.gearLeftOpen.set(rd.gearManualLeftOpen && !rd.leftOpenSwitch, rd.gearManualLeftClose && !rd.leftCloseSwitch );
				cd.gearRightOpen.set(rd.gearManualRightOpen && !rd.rightOpenSwitch, rd.gearManualRightClose && !rd.rightCloseSwitch);
				break;
			
			// close each motor until fully closed, if one motor is closed stop that motor
			case autoClose:
				cd.gearLeftOpen.set(false, !rd.leftCloseSwitch );
				cd.gearRightOpen.set(false, !rd.rightCloseSwitch);
				break;
				
			// open each motor until fully open, if one motor is open stop that motor	
			case autoOpen:
				cd.gearLeftOpen.set(!rd.leftOpenSwitch, false);
				cd.gearRightOpen.set(!rd.rightOpenSwitch, false);
				break;
			}
		}
		
		// This method handles any actions that need to be taken when transitioning from one state to another.
		@Override
		protected void doTransition() {
			
		}
	}
	
	private GearStateMachine gearController;
	
	public SWManipulatorBehavior(Cyborg robot) {
		super(robot);
		rd = (SWRequestData)Cyborg.requestData;
		cd = (SWControlData)Cyborg.controlData;		
		gearController = new GearStateMachine(GearStates.manual);
	}

	@Override
	public void update() {
		super.update();
		// to manage the gear mechanism, update the state machine which will update cd (CBControlData)
		gearController.update();
		// if the user wants to climb, climb, otherwise don't - too simple Drill Sgt.!
		cd.climb = rd.climb;
	}
	
}
