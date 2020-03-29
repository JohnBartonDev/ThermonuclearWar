package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AirPlatform extends Platform {
	
	public AirPlatform(TextureAtlas atlas) {
		super(atlas, new AirBackground(atlas), 0xe2ebe9ff, 0xc9d2d0ff);
	}
	
	private static class AirBackground extends PlatformBackground {
		public AirBackground(TextureAtlas atlas) {
			super(atlas, 0xffa500ff);
		}
	}

}
