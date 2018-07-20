package com.omstead.simplegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.omstead.simplegame.Simplegame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "2048";
		config.width = 128*6;
		config.height = 128*6+15;
		new LwjglApplication(new Simplegame(), config);
	}
}
