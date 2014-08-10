package com.hubclub.hubjump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hubclub.hubjump.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title= "hubjump";
		config.width= 480;
		config.height=800;
		config.useGL30=false;
		new LwjglApplication(new GameClass(), config);
	}
}
