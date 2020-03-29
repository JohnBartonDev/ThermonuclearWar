package com.vabrant.jam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class HealthHudRenderer {
	
	private final static int width = 50;
	private final static int height = 10;
	private final static int hudYOffset = 10;
	private final Color color;
	
	public HealthHudRenderer() {
		color = new Color(Color.BLACK);
	}
	
	public void draw(ShapeDrawer shapeDrawer, Character character, float xOffset, float yOffset, float scale) {
		float width = this.width * scale;
		float height = this.height * scale;
		float xCenterOffset = ((character.getWidth() * scale) / 2) - width / 2;
		float healthWidth = MathUtils.map(0, character.getMaxHealth(), 0, width, character.getHealth());
		healthWidth = MathUtils.clamp(healthWidth, 0, width);
		
		shapeDrawer.filledRectangle((character.getX() * scale) + xCenterOffset + xOffset, character.getY() + yOffset + (character.getHeight() * scale) + (hudYOffset * scale), width, height, Color.WHITE);
		shapeDrawer.filledRectangle((character.getX() * scale) + xCenterOffset + xOffset, character.getY() + yOffset + (character.getHeight() * scale) + (hudYOffset * scale), healthWidth, height, Color.RED);
	}

}
