package org.butternut.sb.level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import org.butternut.sb.Game;
import org.butternut.sb.asset.Assets;
import org.butternut.sb.model.Point;
import org.butternut.sb.model.Rectangle;
import org.butternut.sb.swing.Panels;

public class LevelView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final Color BUCKET_COLOR = new Color(92, 64, 51);
	private static final Color MAGMA = new Color(128, 20, 20);
	private static final Color SAND = new Color(255, 178, 102);
	private static final Color ROCK = new Color(160, 160, 160);
//	private final Game game;
	private final LevelModel levelModel;
	private final LevelKeyController levelKeyController;
	
	public LevelView(
//			Game game,
			LevelModel levelModel) {
//		this.game = game;
		this.levelModel = levelModel;
		this.levelKeyController = new LevelKeyController(levelModel);
		
		this.setPreferredSize(new Dimension((int) Game.WIDTH, (int) Game.HEIGHT));
		super.addKeyListener(this.levelKeyController);
	}
	
	public void initialize() {
		Panels.requestFocus(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//CLEAR AND FILL THE BACKGROUND
		g.clearRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);

		//ESTABLISH NEW FRAMEPOS BASED ON X-COORD OF CHARPOS
		Point framePos = new Point(this.levelModel.mainChar.charPos.x + Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
		
		if(-this.levelModel.mainChar.charPos.x < Game.WIDTH/2 || this.levelModel.boss) {	//beginning of level (let user get to middle of screen -- no scroll)
			
			//draw foreground
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < Game.WIDTH; x += 40) {
				for(int y = 0; y < Game.HEIGHT; y += 20) {
					if((y/20) % 2 == 0) {
						g.drawRect(x, y, 40, 20);
					} else {
						g.drawRect(x + 20, y, 40, 20);
					}
				}
			}
			createWindow(g, new Rectangle(new Point(250, 200), 50, 50));
			createWindow(g, new Rectangle(new Point(750, 120), 50, 50));
			
			//coldpop
			for(int i = 0; i < this.levelModel.popList.size(); i++) {
				g.setColor(Color.CYAN);
				g.fillRoundRect((int) this.levelModel.popList.get(i).topLeft.x,
						(int) this.levelModel.popList.get(i).topLeft.y,
						(int) this.levelModel.popList.get(i).width,
						(int) this.levelModel.popList.get(i).height, 5, 50);
				g.setColor(Color.BLACK);
				g.fillRoundRect((int) (this.levelModel.popList.get(i).topLeft.x + 1),
						(int) this.levelModel.popList.get(i).topLeft.y + 1,
						(int) this.levelModel.popList.get(i).width - 2,
						(int) this.levelModel.popList.get(i).height - 2, 5, 50);
			}
			
			//rocks
			for(int i = 0; i < this.levelModel.rockList.size(); i++) {
				g.setColor(ROCK);
				g.fillRoundRect((int) this.levelModel.rockList.get(i).topLeft.x,
						(int) this.levelModel.rockList.get(i).topLeft.y,
						(int) this.levelModel.rockList.get(i).width,
						(int) this.levelModel.rockList.get(i).height, 50, 50);
			}
			
			//draw blocks
			g.setColor(BUCKET_COLOR);
			for(int i = 0; i < this.levelModel.blockList.size(); i++) {
				g.setColor(BUCKET_COLOR);
				g.fillRect((int) this.levelModel.blockList.get(i).topLeft.x,
						(int) this.levelModel.blockList.get(i).topLeft.y,
						(int) this.levelModel.blockList.get(i).width,
						(int) this.levelModel.blockList.get(i).height);
				if(this.levelModel.blockList.get(i).width == Game.spriteWidth && this.levelModel.blockList.get(i).height == Game.spriteHeight) {
					g.setColor(Color.BLACK);
					g.drawRect((int) this.levelModel.blockList.get(i).topLeft.x,
							(int) this.levelModel.blockList.get(i).topLeft.y,
							Game.spriteWidth,
							Game.spriteHeight);
				}
			}
			
			//lift
			for(int i = 0; i < this.levelModel.liftList.size(); i++) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((int) this.levelModel.liftList.get(i).pos.x,
						(int) this.levelModel.liftList.get(i).pos.y,
						(int) this.levelModel.liftList.get(i).liftBlock.width,
						(int) this.levelModel.liftList.get(i).liftBlock.height);
			}
			
			//sand
			for(int i = 0; i < this.levelModel.sandList.size(); i++) {
				g.setColor(SAND);
				g.fillRoundRect((int) this.levelModel.sandList.get(i).topLeft.x,
						(int) this.levelModel.sandList.get(i).topLeft.y,
						(int) this.levelModel.sandList.get(i).width,
						(int) this.levelModel.sandList.get(i).height, 20, 20);
			}
			
			//magma
			for(int i = 0; i < this.levelModel.magmaList.size(); i++) {
				g.setColor(MAGMA);
				g.fillRect((int) this.levelModel.magmaList.get(i).topLeft.x,
						(int) this.levelModel.magmaList.get(i).topLeft.y,
						(int) this.levelModel.magmaList.get(i).width,
						(int) this.levelModel.magmaList.get(i).height);
			}
			
			//notes
			for(int i = 0; i < this.levelModel.noteList.size(); i++) {
				g.setColor(Color.WHITE);
				g.drawString(this.levelModel.noteList.get(i).data,
						(int) this.levelModel.noteList.get(i).pos.x,
						(int) this.levelModel.noteList.get(i).pos.y);
			}
			
			//fireballs
			for(int i = 0; i < this.levelModel.fireList.size(); i++) {
				g.setColor(Color.RED);
				g.fillRoundRect((int) this.levelModel.fireList.get(i).pos.x,
						(int) this.levelModel.fireList.get(i).pos.y,
						Game.spriteWidth,
						2*Game.spriteHeight,
						5000,
						2000);
				g.setColor(Color.ORANGE);
				g.fillRoundRect((int) this.levelModel.fireList.get(i).pos.x + 5,
						(int) this.levelModel.fireList.get(i).pos.y + 5,
						Game.spriteWidth - 10,
						2*Game.spriteHeight - 10,
						5000,
						2000);
			}
			
			//jetsuit
			for(int i = 0; i < this.levelModel.jetList.size(); i++) {
				Assets.createSprite(g, "src/main/resources/sprites/jetsuitleft.png",
						this.levelModel.jetList.get(i).pos.x,
						this.levelModel.jetList.get(i).pos.y);
			}
			
			//suits
			for(int i = 0; i < this.levelModel.suitList.size(); i++) {
				if(this.levelModel.suitList.get(i).forward) {
					if(this.levelModel.strideSuitFrame == 1) {
						Assets.createImage(g, "src/main/resources/sprites/suit1righttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 2) {
						Assets.createImage(g, "src/main/resources/sprites/suit2righttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 3) {
						Assets.createImage(g, "src/main/resources/sprites/suit3righttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 4) {
						Assets.createImage(g, "src/main/resources/sprites/suit4righttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else {
						Assets.createImage(g, "src/main/resources/sprites/suit1righttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					}
				} else {
					if(this.levelModel.strideSuitFrame == 1) {
						Assets.createImage(g, "src/main/resources/sprites/suit1lefttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 2) {
						Assets.createImage(g, "src/main/resources/sprites/suit2lefttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 3) {
						Assets.createImage(g, "src/main/resources/sprites/suit3lefttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else if(this.levelModel.strideSuitFrame == 4) {
						Assets.createImage(g, "src/main/resources/sprites/suit4lefttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					} else {
						Assets.createImage(g, "src/main/resources/sprites/suit1lefttrans.png",
								this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y,
								this.levelModel.suitList.get(i).suitBlock.width,
								this.levelModel.suitList.get(i).suitBlock.height);
					}
				}
			}
			for(int i = 0; i < this.levelModel.tempSuit.size(); i++) {
				if(this.levelModel.tempSuit.get(i).forward) {
					Assets.createImage(g, "src/main/resources/sprites/deadsuitrighttrans.png",
							this.levelModel.tempSuit.get(i).pos.x,
							this.levelModel.tempSuit.get(i).pos.y,
							this.levelModel.tempSuit.get(i).suitBlock.width,
							this.levelModel.tempSuit.get(i).suitBlock.height);
				} else {
					Assets.createImage(g, "src/main/resources/sprites/deadsuitlefttrans.png",
							this.levelModel.tempSuit.get(i).pos.x,
							this.levelModel.tempSuit.get(i).pos.y,
							this.levelModel.tempSuit.get(i).suitBlock.width,
							this.levelModel.tempSuit.get(i).suitBlock.height);
				}
			}

			//RUNNING ANIMATION (LEFT)
			if(!this.levelModel.mainChar.forward) {
				//JUMPING ANIMATION
				if(this.levelModel.jumping || this.levelModel.falling) {
					Assets.createSprite(g, "src/main/resources/sprites/misha2lefttrans.png",
							-this.levelModel.mainChar.charPos.x,
							this.levelModel.mainChar.charPos.y);
				} else {
					if(this.levelModel.stride == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/stride1lefttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/stride2lefttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/stride3lefttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/stride4lefttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/misha1lefttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					}
				}
			//RUNNING ANIMATION (RIGHT)
			} else if(this.levelModel.mainChar.forward) {
				//JUMPING ANIMATION
				if(this.levelModel.jumping || this.levelModel.falling) {
					Assets.createSprite(g, "src/main/resources/sprites/misha2righttrans.png",
							-this.levelModel.mainChar.charPos.x,
							this.levelModel.mainChar.charPos.y);
				} else {
					if(this.levelModel.stride == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/stride1righttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/stride2righttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/stride3righttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/stride4righttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/misha1righttrans.png",
								-this.levelModel.mainChar.charPos.x,
								this.levelModel.mainChar.charPos.y);
					}
				}
				
			}
			
			//draw level counter
			String level = Integer.toString(this.levelModel.levelCounter);
			g.setColor(Color.WHITE);
			g.drawString("level: " + level, (int) Game.WIDTH - 50, (int) 35);
			String score = Double.toString(this.levelModel.score);
			g.drawString("score: " + score, (int) Game.WIDTH - (5 * score.length()) - 50, 15);
			//draw exit door
			Assets.createSprite(g, "src/main/resources/sprites/exitStairs.png",
					(int) this.levelModel.door.topLeft.x,
					(int) this.levelModel.door.topLeft.y);
			//draw boss door
			Assets.createImage(g, "src/main/resources/sprites/fireExit.png",
					(int) this.levelModel.bossDoor.topLeft.x,
					(int) this.levelModel.bossDoor.topLeft.y, 40, 80);
			
		} else {	//normal mid screen scroll
			
			//draw foreground
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < 10*Game.WIDTH; x += 40) {
				for(int y = 0; y < Game.HEIGHT; y += 20) {
					if((y/20) % 2 == 0) {
						g.drawRect((int)(framePos.x + x), y, 40, 20);
					} else {
						g.drawRect((int)(framePos.x + x + 20), y, 40, 20);
					}
				}
			}
			createWindow(g, new Rectangle(new Point(framePos.x + 250, 200), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 750, 120), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 1250, 50), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 1750, 250), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 2250, 100), 25, 25));
			createWindow(g, new Rectangle(new Point(framePos.x + 2750, 100), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 3250, 100), 30, 30));
			createWindow(g, new Rectangle(new Point(framePos.x + 3750, 100), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 4250, 100), 40, 40));
			createWindow(g, new Rectangle(new Point(framePos.x + 4750, 200), 50, 50));
			createWindow(g, new Rectangle(new Point(framePos.x + 5250, 75), 50, 50)); 
			createWindow(g, new Rectangle(new Point(framePos.x + 5750, 125), 40, 40)); 
			createWindow(g, new Rectangle(new Point(framePos.x + 6250, 200), 30, 30)); 
			
			//coldpop
			for(int i = 0; i < this.levelModel.popList.size(); i++) {
				g.setColor(Color.CYAN);
				g.fillRoundRect((int)(framePos.x + this.levelModel.popList.get(i).topLeft.x),
						(int) this.levelModel.popList.get(i).topLeft.y,
						(int) this.levelModel.popList.get(i).width,
						(int) this.levelModel.popList.get(i).height,
						5,
						50);
				g.setColor(Color.BLACK);
				g.fillRoundRect((int) (framePos.x + this.levelModel.popList.get(i).topLeft.x + 1),
						(int) this.levelModel.popList.get(i).topLeft.y + 1,
						(int) this.levelModel.popList.get(i).width - 2,
						(int) this.levelModel.popList.get(i).height - 2,
						5,
						50);
			}
			
			//rocks
			for(int i = 0; i < this.levelModel.rockList.size(); i++) {
				g.setColor(ROCK);
				g.fillRoundRect((int) (framePos.x + this.levelModel.rockList.get(i).topLeft.x),
						(int) this.levelModel.rockList.get(i).topLeft.y,
						(int) this.levelModel.rockList.get(i).width,
						(int) this.levelModel.rockList.get(i).height,
						50,
						50);
			}
			
			//draw blocks
			for(int i = 0; i < this.levelModel.blockList.size(); i++) {
				g.setColor(BUCKET_COLOR);
				g.fillRect((int)framePos.x + (int) this.levelModel.blockList.get(i).topLeft.x,
						(int) this.levelModel.blockList.get(i).topLeft.y,
						(int) this.levelModel.blockList.get(i).width,
						(int) this.levelModel.blockList.get(i).height);
				if(this.levelModel.blockList.get(i).width == Game.spriteWidth && this.levelModel.blockList.get(i).height == Game.spriteHeight) {
					g.setColor(Color.BLACK);
					g.drawRect((int) framePos.x + (int) this.levelModel.blockList.get(i).topLeft.x,
							(int) this.levelModel.blockList.get(i).topLeft.y,
							Game.spriteWidth,
							Game.spriteHeight);
				}
			}
			
			//lift
			for(int i = 0; i < this.levelModel.liftList.size(); i++) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((int) (framePos.x + this.levelModel.liftList.get(i).pos.x),
						(int) this.levelModel.liftList.get(i).pos.y,
						(int) this.levelModel.liftList.get(i).liftBlock.width,
						(int) this.levelModel.liftList.get(i).liftBlock.height);
			}
			
			//fireballs
			for(int i = 0; i < this.levelModel.fireList.size(); i++) {
				Assets.createSprite(g, "src/main/resources/spritess/fireball.png",
						framePos.x + (int) this.levelModel.fireList.get(i).pos.x,
						(int) this.levelModel.fireList.get(i).pos.y);
			}
			
			//sand
			for(int i = 0; i < this.levelModel.sandList.size(); i++) {
				g.setColor(SAND);
				g.fillRoundRect((int) (framePos.x + this.levelModel.sandList.get(i).topLeft.x),
						(int) this.levelModel.sandList.get(i).topLeft.y,
						(int) this.levelModel.sandList.get(i).width,
						(int) this.levelModel.sandList.get(i).height, 20, 20);
			}
			
			//magma
			for(int i = 0; i < this.levelModel.magmaList.size(); i++) {
				g.setColor(MAGMA);
				g.fillRect((int) (framePos.x + this.levelModel.magmaList.get(i).topLeft.x),
						(int)this.levelModel.magmaList.get(i).topLeft.y,
						(int)this.levelModel.magmaList.get(i).width,
						(int) this.levelModel.magmaList.get(i).height);
			}
			
			//notes
			for(int i = 0; i < this.levelModel.noteList.size(); i++) {
				g.setColor(Color.WHITE);
				g.drawString(this.levelModel.noteList.get(i).data,
						(int) (framePos.x + this.levelModel.noteList.get(i).pos.x),
						(int) this.levelModel.noteList.get(i).pos.y);
			}
			
			//jetsuit
			for(int i = 0; i < this.levelModel.jetList.size(); i++) {
				Assets.createSprite(g, "src/main/resources/sprites/jetsuitleft.png",
						framePos.x + this.levelModel.jetList.get(i).pos.x,
						this.levelModel.jetList.get(i).pos.y);
			}
			
			//suits
			for(int i = 0; i < this.levelModel.suitList.size(); i++) {
				if(this.levelModel.suitList.get(i).forward) {
					if(this.levelModel.strideSuitFrame == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/suit1righttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/suit2righttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/suit3righttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/suit4righttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/suit1righttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					}
				} else {
					if(this.levelModel.strideSuitFrame == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/suit1lefttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/suit2lefttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/suit3lefttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else if(this.levelModel.strideSuitFrame == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/suit4lefttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/suit1lefttrans.png",
								framePos.x + this.levelModel.suitList.get(i).pos.x,
								this.levelModel.suitList.get(i).pos.y);
					}
				}
			}
			for(int i = 0; i < this.levelModel.tempSuit.size(); i++) {
				if(this.levelModel.tempSuit.get(i).forward) {
					Assets.createImage(g, "src/main/resources/sprites/deadsuitrighttrans.png",
							framePos.x + this.levelModel.tempSuit.get(i).pos.x,
							this.levelModel.tempSuit.get(i).pos.y,
							this.levelModel.tempSuit.get(i).suitBlock.width,
							this.levelModel.tempSuit.get(i).suitBlock.height);
				} else {
					Assets.createImage(g, "src/main/resources/sprites/deadsuitlefttrans.png",
							framePos.x + this.levelModel.tempSuit.get(i).pos.x,
							this.levelModel.tempSuit.get(i).pos.y,
							this.levelModel.tempSuit.get(i).suitBlock.width,
							this.levelModel.tempSuit.get(i).suitBlock.height);
				}
			}
			
			//RUNNING ANIMATION (LEFT)
			if(!this.levelModel.mainChar.forward) {
				//JUMPING ANIMATION
				if(this.levelModel.jumping || this.levelModel.falling) {
					Assets.createSprite(g, "src/main/resources/sprites/misha2lefttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
				} else {
					if(this.levelModel.stride == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/stride1lefttrans.png",Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/stride2lefttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/stride3lefttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/stride4lefttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/misha1lefttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					}
				}
			//RUNNING ANIMATION (RIGHT)
			} else if(this.levelModel.mainChar.forward) {
				//JUMPING ANIMATION
				if(this.levelModel.jumping || this.levelModel.falling) {
					Assets.createSprite(g, "src/main/resources/sprites/misha2righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
				} else {
					if(this.levelModel.stride == 1) {
						Assets.createSprite(g, "src/main/resources/sprites/stride1righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 2) {
						Assets.createSprite(g, "src/main/resources/sprites/stride2righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 3) {
						Assets.createSprite(g, "src/main/resources/sprites/stride3righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else if(this.levelModel.stride == 4) {
						Assets.createSprite(g, "src/main/resources/sprites/stride4righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					} else {
						Assets.createSprite(g, "src/main/resources/sprites/misha1righttrans.png", Game.WIDTH/2, this.levelModel.mainChar.charPos.y);
					}
				}
				
			}
			
			String level = Integer.toString(this.levelModel.levelCounter);
			g.setColor(Color.WHITE);
			g.drawString("level: " + level, (int)Game.WIDTH - 50, 35);
			String score = Double.toString(this.levelModel.score);
			g.drawString("score: " + score, (int)Game.WIDTH - (5 * score.length()) - 50, 15);
			
			Assets.createSprite(g, "src/main/resources/sprites/exitStairs.png", (int)framePos.x + (int)this.levelModel.door.topLeft.x, (int)this.levelModel.door.topLeft.y);
			//draw boss door
			Assets.createImage(g, "src/main/resources/sprites/fireExit.png", (int)framePos.x + (int)this.levelModel.bossDoor.topLeft.x, (int)this.levelModel.bossDoor.topLeft.y, 40, 80);
		}
	}
	
	private void createWindow(Graphics g, Rectangle window) {
		g.setColor(Color.YELLOW);
		Polygon p = new Polygon();
		p.addPoint((int)window.topLeft.x, (int)(window.topLeft.y + (window.height)));
		p.addPoint((int)(window.topLeft.x - (.5*window.width)), (int)(window.topLeft.y + (1.5* window.height)));
		p.addPoint((int)(window.topLeft.x + (.5*window.width)), (int)(window.topLeft.y + (1.5* window.height)));
		p.addPoint((int)(window.topLeft.x + window.width), (int)(window.topLeft.y + window.height));
		g.fillPolygon(p);
		
		g.setColor(Color.GRAY);
		g.fillRect((int)window.topLeft.x, (int)window.topLeft.y, (int)window.width, (int)window.height);
		g.setColor(Color.BLUE);
		g.fillRect((int)(window.topLeft.x + (window.width/8)), (int)(window.topLeft.y + (window.height/8)), (int)window.width/3, (int)window.height/3);
		g.fillRect((int)(window.topLeft.x + 5*(window.width/8)), (int)(window.topLeft.y + (window.height/8)), (int)window.width/3, (int)window.height/3);
		g.fillRect((int)(window.topLeft.x + (window.width/8)), (int)(window.topLeft.y + 5*(window.height/8)), (int)window.width/3, (int)window.height/3);
		g.fillRect((int)(window.topLeft.x + 5*(window.width/8)), (int)(window.topLeft.y + 5*(window.height/8)), (int)window.width/3, (int)window.height/3);

	}
}
