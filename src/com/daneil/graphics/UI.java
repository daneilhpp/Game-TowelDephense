package com.daneil.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.daneil.main.Game;

public class UI 
{
	public static BufferedImage HEART = Game.spritesheet.getSprite(8, 16, 9, 8);
	
	public void render(Graphics g)
	{
		for(int i = 0; i < (int)(Game.vida); i++)
		{
			g.drawImage(HEART, 25 + (i*45), 20, 40, 40, null);
		}
		g.setFont(new Font("arial",Font.BOLD,30));
		g.setColor(Color.white);
		g.drawString("$" + Game.dinheiro, 640, 46);
	}
}
