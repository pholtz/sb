package org.butternut.sb.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuKeyController implements KeyListener
{
	private final MenuModel menuModel;
	
	public MenuKeyController(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        // Launch level and set initial character position
        if(key == KeyEvent.VK_1) {
        	this.menuModel.leave = true;
//        	game.mainChar.charPos = Point.of(0, (int)Game.HEIGHT - 2*Game.spriteHeight);
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
