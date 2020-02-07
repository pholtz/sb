package org.butternut.sb.asset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.butternut.sb.Game;

public class Assets
{
	public static void createSprite(Graphics g, String imgPath, double x, double y) {
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
	
	public static void createImage(Graphics g, String imgPath, double x, double y, double width, double height) {
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
