package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vabrant.jam.Constants.DeployableType;

public class Government extends Character {
	
	private static final int damage = 20;
	private static final int shootInterval = 1;
	private static final int health = 500;
	private static final int range = 90;
	
	public Government(TextureAtlas atlas) {
		super(DeployableType.GOVERNMENT, atlas.findRegion("government"), damage, shootInterval, 0, health, range);
	}

	@Override
	protected boolean canEngage(Character c) {
		return true;
	}

}
