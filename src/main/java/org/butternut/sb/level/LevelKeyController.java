package org.butternut.sb.level;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LevelKeyController implements KeyListener
{
	private final LevelModel levelModel;

	public LevelKeyController(LevelModel levelModel) {
		this.levelModel = levelModel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W) {
			if (!this.levelModel.falling) {
				this.levelModel.jumping = true;
			}
		}

		if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			this.levelModel.left = true;
			this.levelModel.walking = true;
		} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			this.levelModel.right = true;
			this.levelModel.walking = true;
		} else if (key == KeyEvent.VK_EQUALS) {
			this.levelModel.levelUp = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			this.levelModel.left = false;
			this.levelModel.walking = true;
		} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			this.levelModel.right = false;
			this.levelModel.walking = true;
		} else if (key == KeyEvent.VK_EQUALS) {
			this.levelModel.levelUp = true;
		}
	}
}
