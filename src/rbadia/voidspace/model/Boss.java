package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Boss extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 4;

	private int bossWidth = 100;
	private int bossHeight = 100;
	private int speed = DEFAULT_SPEED;
	private boolean movingRight = false;
	private boolean movingLeft = false;

	private Random rand = new Random();

	/**
	 * Crates a new boss at a random x location at the top of the screen
	 * 
	 * @param screen
	 *            the game screen
	 */
	public Boss(GameScreen screen)
	{
		this.setLocation(rand.nextInt(screen.getWidth() - bossWidth), 0);
		this.setSize(bossWidth, bossHeight);
	}

	public int getBossWidth()
	{
		return bossWidth;
	}

	public int getBossHeight()
	{
		return bossHeight;
	}

	/**
	 * Returns the current boss speed
	 * 
	 * @return the current boss speed
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * Set the current boss speed
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/**
	 * Returns the default boss speed.
	 * 
	 * @return the default boss speed
	 */
	public int getDefaultSpeed()
	{
		return DEFAULT_SPEED;
	}
	//Movement cues for boss
	public boolean getIsMovingR()
	{
		return movingRight;
	}
	public void isMovingR()
	{
		movingRight = true;
	}
	public void notMovingR()
	{
		movingRight = false;
	}
	public boolean getIsMovingL()
	{
		return movingLeft;
	}
	public void isMovingL()
	{
		movingLeft = true;
	}
	public void notMovingL()
	{
		movingLeft = false;
	}
}
