package edu.ycp.ece220.rgb;

public class Suit {

	public Point pos;
	public Rectangle suitBlock;
	public boolean forward;
	public int startPos, radius;
	
	public Suit(Point inPos, int inRadius) {
		this.pos = inPos;
		this.suitBlock = new Rectangle(this.pos, game.spriteWidth, 2*game.spriteHeight);
		this.forward = true;
		this.radius = inRadius;
		startPos = (int)inPos.x;
	}
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
