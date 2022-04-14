package com.daneil.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.daneil.main.Game;
import com.daneil.world.AStar;
import com.daneil.world.Vector2i;
import com.daneil.world.World;

public class Enemy extends Entity
{
	public double vida = 7;
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) 
	{
		super(x, y, width, height, speed, sprite);		
		path = AStar.findPath(Game.world, new Vector2i(World.xInit,World.yInit), new Vector2i(World.xFinal,World.yFinal));
	}
	
	public void tick()
	{
		followPath(path);
		if(x >= Game.WIDTH)
		{
			Game.entities.remove(this);
			Game.vida-=0.1;
			return;
		}
		
		if(vida <=0)
		{
			Game.entities.remove(this);
			Game.dinheiro+=5;
			return;
		}
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		g.setColor(new Color(50,50,50));
		g.fillRect((int)x+3,(int)y+1,10,12);
		g.fillRect((int)x+5,(int)y,6,12);
		g.fillRect((int)x+4,(int)y+1,2,14);
		g.fillRect((int)x+10,(int)y+1,2,14);
		
		g.setColor(Color.white);
		g.fillRect((int)x+6,(int)y+3,2,3);
		g.fillRect((int)x+10,(int)y+3,2,3);
		
		
		g.setColor(Color.red);
		g.fillRect((int)(x + 3), (int)(y-4),(int)((vida/7)*11), 3);
	}
}
