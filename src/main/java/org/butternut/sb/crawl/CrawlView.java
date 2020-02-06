package org.butternut.sb.crawl;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.butternut.sb.Game;

public class CrawlView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Game game;
	private final CrawlKeyController crawlKeyController;
	
	public CrawlView(Game game) {
		this.game = game;
		this.crawlKeyController = new CrawlKeyController(game);
		
		super.addKeyListener(this.crawlKeyController);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
	}
}
