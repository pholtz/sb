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
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        if(key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W) {
        	if(!this.game.falling) {
        		this.game.jumping = true;
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
        
        if(game.game) {
            if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
            {
                game.left = true;
                game.walking = true;
            }
            else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
            {
                game.right = true;
                game.walking = true;
            } else if(key == KeyEvent.VK_EQUALS) {
            	game.levelUp = true;
            }
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
        if(game.game) {
            if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
            {
                game.left = false;
                game.walking = true;
            }
            else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
            {
                game.right = false;
                game.walking = true;
            } else if(key == KeyEvent.VK_EQUALS) {
            	game.levelUp = true;
            }
        }
	}
}
