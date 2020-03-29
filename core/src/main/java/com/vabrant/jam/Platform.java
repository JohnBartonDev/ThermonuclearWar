package com.vabrant.jam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;
import com.vabrant.jam.Constants.PlayerType;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Platform extends Entity implements RemoveCharacterCallback {
	
	private static final int LANE_SECTION_1_X = Constants.LANE_SECTION_OFFSET;
	private static final int LANE_SECTION_2_X = Constants.LANE_SECTION_WIDTH + Constants.LANE_SECTION_OFFSET;
	private static final int LANE_SECTION_3_X = (Constants.LANE_SECTION_WIDTH * 2) + Constants.LANE_SECTION_OFFSET;
	
	private final float lane1Offset;
	private final float lane2Offset;
	
	final float frontHeight;
	final TextureRegion mainRegion;
	final TextureRegion frontRegion;
	private Color mainColor;
	private Color frontColor;
	final PlatformBackground background;
	private Array<Character> lane1;
	private Array<Character> lane2;
	public HealthHudRenderer healthHudRenderer;
	
	public Platform(TextureAtlas atlas, PlatformBackground background, int main, int front) {
		super(0, 0, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
		mainRegion = atlas.findRegion("platformMain");
		frontRegion = atlas.findRegion("platformFront");
		mainColor = new Color(main);
		frontColor = new Color(front);
		this.background = background;
		lane1 = new Array<>(4);
		lane2 = new Array<>(4);
		frontHeight = frontRegion.getRegionHeight() / 2;
		healthHudRenderer = new HealthHudRenderer();
		lane1Offset = Constants.PLATFORM_HEIGHT / 4;
		lane2Offset = Constants.PLATFORM_HEIGHT / 2 + lane1Offset;
	}
	
	public void addCharacter(PlayerType player, Character character, Lane lane, LaneSection section) {
		float offset = 0;
		switch(section) {
			case LANE_SECTION_1:
				offset = LANE_SECTION_1_X;
				break;
			case LANE_SECTION_2:
				offset = LANE_SECTION_2_X;
				break;
			case LANE_SECTION_3:
				offset = LANE_SECTION_3_X;
				break;
		}

		offset -= (character.getWidth() / 2);
		character.translateX(player.equals(PlayerType.HUMAN) ? offset : -offset);

		switch(lane) {
			case LANE_1:
				lane1.add(character);
				break;
			case LANE_2:
				lane2.add(character);
				break;
		}
	}
	
	public PlatformBackground getBackground() {
		return background;
	}

	@Override
	public void update(float delta) {
		for(int i = 0; i < lane2.size; i++) {
			lane2.get(i).update(delta);
		}
		
		for(int i = 0; i < lane1.size; i++) {
			lane1.get(i).update(delta);
		}
	}
	
	public void drawHealthHud(ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale) {
		float lane2Offset = this.lane2Offset * scale;
		float lane1Offset = this.lane1Offset * scale;
		
		for(int i = 0; i < lane2.size; i++) {
			healthHudRenderer.draw(shapeDrawer, lane2.get(i), xOffset, yOffset + lane2Offset, scale);
		}
		
		for(int i = 0; i < lane1.size; i++) {
			healthHudRenderer.draw(shapeDrawer, lane1.get(i), xOffset, yOffset + lane1Offset, scale);
		}
	}
	
	@Override
	public void draw(Batch batch, ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale) {
		float mainWidth = getWidth() * scale;
		float mainHeight = getHeight() * scale;
		float frontHeight = this.frontHeight * scale;

		//draw main
		batch.setColor(mainColor);
		batch.draw(mainRegion, xOffset, yOffset, mainWidth, mainHeight);
		
		//draw front
		batch.setColor(frontColor);
		batch.draw(frontRegion, xOffset, yOffset - frontHeight, mainWidth, frontHeight);
		
		shapeDrawer.line(xOffset + (3 * scale), yOffset + (mainHeight / 2), xOffset + (mainWidth - (3 * scale)), yOffset + (mainHeight / 2), frontColor);
		
		batch.setColor(Color.WHITE);
		
		debugCurrentPlatformBounds(shapeDrawer, xOffset, yOffset, scale);
		
		float lane1Offset = this.lane1Offset * scale;
		float lane2Offset = this.lane2Offset * scale;

		//draw lane 1 characters
		for(int i = 0; i < lane2.size; i++) {
			lane2.get(i).draw(batch, shapeDrawer, xOffset, lane2Offset + yOffset, scale);
		}
		
		for(int i = 0; i < lane1.size; i++) {
			lane1.get(i).draw(batch, shapeDrawer, xOffset, lane1Offset + yOffset, scale);
		}
	}
	
	private void debugCurrentPlatformBounds(ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale) {
		if(!Constants.DEBUG) return;
		
		float width = Constants.PLATFORM_WIDTH * scale;
		float height = Constants.PLATFORM_HEIGHT * scale;
		
		debugBoundingBox(shapeDrawer, xOffset, yOffset, scale);
		
		//center line
		float lineXOffset = 3 * scale;
		shapeDrawer.line(xOffset, yOffset + (height / 2), xOffset + (width - lineXOffset), yOffset + (height / 2), Color.RED);
		
		lineXOffset = 152 * scale;
		float x = lineXOffset + xOffset;
		
		for(int i = 0; i < 6; i++) {
			shapeDrawer.line(x, yOffset, x, yOffset + height, Color.RED);
			x += lineXOffset;
		}

	}

	@Override
	public void removeCharacter(Character character) {
		Array<Character> chars = character.getLane().equals(Lane.LANE_1) ? lane1 : lane2;
		chars.removeValue(character, false);
	}
}
