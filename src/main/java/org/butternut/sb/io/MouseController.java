package org.butternut.sb.io;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.butternut.sb.Game;
import org.butternut.sb.model.Point;

public class MouseController implements MouseMotionListener
{
	private final Game game;
	
	public MouseController(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.game.cursor = Point.of(e.getX(), e.getY());
	}

}
