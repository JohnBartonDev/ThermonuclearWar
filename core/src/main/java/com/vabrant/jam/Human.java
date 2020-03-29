package com.vabrant.jam;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.vabrant.actionsystem.actions.ActionManager;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;
import com.vabrant.jam.Constants.PlayerType;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Human extends Player {

	private final float platformXOffset = (Constants.GAME_WIDTH - Constants.PLATFORM_WIDTH) / 2;
	Vector2 touch;
	DeployableType selectedCharacter = DeployableType.NONE;
	private Input input;
	private SelectedDeployableHud selectedHud;
	
	public Human(ActionManager manager, TextureAtlas atlas, GroundPlatform ground, WaterPlatform water, AirPlatform air) {
		super(manager, PlayerType.HUMAN, ground, water, air);
		touch = new Vector2();
		input = new Input(this);
		
		selectedHud = new SelectedDeployableHud(atlas, this);
	}
	
	@Override
	public void createGovernment() {
		super.createGovernment();
		government.translateX(-government.getWidth() / 2);
	}
	
	public Input getInput() {
		return input;
	}
	
	@Override
	public void draw(Batch batch, ShapeDrawer shapeDrawer) {
		super.draw(batch, shapeDrawer);
		selectedHud.draw(batch, shapeDrawer);
	}
	
	private class Input extends InputAdapter {
		
		private final Human human;
		
		public Input(Human human) {
			this.human = human;
		}
		
		@Override
		public boolean keyDown(int keycode) {
			switch(keycode) {
				case Keys.Z:
					selectedCharacter = DeployableType.TANK;
					break;
				case Keys.X:
					selectedCharacter = DeployableType.SHIP;
					break;
				case Keys.C:
					selectedCharacter = DeployableType.CHOPPER;
					break;
				case Keys.V:
					selectedCharacter = DeployableType.NUKE;
				break;
			}
			return super.keyDown(keycode);
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if(selectedCharacter == null) return false;
			
			float platformX = screenX - platformXOffset;
			float platformY = screenY - Constants.PLATFORM_Y_OFFSET;
			
			if(platformX < 0 || platformX > (Constants.PLATFORM_WIDTH / 2)) return false;
			if(platformY < 0 || platformY > Constants.PLATFORM_HEIGHT) return false;
			
			Lane lane = null;
			LaneSection section = null;
			
			lane = platformY < (Constants.PLATFORM_HEIGHT / 2) ? Lane.LANE_1 : Lane.LANE_2;
			
			if(platformX < Constants.LANE_SECTION_WIDTH) {
				section = LaneSection.LANE_SECTION_1;
			}
			else if(platformX < (Constants.LANE_SECTION_WIDTH * 2)) {
				section = LaneSection.LANE_SECTION_2;
			}
			else {
				section = LaneSection.LANE_SECTION_3;
			}
			
			platformController.deploy(human, oppositePlayer, selectedCharacter, lane, section);
//			oppositePlayer.platformController.deploy(oppositePlayer, human, selectedCharacter, lane, section);
			
			return super.touchDown(screenX, screenY, pointer, button);
		}
		
		@Override
		public boolean scrolled(int amount) {
			if(amount > 0) {
				platformController.nextPlatform();
			}
			else {
				platformController.previousPlatform();
			}
			return false;
		}
	}

}
