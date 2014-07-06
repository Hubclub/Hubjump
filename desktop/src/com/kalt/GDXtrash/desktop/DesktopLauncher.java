package com.kalt.GDXtrash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kalt.GDXtrash.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30= true;
		config.height= 200*4;
		config.width= 120*4;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
