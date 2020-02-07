package org.butternut.sb.crawl;

import java.util.Optional;

import javax.sound.sampled.Clip;

import org.butternut.sb.Game;
import org.butternut.sb.audio.Audio;

public class CrawlController
{
	private final Game game;
	private final CrawlModel crawlModel;
	
	public CrawlController(Game game,
			CrawlModel crawlModel) {
		this.game = game;
		this.crawlModel = crawlModel;
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
			this.crawlModel.leave = true;
			this.game.clip.stop();
		}
	}
}
