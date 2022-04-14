package com.daneil.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.daneil.entities.Entity;
import com.daneil.entities.TowerController;
import com.daneil.graphics.Spritesheet;
import com.daneil.graphics.UI;
import com.daneil.world.World;

	public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener
	{
		private static final long serialVersionUID = 1L;
		
		public static JFrame frame;
		private Thread thread;
		private boolean isRunning = true;
		public static boolean semDin = false;
		public static final int WIDTH = 240;
		public static final int HEIGHT = 160;
		public static final int SCALE = 3;
		
		private BufferedImage image;
		
		public static double vida = 5;
		public static int dinheiro = 100;
		
		public static List<Entity> entities;
		
		public static Spritesheet spritesheet;
		public static World world;
		public UI ui;
		public TowerController towerController;
		
	public Game()
	{
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		initFrame();
		
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		towerController = new TowerController(0, 0, 0, 0, 0, null);
		spritesheet = new Spritesheet("/spritesheet.png");	
		entities = new ArrayList<Entity>();
		world = new World("/level1.png");
		ui = new UI();
		
	}
	
	public void initFrame()
	{
		frame = new JFrame("Towel Defender");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		
		
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	public synchronized void start()
	{
		thread = new Thread (this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		try 
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		stop();
	}
	
	public static void main(String [] args)
	{
		Game game = new Game();
		game.start();
	}

	public void tick()
	{
		
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
		}
		
		towerController.tick();
		
	}
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) 
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(155,0,255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		Collections.sort(entities,Entity.nodeSorter);
		
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		ui.render(g);
		
		bs.show();
		
		if(semDin)
		{
			g.setFont(new Font("arial",Font.BOLD,50));
			g.setColor(Color.white);
			g.drawString("Você está sem dinheiro", 20, 20);
		}
	}
	
	public void run()
	{	
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		@SuppressWarnings("unused")
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >=1)
			{
				requestFocus();
				tick();
				render();
				frames++;
				delta--;
			}
		if (System.currentTimeMillis() - timer >= 1000)
		{
			frames = 0;
			timer+= 1000;
		}
		}
	}

	public void keyTyped(KeyEvent e)
	{
	
	}

	public void keyPressed(KeyEvent e)
	{	
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			System.exit(1);
		}
	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void mouseClicked(MouseEvent e)
	{
		
	}

	public void mousePressed(MouseEvent e)
	{
		towerController.isPressed = true;
		towerController.xTgt = e.getX() / 3;		
		towerController.yTgt = e.getY() / 3;
	}
		

	public void mouseReleased(MouseEvent e)
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}

	
	
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	public void mouseMoved(MouseEvent e)
	{
		
	}
}