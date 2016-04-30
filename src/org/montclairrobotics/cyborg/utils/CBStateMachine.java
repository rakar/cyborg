package org.montclairrobotics.cyborg.utils;

public abstract class CBStateMachine<T> {
	protected T currentState;
	protected T nextState;
	protected int cycles; 
		
	protected CBStateMachine(T start) {
		setState(start);
	}

	public void setState(T state) {
		currentState = state;
	}
	
	public void update() {
		boolean loop = true;
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
		}
	}
	
	protected void calcNextState() {};
	protected void doTransition() {};
	protected void doCurrentState() {};
	
}
