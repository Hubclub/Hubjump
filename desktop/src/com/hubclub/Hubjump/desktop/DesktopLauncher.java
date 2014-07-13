package com.hubclub.Hubjump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hubclub.Hubjump.Hubjump;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title= "hubjump";
		config.width= 480;
		config.height=800;
		config.useGL30=true;
		new LwjglApplication(new Hubjump(), config);
	}
}
