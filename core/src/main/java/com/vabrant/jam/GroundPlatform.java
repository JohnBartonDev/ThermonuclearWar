package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GroundPlatform extends Platform {
	
	public GroundPlatform(TextureAtlas atlas) {
		super(atlas, new GroundBackground(atlas), 0x00c700ff, 0x00a600ff);
	}
	
	private static class GroundBackground extends PlatformBackground {
		public GroundBackground(TextureAtlas atlas) {
			super(atlas, 0x000000ff);
		}
	}

}
