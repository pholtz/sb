package org.butternut.sb.crawl;

import java.util.Optional;

import javax.sound.sampled.Clip;

import org.butternut.sb.Game;
import org.butternut.sb.audio.Audio;
import org.butternut.sb.state.State;

public class CrawlController
{
	private final Game game;
	
	public CrawlController(Game game) {
		this.game = game;
	}
	
	public void initializeCrawl() {
		Optional.ofNullable(this.game.clip).ifPresent(Clip::stop);
		this.game.clip = Audio.initAudio("src/main/resources/files/Star Wars.wav");
	}
	
	public void processCrawl() {
		this.game.next++;
		if(this.game.next == 3) {
			this.game.introPos--;
			this.game.next = 0;
		}
		if(this.game.introPos == -550) {
			this.game.state = State.MENU;
			this.game.clip.stop();
		}
	}
}
