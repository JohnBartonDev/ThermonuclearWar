package com.vabrant.jam.lwjgl3;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class ThermonuclearWarPackTextures {

	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.alias = false;
		
		TexturePacker.process(settings, "../packTextures/game", "textures", "game");
	}
}
