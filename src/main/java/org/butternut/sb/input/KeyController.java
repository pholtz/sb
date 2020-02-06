package org.butternut.sb.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.butternut.sb.Game;
import org.butternut.sb.model.Point;
import org.butternut.sb.state.State;

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
        
        // Launch level and set initial character position
        if(key == KeyEvent.VK_1 && this.game.state == State.MENU) {
        	this.game.state = State.GAME;
        	game.menu = false;
        	game.gameInit = true;
        	game.mainChar.charPos = Point.of(0, (int)Game.HEIGHT - 2*Game.spriteHeight);
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
