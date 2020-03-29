package com.vabrant.jam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity implements Layer{

	protected Color color;
	protected Rectangle boundingBox;
	protected TextureRegion entityRegion;
	
	public Entity(TextureRegion entityRegion) {
		this(0, 0, entityRegion.getRegionWidth() / 2, entityRegion.getRegionHeight() / 2);
		this.entityRegion = entityRegion;
	}
	
	public Entity(float x, float y, float width, float height) {
		color = new Color();
		boundingBox = new Rectangle(x, y, width, height);
	}
	
	public float getX() {
		return boundingBox.x;
	}
	
	public float getY() {
		return boundingBox.y;
	}
	
	public float getWidth() {
		return boundingBox.width;
	}
	
	public float getHeight() {
		return boundingBox.height;
	}
	
	public void debugBoundingBox(ShapeDrawer shapeDrawer, float offsetX, float offsetY, float scale) {
		if(!Constants.DEBUG) return;
		shapeDrawer.rectangle((boundingBox.x * scale) + offsetX, boundingBox.y + offsetY, boundingBox.width * scale, boundingBox.height * scale, Color.RED);
	}
	
}
