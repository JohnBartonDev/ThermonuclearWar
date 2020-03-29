package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants.DeployableType;

public class Ship extends Character {
	
	private static final int damage = 40;
	private static final int shootInterval = 3;
	private static final float moveSpeed = 20;
	private static final int health = 60;
	private static final int range = 80;
	
	public Ship(TextureAtlas atlas) {
		super(DeployableType.SHIP, atlas.findRegion("ship"), damage, shootInterval, moveSpeed, health, range);
		setMovable(true);
	}
	
	@Override
	protected boolean canEngage(Character c) {
		switch(c.type) {
			case TANK:
			case SHIP:
			case GOVERNMENT:
				return true;
			default:
				return false;
		}
	}

}
