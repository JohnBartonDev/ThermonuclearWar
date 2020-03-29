package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants.DeployableType;

public class Chopper extends Character {
	
	private static final int damage = 15;
	private static final float shootInterval = 0.5f;
	private static final float moveSpeed = 70;
	private static final int health = 100;
	private static final int range = 30;
	
	public Chopper(TextureAtlas atlas) {
		super(DeployableType.CHOPPER, atlas.findRegion("chopper"), damage, shootInterval, moveSpeed, health, range);
		setMovable(true);
		setYOffset(40);
	}
	
	@Override
	protected boolean canEngage(Character c) {
		return true;
	}

}
