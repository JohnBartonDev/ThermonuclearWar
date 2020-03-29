package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.vabrant.jam.Constants.DeployableType;

public class DeployableFactory {
	
	public static void initPools(TextureAtlas gameAtlas) {
		Pools.set(Tank.class, new Pool<Tank>() {
			@Override
			protected Tank newObject() {
				return new Tank(gameAtlas);
			}
		});
		
		Pools.set(Ship.class, new Pool<Ship>() {
			@Override
			protected Ship newObject() {
				return new Ship(gameAtlas);
			}
		});
		
		Pools.set(Chopper.class, new Pool<Chopper>() {
			@Override
			protected Chopper newObject() {
				return new Chopper(gameAtlas);
			}
		});
		
		Pools.set(Government.class, new Pool<Government>() {
			@Override
			protected Government newObject() {
				return new Government(gameAtlas);
			}
		});
	}
	
	public static <T extends Deployable> Deployable getDeployable(DeployableType type) {
		Class klass = null;
		
		switch(type) {
			case TANK:
				klass = Tank.class;
				break;
			case SHIP:
				klass = Ship.class;
				break;
			case CHOPPER:
				klass = Chopper.class;
				break;
			case GOVERNMENT:
				klass = Government.class;
				break;
			case NUKE:
				break;
		}
		
		return Pools.<T>obtain(klass);
	}
}
