package com.vabrant.jam;

import com.vabrant.actionsystem.actions.ActionManager;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;
import com.vabrant.jam.Constants.PlayerType;
import com.vabrant.jam.ai.AIStateMachine;

public class AI extends Player {
	
	AttackState attackState;
	AIStateMachine stateMachine;
	
	public AI(ActionManager manager, GroundPlatform ground, WaterPlatform water, AirPlatform air) {
		super(manager, PlayerType.AI, ground, water, air);
		
		attackState = new AttackState();
		stateMachine = new AIStateMachine(this, null);
		stateMachine.changeState(attackState);
	}
	
	protected void deploy(DeployableType c, Lane lane, LaneSection section) {
		platformController.deploy(this, oppositePlayer, c, lane, section);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		stateMachine.update();
	}
	
	@Override
	public void createGovernment() {
		super.createGovernment();
		government.translateX(Constants.PLATFORM_WIDTH - (government.getWidth() / 2));
	}

}
