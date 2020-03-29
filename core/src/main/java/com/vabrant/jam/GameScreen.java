package com.vabrant.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends Screen{

	private boolean over;
	private GroundPlatform groundPlatform;
	private WaterPlatform waterPlatform;
	private AirPlatform airPlatform;
	private Human human;
	private AI ai;
	
	public GameScreen(ThermonuclearWar game) {
		super(game);
		
		game.clearColor.set(0x0cd8ffff);
		
		TextureAtlas gameAtlas = game.assetManager.get(Constants.GAME_ATLAS);
		
		groundPlatform = new GroundPlatform(gameAtlas);
		waterPlatform = new WaterPlatform(gameAtlas);
		airPlatform = new AirPlatform(gameAtlas);
		
		human = new Human(actionManager, gameAtlas, groundPlatform, waterPlatform, airPlatform);
		ai =  new AI(actionManager, groundPlatform, waterPlatform, airPlatform);
		
		human.setOppositePlayer(ai);
		ai.setOppositePlayer(human);
		human.createGovernment();
		ai.createGovernment();
//		human.getGovernment().setYOffset(100);
//		ai.getGovernment().setYOffset(100);

		inputMultiplexer.addProcessor(human.getInput());
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void update(float delta) {
		if(over) return;
		
		groundPlatform.update(delta);
		waterPlatform.update(delta);
		airPlatform.update(delta);
		
		human.update(delta);
		ai.update(delta);
		
		if(human.isDead() || ai.isDead()) {
			over = true;
		}
	}

	@Override
	public void render(Batch batch, ShapeDrawer shapeDrawer) {
		human.draw(batch, shapeDrawer);
		
		float x = (Constants.GAME_WIDTH - Constants.PLATFORM_WIDTH) / 2;
		float offset = 200;
		
		human.getGovernment().draw(batch, shapeDrawer, x, Constants.PLATFORM_Y_OFFSET + offset, 1f);
		ai.getGovernment().draw(batch, shapeDrawer, x, Constants.PLATFORM_Y_OFFSET + offset, 1f);
		
		HealthHudRenderer h = human.getHealthHudRenderer();
		h.draw(shapeDrawer, human.getGovernment(), x, Constants.PLATFORM_Y_OFFSET + offset, 1f);
		h.draw(shapeDrawer, ai.getGovernment(), x, Constants.PLATFORM_Y_OFFSET + offset, 1f);
	}
}
