package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Asteroid2;
import rbadia.voidspace.model.Asteroid3;
import rbadia.voidspace.model.BadBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Enemy;
import rbadia.voidspace.model.ExtraLife;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Handles general game logic and status.
 */
public class GameLogic
{
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;

	private Ship ship;
	private Asteroid asteroid;
	private Asteroid2 asteroid2;
	private Asteroid3 asteroid3;
	private Enemy enemy;
	private Boss boss;
	private ExtraLife extraLife;
	private List<Bullet> bullets;
	private BadBullet badBullets;
	private BossBullet bossBullets;
	private boolean powerUp=false;

	/**
	 * Create a new game logic handler
	 * 
	 * @param gameScreen
	 *            the game screen
	 */
	public GameLogic(GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

		// init some variables
		bullets = new ArrayList<Bullet>();
	}

	/**
	 * Returns the game status
	 * 
	 * @return the game status
	 */
	public GameStatus getStatus()
	{
		return status;
	}

	public SoundManager getSoundMan()
	{
		return soundMan;
	}

	public GameScreen getGameScreen()
	{
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame()
	{
		status.setGameStarting(true);

		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setEnemiesDestroyed(0);
		status.setNewAsteroid(false);
		status.setNewAsteroid2(false);
		status.setNewAsteroid3(false);
		status.setNewEnemy(false);
		status.setNewBoss(false);
		status.setNewExtraLife(false);

		// init the ship and the asteroid
		newShip(gameScreen);
		newAsteroid(gameScreen);
		newAsteroid2(gameScreen);
		newAsteroid3(gameScreen);
		newEnemy(gameScreen);
		newExtraLife(gameScreen);
		newBadBullet(gameScreen);
		newBoss(gameScreen);
		newBossBullet(gameScreen);
		

		// prepare game screen
		gameScreen.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions()
	{
		// check game over conditions
		if (!status.isGameOver() && status.isGameStarted())
		{
			if (status.getShipsLeft() == 0)
			{
				gameOver();
			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver()
	{
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet()
	{
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Fire a Powerbullet from ship.
	 */
	public void firePowerBullet()
	{
		Bullet bullet = new Bullet(ship);
		Bullet bullet2 = new Bullet(ship);
		Bullet bullet3 = new Bullet(ship);
		bullets.add(bullet);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullet.translate(-15,-4 );
		bullet2.translate(0, -15      );
		bullet3.translate(15,2);
		soundMan.playBulletSound();
	}

	/**
	 * Move a bullet once fired.
	 * 
	 * @param bullet
	 *            the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet)
	{
		if (bullet.getY() - bullet.getSpeed() >= 0)
		{
			for(int i=0; i<bullets.size();i++)
			{
				if(i==0)
				{
					bullets.get(i).translate(-1, -bullet.getSpeed());
				}else if(i==1)
				{
					bullets.get(i).translate(0, -bullet.getSpeed());
				}else if(i==2)
				{
					bullets.get(i).translate(1, -bullet.getSpeed());
				}
			}
			return false;
		} else
		{
			return true;
		}
	}
	
	/*
	 * Determines whether the power up is activated or not.
	 */
	public boolean powerUp()
	{
		this.powerUp = true;
		return powerUp;
	}
	
	/*
	 * Disable powerUp.
	 */
	public boolean disablePowerUp()
	{
		this.powerUp = false;
		return powerUp;
	}
	
	public boolean getPowerUp()
	{
		return powerUp;
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen)
	{
		this.ship = new Ship(screen);
		return ship;
	}

	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen)
	{
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}
	
	/**
	 * Create a new asteroid2.
	 */
	public Asteroid2 newAsteroid2(GameScreen screen)
	{
		this.asteroid2 = new Asteroid2(screen);
		return asteroid2;
	}
	
	/**
	 * Create a new asteroid3.
	 */
	public Asteroid3 newAsteroid3(GameScreen screen)
	{
		this.asteroid3 = new Asteroid3(screen);
		return asteroid3;
	}

	/**
	 * Create a new enemy.
	 */
	public Enemy newEnemy(GameScreen screen)
	{
		this.enemy = new Enemy(screen);
		return enemy;
	}

	/**
	 * Create a new extra life.
	 */
	public ExtraLife newExtraLife(GameScreen screen)
	{
		this.extraLife = new ExtraLife(screen);
		return extraLife;
	}
	
	/**
	 * Create  Boss.
	 */
	public Boss newBoss(GameScreen screen)
	{	
		this.boss = new Boss(screen);
		return boss;
	}
	
	/**
	 * Create  bossBullets.
	 */
	public BossBullet newBossBullet(GameScreen screen)
	{	
		this.bossBullets = new BossBullet(screen);
		return bossBullets;
	}
	
	/**
	 * Create  badBullets.
	 */
	public BadBullet newBadBullet(GameScreen screen)
	{	
		this.badBullets = new BadBullet(screen);
		return badBullets;
	}

	
	
	/**
	 * Returns the ship.
	 * 
	 * @return the ship
	 */
	public Ship getShip()
	{
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * 
	 * @return the asteroid
	 */
	public Asteroid getAsteroid()
	{
		return asteroid;
	}
	
	/**
	 * Returns the asteroid2.
	 * 
	 * @return the asteroid2
	 */
	public Asteroid2 getAsteroid2()
	{
		return asteroid2;
	}
	
	/**
	 * Returns the asteroid3.
	 * 
	 * @return the asteroid3
	 */
	public Asteroid3 getAsteroid3()
	{
		return asteroid3;
	}


	/**
	 * Returns the enemy.
	 * 
	 * @return the enemy
	 */
	public Enemy getEnemy()
	{
		return enemy;
	}
	
	/**
	 * Returns the boss.
	 * 
	 * @return the boss
	 */
	public Boss getBoss()
	{
		return boss;
	}
	
	/**
	 * Returns the boss bullet.
	 * 
	 * @return the boss bullet
	 */
	public BossBullet getBossBullet()
	{
		return bossBullets;
	}
	
	/**
	 * Returns the extra life.
	 * 
	 * @return the extra life
	 */
	public ExtraLife getExtraLife()
	{
		return extraLife;
	}
	
	/**
	 * Returns bad bullets.
	 * 
	 * @return the bad Bullets
	 */
	public BadBullet getBadBullet()
	{
		return badBullets;
	}

	/**
	 * Returns the list of bullets.
	 * 
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets()
	{
		return bullets;
	}
}
