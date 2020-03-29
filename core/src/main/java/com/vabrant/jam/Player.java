package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.vabrant.actionsystem.actions.ActionManager;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.PlayerType;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player {
	
	protected float timer;
	protected int elixir = 5;
	protected float elixrInterval = 1f;
	protected final int maxElixr = 10;

	private boolean isDead;
	protected final Array<Character> characters;
	protected PlatformController platformController;
	public final PlayerType type;
	protected Player oppositePlayer;
	protected Government government;
	
	public Player(ActionManager manager, PlayerType type, GroundPlatform ground, WaterPlatform water, AirPlatform air) {
		this.type = type;
		characters = new Array<>(20);
		platformController = new PlatformController(manager, ground, water, air);
	}
	
	public void createGovernment() {
		government = (Government)DeployableFactory.getDeployable(DeployableType.GOVERNMENT);
		government.setup(this);
		characters.add(government);
	}
	
	public HealthHudRenderer getHealthHudRenderer() {
		return platformController.getMainPlatform().healthHudRenderer;
	}
	
	public Government getGovernment() {
		return government;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setOppositePlayer(Player oppositePlayer) {
		this.oppositePlayer = oppositePlayer;
	}
	
	void addCharacter(Character character) {
		characters.add(character);
	}
	
	public int getElixir() {
		return elixir;
	}
	
	public void subtractElixir(int amount) {
		elixir -= amount;
	}
	
	private void updateElixir(float delta) {
		if(elixir == maxElixr) return;
		
		if((timer += delta) > elixrInterval) {
			elixir++;
			timer = 0;
		}
	}
	
	private void removeDeadCharacters() {
		for(int i = characters.size - 1; i >= 0; i--) {
			Character c = characters.get(i);
			if(c.isDead()) {
				
				if(c instanceof Government) {
					isDead = true;
				}
				else {
					c.removeFromPlatform();
					characters.removeValue(c, false);
					Pools.free(c);
				}
			}
		}
	}
	
	public void update(float delta) {
		government.update(delta);
		updateElixir(delta);
		removeDeadCharacters();
	}
	
	public void draw(Batch batch, ShapeDrawer shapeDrawer) {
		platformController.draw(batch, shapeDrawer);
	}

	
}
