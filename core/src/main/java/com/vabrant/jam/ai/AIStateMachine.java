package com.vabrant.jam.ai;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.utils.Array;
import com.vabrant.jam.AI;

public class AIStateMachine extends DefaultStateMachine<AI, AIState> {

	private Array<Transition> transitions;
	
	public AIStateMachine(AI ai, AIState initialState) {
		super(ai, initialState);
		transitions = new Array<>(2);
	}
	
	public void addTransition(Transition transition) {
		transitions.add(transition);
	}
}
