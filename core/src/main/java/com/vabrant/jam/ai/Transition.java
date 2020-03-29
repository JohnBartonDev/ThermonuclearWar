package com.vabrant.jam.ai;

import com.badlogic.gdx.utils.Array;
import com.vabrant.jam.AI;

public class Transition {

	public AIState fromState;
	public AIState toState;
	public Array<Condition> conditions;

	public Transition(AIState fromState, AIState toState) {
		this.fromState = fromState;
		this.toState = toState;
		conditions = new Array<>(5);
	}
	
	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public boolean checkConditions(AI ai) {
		for(int i = 0; i < conditions.size; i++) {
			if(conditions.get(i).isTrue(ai)) return true;
		}
		return false;
	}
}
