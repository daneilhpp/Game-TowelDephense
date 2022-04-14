package com.daneil.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.daneil.main.Game;

public class Player extends Entity
{
	public int xTgt, yTgt;
	public boolean atacando = false;
	public int tirar = 0;
	
	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite)
	{
		super(x, y, width, height, speed, sprite);
		
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final Runnable runnable = new Runnable() {
            int countdownStarter = 10;

            public void run() {
                countdownStarter--;

                if (countdownStarter < 0) {
                    tirar = 1;
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
	}
	
	public void tick()
	{
		Enemy enemy = null;
		for(int i = 0; i < Game.entities.size(); i++)
		{
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy)
			{
				int xEnemy = e.getX();
				int yEnemy = e.getY();
				
				if(Entity.calculateDistance(this.getX(), this.getY(), xEnemy, yEnemy) < 40)
				{
					enemy = (Enemy)e;
				}
			}
		}
		
		if(enemy != null)
		{
			atacando = true;
			xTgt = enemy.getX();
			yTgt = enemy.getY();
			enemy.vida-=0.01;
		}
		else
			atacando = false;
		
		if(tirar == 1)
			Game.entities.remove(this);
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		
		if(atacando)
		{
			g.setColor(Color.white);
			g.drawLine((int)(x+8), (int)(y+8), xTgt+8, yTgt+8);
		}
	}
}
