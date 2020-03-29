package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants.DeployableType;

public class Tank extends Character {
	
	private static final int damage = 15;
	private static final int shootInterval = 1;
	private static final float moveSpeed = 50;
	private static final int health = 150;
	private static final int range = 30;
	
	public Tank(TextureAtlas atlas) {
		super(DeployableType.TANK, atlas.findRegion("tank"), damage, shootInterval, moveSpeed, health, range);
		setMovable(true);
	}
	
	@Override
	protected boolean canEngage(Character c) {
		switch(c.type) {
			case TANK:
			case GOVERNMENT:
				return true;
			default:
				return false;
		}
	}

}
