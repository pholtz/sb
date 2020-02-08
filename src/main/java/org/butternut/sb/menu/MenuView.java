package org.butternut.sb.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.butternut.sb.Game;
import org.butternut.sb.asset.Assets;
import org.butternut.sb.swing.Panels;

public class MenuView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Game game;
	private final MenuModel menuModel;
	private final MenuKeyController menuKeyController;
	
	public MenuView(Game game,
			MenuModel menuModel) {
		this.game = game;
		this.menuModel = menuModel;
		this.menuKeyController = new MenuKeyController(menuModel);
		
		this.setPreferredSize(new Dimension((int) Game.WIDTH, (int) Game.HEIGHT));
		super.addKeyListener(this.menuKeyController);
	}
	
	public void initialize() {
		Panels.requestFocus(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
		Assets.createImage(g, "src/main/resources/files/sb2.png", 0, 0, Game.WIDTH, Game.HEIGHT); 
	}
}
