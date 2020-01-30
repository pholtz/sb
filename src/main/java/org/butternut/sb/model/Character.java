package org.butternut.sb.model;

import org.butternut.sb.Game;

public class Character {

	public Point charPos;
	private String sprite1, sprite2, sprite3;
	public Rectangle charBlock;
	public boolean forward;
	
	public Character(String in_sprite1, String in_sprite2, String in_sprite3, Point in_charPos) {
		this.sprite1 = in_sprite1;
		this.sprite2 = in_sprite2;
		this.sprite3 = in_sprite3;
		this.charPos = in_charPos;
		this.charBlock = new Rectangle(charPos, Game.spriteWidth, 2*Game.spriteHeight);
		this.forward = true;
	}
	
	public String getSprite1() {
		return sprite1;
	}
	
	public String getSprite2() {
		return sprite2;
	}
	
	public String getSprite3() {
		return sprite3;
	}

}
