package org.butternut.sb.state;

import javax.swing.JFrame;

import org.butternut.sb.Game;
import org.butternut.sb.audio.AudioController;
import org.butternut.sb.crawl.CrawlController;
import org.butternut.sb.crawl.CrawlModel;
import org.butternut.sb.crawl.CrawlView;
import org.butternut.sb.menu.MenuController;
import org.butternut.sb.menu.MenuModel;
import org.butternut.sb.menu.MenuView;

public class StateController
{
	// Get rid of these
//	private final Game game;
	private final AudioController audioController;
	private boolean stateTransition = true;
	
	private final JFrame frame;
	private final CrawlModel crawlModel;
	private final CrawlView crawlView;
	private final CrawlController crawlController;
	private final MenuModel menuModel;
	private final MenuView menuView;
	private final MenuController menuController;
	private State state;
	
	public StateController(JFrame frame,
			Game game) {
//		this.game = game;
		this.state = State.CRAWL;
		this.audioController = new AudioController();
		
		this.frame = frame;
		this.crawlModel = new CrawlModel();
		this.crawlView = new CrawlView(game, this.crawlModel);
		this.crawlController = new CrawlController(game, this.crawlModel);
		this.menuModel = new MenuModel();
		this.menuView = new MenuView(game, this.menuModel);
		this.menuController = new MenuController(game, this.menuModel);
	}
	
	// TODO: KeyControllers don't work until window is tabbed out and back in
	public void processTimestep() {
		switch(this.state) {
			case CRAWL:
				if(this.crawlModel.enter) {
					System.out.println("State transition -> entering crawl");
					this.crawlModel.enter = false;
					this.frame.getContentPane().removeAll();
					this.frame.setContentPane(this.crawlView);
					this.frame.pack();
					this.crawlController.initializeCrawl();
				}
				this.crawlController.processCrawl();
				this.crawlView.repaint();
				if(this.crawlModel.leave) {
					System.out.println("State transition -> leaving crawl");
					this.state = State.MENU;
				}
				break;
			
			case MENU:
				if(this.menuModel.enter) {
					System.out.println("State transition -> entering menu");
					this.menuModel.enter = false;
					this.frame.getContentPane().removeAll();
					this.frame.setContentPane(this.menuView);
					this.frame.pack();
					this.menuController.initializeMenu();
				}
				this.menuController.processMenu();
				this.menuView.repaint();
				if(this.menuModel.leave) {
					System.out.println("State transition -> leaving menu");
					this.state = State.GAME;
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
