package org.butternut.sb.model;

import org.butternut.sb.Game;

public class Suit {

	public Point pos;
	public Rectangle suitBlock;
	public boolean forward;
	public int startPos, radius;
	
	public Suit(Point inPos, int inRadius) {
		this.pos = inPos;
		this.suitBlock = new Rectangle(this.pos, Game.spriteWidth, 2*Game.spriteHeight);
		this.forward = true;
		this.radius = inRadius;
		startPos = (int)inPos.x;
	}
}
