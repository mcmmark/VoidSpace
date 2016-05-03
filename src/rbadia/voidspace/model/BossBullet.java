package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a bullet fired by boss.
 */
public class BossBullet extends Rectangle
{
	private static final long serialVersionUID = 1L;

	private int bossBulletWidth = 20;
	private int bossBulletHeight = 50;
	private int speed = 12;

	private Random rand = new Random();
	
	/**
	 * Creates a new bullet for the boss, centered on it
	 * 
	 * @param screen
	 */
	public BossBullet(GameScreen screen)
	{
		this.setLocation(rand.nextInt(screen.getWidth() - bossBulletWidth),0);
		this.setSize(bossBulletWidth, bossBulletHeight);
	}

	/**
	 * Return the bullet's speed.
	 * 
	 * @return the bullet's speed.
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
}
