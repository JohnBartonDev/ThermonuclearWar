package com.vabrant.jam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vabrant.jam.Constants.DeployableType;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SelectedDeployableHud {
	
	private final float y = Constants.GAME_HEIGHT - 100;
	private final float padding = 20;
	private final float size = 86;
	public Vector2 tankPos;
	public Vector2 shipPos;
	public Vector2 chopperPos;
	public Vector2 nukePos;
	private final Human human;
	private final Color color;
	private final TextureRegion selectHudRegion;
	private final TextureRegion keybindHudRegion;
	private final TextureRegion tankIconRegion;
	private final TextureRegion shipIconRegion;
	private final TextureRegion chopperIconRegion;
	private final TextureRegion nukeIconRegion;
	private final TextureRegion zRegion;
	private final TextureRegion xRegion;
	private final TextureRegion cRegion;
	private final TextureRegion vRegion;
	
	public SelectedDeployableHud(TextureAtlas atlas, Human human) {
		selectHudRegion = atlas.findRegion("selectedHud");
		keybindHudRegion = atlas.findRegion("keybindHud");
		tankIconRegion = atlas.findRegion("tankIcon");
		shipIconRegion = atlas.findRegion("shipIcon");
		chopperIconRegion = atlas.findRegion("chopperIcon");
		nukeIconRegion = atlas.findRegion("nukeIcon");
		zRegion = atlas.findRegion("z");
		xRegion = atlas.findRegion("x");
		cRegion = atlas.findRegion("c");
		vRegion = atlas.findRegion("v");
		
		this.human = human;
		
		color = new Color(Color.BLACK);
		color.a = 0.4f;
		
		float width = 80 * 4 + (padding * 3);
		float x = (Constants.GAME_WIDTH - width) / 2;
		
		tankPos = new Vector2(x, y);
		
		x += size + padding;
		shipPos = new Vector2(x, y);
		
		x += size + padding;
		chopperPos = new Vector2(x, y);
		
		x += size + padding;
		nukePos = new Vector2(x, y);
	}
	
	public void draw(Batch batch, ShapeDrawer shapeDrawer) {
		drawHud(batch, DeployableType.TANK, human.selectedCharacter.equals(DeployableType.TANK) ? true : false);
		drawHud(batch, DeployableType.SHIP, human.selectedCharacter.equals(DeployableType.SHIP) ? true : false);
		drawHud(batch, DeployableType.CHOPPER, human.selectedCharacter.equals(DeployableType.CHOPPER) ? true : false);
		drawHud(batch, DeployableType.NUKE, human.selectedCharacter.equals(DeployableType.NUKE) ? true : false);
	}
	
	private TextureRegion getIconRegion(DeployableType type) {
		switch(type) {
			case TANK:
				return tankIconRegion;
			case SHIP:
				return shipIconRegion;
			case CHOPPER:
				return chopperIconRegion;
			case NUKE:
				return nukeIconRegion;
			default:
				return null;
		}
	}
	
	private TextureRegion getKeybindRegion(DeployableType  type) {
		switch(type) {
			case TANK:
				return zRegion;
			case SHIP:
				return xRegion;
			case CHOPPER:
				return cRegion;
			case NUKE:
				return vRegion;
			default:
				return null;
		}
	}
	
	private void drawHud(Batch batch, DeployableType type, boolean offset) {
		Vector2 pos = null;
		TextureRegion keybindRegion = getKeybindRegion(type);
		TextureRegion iconRegion = getIconRegion(type);
		
		switch(type) {
			case TANK:
				pos = tankPos;
				break;
			case SHIP:
				pos = shipPos;
				break;
			case CHOPPER:
				pos = chopperPos;
				break;
			case NUKE:
				pos = nukePos;
				break;
		}
		
		float yOffset = offset ? -20 : 0;
		batch.draw(selectHudRegion, pos.x, pos.y + yOffset, size, size);

		float width = iconRegion.getRegionWidth() / 2;
		float height = iconRegion.getRegionHeight() / 2;
		float offsetX = (size - width - 6) / 2;
		float offsetY = (size - height - 6) / 2;
		
		batch.draw(iconRegion, pos.x + offsetX, pos.y + offsetY + 6 + yOffset, width, height);
		
		float keybindHudSize = 36;
		float x = pos.x + (size - keybindHudSize) / 2;
		float y = pos.y - keybindHudSize - 20; 
		
		batch.draw(keybindHudRegion, x, y + yOffset, keybindHudSize, keybindHudSize);
		
		width = keybindRegion.getRegionWidth() / 2;
		height = keybindRegion.getRegionHeight() / 2;
		offsetX = (keybindHudSize - width - 6) / 2;
		offsetY = (keybindHudSize - height - 6) / 2;
		
		batch.draw(keybindRegion, x + offsetX, y + offsetY + 6 + yOffset, width, height);
	}

}
