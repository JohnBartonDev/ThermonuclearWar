package com.vabrant.jam;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vabrant.actionsystem.actions.ActionManager;

import space.earlygrey.shapedrawer.ShapeDrawer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ThermonuclearWar implements ApplicationListener {
	
	private boolean disposeCurrentScreen;
	private boolean switchScreens;
	public SpriteBatch batch;
	public AssetManager assetManager;
	public Screen currentScreen;
	public Screen nextScreen;
	public ShapeDrawer shapeDrawer;
	public ActionManager actionManager;
	public Color clearColor;
	
	@Override
	public void create() {
		clearColor = new Color(Color.WHITE);
		actionManager = new ActionManager(10);
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		currentScreen = new SplashScreen(this);
		currentScreen.show();
	}

	@Override
	public void resize(int width, int height) {
		if(currentScreen != null) currentScreen.resize(width, height);
	}

	@Override
	public void pause() {
		if(currentScreen != null) currentScreen.pause();
	}

	@Override
	public void resume() {
		if(currentScreen != null) currentScreen.resume();
	}

	@Override
	public void dispose() {
		if(currentScreen != null) currentScreen.dispose();
		batch.dispose();
		assetManager.dispose();
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float delta = Gdx.graphics.getDeltaTime();
		actionManager.update(delta);
		if(currentScreen != null) currentScreen.update(delta);
		
		batch.setProjectionMatrix(currentScreen.getCamera().combined);
		batch.enableBlending();
		batch.begin();
		if(currentScreen != null) currentScreen.render(batch, shapeDrawer);
		batch.end();
		
		if(switchScreens) switchScreens();
	}
	
	public void setScreen(Screen screen, boolean disposeCurrentScreen) {
		if(screen == null) throw new IllegalArgumentException("Screen is null.");
		switchScreens = true;
		nextScreen = screen;
		this.disposeCurrentScreen = disposeCurrentScreen;
	}
	
	private void switchScreens() {
		switchScreens = false;
		
		//reset and dispose the current screen
		if(currentScreen != null) {
			currentScreen.hide();
			if(disposeCurrentScreen) currentScreen.dispose();
			currentScreen = null;
		}
		
		//set and show the next screen 
		currentScreen = nextScreen;
		nextScreen = null;
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		currentScreen.show();
	}
	
}