package org.butternut.sb.state;

import org.butternut.sb.Game;
import org.butternut.sb.audio.AudioController;
import org.butternut.sb.crawl.CrawlController;

public class StateController
{
	private final Game game;
	private final AudioController audioController;
	private final CrawlController crawlController;
	private boolean stateTransition = true;
	
	public StateController(Game game) {
		this.game = game;
		this.audioController = new AudioController();
		this.crawlController = new CrawlController(game);
	}
	
	public void processTimestep() {
		switch(this.game.state) {
			case CRAWL:
				if(this.isStateTransition()) {
					this.crawlController.initializeCrawl();
				}
				this.crawlController.processCrawl();
				break;
			
			case MENU:
				if(this.isStateTransition()) {
					this.audioController.initializeMenuAudio();
				}
				break;
			
			case GAME:
				if(this.isStateTransition()) {
					this.audioController.initializeGameAudio();
				}
				break;
				
			default:
				break;
		}
	}
	
	private boolean isStateTransition() {
		if(this.stateTransition) {
			this.stateTransition = false;
			return true;
		}
		return false;
	}
}
