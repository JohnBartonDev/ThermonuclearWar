package com.vabrant.jam;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SplashScreen extends Screen {
	
	private boolean isLoading;
	public SplashScreen(ThermonuclearWar game) {
		super(game);
	}
	
	@Override
	public void show() {
		isLoading = true;
		
		AssetManager assetManager = game.assetManager;
		assetManager.load(Constants.GAME_ATLAS, TextureAtlas.class);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(isLoading) {
			if(game.assetManager.update()) {
				isLoading = false;
				TextureAtlas atlas = game.assetManager.get(Constants.GAME_ATLAS);
				
				game.shapeDrawer = new ShapeDrawer(game.batch, atlas.findRegion("pixel"));
				DeployableFactory.initPools(atlas);
			}
		}
		
		if(!isLoading) {
			game.setScreen(new GameScreen(game), true);
		}
	}
	
}
