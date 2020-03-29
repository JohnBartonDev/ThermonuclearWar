package com.vabrant.jam;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;
import com.vabrant.jam.ai.AIState;
import com.vabrant.jam.ai.Condition;

public class AttackState implements AIState {

	private DeployableType deployType = DeployableType.TANK;
	private ElixirCondition elixirCondition;
	private final Array<Condition> attackConditions;
	
	public AttackState() {
		elixirCondition = new ElixirCondition();
		attackConditions = new Array<>();
	}

	@Override
	public void enter(AI entity) {

	}
	
	private void findType() {
		if(deployType != null) return; 
		
		switch(MathUtils.random(0, 2)) {
			case 0:
				deployType = DeployableType.TANK;
				break;
			case 1:
				deployType = DeployableType.SHIP;
				break;
			case 2:
				deployType = DeployableType.CHOPPER;
				break;
		}
		elixirCondition.neededElixr = deployType.getElixrCost();
	}
	
	private void deploy(AI entity) {
		if(deployType == null) return;
		if(!elixirCondition.isTrue(entity)) return;
		makeDeployable(entity);
		entity.deploy(deployType, Lane.LANE_1, LaneSection.LANE_SECTION_3);
		deployType = null;
	}
	
	private void makeDeployable(AI owner) {
		if(!owner.platformController.isDeployable(owner, deployType)) {
			for(int i = 0; i < 3; i++) {
				owner.platformController.nextPlatform();
				if(owner.platformController.isDeployable(owner, deployType)) {
					break;
				}
			}
		}
	}

	@Override
	public void update(AI entity) {
		findType();
		deploy(entity);
	}

	@Override
	public void exit(AI entity) {
	}

	@Override
	public boolean onMessage(AI entity, Telegram telegram) {
		return false;
	}

	private class ElixirCondition implements Condition {

		int neededElixr;
		
		@Override
		public boolean isTrue(AI owner) {
			if(owner.getElixir() > neededElixr) return true;
			return false;
		}
	}

}
