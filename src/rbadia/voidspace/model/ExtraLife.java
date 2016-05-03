package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class ExtraLife extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 4;

	private int extraLifeWidth = 32;
	private int extraLifeHeight = 32;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();

	/**
	 * Crates a new extra life at a random x location at the top of the screen
	 * 
	 * @param screen
	 *            the game screen
	 */
	public ExtraLife(GameScreen screen)
	{
		this.setLocation(rand.nextInt(screen.getWidth() - extraLifeWidth), 0);
		this.setSize(extraLifeWidth, extraLifeHeight);
	}

	public int getExtraLifeWidth()
	{
		return extraLifeWidth;
	}

	public int getExtraLifeHeight()
	{
		return extraLifeHeight;
	}

	/**
	 * Returns the current extra life speed
	 * 
	 * @return the current extra life speed
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * Set the current extra life speed
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/**
	 * Returns the default extra life speed.
	 * 
	 * @return the default extra life speed
	 */
	public int getDefaultSpeed()
	{
		return DEFAULT_SPEED;
	}
}
