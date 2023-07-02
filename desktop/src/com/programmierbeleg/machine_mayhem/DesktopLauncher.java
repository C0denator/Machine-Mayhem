package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

public class DesktopLauncher {

	private static int foregroundFPS=120;
	private static boolean vollbildAn=true;
	private static int breite=1920;
	private static int höhe=1080;


	public static void main (String[] arg) {
		System.out.println("Programm gestartet - Daumen drücken!");

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(foregroundFPS);
		config.useVsync(true);
		config.setResizable(false);
		config.setTitle("Machine Mayhem");
		config.setWindowIcon(Files.FileType.Internal,"assets/Texturen/robot_1.png");
		if(vollbildAn){
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		}else{
			config.setWindowedMode(breite,höhe);
		}
		new Lwjgl3Application(Spiel.starteSpiel(), config);

		System.out.println("Spiel gestartet");
	}
}
