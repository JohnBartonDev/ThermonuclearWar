package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Deployable extends Entity {
	
	final int damage;
	
	public Deployable(TextureRegion region, int damage) {
		super(region);
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}

}
