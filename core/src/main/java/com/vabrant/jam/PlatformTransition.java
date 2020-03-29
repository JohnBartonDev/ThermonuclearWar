package com.vabrant.jam;

import com.vabrant.actionsystem.actions.Movable;
import com.vabrant.actionsystem.actions.Scalable;

public class PlatformTransition {
	
	private float timer;
	private final float offset = 0.25f;
	private final float duration = 0.5f;
	private boolean transition;
	private Layer[] layers;
	private LayerTransitionValues[] values;
	private PlatformController controller;
	
	public PlatformTransition(PlatformController controller) {
		this.controller = controller;
		layers = new Layer[6];
		values = new LayerTransitionValues[6];
		
		for(int i = 0; i < values.length; i++) {
			values[i] = new LayerTransitionValues();
		}
	}
	
	public void transition() {
		//set layers
		Platform[] platforms = controller.getPlatforms();
		for(int i = 0, j = 0; i < platforms.length; i++) {
			Platform p = platforms[i];
			layers[j] = p;
			values[j].setY(10);
			
			switch(i) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
			}
			
			layers[j] = p.getBackground();
		}
		
		
		
		transition = true;
	}
	
	public void update(float delta) {
		if(!transition) return;
		
		timer += delta;
		
		for(int i = 0; i < layers.length; i++) {
			float time = timer - (offset * i);
			if(timer < 0) {
				continue;
			}
			
			boolean finished = time >= duration;
			float percent = 0;
			
			if(finished) {
				percent = 1f;
			}
			else {
				percent = time / duration;
			}
		}
	}
	
	private void reset() {
		timer = 0;
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
