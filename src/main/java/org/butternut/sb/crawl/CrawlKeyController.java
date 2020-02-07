package org.butternut.sb.crawl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CrawlKeyController implements KeyListener
{
	private final CrawlModel crawlModel;
	
	public CrawlKeyController(CrawlModel crawlModel) {
		this.crawlModel = crawlModel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        if(key == KeyEvent.VK_Q) {
        	this.crawlModel.leave = true;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
