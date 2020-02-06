package org.butternut.sb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import org.butternut.sb.level.Level;
import org.butternut.sb.level.LevelLoader;
import org.butternut.sb.model.Character;
import org.butternut.sb.model.Fire;
import org.butternut.sb.model.Lift;
import org.butternut.sb.model.Note;
import org.butternut.sb.model.Point;
import org.butternut.sb.model.Rectangle;
import org.butternut.sb.model.State;
import org.butternut.sb.model.Suit;

public class Game
{
	public static final double WIDTH = 900;
	public static final double HEIGHT = 660;
	public static final int spriteHeight = 20;
	public static final int spriteWidth = 20;
	public static final int popHeight = 15;
	public static final int popWidth = 10;
	public static final double GROUND = HEIGHT - 2*spriteHeight;
	
	// Immutable dependencies
	private final LevelLoader levelLoader;
	
	// Mutable observable state
	public State state;
	public int levelCounter;
	public Point cursor;
	
	private Random random;
	
	public Rectangle door, bossDoor;
	public Point startPoint, startcharPos;
	public boolean menu, game, walking, jumping, gravity, left, right, pregame,
	menuInit, crawl, crawlInit, gameInit, falling, death, deathInit, suitDeath, highscoresInit, newHighScore, highscores, boss;
	public int next, jumpTime, introPos, stride, timer, flameCount;
	double g, reset, score, coldPop, suitG;
	public boolean levelUp;
	
	public Character mainChar;
	Fire fire1;
	ArrayList<Suit> tempSuit;
	
	public ArrayList<Rectangle> blockList;
	public ArrayList<Fire> fireList;
	public ArrayList<Suit> suitList;
	public ArrayList<Rectangle> magmaList;
	public ArrayList<Rectangle> popList;
	public ArrayList<Lift> liftList;
	public ArrayList<Rectangle> sandList;
	public ArrayList<Rectangle> rockList;
	public ArrayList<Note> noteList;
	public ArrayList<Suit> jetList;
	int nextSuitFrame, strideSuitFrame;
	
	//MUSIC INITIALIZERS
    File yourFile;
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    public Clip clip = null;
    
    File yourFile1;
    AudioInputStream stream1;
    AudioFormat format1;
    DataLine.Info info1;
    Clip clip1 = null;
    
    //FILE IO
    FileReader scoresfr;
    BufferedReader scoresbr;
    FileWriter scoreswriter;
    File scoresfile;
    FileReader namesfr;
    BufferedReader namesbr;
    FileWriter nameswriter;
    File namesfile;
    ArrayList<Integer> scoresList;
    ArrayList<String> namesList;
    Scanner keyboard;
	public int count;
	
