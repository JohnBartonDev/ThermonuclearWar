package com.vabrant.jam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class PlatformBackground implements Layer {

	final float width;
	final float height;
	private Color color;
	private final TextureRegion hillsRegion;
	
	public PlatformBackground(TextureAtlas atlas, int color) {
		hillsRegion = atlas.findRegion("hills");
		width = hillsRegion.getRegionWidth() / 2;
		height = hillsRegion.getRegionHeight() / 2;
		this.color = new Color(color);
	}

	@Override
	public void update(float delta) {
	}
	
	@Override
	public void draw(Batch batch, ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale) {
		batch.setColor(color);
		batch.draw(hillsRegion, 0, yOffset, width, height);
		batch.draw(hillsRegion, width - 1, yOffset, width, height);
		batch.draw(hillsRegion.getTexture(), (width - 1) * 2, yOffset, 81, height, hillsRegion.getRegionX(), hillsRegion.getRegionY(), 81 * 2, hillsRegion.getRegionHeight(), false, false);
		batch.setColor(Color.WHITE);
	}

}
