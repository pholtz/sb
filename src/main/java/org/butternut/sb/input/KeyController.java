package org.butternut.sb.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.butternut.sb.Game;
import org.butternut.sb.model.Point;

public class KeyController implements KeyListener
{
	private final Game game;
	
	public KeyController(Game game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.runCompositeEvent(e, "Typed");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        if(key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W) {
        	if(!this.game.falling) {
        		this.game.jumping = true;
        	}
        }
        this.runCompositeEvent(e, "Pressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.runCompositeEvent(e, "Released");
	}
	
	// TODO: Get rid of this hot garbage
	private void runCompositeEvent(KeyEvent e, String text) {
        int key = e.getKeyCode();
        if(game.game) {
            if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
            {
                if(text.contains("Pressed") || text.contains("Typed")) {
                    game.left = true;
                } else if(text.contains("Released")) {
                    game.left = false;
                }
                game.walking = true;
            }
            else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
            {
                if(text.contains("Pressed") || text.contains("Typed")) {
                	game.right = true;
                } else if(text.contains("Released")) {
                	game.right = false;
                }
                game.walking = true;
            } else if(key == KeyEvent.VK_EQUALS) {
            	game.levelUp = true;
            }
        }
        
        if(key == KeyEvent.VK_1 && game.menu) {
        	
        	//if '1' is pressed, exit menu and launch gameInit
        	game.menu = false;
        	game.gameInit = true;
        	
        	//reset charPos for new game
        	game.mainChar.charPos = Point.of(0, (int)Game.HEIGHT - 2*Game.spriteHeight);
        }
        
        if(key == KeyEvent.VK_Q && game.crawl) {
        	
        	//stop crawl music
        	game.clip.stop();
        	
        	//if 'q' is pressed, exit crawl and launch menuInit
        	game.menuInit = true;
        	game.crawl = false;
        	
        }
	}
}
