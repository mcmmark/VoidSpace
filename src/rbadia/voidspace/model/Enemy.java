package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Enemy extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 4;

	private int enemyWidth = 32;
	private int enemyHeight = 32;
	private int speed = DEFAULT_SPEED;
	private boolean movingRight = false;
	private boolean movingLeft = false;


	private Random rand = new Random();

	/**
	 * Crates a new enemy at a random x location at the top of the screen
	 * 
	 * @param screen
	 *            the game screen
	 */
	public Enemy(GameScreen screen)
	{
		this.setLocation(rand.nextInt(screen.getWidth() - enemyWidth), 0);
		this.setSize(enemyWidth, enemyHeight);
	}

	public int getEnemyWidth()
	{
		return enemyWidth;
	}

	public int getEnemyHeight()
	{
		return enemyHeight;
	}

	/**
	 * Returns the current enemy speed
	 * 
	 * @return the current enemy speed
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * Set the current enemy speed
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/**
	 * Returns the default enemy speed.
	 * 
	 * @return the default enemy speed
	 */
	public int getDefaultSpeed()
	{
		return DEFAULT_SPEED;
	}
	//Movement cues for enemy
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
