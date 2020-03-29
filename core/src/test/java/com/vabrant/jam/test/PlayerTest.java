package com.vabrant.jam.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.AI;
import com.vabrant.jam.AirPlatform;
import com.vabrant.jam.DeployableFactory;
import com.vabrant.jam.Constants;
import com.vabrant.jam.GroundPlatform;
import com.vabrant.jam.Human;
import com.vabrant.jam.Player;
import com.vabrant.jam.WaterPlatform;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class PlayerTest extends ThermonuclearTestListener {

	Player player;
	Player oppositePlayer;
	
	@Override
	public void create() {
		super.create();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.GAME_ATLAS));
		
		GroundPlatform g = new GroundPlatform(atlas);
		WaterPlatform w = new WaterPlatform(atlas);
		AirPlatform a = new AirPlatform(atlas);
		
		player = new Human(actionManager, atlas, g, w, a);
		oppositePlayer = new AI(actionManager, g, w, a);
		inputMultiplexer.addProcessor(((Human)player).getInput());
		
		DeployableFactory.initPools(atlas);
	}

	@Override
	public void update(float delta) {
		player.update(delta);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
			case Keys.SPACE:
				break;
		}
		return super.keyDown(keycode);
	}
	
	@Override
	public void draw(SpriteBatch batch, ShapeDrawer shapeDrawer) {
		player.draw(batch, shapeDrawer);
	}
}
