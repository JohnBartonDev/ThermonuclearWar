package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WaterPlatform extends Platform {
	
	public WaterPlatform(TextureAtlas atlas) {
		super(atlas, new WaterBackground(atlas), 0x00ffefff, 0x00e6d7ff);
	}
	
	private static class WaterBackground extends PlatformBackground {
		public WaterBackground(TextureAtlas atlas) {
			super(atlas, 0xa020f0ff);
		}
	}

}
