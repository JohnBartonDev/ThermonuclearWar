package com.vabrant.jam.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vabrant.actionsystem.actions.ActionManager;
import com.vabrant.jam.Constants;
import com.vabrant.jam.InputMultiplexer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class ThermonuclearTestListener extends ApplicationAdapter implements InputProcessor {
	
	public SpriteBatch batch;
	public Viewport viewport;
	public ShapeDrawer shapeDrawer;
	public InputMultiplexer inputMultiplexer;
	public ActionManager actionManager;
	
	@Override
	public void create() {
		actionManager = new ActionManager();
		batch = new SpriteBatch();
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		
		inputMultiplexer = new InputMultiplexer(viewport);
		inputMultiplexer.addProcessor(this);
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		
		shapeDrawer = new ShapeDrawer(batch, new TextureRegion(new Texture(pixmap)));
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	public void update(float delta) {}
	public void draw(SpriteBatch batch, ShapeDrawer shapeDrawer) {}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		float delta = Gdx.graphics.getDeltaTime();
		actionManager.update(delta);
		update(delta);
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.enableBlending();
		batch.begin();
		draw(batch, shapeDrawer);
		batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
