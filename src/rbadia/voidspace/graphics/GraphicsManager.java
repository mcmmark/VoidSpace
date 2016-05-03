package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BadBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Enemy;
import rbadia.voidspace.model.ExtraLife;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager
{
	private BufferedImage shipImg;
	private BufferedImage bulletImg;
	private BufferedImage badBulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage enemyImg;
	private BufferedImage extraLifeImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage extraLifeSparkleImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage bossImg;
	private BufferedImage bossExplosionImg;
	private BufferedImage bossBulletImg;
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager()
	{
		// load images
		try
		{
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship1.png"));
			this.enemyImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemy1.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.extraLifeImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ExtraLife.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.extraLifeSparkleImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ExtraLifeSparkle.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.badBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Boss.png"));
			this.bossExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossExplosion.png"));
			this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossBullet.png"));
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * 
	 * @param ship
	 *            the ship to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * 
	 * @param bullet
	 *            the bullet to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}
	
	/**
	 * Draws a Bad bullet image to the specified graphics canvas.
	 * 
	 * @param BadBullet
	 *            the Badbullet to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawBadBullet(BadBullet badBullet, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(badBulletImg, badBullet.x, badBullet.y, observer);
	}
	
	/**
	 * Draws a Boss bullet image to the specified graphics canvas.
	 * 
	 * @param BossBullet
	 *            the Bossbullet to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawBossBullet(BossBullet bossbullet, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(bossBulletImg, bossbullet.x, bossbullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * 
	 * @param asteroid
	 *            the asteroid to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	
	
	/**
	 * Draws an enemy image to the specified graphics canvas.
	 * 
	 * @param enemy
	 *            the enemy to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawEnemy(Enemy enemy, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(enemyImg, enemy.x, enemy.y, observer);
	}
	
	/**
	 * Draws an boss image to the specified graphics canvas.
	 * 
	 * @param boss
	 *            the boss to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawBoss(Boss boss, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(bossImg, boss.x, boss.y, observer);
	}
	
	/**
	 * Draws an extra life image to the specified graphics canvas.
	 * 
	 * @param extra life
	 *            the extra life to draw
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawExtraLife(ExtraLife extraLife, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(extraLifeImg, extraLife.x, extraLife.y, observer);
	}

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * 
	 * @param shipExplosion
	 *            the bounding rectangle of the explosion
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * 
	 * @param asteroidExplosion
	 *            the bounding rectangle of the explosion
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}
	
	
	/**
	 * Draws an enemy explosion image to the specified graphics canvas.
	 * 
	 * @param enemyExplosion
	 *            the bounding rectangle of the explosion
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawEnemyExplosion(Rectangle enemyExplosion, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(asteroidExplosionImg, enemyExplosion.x, enemyExplosion.y, observer);
	}
	
	/**
	 * Draws an enemy explosion image to the specified graphics canvas.
	 * 
	 * @param enemyExplosion
	 *            the bounding rectangle of the explosion
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(bossExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}
	
	/**
	 * Draws an extra life explosion image to the specified graphics canvas.
	 * 
	 * @param extraLifeSparkle
	 *            the bounding rectangle of the explosion
	 * @param g2d
	 *            the graphics canvas
	 * @param observer
	 *            object to be notified
	 */
	public void drawExtraLifeSparkle(Rectangle extraLifeSparkle, Graphics2D g2d, ImageObserver observer)
	{
		g2d.drawImage(extraLifeSparkleImg, extraLifeSparkle.x, extraLifeSparkle.y, observer);
	}

}
