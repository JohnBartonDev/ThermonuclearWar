package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.vabrant.actionsystem.actions.ActionManager;
import com.vabrant.actionsystem.actions.GroupAction;
import com.vabrant.actionsystem.actions.Movable;
import com.vabrant.actionsystem.actions.MoveAction;
import com.vabrant.actionsystem.actions.Scalable;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.LaneSection;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class PlatformController {

	private boolean isTransitioning;
	private boolean isDeployable = true;
	private final float p1Y = 0;
	private final float p2Y = 90;
	private final float p3Y = 180;
	private final Platform[] platforms;
	private final Layer[] layers;
	private LayerTransitionValues[] transitionValues;
	private ActionManager manager;
	
	public PlatformController(ActionManager manager, GroundPlatform ground, WaterPlatform water, AirPlatform air) {
		this.manager = manager;
		platforms = new Platform[3];
		platforms[0] = ground;
		platforms[1] = water;
		platforms[2] = air;
		layers = new Layer[8];
		transitionValues = new LayerTransitionValues[8];
		for(int i = 0; i < transitionValues.length; i++) {
			transitionValues[i] = new LayerTransitionValues();
		}
	}
	
	public boolean isDeployable(Player player, DeployableType type) {
		if(!isDeployable) return false;
		
		Platform p = getMainPlatform();
		if(player.getElixir() < type.getElixrCost()) return false;
		
		switch(type) {
			case TANK:
				return (!(p instanceof GroundPlatform)) ? false : true;
			case SHIP:
				return (!(p instanceof WaterPlatform)) ? false : true;
			case CHOPPER:
				return (!(p instanceof AirPlatform)) ? false : true;
			case NUKE:
				return true;
			default:
				return false;
		}
	}
	
	public void deploy(Player player, Player oppositePlayer, DeployableType type, Lane lane, LaneSection section) {
		if(!isDeployable(player, type)) return;
		
		Deployable deployable = DeployableFactory.getDeployable(type);
	
		
		if(type.equals(DeployableType.NUKE)) {
		}
		else {
			Character c = (Character)deployable;
			
			Platform p = getMainPlatform();
			
			c.setup(player, p, lane);
			player.subtractElixir(type.getElixrCost());
			player.addCharacter((Character)deployable);
			getMainPlatform().addCharacter(player.type, (Character)deployable, lane, section);
		}
	}
	
	public Platform[] getPlatforms() {
		return platforms;
	}
	
	public Platform getMainPlatform() {
		return platforms[0];
	}
	
	public void transition() {
		for(int i = 0, j = 0; i < platforms.length; i++) {
			Platform p = platforms[i];
			layers[j++] = p;
			layers[j++] = p.getBackground();
			
			if(i == 0) {
				layers[6] = p;
				layers[7] = p.getBackground();
			}
		}
		
		Interpolation interpolation = Interpolation.exp5Out;
		MoveAction p1M = MoveAction.moveYBy(transitionValues[0], 200, 1f, interpolation);
		MoveAction p1B = MoveAction.moveYBy(transitionValues[1], 200, 1f, interpolation);
		MoveAction p2M = MoveAction.moveYBy(transitionValues[2], 90, 0.5f, interpolation);
		MoveAction p2B = MoveAction.moveYBy(transitionValues[3], 90, 0.5f, interpolation);
		MoveAction p3M = MoveAction.moveYBy(transitionValues[4], 90, 0.5f, interpolation);
		MoveAction p3B = MoveAction.moveYBy(transitionValues[5], 90, 0.5f, interpolation);
		
		GroupAction group = GroupAction.getAction()
				.parallel(0.05f)
				.add(p1M)
				.add(p1B)
				.add(p2M)
				.add(p2B)
				.add(p3M)
				.add(p3B);
		manager.addAction(group);
		
		isTransitioning = true;
	}
	
	public void nextPlatform() {
		changePlatforms(1);
	}
	
	public void previousPlatform() {
		changePlatforms(-1);
	}

	private void changePlatforms(int d) {
		if(d == 1) {
			Platform temp = platforms[0];
			platforms[0] = platforms[1];
			platforms[1] = platforms[2];
			platforms[2] = temp;
		}
		else {
			Platform temp = platforms[0];
			platforms[0] = platforms[2];
			platforms[2] = platforms[1];
			platforms[1] = temp;
		}
	}

	public void draw(Batch batch, ShapeDrawer shapeDrawer) {
		if(!isTransitioning) {
			for(int i = platforms.length - 1; i >= 0; i--) {
				Platform p = platforms[i];
	
				float y = 0;
				float scale = 1f;
				
				switch(i) {
					case 0:
						y = p1Y;
						y -= transitionValues[0].getY();
						scale = 1f;
						break;
					case 1:
						y = p2Y;
						y -= transitionValues[1].getY();
						scale = 0.8f;
						break;
					case 2:
						y = p3Y;
						y -= transitionValues[2].getY();
						scale = 0.6f;
						break;
				}
				
				p.getBackground().draw(batch, shapeDrawer, 0, y, 1f);
				
				float x = (Constants.GAME_WIDTH - (p.getWidth() * scale)) / 2;
				y += Constants.PLATFORM_Y_OFFSET;
				
				p.draw(batch, shapeDrawer, x, y, scale);
				p.drawHealthHud(shapeDrawer, x, y, scale);
			}
		}
		else {
			for(int i = platforms.length - 1; i >= 0; i--) {
				Platform p = platforms[i];
				
				float y = 0;
				float scale = 1f;
				
				switch(i) {
					case 0:
						y = p1Y;
						break;
					case 1:
						y = p2Y;
						scale = 0.8f;
						break;
					case 2:
						y = p3Y;
						scale = 0.6f;
						break;
				}
				
				int index = i * 2;
				LayerTransitionValues v = transitionValues[index + 1];
				p.getBackground().draw(batch, shapeDrawer, 0, y - v.getY(), 1f);
				
				v = transitionValues[index];
				float x = (Constants.GAME_WIDTH - (p.getWidth() * scale)) / 2;
				y += (Constants.PLATFORM_Y_OFFSET - v.getY());
				
				p.draw(batch, shapeDrawer, x, y, scale);
				p.drawHealthHud(shapeDrawer, x, y, scale);
			}
		}
	}
	
	private class LayerTransitionValues implements Movable, Scalable {

		private float x;
		private float y;
		private float scaleX = 1;
		private float scaleY = 1;

		@Override
		public void setX(float x) {
			this.x = x;
		}

		@Override
		public void setY(float y) {
			this.y = y;
		}

		@Override
		public float getX() {
			return x;
		}

		@Override
		public float getY() {
			return y;
		}

		@Override
		public void setPosition(float x, float y) {
		}

		@Override
		public void setScaleX(float scaleX) {
			this.scaleX = scaleX;
		}

		@Override
		public void setScaleY(float scaleY) {
			this.scaleY = scaleY;
		}

		@Override
		public float getScaleX() {
			return scaleX;
		}

		@Override
		public float getScaleY() {
			return scaleY;
		}

		@Override
		public void setScale(float scaleX, float scaleY) {
		}

	}

}
