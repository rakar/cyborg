package org.montclairrobotics.cyborg.utils;

public abstract class CBStateMachine<T> {
	protected T currentState;
	protected T nextState;
	protected int cycles; 
	protected boolean loop;
	protected CBStateMachineLoopMode loopMode = CBStateMachineLoopMode.OneShot;
	
	enum CBStateMachineLoopMode {OneShot, Looping};
	
	protected CBStateMachine(T start) {
		setState(start);
	}

	public CBStateMachine<T> setState(T state) {
		currentState = state;
		return this;
	}
	
	public CBStateMachine<T> setLoopMode(CBStateMachineLoopMode loopMode) {
		this.loopMode = loopMode;
		return this;
	}
	
	public void update() {
		loop = true;
		while(loop) {
			calcNextState();
			loop = currentState!=nextState;
			if(loop) {
				cycles = 0;
				doTransition();
			}
			currentState=nextState;
			doCurrentState();
			cycles++;
			loop = loop && (loopMode == CBStateMachineLoopMode.Looping);
		}
	}
	
	public CBStateMachine<T> exit()
	{
		loop = false;
		return this;
	}
	
	protected void calcNextState() {};
	protected void doTransition() {};
	protected void doCurrentState() {};
	
}
