package org.butternut.sb.model;

public class Lift {

	public Point pos;
	public Rectangle liftBlock;
	public int startHeight, maxHeight;
	
	public Lift(Rectangle inBlock, int inMax) {
		this.startHeight = (int)inBlock.topLeft.y;
		this.liftBlock = inBlock;
		this.pos = inBlock.topLeft;
		this.maxHeight = inMax;
	}

}
