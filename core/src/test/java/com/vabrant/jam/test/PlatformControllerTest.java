package com.vabrant.jam.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.AirPlatform;
import com.vabrant.jam.Constants;
import com.vabrant.jam.GroundPlatform;
import com.vabrant.jam.PlatformController;
import com.vabrant.jam.WaterPlatform;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class PlatformControllerTest extends ThermonuclearTestListener {

	PlatformController controller;
	
	@Override
	public void create() {
		super.create();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.GAME_ATLAS));
		
		controller = new PlatformController(actionManager, new GroundPlatform(atlas), new WaterPlatform(atlas), new AirPlatform(atlas));
	}
	
	public boolean keyDown(int keycode) {
		switch(keycode) {
			case Keys.SPACE:
				controller.transition();
				break;
			case Keys.UP:
				controller.nextPlatform();
				break;
			case Keys.DOWN:
				controller.previousPlatform();
				break;
		}
		return false;
	}
	
	@Override
	public void draw(SpriteBatch batch, ShapeDrawer shapeDrawer) {
		controller.draw(batch, shapeDrawer);
	}

}
