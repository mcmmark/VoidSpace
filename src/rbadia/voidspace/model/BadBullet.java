package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a bullet fired by a ship.
 */
public class BadBullet extends Rectangle
{
	private static final long serialVersionUID = 1L;

	private int badBulletWidth = 8;
	private int badBulletHeight = 8;
	private int speed = 12;

	
	private Random rand = new Random();
	/**
	 * Creates a new bullet for the enemy
	 * 
	 * @param screen
	 */
	public BadBullet(GameScreen screen) {
		this.setLocation(rand.nextInt(screen.getWidth() - badBulletWidth),0);
		this.setSize(badBulletWidth, badBulletHeight);
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
