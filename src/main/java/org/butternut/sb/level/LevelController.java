package org.butternut.sb.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.Clip;

import org.butternut.sb.Game;
import org.butternut.sb.audio.Audio;
import org.butternut.sb.model.Fire;
import org.butternut.sb.model.Lift;
import org.butternut.sb.model.Note;
import org.butternut.sb.model.Point;
import org.butternut.sb.model.Rectangle;
import org.butternut.sb.model.Suit;

public class LevelController
{
	public static final double WIDTH = 900;
	public static final double HEIGHT = 660;
	public static final int spriteHeight = 20;
	public static final int spriteWidth = 20;
	public static final int popHeight = 15;
	public static final int popWidth = 10;
	public static final double GROUND = HEIGHT - 2*spriteHeight;
	
	private final Game game;
	private final LevelModel levelModel;
	private final LevelLoader levelLoader;
	
	public LevelController(Game game,
			LevelModel levelModel) {
		this.game = game;
		this.levelModel = levelModel;
		this.levelLoader = new LevelLoader();
	}
	
	public void initializeLevel() {
		Optional.ofNullable(this.levelModel.clip).ifPresent(Clip::stop);
		this.clearlists();
		this.levelModel.mainChar.charPos = Point.of(0, 0);
		this.levelModel.mainChar.charBlock.topLeft = this.levelModel.mainChar.charPos;
		
		Optional.ofNullable(this.levelModel.clip).ifPresent(Clip::stop);
		
		if(this.levelModel.levelCounter == 0 || this.levelModel.levelCounter == 1) {
			
			Level level = this.levelLoader.load(String.valueOf(this.levelModel.levelCounter) + ".json");
			
			this.levelModel.clip = Audio.initAudio("src/main/resources/files/" + level.getMusic().get(0));
			
			this.levelModel.mainChar.charPos = Point.of(level.getCharacter().get(0), level.getCharacter().get(1));
			this.levelModel.mainChar.charBlock.topLeft = this.levelModel.mainChar.charPos;
			
			for(List<Object> note : Optional.ofNullable(level.getNotes()).orElse(new ArrayList<>())) {
				this.levelModel.noteList.add(new Note(new Point(Double.valueOf(String.valueOf(note.get(0))), Double.valueOf(String.valueOf(note.get(1)))), (String) note.get(2)));
			}
			
			for(List<Long> block : Optional.ofNullable(level.getBlocks()).orElse(new ArrayList<>())) {
				this.addblock(block.get(0), block.get(1));
			}
			
			for(List<Long> coldpop : Optional.ofNullable(level.getColdpops()).orElse(new ArrayList<>())) {
				this.coldpop(coldpop.get(0), coldpop.get(1));
			}
			
			for(List<Long> suit : Optional.ofNullable(level.getSuits()).orElse(new ArrayList<>())) {
				this.levelModel.suitList.add(new Suit(new Point(suit.get(0), suit.get(1)), 50));
			}
			
			this.levelModel.door.topLeft = new Point(level.getDoor().get(0), level.getDoor().get(1));
		}
	}
	
