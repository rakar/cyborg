package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

public class SWManipulatorBehavior extends CBBehavior {

	private SWRequestData rd;
	private SWControlData cd; 
	
	private enum GearStates { manual, autoOpen, autoClose };
	private class GearController extends CBStateMachine<GearStates> {

		protected GearController(GearStates start) {
			super(start);
		}
		
		@Override
		protected void calcNextState() {
			boolean manualPress = rd.gearManualLeftClose||rd.gearManualLeftOpen||rd.gearManualRightClose||rd.gearManualRightOpen;
			
			if (currentState==GearStates.autoOpen && rd.leftOpenSwitch && rd.rightOpenSwitch) {
				nextState = GearStates.manual;
			}
			
			if (currentState==GearStates.autoClose && rd.leftCloseSwitch && rd.rightCloseSwitch) {
				nextState = GearStates.manual;
			}

			if(manualPress) nextState = GearStates.manual;
			else if (rd.gearAutoOpen) nextState = GearStates.autoOpen;
			else if (rd.gearAutoClose) nextState = GearStates.autoClose;
			
			if(secondsInState>2) nextState = GearStates.manual;
		}
		
		@Override
		protected void doCurrentState() {
			switch(currentState) {
			case manual:
				cd.gearLeftOpen.set(rd.gearManualLeftOpen && !rd.leftOpenSwitch, rd.gearManualLeftClose && !rd.leftCloseSwitch );
				cd.gearRightOpen.set(rd.gearManualRightOpen && !rd.rightOpenSwitch, rd.gearManualRightClose && !rd.rightCloseSwitch);
				break;
			case autoClose:
				cd.gearLeftOpen.set(false, !rd.leftCloseSwitch );
				cd.gearRightOpen.set(false, !rd.rightCloseSwitch);
				break;
			case autoOpen:
				cd.gearLeftOpen.set(!rd.leftOpenSwitch, false);
				cd.gearRightOpen.set(!rd.rightOpenSwitch, false);
				break;
			default:
				cd.gearLeftOpen.set(CBTriStateValue.nil);
				cd.gearRightOpen.set(CBTriStateValue.nil);
				break;
			}
		}
		
		@Override
		protected void doTransition() {
			
		}
		
	}
	
	private GearController gearController;
	
	public SWManipulatorBehavior(Cyborg robot) {
		super(robot);
		rd = (SWRequestData)Cyborg.requestData;
		cd = (SWControlData)Cyborg.controlData;		
		gearController = new GearController(GearStates.manual);
	}

	@Override
	public void update() {
		super.update();
		gearController.update();
	}
	
}
