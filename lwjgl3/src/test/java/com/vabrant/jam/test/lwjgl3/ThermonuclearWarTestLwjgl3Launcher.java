package com.vabrant.jam.test.lwjgl3;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class ThermonuclearWarTestLwjgl3Launcher {
	
	public static Class<? extends ApplicationListener> LAUNCH_CLASS = null;
	
	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("ThermonuclearWar");
		configuration.setWindowedMode(1080, 720);
		configuration.setResizable(false);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}
	
	public static void main(String[] args) {
		ApplicationListener listener = null;
		Lwjgl3ApplicationConfiguration config = getDefaultConfiguration();
		
		try {
			listener = ClassReflection.newInstance(LAUNCH_CLASS);
		}
		catch(Exception e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
		new Lwjgl3Application(listener, config);
	}
}
