package edu.ycp.ece220.rgb;

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
		this.fireBlock = new Rectangle(this.pos, game.spriteWidth, 2*game.spriteHeight);
		this.forward = right;
		startPos = (int)inPos.x;
		this.radius = inRadius;
	}

}
