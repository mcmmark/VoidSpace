package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus
{
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;

	// status variables
	private boolean newShip;
	private boolean newAsteroid;
	private boolean newAsteroid2;
	private boolean newAsteroid3;
	private boolean newEnemy;
	private boolean newExtraLife;
	private boolean newBadBullet;
	private boolean newBoss;
	private long asteroidsDestroyed = 0;
	private long enemiesDestroyed = 0;
	private long bossesDestroyed = 0;
	private long bossHealth = 10;
	private long score = 0;
	private int shipsLeft;
	private boolean spawnBoss = false;

	public GameStatus()
	{

	}

	/**
	 * Indicates if the game has already started or not.
	 * 
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted()
	{
		return gameStarted;
	}

	public synchronized void setGameStarted(boolean gameStarted)
	{
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or
	 * not.
	 * 
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting()
	{
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting)
	{
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is
	 * displaying.
	 * 
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver()
	{
		return gameOver;
	}

	public synchronized void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * 
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip()
	{
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip)
	{
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * 
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid()
	{
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid)
	{
		this.newAsteroid = newAsteroid;
	}

	public synchronized boolean isNewBadBullet()
	{
		return newBadBullet;
	}

	

	/**
	 * Returns the number of asteroid destroyed.
	 * 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed()
	{
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed)
	{
		this.asteroidsDestroyed = asteroidsDestroyed;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * 
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid2()
	{
		return newAsteroid2;

	}

	public synchronized void setNewAsteroid2(boolean newAsteroid2)
	{
		this.newAsteroid2 = newAsteroid2;
	}

	public synchronized long getScore()
	{
		score = enemiesDestroyed * 250 + asteroidsDestroyed * 50;

		return score;
	}

	/**
	 * Indicates if a new asteroid3 should be created/drawn.
	 * 
	 * @return if a new asteroid3 should be created/drawn
	 */
	public synchronized boolean isNewAsteroid3()
	{
		return newAsteroid3;

	}

	public synchronized void setNewAsteroid3(boolean newAsteroid3)
	{
		this.newAsteroid3 = newAsteroid3;
	}

	/**
	 * Indicates if a new enemy should be created/drawn.
	 * 
	 * @return if a new enemy should be created/drawn
	 */
	public synchronized boolean isNewEnemy()
	{
		return newEnemy;
	}

	public synchronized void setNewEnemy(boolean newEnemy)
	{
		this.newEnemy = newEnemy;
	}

	/**
	 * Returns the number of enemy destroyed.
	 * 
	 * @return the number of enemy destroyed
	 */
	public synchronized long getEnemiesDestroyed()
	{
		return enemiesDestroyed;
	}

	public synchronized void setEnemiesDestroyed(long enemiesDestroyed)
	{
		this.enemiesDestroyed = enemiesDestroyed;
	}
	
	/**
	 * Indicates if a new boss should be created/drawn.
	 * 
	 * @return if a new boss should be created/drawn
	 */
	public synchronized boolean isNewBoss()
	{
		return newBoss;
	}

	public synchronized void setNewBoss(boolean newBoss)
	{
		this.newBoss = newBoss;
	}

	/**
	 * Returns the number of boss destroyed.
	 * 
	 * @return the number of boss destroyed
	 */
	public synchronized long getBossHealth()
	{
		return bossHealth;
	}

	public synchronized void setBossHealth(long bossHealth)
	{
		this.bossHealth = bossHealth;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * 
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewExtraLife()
	{
		return newExtraLife;
	}

	public synchronized void setNewExtraLife(boolean newExtraLife)
	{
		this.newExtraLife = newExtraLife;
	}

	/**
	 * Returns the number ships/lives left.
	 * 
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft()
	{
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft)
	{
		this.shipsLeft = shipsLeft;
	}

	
	public void setBossesDestroyed(long bossesDestroyed) {
		this.bossesDestroyed = bossesDestroyed;
	}
	
	/**
	 * Returns the number of bosses destroyed.
	 * 
	 * @return bosses destroyed
	 */
	
	public long getBossesDestroyed() {
		return bossesDestroyed;
	}
	
	public void setBossSpawn(boolean set){
		this.spawnBoss = set;
	}
	
	public boolean doesBossSpawn(){
		return spawnBoss;
	}

}
