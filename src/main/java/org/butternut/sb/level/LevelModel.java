package org.butternut.sb.level;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

import org.butternut.sb.model.Character;
import org.butternut.sb.model.Fire;
import org.butternut.sb.model.Lift;
import org.butternut.sb.model.Note;
import org.butternut.sb.model.Point;
import org.butternut.sb.model.Rectangle;
import org.butternut.sb.model.Suit;

public class LevelModel
{
	public boolean enter;
	public boolean leave;
	public boolean levelUp;
	public int levelCounter;
	public Clip clip;
	
	// Physics
	public boolean falling;
	public boolean jumping;
	public boolean walking;
	public boolean left;
	public boolean right;
	public boolean gravity;
	
	public int next;
	public int stride;
	public int nextSuitFrame;
	public int strideSuitFrame;
	public int jumpTime;
	public double g;
	public double suitG;
	
	// Entities
	public Character mainChar;
	public List<Suit> tempSuit;
	public List<Rectangle> popList;
	public List<Rectangle> rockList;
	public List<Rectangle> blockList;
	public List<Lift> liftList;
	public List<Rectangle> sandList;
	public List<Rectangle> magmaList;
	public List<Note> noteList;
	public List<Fire> fireList;
	public List<Suit> jetList;
	public List<Suit> suitList;
	
	public Rectangle door;
	public Rectangle bossDoor;
	
	// Other
	public double score;
	public boolean boss;
	public boolean suitDeath;
	public boolean death;
	public int timer;
	public double coldPop;
	
	public LevelModel() {
		this.enter = true;
		this.leave = false;
		
		this.mainChar = new Character("src/main/resources/sprites/mario1.png", "src/main/resources/sprites/mario2.png", "src/main/resources/sprites/mario3.png", new Point(0, LevelController.GROUND - LevelController.spriteHeight));
		this.tempSuit = new ArrayList<>();
		this.popList = new ArrayList<>();
		this.rockList = new ArrayList<>();
		this.blockList = new ArrayList<>();
		this.liftList = new ArrayList<>();
		this.sandList = new ArrayList<>();
		this.magmaList = new ArrayList<>();
		this.noteList = new ArrayList<>();
		this.fireList = new ArrayList<>();
		this.jetList = new ArrayList<>();
		this.suitList = new ArrayList<>();
		this.door = new Rectangle(new Point(5200, 30), LevelController.spriteWidth, 2*LevelController.spriteHeight);
		this.bossDoor = new Rectangle(new Point(-160, 650), 2*LevelController.spriteWidth, 4*LevelController.spriteHeight);
	}
}
