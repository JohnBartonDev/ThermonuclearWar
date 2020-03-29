package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.Batch;

import space.earlygrey.shapedrawer.ShapeDrawer;

public interface Layer {
	public void update(float delta);
	public void draw(Batch batch, ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale);
}