	/**
	 * Constructor: initialize the game state.
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public Game() throws IOException, URISyntaxException {
		this.levelLoader = new LevelLoader();
		this.state = State.PRECRAWL;
		this.levelCounter = 0;
		this.cursor = Point.of(0, 0);
		
		keyboard = new Scanner(System.in);
		scoresList = new ArrayList<Integer>();
		namesList = new ArrayList<String>();
		scoresfile = Paths.get("src/main/resources/files/scores.txt").toFile();
		scoresfr = new FileReader(scoresfile);
		scoresbr = new BufferedReader(scoresfr);
		scoreswriter = new FileWriter(scoresfile);
		namesfile = Paths.get("src/main/resources/files/names.txt").toFile();
		namesfr = new FileReader(namesfile);
		namesbr = new BufferedReader(namesfr);
		nameswriter = new FileWriter(namesfile);
		nextSuitFrame = 0;
		strideSuitFrame = 0;
		random = new Random(19580427);
		menu = false;
		game = false;
		boss = false;
		next = 0;
		jumpTime = 0;
		startcharPos = new Point(0, GROUND - spriteHeight);
		walking = false;
		stride = 0;
		jumping = false;
		gravity = false;
		left = false;
		right = false;
		menuInit = false;
		crawlInit = true;
		crawl = true;
		introPos = (int)HEIGHT;
		mainChar = new Character("src/main/resources/sprites/mario1.png", "src/main/resources/sprites/mario2.png", "src/main/resources/sprites/mario3.png", startcharPos);
		blockList = new ArrayList<Rectangle>();
		gameInit = false;
		falling = false;
		death = false;
		deathInit = false;
		g = 0;
		suitG = 0;
		reset = 0;
		flameCount = 0;
		fire1 = new Fire(new Point(300, GROUND - spriteHeight), 25, true);
		fireList = new ArrayList<Fire>();
		door = new Rectangle(new Point(5200, 30), spriteWidth, 2*spriteHeight);
		levelUp = false;
		suitList = new ArrayList<Suit>();
		tempSuit = new ArrayList<Suit>();
		popList = new ArrayList<Rectangle>();
		liftList = new ArrayList<Lift>();
		magmaList = new ArrayList<Rectangle>();
		sandList = new ArrayList<Rectangle>();
		rockList = new ArrayList<Rectangle>();
		noteList = new ArrayList<Note>();
		jetList = new ArrayList<Suit>();
		suitDeath = false;
		highscoresInit = false;
		newHighScore = false;
		highscores = false;
		timer = 0;
		score = 0;
		bossDoor = new Rectangle(new Point(-160, 650), 2*spriteWidth, 4*spriteHeight);
		pregame = false;
	}
	
	public static Game initialize() {
		try {
			return new Game();
		} catch(Exception exception) {
			throw new RuntimeException("Error while intializing game", exception);
		}
	}

	/**
	 * This method is called approximately 60 times per second.
	 * It should update the game state by moving the bomber and bombs,
	 * and moving the bucket if the player has moved the mouse.
	 * @throws IOException 
	 */
	public void timerTick() {
		
		// TODO: Remove all timestep based logic from Game -> TimestepController
		
//		//CRAWL INIT____________________________
//		if(crawlInit) {
//			initAudio("src/main/resources/files/Star Wars.wav");
//			crawlInit = false;
//		}
//		
//		//CRAWL LOGIC_____________________________
//		if(crawl) {
//			next++;
//			if(next == 3) {
//				introPos--;
//				next = 0;
//			}
//			if(introPos == -550) {
//				menuInit = true;
//				crawl = false;
//				clip.stop();
//			}
//		}
//		
//		//MENU INIT____________________________
//		if(menuInit) {
//			initAudio("src/main/resources/files/blinkwhats.WAV");
//			menuInit = false;
//			menu = true;
//		}
		
		
		//GAME INIT___________________________
		if(gameInit) {
			Optional.ofNullable(this.clip).ifPresent(Clip::stop);
			Optional.ofNullable(this.clip1).ifPresent(Clip::stop);
			this.clearlists();
			this.mainChar.charPos = Point.of(0, 0);
			this.mainChar.charBlock.topLeft = this.mainChar.charPos;
			
			if(levelCounter == 0 || levelCounter == 1) {
				
				Level level = this.levelLoader.load(String.valueOf(this.levelCounter) + ".json");
				
				this.initMusic("src/main/resources/files/" + level.getMusic().get(0));
				
				this.mainChar.charPos = Point.of(level.getCharacter().get(0), level.getCharacter().get(1));
				this.mainChar.charBlock.topLeft = this.mainChar.charPos;
				
				for(List<Object> note : Optional.ofNullable(level.getNotes()).orElse(new ArrayList<>())) {
					this.noteList.add(new Note(new Point(Double.valueOf(String.valueOf(note.get(0))), Double.valueOf(String.valueOf(note.get(1)))), (String) note.get(2)));
				}
				
				for(List<Long> block : Optional.ofNullable(level.getBlocks()).orElse(new ArrayList<>())) {
					this.addblock(block.get(0), block.get(1));
				}
				
				for(List<Long> coldpop : Optional.ofNullable(level.getColdpops()).orElse(new ArrayList<>())) {
					this.coldpop(coldpop.get(0), coldpop.get(1));
				}
				
				for(List<Long> suit : Optional.ofNullable(level.getSuits()).orElse(new ArrayList<>())) {
					this.suitList.add(new Suit(new Point(suit.get(0), suit.get(1)), 50));
				}
				
				this.door.topLeft = new Point(level.getDoor().get(0), level.getDoor().get(1));
			}
			
			if(levelCounter == 2) {
				clearlists();
				for(double i = 0; i < 2*WIDTH; i+= spriteWidth) {
					blockList.add(new Rectangle(new Point(i, HEIGHT - spriteHeight), spriteWidth, spriteHeight));
//					blockList.add(new Rectangle(new Point(i, 0), spriteWidth, spriteHeight));
				}
				blockList.add(new Rectangle(new Point(250, GROUND - 100), 4*spriteWidth, spriteHeight));
				suitList.add(new Suit(new Point(290, GROUND - 140), 30));
				coldpop(250, GROUND - 120);
				coldpop(280, GROUND - 120);
				coldpop(310, GROUND - 120);
				
				blockList.add(new Rectangle(new Point(100, GROUND - 250), 4*spriteWidth, spriteHeight));
				suitList.add(new Suit(new Point(135, GROUND - 290), 30));
				coldpop(100, GROUND - 270);
				coldpop(130, GROUND - 270);
				coldpop(160, GROUND - 270);
				
				blockList.add(new Rectangle(new Point(300, GROUND - 400), 4*spriteWidth, spriteHeight));
				suitList.add(new Suit(new Point(330, GROUND - 440), 30));
				coldpop(300, GROUND - 420);
				coldpop(330, GROUND - 420);
				coldpop(360, GROUND - 420);
				
				for(double i = 3*spriteHeight; i < HEIGHT - spriteHeight; i+= spriteHeight) {
					blockList.add(new Rectangle(new Point(400, i), spriteWidth, spriteHeight));
//					blockList.add(new Rectangle(new Point(i, 0), spriteWidth, spriteHeight));
				}
				door.topLeft.x = 800;
			}
			
			if(levelCounter == 3) {
				clearlists();
				for(double i = 0; i < 200; i+= spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
				}
				//stairs
				addblock(200, GROUND);
				addblock(220, GROUND - spriteHeight);
				addblock(240, GROUND - 2*spriteHeight);
				addblock(260, GROUND - 3*spriteHeight);
				addblock(280, GROUND - 4*spriteHeight);
				addblock(300, GROUND - 5*spriteHeight);
				//2pair jumps
				addblock(450, GROUND - 5*spriteHeight);
				addblock(470, GROUND - 5*spriteHeight);
				coldpop(450, GROUND - 6*spriteHeight);
				coldpop(470, GROUND - 6*spriteHeight);
				
				addblock(620, GROUND - 5*spriteHeight);
				addblock(640, GROUND - 5*spriteHeight);
				coldpop(620, GROUND - 6*spriteHeight);
				coldpop(640, GROUND - 6*spriteHeight);
				
				addblock(790, GROUND - 5*spriteHeight);
				addblock(810, GROUND - 5*spriteHeight);
				coldpop(790, GROUND - 6*spriteHeight);
				coldpop(810, GROUND - 6*spriteHeight);
				
				//stairs
				addblock(960, GROUND - 5*spriteHeight);
				addblock(980, GROUND - 4*spriteHeight);
				addblock(1000, GROUND - 3*spriteHeight);
				addblock(1020, GROUND - 2*spriteHeight);
				addblock(1040, GROUND - spriteHeight);
				addblock(1060, GROUND);
				for(double i = 1080; i < 1280; i+= spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
				}
				//exit
				door.topLeft = new Point(80, 40);
				addblock(80, 80);
				addblock(100, 80);
				addblock(120, 80);
				addblock(60, 80);
				addblock(60, 60);
				addblock(60, 40);
				
				liftList.add(new Lift(new Rectangle(new Point(1300, HEIGHT - spriteHeight), 100, 10), 160));
				addblock(1280, 160);
				addblock(1260, 160);
				jetList.add(new Suit(new Point(1100, 300), 300));
				addblock(960, 160);
				addblock(940, 160);
				jetList.add(new Suit(new Point(800, 300), 200));
				addblock(680, 160);
				addblock(660, 160);
				jetList.add(new Suit(new Point(460, 300), 100));
				addblock(320, 160);
				addblock(300, 160);
			}
			
			if(levelCounter == 4) {
				clearlists();
				mainChar.charPos.y = 100;
				for(int i = 200; i < 300; i += spriteWidth) {
					for(int j = 180; j < i; j += spriteHeight) {
						addblock(i, HEIGHT - j + 140);
					}
				}
				for(double i = 0; i < 300; i += spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
				}
				for(double i = 300; i < 3400; i += spriteWidth) {
					addmagma(i, HEIGHT - spriteHeight);
					addmagma(i, HEIGHT - 2*spriteHeight);
				}
				addblock(3400, HEIGHT - spriteHeight);
				addblock(3400, HEIGHT - 2*spriteHeight);
				for(int i = 300; i < 3300; i+= 300) {
					addblock(i, 520);
					coldpop(i, 500);
					addblock(i + 20, 520);
					coldpop(i + 20, 500);
					addblock(i + 40, 520);
					coldpop(i + 40, 500);
					addrock(i + 160, -random.nextDouble()*1000);
				}
				liftList.add(new Lift(new Rectangle(new Point(3420, GROUND), 100, 10), 150));
				coldpop(3460, 200);
				coldpop(3460, 250);
				coldpop(3460, 300);
				coldpop(3460, 350);
				coldpop(3460, 400);
				coldpop(3460, 450);
				coldpop(3460, 500);
				for(int i = 3520; i < 4000; i += spriteWidth) {
					addblock(i, 150);
				}
				suitList.add(new Suit(new Point(3750, 110), 200));
				for(int i = 4000; i < 5000; i += spriteWidth) {
					addsand(i, 150);
				}
				for(int i = 5000; i < 5500; i += spriteWidth) {
					addblock(i, 150);
				}
				suitList.add(new Suit(new Point(5200, 110), 180));
				door.topLeft = new Point(10000, -110);	//5480, 110
				addsand(5500, 170);
				addsand(5520, 190);
				addsand(5540, 210);
				addsand(5560, 230);
				addsand(5580, 250);
				addsand(5600, 270);
				addsand(5620, 290);
				addsand(5640, 310);
				addsand(5660, 330);
				addsand(5680, 350);
				addsand(5700, 370);
				addsand(5720, 390);
				addsand(5740, 410);
				for(int i = 0; i < 300; i += spriteWidth) {
					addblock(5760 + i, 50 + i);
					addblock(5760 + i, 430);
				}
				bossDoor.topLeft = new Point(6020, 350);
			}
			
			if(levelCounter == 5) {
				clip.stop();
				clip.drain();
				clip1.stop();
				clip1.drain();
				initMusic("src/main/resources/files/bbsabotage.WAV");
				boss = true;
				clearlists();
				mainChar.charPos.x = -40;
				mainChar.charPos.y = GROUND;
				for(double i = 0; i < WIDTH; i += spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
					addblock(i, 0);
				}
				for(int i = 0; i < HEIGHT; i += spriteHeight) {
					addblock(0, i);
					addblock(WIDTH - spriteWidth, i);
				}
				suitList.add(new Suit(new Point(760, 200), 0));
				suitList.get(0).forward = false;
				suitList.get(0).suitBlock.width = 100;
				suitList.get(0).suitBlock.height = 200;
				for(int i = 0; i < 900; i += spriteWidth) {
					addblock(i, 200);
				}
				blockList.remove(196);
				liftList.add(new Lift(new Rectangle(new Point(365, GROUND), 80, 10), 300));
			}
			
			if(levelCounter == 6) {
				boss = false;
				clearlists();
				for(double i = 0; i < WIDTH/2; i += spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
					addblock(i, 0);
					addblock(-20, i);
					addblock(i + 1400, HEIGHT - spriteHeight);
					addblock(2700 + i, GROUND);
				}
				addsand(600, 500);
				addsand(620, 500);
				addsand(800, 400);
				addsand(820, 400);
				addblock(1000, 300);
				addblock(1020, 300);
				addblock(1040, 300);
				for(int i = 0; i < 400; i += spriteHeight) {
					addblock(1020, 300 + i);
				}
				addsand(1200, 200);
				addsand(1220, 200);
				addsand(1400, 200);
				addsand(1420, 200);
				coldpop(1400, 250);
				coldpop(1420, 300);
				coldpop(1400, 350);
				coldpop(1420, 400);
				coldpop(1400, 450);
				coldpop(1420, 500);
				suitList.add(new Suit(new Point(1700, HEIGHT - 2*spriteHeight), 150));
				addsand(2000, 600);
				addsand(2020, 600);
				jetList.add(new Suit(new Point(2100, 500), 400));
				addsand(2200, 500);
				addsand(2220, 500);
				jetList.add(new Suit(new Point(2300, 400), 300));
				addsand(2400, 400);
				addsand(2420, 400);
				jetList.add(new Suit(new Point(2500, 300), 200));
				addblock(4020, 200);
				addblock(4040, 200);
				addblock(4060, 200);
				for(int i = 0; i < HEIGHT; i += spriteHeight) {
					addblock(4040, 220 + i);
				}
				door.topLeft = new Point(4040, 160);
				addblock(3340, 500);
				addblock(3360, 500);
				addblock(3380, 500);
				addblock(3600, 350);
				addblock(3620, 350);
				addblock(3640, 350);
				addblock(3860, 200);
				addblock(3880, 200);
				addblock(3900, 200);
			}
			
			if(levelCounter == 7) {
				clearlists();
				for(int i = 0; i < 2*WIDTH; i += spriteWidth) {
					addblock(i, HEIGHT - spriteHeight);
//					addblock(i, 0);
					addblock(-20, i);
					addblock(1820 + i, 80);
				}
				addrock(380, -100);
				addrock(680, -400);
				addrock(980, -200);
				addrock(1300, -50);
				addrock(1450, -350);
				addrock(1600, - 500);
				addblock(100, GROUND);
				addblock(200, GROUND);
				addblock(200, GROUND - spriteHeight);
				coldpop(100, GROUND - spriteHeight);
				coldpop(150, GROUND);
				suitList.add(new Suit(new Point(150, GROUND - spriteHeight), 30));
				addblock(300, GROUND);
				addblock(300, GROUND - spriteHeight);
				addblock(300, GROUND - 2*spriteHeight);
				coldpop(200, GROUND - 2*spriteHeight);
				coldpop(250, GROUND);
				coldpop(300, GROUND - 3*spriteHeight);
				for(int i = 0; i < 1.5*WIDTH; i += spriteWidth) {
					addmagma(320 + i, GROUND);
					addmagma(320 + i, GROUND - spriteHeight);
				}
				suitList.add(new Suit(new Point(250, GROUND - spriteHeight), 30));
				coldpop(700, 300);
				coldpop(1000, 200);
				for(int i = 0; i < 100; i += spriteWidth) {
					addblock(i + 500, 500);
					coldpop(i + 500, 480);
					addblock(i + 800, 400);
					coldpop(i + 800, 380);
					addblock(i + 1100, 300);
					coldpop(i + 1100, 280);
				}
				addblock(1680, GROUND);
				addblock(1680, GROUND - spriteHeight);
				addblock(1680, GROUND - 2*spriteHeight);
				liftList.add(new Lift(new Rectangle(new Point(2*WIDTH + spriteWidth, HEIGHT - spriteHeight), 30, 5), 160));
				for(int i = 0; i < WIDTH; i += 2*spriteWidth) {
					coldpop(1820 + i, 60);
					coldpop(1840 + i, -60);
					addblock(4700 + i, 40);
					coldpop(4720 + i, 20);
				}
				liftList.add(new Lift(new Rectangle(new Point(4600, HEIGHT), 80, 5), 60));
				noteList.add(new Note(new Point(3620, 100), "dead end"));
				noteList.add(new Note(new Point(4000, 300), "nothing to see here"));
				noteList.add(new Note(new Point(4500, 500), "stop trying"));
				noteList.add(new Note(new Point(5000, 60), "victory coldpop"));
			}
			
			gameInit = false;
			count = 0;
			pregame = true;
			
			if(levelCounter == 10) {
				game = false;
				highscoresInit = true;
			}
		}
		
		if(pregame) {
			count++;
			if(count > 180) {
				pregame = false;
				game = true;
			}
		}
		
		
		//GAME LOGIC____________________________
		if(game) {
			
			if(boss && !suitDeath) {
//				if(suitList.get(0).forward) {
//					suitList.get(0).pos.x += 5;
//				} else {
//					suitList.get(0).pos.x -= 5;
//				}
				flameCount++;
				if(flameCount > 180) {
					fireList.add(new Fire(new Point(760, 500), 400, true));
					flameCount = 0;
				}
			}
			if(boss && suitDeath) {
				if(liftList.size() == 1) {
					tempSuit.remove(0);
					for(int i = spriteWidth; i < WIDTH - 5*spriteWidth; i += spriteWidth) {
						coldpop(i, 180);
					}
					liftList.add(new Lift(new Rectangle(new Point(800, GROUND + 15), 25, 5), 200));
					door.topLeft = new Point(860, 160);
				}
			}
			
			if(suitDeath) {
				timer++;
				if(timer == 360 && tempSuit.size() > 0) {
					tempSuit.get(0).suitBlock.width = spriteWidth;
					tempSuit.get(0).suitBlock.height = 2*spriteHeight;
					tempSuit.get(0).suitBlock.topLeft.y -= spriteHeight;
					suitList.add(tempSuit.get(0));
					tempSuit.remove(0);
					timer = 0;
					suitDeath = false;
				}
			}
			
			//handle jumping
			if(jumping) {
				mainChar.charPos.y -= (22 - (jumpTime * .5));
				jumpTime++;
				if(jumpTime > 30) {
					jumping = false;
					gravity = true;
					jumpTime = 0;
				}
			}
			
			//player takes coldpop
			for(int i = 0; i < popList.size(); i++) {
				if(mainChar.charBlock.takesPop(popList.get(i))) {
					score += 10;
					coldPop++;
					popList.remove(i);
					initAudio("src/main/resources/files/coldpop.wav");
				}
			}
			
			//FIRELIST
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
				if(mainChar.charBlock.overlaps(fireList.get(i).fireBlock)) {
					game = false;
					deathInit = true;
				}
			}
			
			//SUITLIST
			for(int i = 0; i < suitList.size(); i++) {
				
				if(mainChar.charBlock.overlaps(suitList.get(i).suitBlock) || mainChar.charBlock.isJumpingInto(suitList.get(i).suitBlock)) {
					game = false;
					deathInit = true;
					break;
				}
				
				if(mainChar.charBlock.isStandingOn(suitList.get(i).suitBlock) && falling) {
					Suit temp = suitList.remove(i);
					temp.suitBlock.width = 2*spriteWidth;
					temp.suitBlock.height = spriteHeight;
					temp.suitBlock.topLeft.y += spriteHeight;
					tempSuit.add(temp);
					suitDeath = true;
					jumping = true;
					jumpTime = -5;
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
					
					if(nextSuitFrame > 0 && nextSuitFrame <= 10) {
						strideSuitFrame = 1;
					} else if(nextSuitFrame > 10 && nextSuitFrame <= 20) {
						strideSuitFrame = 2;
					} else if(nextSuitFrame > 20 && nextSuitFrame <= 30) {
						strideSuitFrame = 3;
					} else if(nextSuitFrame > 30 && nextSuitFrame <= 40) {
						strideSuitFrame = 4;
					}
					if(nextSuitFrame == 40) {
						nextSuitFrame = 0;
					} else {
						nextSuitFrame++;
					}
				}
				
			}
			
			//BLOCKLIST COLLISION CHECK
			for(int i = 0; i < blockList.size(); i++) {
				//side collision
				if(mainChar.charBlock.overlaps(blockList.get(i))) {
					walking = false;
					break;
				} else {
					walking = true;
				}
			}
			//PART 2
			for(int i = 0; i < blockList.size(); i++) {
				//jump collision
				if(mainChar.charBlock.isJumpingInto(blockList.get(i))) {
					jumping = false;
					jumpTime = 0;
					gravity = true;
					break;
				} 
				//if character is standing on a block in the blockList
				else if(mainChar.charBlock.isStandingOn(blockList.get(i))) {
					gravity = false;
					break;
				} else {
					gravity = true;
				}
			}
			//LIFTLIST: check to see if player is standing on lift--if they are, they clearly aren't falling
			for(int i = 0; i < liftList.size(); i++) {
				if(mainChar.charBlock.isStandingOn(liftList.get(i).liftBlock)) {
					gravity = false;
				}
			}
			//SANDLIST collision check--drops blocks is char is standing on them
			for(int i = 0; i < sandList.size(); i++) {
				//jump collision
				if(mainChar.charBlock.isJumpingInto(sandList.get(i))) {
					jumping = false;
					jumpTime = 0;
					gravity = true;
					break;
				} 
				else if(mainChar.charBlock.isStandingOn(sandList.get(i))) {
					sandList.get(i).topLeft.y+=2;
					mainChar.charPos.y+=2;
					gravity = false;
				}
			}
			
			//MAGMALIST
			for(int i = 0; i < magmaList.size(); i++) {
				if(mainChar.charBlock.overlaps(magmaList.get(i))) {
					game = false;
					deathInit = true;
					break;
				}
			}
			
			//ROCKLIST collision checker and y-pos updater
			for(int i = 0; i < rockList.size(); i++) {
				if(rockList.get(i).topLeft.y >= 700) {
					rockList.get(i).topLeft.y = rockList.get(i).startHeight;
//					initAudio("files/thud.wav");
				} else {
					rockList.get(i).topLeft.y += 9;
				}
				if(mainChar.charBlock.overlaps(rockList.get(i))) {
					game = false;
					deathInit = true;
					break;
				}
			}
			
			//jetSuit y-pos updater and collision checker
			for(int i = 0; i < jetList.size(); i++) {
				if(jetList.get(i).forward) {
					if(jetList.get(i).pos.y < (jetList.get(i).suitBlock.startHeight - jetList.get(i).radius)) {
						jetList.get(i).forward = false;
					}
				} else {
					if(jetList.get(i).pos.y > (jetList.get(i).suitBlock.startHeight)) {
						jetList.get(i).forward = true;
						suitG = -1;
					}
				}
				suitG += .01;
				if(jetList.get(i).forward) {
					jetList.get(i).pos.y -= suitG;
				} else {
					jetList.get(i).pos.y += suitG;
				}
				if(mainChar.charBlock.isStandingOn(jetList.get(i).suitBlock)) {
					suitList.add(jetList.remove(i));
					jumping = true;
					jumpTime = -5;
				} else if(mainChar.charBlock.overlaps(jetList.get(i).suitBlock)) {
					game = false;
					deathInit = true;
					break;
				}
			}
			
			//MAIN CHAR MOVEMENT______________________
			//handle side to side movement & determine which animation frame to display
			//oscillates between animation frames for side to side movement
			if((left && walking )|| (right && walking)) {
				if(left) {
					if(g < 5) {
						g += .1;
					}
	                mainChar.charPos.x += 3 + g;
	                mainChar.forward = false;
				} else if(right) {
					if(g > -5) {
						g -= .1;
					}
					mainChar.charPos.x -= 3 - g;
					mainChar.forward = true;
				}
				if(next > 0 && next <= 5) {
					stride = 1;
				} else if(next > 5 && next <= 10) {
					stride = 2;
				} else if(next > 10 && next <= 15) {
					stride = 3;
				} else if(next > 15 && next <= 20) {
					stride = 4;
				}
				if(next == 20) {
					next = 0;
				} else {
					next++;
				}
			} else {
				stride = 0;
				g = 0;
			}
			
			//LIFT
			for(int i = 0; i < liftList.size(); i++) {
				if(mainChar.charBlock.isStandingOn(liftList.get(i).liftBlock) && liftList.get(i).liftBlock.topLeft.y > liftList.get(i).maxHeight) {
					liftList.get(i).liftBlock.topLeft.y -= 5;
				} else if(mainChar.charBlock.isStandingOn(liftList.get(i).liftBlock)) {
					//do nothing
				} else if(liftList.get(i).liftBlock.topLeft.y < liftList.get(i).startHeight) {
					liftList.get(i).liftBlock.topLeft.y += 2;
				}
			}
			
			//GRAVITY
			if(gravity) {
					mainChar.charPos.y += 9;
					falling = true;
			} else {
				falling = false;
			}
			//gravity for bots
			for(int i = 0; i < suitList.size(); i++) {
				suitList.get(i).pos.y += 5;
				suitList.get(i).pos.x *= -1;
				for(int j = 0; j < blockList.size(); j++) {
					if(suitList.get(i).suitBlock.isStandingOn(blockList.get(j))) {
						break;
					}
				}
				suitList.get(i).pos.x *= -1;
			}
			for(int i = 0; i < fireList.size(); i++) {
				fireList.get(i).pos.y += 2;
				fireList.get(i).pos.x *= -1;
				for(int j = 0; j < blockList.size(); j++) {
					if(fireList.get(i).fireBlock.isStandingOn(blockList.get(j))) {
						break;
					}
				}
				fireList.get(i).pos.x *= -1;
			}
			
			//FALLING DEATH
			if(mainChar.charPos.y >= 700) {
				game = false;
				deathInit = true;
			} else {
				death = false;
			}
			//player finds exit
			if(mainChar.charBlock.overlaps(door) || mainChar.charBlock.overlaps(bossDoor)) {
				game = false;
				levelUp = true;
				initAudio("src/main/resources/files/doorOpen.wav");
			}
			
		}
		
