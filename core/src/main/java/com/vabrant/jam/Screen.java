package com.vabrant.jam;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vabrant.actionsystem.actions.ActionManager;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Screen {

	public boolean debugCameraAndViewport;
	public float screenX;
	public float screenY;
	public ThermonuclearWar game;
	public Viewport viewport;
	public final ActionManager actionManager;
	public final InputMultiplexer inputMultiplexer;
	
	public Screen(ThermonuclearWar game) {
		this.game = game;
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		actionManager = new ActionManager(15);
		inputMultiplexer = new InputMultiplexer(viewport);
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	public Camera getCamera() {
		return viewport.getCamera();
	}

	public void update(float delta) {
		actionManager.update(delta);
	}
	
	public void dispose() {
		actionManager.freeAll();
	}
	
	public void render(Batch batch, ShapeDrawer shapeDrawer) {}
	public void pause() {}
	public void resume() {}
	public void hide() {}
	public void show() {}
}
