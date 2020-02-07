package org.butternut.sb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.butternut.sb.input.KeyController;
import org.butternut.sb.input.MouseController;
import org.butternut.sb.model.Point;
import org.butternut.sb.model.Rectangle;
import org.butternut.sb.state.State;

/**
 * KaboomView is the GUI panel class.
 * It draws a picture of each frame of animation based on the
 * game state (Game object), handles user input (mouse moves),
 * and updates the game state based on the user input.
 * @param <keyListener>
 */
public class View extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(0, 127, 0);
	private static final Color BUCKET_COLOR = new Color(92, 64, 51);
	private static final Color MAGMA = new Color(128, 20, 20);
	private static final Color SAND = new Color(255, 178, 102);
	private static final Color ROCK = new Color(160, 160, 160);
	
	private final Game game;
	private final MouseController mouseController;
	private final KeyController keyController;
	
	public View(Game game,
			MouseController mouseController,
			KeyController keyController) {
		this.game = game;
		this.mouseController = mouseController;
		this.keyController = keyController;
		
		this.setBackground(BACKGROUND_COLOR);
		this.setPreferredSize(new Dimension((int) Game.WIDTH, (int) Game.HEIGHT));
		
		super.addMouseMotionListener(this.mouseController);
		super.addKeyListener(this.keyController);
	    super.setFocusable(true);
	    super.requestFocusInWindow();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
//		//CRAWL______________________________
//		if(game.crawl) {
//			g.setColor(Color.BLACK);
//			g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
//			createImage(g, "src/main/resources/files/intro.png", ((int) Game.WIDTH/2) - 130, game.introPos, 263, 514);
//		}
		
//		//MENU_______________________________
//		if(this.game.state == State.MENU) {
//			g.setColor(Color.BLACK);
//			g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
//			createImage(g, "src/main/resources/files/sb2.png", 0, 0, Game.WIDTH, Game.HEIGHT); 
//		}

		//GAME_______________________________
		if(game.game || game.pregame) {
			
			//CLEAR AND FILL THE BACKGROUND
			g.clearRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);

			//ESTABLISH NEW FRAMEPOS BASED ON X-COORD OF CHARPOS
			Point framePos = new Point(game.mainChar.charPos.x + Game.WIDTH/2, game.mainChar.charPos.y);
			
			if(-game.mainChar.charPos.x < Game.WIDTH/2 || game.boss) {	//beginning of level (let user get to middle of screen -- no scroll)
				
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
				for(int i = 0; i < game.popList.size(); i++) {
					g.setColor(Color.CYAN);
					g.fillRoundRect((int)game.popList.get(i).topLeft.x, (int)game.popList.get(i).topLeft.y, (int)game.popList.get(i).width, (int)game.popList.get(i).height, 5, 50);
					g.setColor(Color.BLACK);
					g.fillRoundRect((int)(game.popList.get(i).topLeft.x + 1), (int)game.popList.get(i).topLeft.y + 1, (int)game.popList.get(i).width - 2, (int)game.popList.get(i).height - 2, 5, 50);
				}
				
				//rocks
				for(int i = 0; i < game.rockList.size(); i++) {
					g.setColor(ROCK);
					g.fillRoundRect((int)game.rockList.get(i).topLeft.x, (int)game.rockList.get(i).topLeft.y, (int)game.rockList.get(i).width, (int)game.rockList.get(i).height, 50, 50);
				}
				
				//draw blocks
				g.setColor(BUCKET_COLOR);
				for(int i = 0; i < game.blockList.size(); i++) {
					g.setColor(BUCKET_COLOR);
					g.fillRect((int)game.blockList.get(i).topLeft.x, (int)game.blockList.get(i).topLeft.y, (int)game.blockList.get(i).width, (int)game.blockList.get(i).height);
					if(game.blockList.get(i).width == Game.spriteWidth && game.blockList.get(i).height == Game.spriteHeight) {
						g.setColor(Color.BLACK);
						g.drawRect((int)game.blockList.get(i).topLeft.x, (int)game.blockList.get(i).topLeft.y, Game.spriteWidth, Game.spriteHeight);
					}
				}
				
				//lift
				for(int i = 0; i < game.liftList.size(); i++) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect((int)game.liftList.get(i).pos.x, (int)game.liftList.get(i).pos.y, (int)game.liftList.get(i).liftBlock.width, (int)game.liftList.get(i).liftBlock.height);
				}
				
				//sand
				for(int i = 0; i < game.sandList.size(); i++) {
					g.setColor(SAND);
					g.fillRoundRect((int)game.sandList.get(i).topLeft.x, (int)game.sandList.get(i).topLeft.y, (int)game.sandList.get(i).width, (int)game.sandList.get(i).height, 20, 20);
				}
				
				//magma
				for(int i = 0; i < game.magmaList.size(); i++) {
					g.setColor(MAGMA);
					g.fillRect((int)game.magmaList.get(i).topLeft.x, (int)game.magmaList.get(i).topLeft.y, (int)game.magmaList.get(i).width, (int)game.magmaList.get(i).height);
				}
				
				//notes
				for(int i = 0; i < game.noteList.size(); i++) {
					g.setColor(Color.WHITE);
					g.drawString(game.noteList.get(i).data, (int)game.noteList.get(i).pos.x, (int)game.noteList.get(i).pos.y);
				}
				
				//fireballs
				for(int i = 0; i < game.fireList.size(); i++) {
					g.setColor(Color.RED);
					g.fillRoundRect((int)game.fireList.get(i).pos.x, (int)game.fireList.get(i).pos.y, Game.spriteWidth, 2*Game.spriteHeight, 5000, 2000);
					g.setColor(Color.ORANGE);
					g.fillRoundRect((int)game.fireList.get(i).pos.x + 5, (int)game.fireList.get(i).pos.y + 5, Game.spriteWidth - 10, 2*Game.spriteHeight - 10, 5000, 2000);
				}
				
				//jetsuit
				for(int i = 0; i < game.jetList.size(); i++) {
					createSprite(g, "src/main/resources/sprites/jetsuitleft.png", game.jetList.get(i).pos.x, game.jetList.get(i).pos.y);
				}
				
				//suits
				for(int i = 0; i < game.suitList.size(); i++) {
					if(game.suitList.get(i).forward) {
						if(game.strideSuitFrame == 1) {
							createImage(g, "src/main/resources/sprites/suit1righttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 2) {
							createImage(g, "src/main/resources/sprites/suit2righttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 3) {
							createImage(g, "src/main/resources/sprites/suit3righttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 4) {
							createImage(g, "src/main/resources/sprites/suit4righttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else {
							createImage(g, "src/main/resources/sprites/suit1righttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						}
					} else {
						if(game.strideSuitFrame == 1) {
							createImage(g, "src/main/resources/sprites/suit1lefttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 2) {
							createImage(g, "src/main/resources/sprites/suit2lefttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 3) {
							createImage(g, "src/main/resources/sprites/suit3lefttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else if(game.strideSuitFrame == 4) {
							createImage(g, "src/main/resources/sprites/suit4lefttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						} else {
							createImage(g, "src/main/resources/sprites/suit1lefttrans.png", game.suitList.get(i).pos.x, game.suitList.get(i).pos.y, game.suitList.get(i).suitBlock.width,game.suitList.get(i).suitBlock.height);
						}
					}
				}
				for(int i = 0; i < game.tempSuit.size(); i++) {
					if(game.tempSuit.get(i).forward) {
						createImage(g, "src/main/resources/sprites/deadsuitrighttrans.png", game.tempSuit.get(i).pos.x, game.tempSuit.get(i).pos.y, game.tempSuit.get(i).suitBlock.width, game.tempSuit.get(i).suitBlock.height);
					} else {
						createImage(g, "src/main/resources/sprites/deadsuitlefttrans.png", game.tempSuit.get(i).pos.x, game.tempSuit.get(i).pos.y, game.tempSuit.get(i).suitBlock.width, game.tempSuit.get(i).suitBlock.height);
					}
				}

				//RUNNING ANIMATION (LEFT)
				if(!game.mainChar.forward) {
					//JUMPING ANIMATION
					if(game.jumping || game.falling) {
						createSprite(g, "src/main/resources/sprites/misha2lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
					} else {
						if(game.stride == 1) {
							createSprite(g, "src/main/resources/sprites/stride1lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 2) {
							createSprite(g, "src/main/resources/sprites/stride2lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 3) {
							createSprite(g, "src/main/resources/sprites/stride3lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 4) {
							createSprite(g, "src/main/resources/sprites/stride4lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/misha1lefttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						}
					}
				//RUNNING ANIMATION (RIGHT)
				} else if(game.mainChar.forward) {
					//JUMPING ANIMATION
					if(game.jumping || game.falling) {
						createSprite(g, "src/main/resources/sprites/misha2righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
					} else {
						if(game.stride == 1) {
							createSprite(g, "src/main/resources/sprites/stride1righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 2) {
							createSprite(g, "src/main/resources/sprites/stride2righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 3) {
							createSprite(g, "src/main/resources/sprites/stride3righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else if(game.stride == 4) {
							createSprite(g, "src/main/resources/sprites/stride4righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/misha1righttrans.png", -game.mainChar.charPos.x, game.mainChar.charPos.y);
						}
					}
					
				}
				
				//draw level counter
				String level = Integer.toString(game.levelCounter);
				g.setColor(Color.WHITE);
				g.drawString("level: " + level, (int)Game.WIDTH - 50, (int)35);
				String score = Double.toString(game.score);
				g.drawString("score: " + score, (int)Game.WIDTH - (5 * score.length()) - 50, 15);
				//draw exit door
				createSprite(g, "src/main/resources/sprites/exitStairs.png", (int)game.door.topLeft.x, (int)game.door.topLeft.y);
				//draw boss door
				createImage(g, "src/main/resources/sprites/fireExit.png", (int)game.bossDoor.topLeft.x, (int)game.bossDoor.topLeft.y, 40, 80);
				
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
				for(int i = 0; i < game.popList.size(); i++) {
					g.setColor(Color.CYAN);
					g.fillRoundRect((int)(framePos.x + game.popList.get(i).topLeft.x), (int)game.popList.get(i).topLeft.y, (int)game.popList.get(i).width, (int)game.popList.get(i).height, 5, 50);
					g.setColor(Color.BLACK);
					g.fillRoundRect((int)(framePos.x + game.popList.get(i).topLeft.x + 1), (int)game.popList.get(i).topLeft.y + 1, (int)game.popList.get(i).width - 2, (int)game.popList.get(i).height - 2, 5, 50);

				}
				
				//rocks
				for(int i = 0; i < game.rockList.size(); i++) {
					g.setColor(ROCK);
					g.fillRoundRect((int)(framePos.x + game.rockList.get(i).topLeft.x), (int)game.rockList.get(i).topLeft.y, (int)game.rockList.get(i).width, (int)game.rockList.get(i).height, 50, 50);
				}
				
				//draw blocks
				for(int i = 0; i < game.blockList.size(); i++) {
					g.setColor(BUCKET_COLOR);
					g.fillRect((int)framePos.x + (int)game.blockList.get(i).topLeft.x, (int)game.blockList.get(i).topLeft.y, (int)game.blockList.get(i).width, (int)game.blockList.get(i).height);
					if(game.blockList.get(i).width == Game.spriteWidth && game.blockList.get(i).height == Game.spriteHeight) {
						g.setColor(Color.BLACK);
						g.drawRect((int)framePos.x + (int)game.blockList.get(i).topLeft.x, (int)game.blockList.get(i).topLeft.y, Game.spriteWidth, Game.spriteHeight);
					}
				}
				
				//lift
				for(int i = 0; i < game.liftList.size(); i++) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect((int)(framePos.x + game.liftList.get(i).pos.x), (int)game.liftList.get(i).pos.y, (int)game.liftList.get(i).liftBlock.width, (int)game.liftList.get(i).liftBlock.height);
				}
				
				//fireballs
				for(int i = 0; i < game.fireList.size(); i++) {
					createSprite(g, "src/main/resources/spritess/fireball.png", framePos.x + (int)game.fireList.get(i).pos.x, (int)game.fireList.get(i).pos.y);
				}
				
				//sand
				for(int i = 0; i < game.sandList.size(); i++) {
					g.setColor(SAND);
					g.fillRoundRect((int)(framePos.x + game.sandList.get(i).topLeft.x), (int)game.sandList.get(i).topLeft.y, (int)game.sandList.get(i).width, (int)game.sandList.get(i).height, 20, 20);
				}
				
				//magma
				for(int i = 0; i < game.magmaList.size(); i++) {
					g.setColor(MAGMA);
					g.fillRect((int)(framePos.x + game.magmaList.get(i).topLeft.x), (int)game.magmaList.get(i).topLeft.y, (int)game.magmaList.get(i).width, (int)game.magmaList.get(i).height);
				}
				
				//notes
				for(int i = 0; i < game.noteList.size(); i++) {
					g.setColor(Color.WHITE);
					g.drawString(game.noteList.get(i).data, (int)(framePos.x + game.noteList.get(i).pos.x), (int)game.noteList.get(i).pos.y);
				}
				
				//jetsuit
				for(int i = 0; i < game.jetList.size(); i++) {
					createSprite(g, "src/main/resources/sprites/jetsuitleft.png", framePos.x + game.jetList.get(i).pos.x, game.jetList.get(i).pos.y);
				}
				
				//suits
				for(int i = 0; i < game.suitList.size(); i++) {
					if(game.suitList.get(i).forward) {
						if(game.strideSuitFrame == 1) {
							createSprite(g, "src/main/resources/sprites/suit1righttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 2) {
							createSprite(g, "src/main/resources/sprites/suit2righttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 3) {
							createSprite(g, "src/main/resources/sprites/suit3righttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 4) {
							createSprite(g, "src/main/resources/sprites/suit4righttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/suit1righttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						}
					} else {
						if(game.strideSuitFrame == 1) {
							createSprite(g, "src/main/resources/sprites/suit1lefttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 2) {
							createSprite(g, "src/main/resources/sprites/suit2lefttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 3) {
							createSprite(g, "src/main/resources/sprites/suit3lefttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else if(game.strideSuitFrame == 4) {
							createSprite(g, "src/main/resources/sprites/suit4lefttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/suit1lefttrans.png", framePos.x + game.suitList.get(i).pos.x, game.suitList.get(i).pos.y);
						}
					}
				}
				for(int i = 0; i < game.tempSuit.size(); i++) {
					if(game.tempSuit.get(i).forward) {
						createImage(g, "src/main/resources/sprites/deadsuitrighttrans.png", framePos.x + game.tempSuit.get(i).pos.x, game.tempSuit.get(i).pos.y, game.tempSuit.get(i).suitBlock.width, game.tempSuit.get(i).suitBlock.height);
					} else {
						createImage(g, "src/main/resources/sprites/deadsuitlefttrans.png", framePos.x + game.tempSuit.get(i).pos.x, game.tempSuit.get(i).pos.y, game.tempSuit.get(i).suitBlock.width, game.tempSuit.get(i).suitBlock.height);
					}
				}
				
				//RUNNING ANIMATION (LEFT)
				if(!game.mainChar.forward) {
					//JUMPING ANIMATION
					if(game.jumping || game.falling) {
						createSprite(g, "src/main/resources/sprites/misha2lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
					} else {
						if(game.stride == 1) {
							createSprite(g, "src/main/resources/sprites/stride1lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 2) {
							createSprite(g, "src/main/resources/sprites/stride2lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 3) {
							createSprite(g, "src/main/resources/sprites/stride3lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 4) {
							createSprite(g, "src/main/resources/sprites/stride4lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/misha1lefttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						}
					}
				//RUNNING ANIMATION (RIGHT)
				} else if(game.mainChar.forward) {
					//JUMPING ANIMATION
					if(game.jumping || game.falling) {
						createSprite(g, "src/main/resources/sprites/misha2righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
					} else {
						if(game.stride == 1) {
							createSprite(g, "src/main/resources/sprites/stride1righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 2) {
							createSprite(g, "src/main/resources/sprites/stride2righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 3) {
							createSprite(g, "src/main/resources/sprites/stride3righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else if(game.stride == 4) {
							createSprite(g, "src/main/resources/sprites/stride4righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						} else {
							createSprite(g, "src/main/resources/sprites/misha1righttrans.png", Game.WIDTH/2, game.mainChar.charPos.y);
						}
					}
					
				}
				
				String level = Integer.toString(game.levelCounter);
				g.setColor(Color.WHITE);
				g.drawString("level: " + level, (int)Game.WIDTH - 50, 35);
				String score = Double.toString(game.score);
				g.drawString("score: " + score, (int)Game.WIDTH - (5 * score.length()) - 50, 15);
				
//				g.fillRect((int)framePos.x + (int)game.door.topLeft.x, (int)game.door.topLeft.y, (int)game.door.width, (int)game.door.height);
				createSprite(g, "src/main/resources/sprites/exitStairs.png", (int)framePos.x + (int)game.door.topLeft.x, (int)game.door.topLeft.y);
				//draw boss door
				createImage(g, "src/main/resources/sprites/fireExit.png", (int)framePos.x + (int)game.bossDoor.topLeft.x, (int)game.bossDoor.topLeft.y, 40, 80);
			}
			//draw counter
			if(game.pregame) {
				int countDown = game.count / 60;
				g.drawString(Integer.toString(countDown), (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			}

		}
		
		//LEVELUP
		if(game.levelUp) {
			if(game.reset < 30) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			} else if(game.reset > 30 && game.reset < 60) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			} else if(game.reset > 60 && game.reset < 90) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			} else if(game.reset > 90 && game.reset < 120) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			} else if(game.reset > 120 && game.reset < 150) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			} else if(game.reset > 150 && game.reset < 180) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.drawString("LEVEL CLEAR", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
			}
		}
		
		//DEATH______________________________
		if(game.death) {
//			//CLEAR AND FILL THE BACKGROUND
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", (int)Game.WIDTH/2, (int)Game.HEIGHT/2);
		}
		
		if(game.highscoresInit) {
			if(game.newHighScore) {
				g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("ENTER YOUR NAME INTO THE CONSOLE", 300, 240);
			}
		}
		
		if(game.highscores) {
			g.clearRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
			g.setColor(Color.WHITE);
			for(int i = 0; i < game.scoresList.size(); i++) {
				g.drawString(Integer.toString(i), 150, (10*i) + 50);
				g.drawString(game.namesList.get(i), 200, (10*i) + 50);
				g.drawString(Integer.toString(game.scoresList.get(i)), 250, (10*i) + 50);
			}
		}
		g.dispose();
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
	
	private void createSprite(Graphics g, String imgPath, double x, double y) {
		BufferedImage img = null;
		File file = new File(imgPath);
		try {
			img = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		ImageObserver observer = null;
		g.drawImage(img, (int)x, (int)y, Game.spriteWidth, 2*Game.spriteHeight, observer);
	}
	
	private void createImage(Graphics g, String imgPath, double x, double y, double width, double height) {
		BufferedImage img = null;
		File file = new File(imgPath);
		try {
			img = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		ImageObserver observer = null;
		g.drawImage(img, (int)x, (int)y, (int)width, (int)height, observer);
	}
}
