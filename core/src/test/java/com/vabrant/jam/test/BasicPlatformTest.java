package com.vabrant.jam.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants;
import com.vabrant.jam.Platform;
import com.vabrant.jam.PlatformBackground;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class BasicPlatformTest extends ThermonuclearTestListener {

	TestPlatform platform;
	
	@Override
	public void create() {
		super.create();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.GAME_ATLAS));
		platform = new TestPlatform(atlas);
	}
	
	@Override
	public void update(float delta) {
		platform.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch, ShapeDrawer shapeDrawer) {
		platform.draw(batch, shapeDrawer, 0, 0, 1f);
	}
	
	private class TestPlatformBackground extends PlatformBackground {
		
		public TestPlatformBackground(TextureAtlas atlas) {
			super(atlas, 0x000000ff);
		}
	}
	
	private class TestPlatform extends Platform {

		public TestPlatform(TextureAtlas atlas) {
			super(atlas, new TestPlatformBackground(atlas), 0x00c700ff, 0x00a600ff);
		}
		
	}

}
