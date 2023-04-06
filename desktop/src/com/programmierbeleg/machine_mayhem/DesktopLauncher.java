package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

public class DesktopLauncher {

	private static int foregroundFPS=60;
	private static int breite=1920;
	private static int höhe=1080;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(foregroundFPS);
		config.useVsync(true);
		config.setTitle("Machine Mayhem");
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setWindowedMode(breite,höhe);
		new Lwjgl3Application(new Spiel(), config);
		System.out.println("lul");
	}
}
