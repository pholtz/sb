package org.butternut.sb.crawl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.butternut.sb.Game;
import org.butternut.sb.state.State;

public class CrawlKeyController implements KeyListener
{
	private final Game game;
	
	public CrawlKeyController(Game game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        if(key == KeyEvent.VK_Q && this.game.state == State.CRAWL) {
        	this.game.clip.stop();
        	this.game.state = State.MENU;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
