package com.vabrant.jam.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants;
import com.vabrant.jam.Constants.Direction;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;
import com.vabrant.jam.Constants.PlayerType;
import com.vabrant.jam.GroundPlatform;
import com.vabrant.jam.Platform;
import com.vabrant.jam.Tank;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class CharacterTest extends ThermonuclearTestListener {

	Platform platform;
	Tank tank;
	Tank target;
	
	@Override
	public void create() {
		super.create();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.GAME_ATLAS));
		
		tank = new Tank(atlas);
		
		target = new Tank(atlas);
		
		platform = new GroundPlatform(atlas);
		platform.addCharacter(PlayerType.HUMAN, tank, Lane.LANE_2, LaneSection.LANE_SECTION_1);
	}

	@Override
	public void update(float delta) {
//		platform.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch, ShapeDrawer shapeDrawer) {
		platform.draw(batch, shapeDrawer, 0, 0, 1f);
		target.draw(batch, shapeDrawer, 300, 120, 1f);
	}
	
}
