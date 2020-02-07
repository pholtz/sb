package org.butternut.sb.menu;

import java.util.Optional;

import javax.sound.sampled.Clip;

import org.butternut.sb.Game;
import org.butternut.sb.audio.Audio;

public class MenuController
{
	private final Game game;
	private final MenuModel menuModel;
	
	public MenuController(Game game,
			MenuModel menuModel) {
		this.game = game;
		this.menuModel = menuModel;
	}
	
	public void initializeMenu() {
		Optional.ofNullable(this.game.clip).ifPresent(Clip::stop);
		this.game.clip = Audio.initAudio("src/main/resources/files/blinkwhats.WAV");
	}
	
	public void processMenu() {

	}
}