	public void processLevel() {
		
		if(this.levelModel.suitDeath) {
			this.levelModel.timer++;
			if(this.levelModel.timer == 360 && this.levelModel.tempSuit.size() > 0) {
				this.levelModel.tempSuit.get(0).suitBlock.width = spriteWidth;
				this.levelModel.tempSuit.get(0).suitBlock.height = 2*spriteHeight;
				this.levelModel.tempSuit.get(0).suitBlock.topLeft.y -= spriteHeight;
				this.levelModel.suitList.add(this.levelModel.tempSuit.get(0));
				this.levelModel.tempSuit.remove(0);
				this.levelModel.timer = 0;
				this.levelModel.suitDeath = false;
			}
		}
		
		//handle jumping
		if(this.levelModel.jumping) {
			this.levelModel.mainChar.charPos.y -= (22 - (this.levelModel.jumpTime * .5));
			this.levelModel.jumpTime++;
			if(this.levelModel.jumpTime > 30) {
				this.levelModel.jumping = false;
				this.levelModel.gravity = true;
				this.levelModel.jumpTime = 0;
			}
		}
		
		//player takes coldpop
		for(int i = 0; i < this.levelModel.popList.size(); i++) {
			if(this.levelModel.mainChar.charBlock.takesPop(this.levelModel.popList.get(i))) {
				this.levelModel.score += 10;
				this.levelModel.coldPop++;
				this.levelModel.popList.remove(i);
				Audio.initAudio("src/main/resources/files/coldpop.wav");
			}
		}
		
		//FIRELIST
		List<Fire> fireList = this.levelModel.fireList;
		for(int i = 0; i < fireList.size(); i++) {
			if(fireList.get(i).radius != 0) {
				//update x coordinates of all fires in fireList
				if(fireList.get(i).forward) {
					fireList.get(i).pos.x -= 5;
					if(fireList.get(i).pos.x < (fireList.get(i).startPos - fireList.get(i).radius)) {
						fireList.get(i).forward = false;
					}
				} else {
					fireList.get(i).pos.x += 5;
					if(fireList.get(i).pos.x > (fireList.get(i).startPos + fireList.get(i).radius)) {
						fireList.get(i).forward = true;
					}
				}
			}
			//collision check for fireList
			if(this.levelModel.mainChar.charBlock.overlaps(fireList.get(i).fireBlock)) {
				this.levelModel.death = true;
			}
		}
		
		//SUITLIST
		List<Suit> suitList = this.levelModel.suitList;
		for(int i = 0; i < suitList.size(); i++) {
			
			if(this.levelModel.mainChar.charBlock.overlaps(suitList.get(i).suitBlock)
					|| this.levelModel.mainChar.charBlock.isJumpingInto(suitList.get(i).suitBlock)) {
				this.levelModel.death = true;
				break;
			}
			
			if(this.levelModel.mainChar.charBlock.isStandingOn(suitList.get(i).suitBlock) && this.levelModel.falling) {
				Suit temp = suitList.remove(i);
				temp.suitBlock.width = 2*spriteWidth;
				temp.suitBlock.height = spriteHeight;
				temp.suitBlock.topLeft.y += spriteHeight;
				this.levelModel.tempSuit.add(temp);
				this.levelModel.suitDeath = true;
				this.levelModel.jumping = true;
				this.levelModel.jumpTime = -5;
				break;
			}
			
			//update x coordinates of all suits in suitlist
			if(suitList.get(i).radius != 0) {
				if(suitList.get(i).forward) {
					suitList.get(i).pos.x += 1;
					if(suitList.get(i).pos.x > (suitList.get(i).startPos + suitList.get(i).radius)) {
						suitList.get(i).forward = false;
					}
				} else {
					suitList.get(i).pos.x -= 1;
					if(suitList.get(i).pos.x < (suitList.get(i).startPos - suitList.get(i).radius)) {
						suitList.get(i).forward = true;
					}
				}
				
				if(this.levelModel.nextSuitFrame > 0 && this.levelModel.nextSuitFrame <= 10) {
					this.levelModel.strideSuitFrame = 1;
				} else if(this.levelModel.nextSuitFrame > 10 && this.levelModel.nextSuitFrame <= 20) {
					this.levelModel.strideSuitFrame = 2;
				} else if(this.levelModel.nextSuitFrame > 20 && this.levelModel.nextSuitFrame <= 30) {
					this.levelModel.strideSuitFrame = 3;
				} else if(this.levelModel.nextSuitFrame > 30 && this.levelModel.nextSuitFrame <= 40) {
					this.levelModel.strideSuitFrame = 4;
				}
				if(this.levelModel.nextSuitFrame == 40) {
					this.levelModel.nextSuitFrame = 0;
				} else {
					this.levelModel.nextSuitFrame++;
				}
			}
			
		}
		
		//BLOCKLIST COLLISION CHECK
		for(int i = 0; i < this.levelModel.blockList.size(); i++) {
			//side collision
			if(this.levelModel.mainChar.charBlock.overlaps(this.levelModel.blockList.get(i))) {
				this.levelModel.walking = false;
				break;
			} else {
				this.levelModel.walking = true;
			}
		}
		//PART 2
		for(int i = 0; i < this.levelModel.blockList.size(); i++) {
			//jump collision
			if(this.levelModel.mainChar.charBlock.isJumpingInto(this.levelModel.blockList.get(i))) {
				this.levelModel.jumping = false;
				this.levelModel.jumpTime = 0;
				this.levelModel.gravity = true;
				break;
			} 
			//if character is standing on a block in the blockList
			else if(this.levelModel.mainChar.charBlock.isStandingOn(this.levelModel.blockList.get(i))) {
				this.levelModel.gravity = false;
				break;
			} else {
				this.levelModel.gravity = true;
			}
		}
		//LIFTLIST: check to see if player is standing on lift--if they are, they clearly aren't falling
		for(int i = 0; i < this.levelModel.liftList.size(); i++) {
			if(this.levelModel.mainChar.charBlock.isStandingOn(this.levelModel.liftList.get(i).liftBlock)) {
				this.levelModel.gravity = false;
			}
		}
		//SANDLIST collision check--drops blocks is char is standing on them
		for(int i = 0; i < this.levelModel.sandList.size(); i++) {
			//jump collision
			if(this.levelModel.mainChar.charBlock.isJumpingInto(this.levelModel.sandList.get(i))) {
				this.levelModel.jumping = false;
				this.levelModel.jumpTime = 0;
				this.levelModel.gravity = true;
				break;
			} 
			else if(this.levelModel.mainChar.charBlock.isStandingOn(this.levelModel.sandList.get(i))) {
				this.levelModel.sandList.get(i).topLeft.y+=2;
				this.levelModel.mainChar.charPos.y+=2;
				this.levelModel.gravity = false;
			}
		}
		
		//MAGMALIST
		for(int i = 0; i < this.levelModel.magmaList.size(); i++) {
			if(this.levelModel.mainChar.charBlock.overlaps(this.levelModel.magmaList.get(i))) {
				this.levelModel.death = true;
				break;
			}
		}
		
		//ROCKLIST collision checker and y-pos updater
		for(int i = 0; i < this.levelModel.rockList.size(); i++) {
			if(this.levelModel.rockList.get(i).topLeft.y >= 700) {
				this.levelModel.rockList.get(i).topLeft.y = this.levelModel.rockList.get(i).startHeight;
			} else {
				this.levelModel.rockList.get(i).topLeft.y += 9;
			}
			if(this.levelModel.mainChar.charBlock.overlaps(this.levelModel.rockList.get(i))) {
				this.levelModel.death = true;
				break;
			}
		}
		
		//jetSuit y-pos updater and collision checker
		List<Suit> jetList = this.levelModel.jetList;
		for(int i = 0; i < jetList.size(); i++) {
			if(jetList.get(i).forward) {
				if(jetList.get(i).pos.y < (jetList.get(i).suitBlock.startHeight - jetList.get(i).radius)) {
					jetList.get(i).forward = false;
				}
			} else {
				if(jetList.get(i).pos.y > (jetList.get(i).suitBlock.startHeight)) {
					jetList.get(i).forward = true;
					this.levelModel.suitG = -1;
				}
			}
			this.levelModel.suitG += .01;
			if(jetList.get(i).forward) {
				jetList.get(i).pos.y -= this.levelModel.suitG;
			} else {
				jetList.get(i).pos.y += this.levelModel.suitG;
			}
			if(this.levelModel.mainChar.charBlock.isStandingOn(jetList.get(i).suitBlock)) {
				suitList.add(jetList.remove(i));
				this.levelModel.jumping = true;
				this.levelModel.jumpTime = -5;
			} else if(this.levelModel.mainChar.charBlock.overlaps(jetList.get(i).suitBlock)) {
				this.levelModel.death = true;
				break;
			}
		}
		
		//MAIN CHAR MOVEMENT______________________
		//handle side to side movement & determine which animation frame to display
		//oscillates between animation frames for side to side movement
		if((this.levelModel.left && this.levelModel.walking )
				|| (this.levelModel.right && this.levelModel.walking)) {
			if(this.levelModel.left) {
				if(this.levelModel.g < 5) {
					this.levelModel.g += .1;
				}
				this.levelModel.mainChar.charPos.x += 3 + this.levelModel.g;
				this.levelModel.mainChar.forward = false;
			} else if(this.levelModel.right) {
				if(this.levelModel.g > -5) {
					this.levelModel.g -= .1;
				}
				this.levelModel.mainChar.charPos.x -= 3 - this.levelModel.g;
				this.levelModel.mainChar.forward = true;
			}
			if(this.levelModel.next > 0 && this.levelModel.next <= 5) {
				this.levelModel.stride = 1;
			} else if(this.levelModel.next > 5 && this.levelModel.next <= 10) {
				this.levelModel.stride = 2;
			} else if(this.levelModel.next > 10 && this.levelModel.next <= 15) {
				this.levelModel.stride = 3;
			} else if(this.levelModel.next > 15 && this.levelModel.next <= 20) {
				this.levelModel.stride = 4;
			}
			if(this.levelModel.next == 20) {
				this.levelModel.next = 0;
			} else {
				this.levelModel.next++;
			}
		} else {
			this.levelModel.stride = 0;
			this.levelModel.g = 0;
		}
		
		//LIFT
		List<Lift> liftList = this.levelModel.liftList;
		for(int i = 0; i < liftList.size(); i++) {
			if(this.levelModel.mainChar.charBlock.isStandingOn(liftList.get(i).liftBlock) && liftList.get(i).liftBlock.topLeft.y > liftList.get(i).maxHeight) {
				liftList.get(i).liftBlock.topLeft.y -= 5;
			} else if(this.levelModel.mainChar.charBlock.isStandingOn(liftList.get(i).liftBlock)) {
				//do nothing
			} else if(liftList.get(i).liftBlock.topLeft.y < liftList.get(i).startHeight) {
				liftList.get(i).liftBlock.topLeft.y += 2;
			}
		}
		
		//GRAVITY
		if(this.levelModel.gravity) {
			this.levelModel.mainChar.charPos.y += 9;
			this.levelModel.falling = true;
		} else {
			this.levelModel.falling = false;
		}
		//gravity for bots
		for(int i = 0; i < suitList.size(); i++) {
			suitList.get(i).pos.y += 5;
			suitList.get(i).pos.x *= -1;
			for(int j = 0; j < this.levelModel.blockList.size(); j++) {
				if(suitList.get(i).suitBlock.isStandingOn(this.levelModel.blockList.get(j))) {
					break;
				}
			}
			suitList.get(i).pos.x *= -1;
		}
		for(int i = 0; i < fireList.size(); i++) {
			fireList.get(i).pos.y += 2;
			fireList.get(i).pos.x *= -1;
			for(int j = 0; j < this.levelModel.blockList.size(); j++) {
				if(fireList.get(i).fireBlock.isStandingOn(this.levelModel.blockList.get(j))) {
					break;
				}
			}
			fireList.get(i).pos.x *= -1;
		}
		
		//FALLING DEATH
		if(this.levelModel.mainChar.charPos.y >= 700) {
			this.levelModel.death = true;
		}
		//player finds exit
		if(this.levelModel.mainChar.charBlock.overlaps(this.levelModel.door)
				|| this.levelModel.mainChar.charBlock.overlaps(this.levelModel.bossDoor)) {
			this.levelModel.levelUp = true;
			Audio.initAudio("src/main/resources/files/doorOpen.wav");
		}
	}
	
	private void addblock(double x, double y) {
		this.levelModel.blockList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	
	private void coldpop(double x, double y) {
		this.levelModel.popList.add(new Rectangle(new Point((int)(x + 5), (int)y), popWidth, popHeight));
	}
	
	private void addmagma(double x, double y) {
		this.levelModel.magmaList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	
	private void addsand(double x, double y) {
		this.levelModel.sandList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	
	private void addrock(double x, double y) {
		this.levelModel.rockList.add(new Rectangle(new Point((int)x, (int)y), 2*spriteWidth, 2*spriteHeight));
	}
	
	private void clearlists() {
		this.levelModel.blockList.clear();
		this.levelModel.fireList.clear();
		this.levelModel.suitList.clear();
		this.levelModel.tempSuit.clear();
		this.levelModel.popList.clear();
		this.levelModel.liftList.clear();
		this.levelModel.magmaList.clear();
		this.levelModel.rockList.clear();
		this.levelModel.noteList.clear();
		this.levelModel.jetList.clear();
		this.levelModel.sandList.clear();
	}
}