		if(levelUp) {
			reset++;
			if(reset > 180) {
				this.levelCounter++;
				levelUp = false;
				gameInit = true;
				reset = 0;
				g = 0;
				mainChar.charPos.x = -15;
				mainChar.charPos.y = GROUND - spriteHeight;
				left = false;
				right = false;
				score += 25;
			}
			suitDeath = false;
		}
		
		
		if(deathInit) {
			initAudio("src/main/resources/files/fullbronch.wav");
			deathInit = false;
			death = true;
		}
		
		if(death) {
			reset++;
			if(reset > 270) {
				death = false;
				game = true;
				reset = 0;
				g = 0;
				mainChar.charPos.x = -15;
				mainChar.charPos.y = GROUND;
				left = false;
				right = false;
				for(int i = 0; i < sandList.size(); i++) {
					sandList.get(i).topLeft.y = sandList.get(i).startHeight;
				}
				score -= 50;
			}
		}
		
		if(highscoresInit) {
			String line = "";
			while(line != null) {
				try {
					line = namesbr.readLine();
					namesList.add(line);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			line = "";
			while(true) {
				try {
					line = scoresbr.readLine();
					if(line == null) {
						break;
					}
					int temp = Integer.parseInt(line);
					scoresList.add(temp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for(int i = 0; i < scoresList.size(); i++) {
				int highscore = scoresList.get(i);
				if(score > highscore) {
					newHighScore = true;
					String name = keyboard.nextLine();
					scoresList.add(i, (int)score);
					scoresList.remove(scoresList.size() - 1);
					namesList.add(i, name);
					namesList.remove(namesList.size() - 1);
					break;
				}
			}
			try {
				FileWriter scorewriter = new FileWriter(scoresfile);
				FileWriter namewriter = new FileWriter(namesfile);
				for(int i = 0; i < scoresList.size(); i++) {
					scorewriter.write(Integer.toString(scoresList.get(i)));
				}
				for(int i = 0; i < namesList.size(); i++) {
					namewriter.write(namesList.get(i));
				}
				scorewriter.close();
				namewriter.close();
			} catch(IOException ioException ) {
				
			}

			highscoresInit = false;
			highscores = true;
		}
		
		//CONSOLE READOUT
		// System.out.printf("%f %f  %b\n", mainChar.charPos.x, mainChar.charPos.y, highscores);
		
	}
	
	public void addblock(double x, double y) {
		blockList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	public void coldpop(double x, double y) {
		popList.add(new Rectangle(new Point((int)(x + 5), (int)y), popWidth, popHeight));
	}
	public void addmagma(double x, double y) {
		magmaList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	public void addsand(double x, double y) {
		sandList.add(new Rectangle(new Point((int)x, (int)y), spriteWidth, spriteHeight));
	}
	public void addrock(double x, double y) {
		rockList.add(new Rectangle(new Point((int)x, (int)y), 2*spriteWidth, 2*spriteHeight));
	}
	public void clearlists() {
		blockList.clear();
		fireList.clear();
		suitList.clear();
		tempSuit.clear();
		popList.clear();
		liftList.clear();
		magmaList.clear();
		rockList.clear();
		noteList.clear();
		jetList.clear();
		sandList.clear();
	}
	public void initAudio(String path) {
		try {
		    yourFile = new File(path);
		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();	//how do you end this?
			}
			catch (Exception e) {
				//whatevers
			}
	}
	public void initMusic(String path) {
		try {
		    yourFile1 = new File(path);
		    stream1 = AudioSystem.getAudioInputStream(yourFile1);
		    format1 = stream1.getFormat();
		    info1 = new DataLine.Info(Clip.class, format1);
		    clip1 = (Clip) AudioSystem.getLine(info1);
		    clip1.open(stream1);
		    clip1.start();	//how do you end this?
			}
			catch (Exception e) {
				//whatevers
			}
	}
}
