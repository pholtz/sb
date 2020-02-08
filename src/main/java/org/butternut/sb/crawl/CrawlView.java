package org.butternut.sb.crawl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.butternut.sb.Game;
import org.butternut.sb.asset.Assets;
import org.butternut.sb.swing.Panels;

public class CrawlView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Game game;
	private final CrawlModel crawlModel;
	private final CrawlKeyController crawlKeyController;
	
	public CrawlView(Game game,
			CrawlModel crawlModel) {
		this.game = game;
		this.crawlModel = crawlModel;
		this.crawlKeyController = new CrawlKeyController(crawlModel);
		
		this.setPreferredSize(new Dimension((int) Game.WIDTH, (int) Game.HEIGHT));
		super.addKeyListener(this.crawlKeyController);
	}
	
	public void initialize() {
		Panels.requestFocus(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
		Assets.createImage(g, "src/main/resources/files/intro.png", ((int) Game.WIDTH/2) - 130, game.introPos, 263, 514);
	}
}
