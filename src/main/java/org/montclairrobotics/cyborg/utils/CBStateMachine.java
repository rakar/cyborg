package org.montclairrobotics.cyborg.utils;

import java.util.Date;

/**
 * Abstract State Machine class
 * Housing the framework/basis of a state machine.
 * To create a new state machine code must be provided for:
 * calcNextState() - determine if what the next state should be
 * doTransition() - perform any functions based on transitions from one state to another
 * doCurrentState() - perform any functions based on the current state
 * @param <T> enum of states
 */
public abstract class CBStateMachine<T> {
	protected T currentState;
	protected T nextState;
	protected int cyclesInState; 
	private long stateStartTime;

	/**
	 * Duration of the current state in seconds.
	 */
	protected double secondsInState;
	protected boolean loop;
	//protected CBStateMachineLoopMode loopMode = CBStateMachineLoopMode.OneShot;
    boolean loopMode = false;
	
	/**
	 * The state machine loop modes control whether the machine can transition multiple states in a single update
	 * If this state is set to Looping doTransition() and doCurrentState() will be called for each transition, 
	 * but those transitions will be invisible outside of what this class and any overridden methods do. 
	 */
	protected enum CBStateMachineLoopMode {OneShot, Looping};
	
	protected CBStateMachine(T start) {
		setState(start);
	}

	public CBStateMachine<T> setState(T state) {
		currentState = state;
		return this;
	}
	
	public CBStateMachine<T> setLoopMode(CBStateMachineLoopMode loopMode) {
		this.loopMode = (loopMode==CBStateMachineLoopMode.Looping);
		return this;
	}
	
	public boolean isTransition(T from, T to) {
		return (currentState==from && nextState==to);
	}
	
	public boolean isTransitionFrom(T from) {
		return (currentState==from);
	}
	
	public boolean isTransitionTo(T to) {
		return (nextState==to);
	}
	
	public void update() {
		if(stateStartTime==0) {
			stateStartTime = new Date().getTime();
		}
		doCurrentState();
		secondsInState = (new Date().getTime()-stateStartTime)/1000.0;
		nextState=currentState;
		calcNextState();
		loop = currentState!=nextState;
		while(loop) {
			cyclesInState = 0;
			stateStartTime = new Date().getTime();
			secondsInState = 0;
			doTransition();
			currentState=nextState;
			doCurrentState();
            loop = loopMode;
			if(loop) {
                calcNextState();
                loop = (currentState != nextState);
            }
		}
        cyclesInState++;
	}
	
	public CBStateMachine<T> exit()
	{
		loop = false;
		return this;
	}
	
	protected abstract void calcNextState();
	protected abstract void doTransition();
	protected abstract void doCurrentState();
	
}
