package org.butternut.sb.time;

import org.butternut.sb.Game;
import org.butternut.sb.audio.Audio;
import org.butternut.sb.model.State;

public class AnimationController
{
	private final Game game;
	
	public AnimationController(Game game) {
		this.game = game;
	}
	
	public void processTimestep() {
		switch(this.game.state) {
			case PRECRAWL:
				this.game.clip = Audio.initAudio("src/main/resources/files/Star Wars.wav");
				this.game.state = State.CRAWL;
				break;
				
			case CRAWL:
				this.game.next++;
				if(this.game.next == 3) {
					this.game.introPos--;
					this.game.next = 0;
				}
				if(this.game.introPos == -550) {
					this.game.state = State.MENU;
					this.game.clip.stop();
				}
				break;
			
			case PREMENU:
				this.game.clip = Audio.initAudio("src/main/resources/files/blinkwhats.WAV");
				this.game.state = State.MENU;
				break;
			
			case MENU:
				break;
			
			default:
				break;
		}
	}
}
