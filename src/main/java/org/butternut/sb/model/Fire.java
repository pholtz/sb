package org.butternut.sb.model;

import org.butternut.sb.Game;

public class Fire {

	public Point pos;
	public Rectangle fireBlock;
	public boolean forward;
	public int startPos, radius;
	
	/**
	 * @param args
	 */
	public Fire(Point inPos, int inRadius, boolean right) {
		this.pos = inPos;
		this.fireBlock = new Rectangle(this.pos, Game.spriteWidth, 2*Game.spriteHeight);
		this.forward = right;
		startPos = (int)inPos.x;
		this.radius = inRadius;
	}

}
