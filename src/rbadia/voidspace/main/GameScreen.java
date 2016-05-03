package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
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
 * Main game screen. Handles all game graphics updates and some of the game
 * logic.
 */
public class GameScreen extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_ENEMY_DELAY = 500;
	private static final int NEW_EXTRALIFE_DELAY = 5000;
	private static final int NEW_POWERUP_DELAY = 10000;
	private static final int NEW_BOSS_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastAsteroid2Time;
	private long lastAsteroid3Time;
	private long lastEnemyTime;
	private long lastBossTime;
	private long lastExtraLifeTime;
	private long lastPowerUpTime;

	private Rectangle asteroidExplosion;
	private Rectangle asteroidExplosion2;
	private Rectangle asteroidExplosion3;
	private Rectangle shipExplosion;
	private Rectangle enemyExplosion;
	private Rectangle bossExplosion;
	private Rectangle extraLifeSparkle;

	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;

	/**
	 * This method initializes
	 * 
	 */
	public GameScreen()
	{
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize()
	{
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen()
	{
		Ship ship = gameLogic.getShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		Enemy enemy = gameLogic.getEnemy();
		Asteroid2 asteroid2 = gameLogic.getAsteroid2();
		Asteroid3 asteroid3 = gameLogic.getAsteroid3();
		Boss boss = gameLogic.getBoss();
		BossBullet bossBullets = gameLogic.getBossBullet();
		ExtraLife extraLife = gameLogic.getExtraLife();
		BadBullet badBullets = gameLogic.getBadBullet();
		List<Bullet> bullets = gameLogic.getBullets();
		long Timer = System.currentTimeMillis();


		// set orignal font - for later use
		if (this.originalFont == null)
		{
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(1000);

		// if the game is starting, draw "Get Ready" message
		if (status.isGameStarting())
		{
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if (status.isGameOver())
		{
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if ((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY)
			{
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if ((currentTime - lastShipTime) < NEW_SHIP_DELAY)
			{
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			if ((currentTime - lastEnemyTime) < NEW_ENEMY_DELAY)
			{
				graphicsMan.drawEnemyExplosion(enemyExplosion, g2d, this);
			}
			if ((currentTime - lastExtraLifeTime) < NEW_EXTRALIFE_DELAY)
			{
				graphicsMan.drawExtraLifeSparkle(extraLifeSparkle, g2d, this);
			}
			if ((currentTime - lastAsteroid2Time) < NEW_ASTEROID_DELAY)
			{
				graphicsMan.drawAsteroidExplosion(asteroidExplosion2, g2d, this);
			}
			if ((currentTime - lastAsteroid3Time) < NEW_ASTEROID_DELAY)
			{
				graphicsMan.drawAsteroidExplosion(asteroidExplosion3, g2d, this);
			}
			if ((currentTime - lastBossTime) < NEW_BOSS_DELAY)
			{
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}

			return;
		}

		// the game has not started yet
		if (!status.isGameStarted())
		{
			// draw game title screen
			initialMessage();
			return;
		}

		// draw asteroid
		if (!status.isNewAsteroid())
		{
			// draw the asteroid until it reaches the bottom of the screen
			if (status.getAsteroidsDestroyed() >= 25)
			{
				if (asteroid.getX() <= (getWidth() / 2))
				{
					if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6))
					{
						asteroid.translate(2, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 2)
					{
						asteroid.translate(-4, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					}

					else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 3)
					{
						asteroid.translate(4, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 4)
					{
						asteroid.translate(-2, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 5)
					{
						asteroid.translate(3, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 6)
					{
						asteroid.translate(-1, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else

					{
						asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
					}
				} else if (asteroid.getX() >= getWidth() / 2)
				{
					if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6))
					{
						asteroid.translate(-3, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 2)
					{
						asteroid.translate(3, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					}

					else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 3)
					{
						asteroid.translate(-4, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 4)
					{
						asteroid.translate(4, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 5)
					{
						asteroid.translate(-3, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else if (asteroid.getY() + asteroid.getSpeed() < (this.getHeight() / 6) * 8)
					{
						asteroid.translate(2, asteroid.getSpeed() / 2);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					} else
					{
						asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
					}

				}

			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY)
			{
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			} else
			{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}

		// draw asteroid2
		if (!status.isNewAsteroid2())
		{

			// draw the asteroid until it reaches the bottom of the screen
			if (asteroid2.getY() > getHeight() / 2 && asteroid2.getCenterY() >= getWidth() / 2)
			{
				if (asteroid2.getY() + asteroid2.getSpeed() < this.getHeight())
				{
					asteroid2.translate(-3, asteroid2.getSpeed() / 4);
					graphicsMan.drawAsteroid(asteroid2, g2d, this);
				} else
				{
					asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
				}
			} else if (asteroid2.getY() > getHeight() / 2 && asteroid2.getCenterY() <= getWidth() / 2)
			{
				if (asteroid2.getY() + asteroid2.getSpeed() < this.getHeight())
				{
					asteroid2.translate(3, asteroid2.getSpeed() / 4);
					graphicsMan.drawAsteroid(asteroid2, g2d, this);
				} else
				{
					asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
				}
			} else
			{
				if (asteroid2.getY() + asteroid2.getSpeed() < this.getHeight())
				{
					asteroid2.translate(0, asteroid2.getSpeed());
					graphicsMan.drawAsteroid(asteroid2, g2d, this);
				} else
				{
					asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
				}
			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastAsteroid2Time) > NEW_ASTEROID_DELAY)
			{
				// draw a new asteroid
				lastAsteroid2Time = currentTime;
				status.setNewAsteroid2(false);
				asteroid2.setLocation(rand.nextInt(getWidth() - asteroid2.width), 0);
			} else
			{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion2, g2d, this);
			}
		}

		// draw asteroid3
		if (!status.isNewAsteroid3())
		{
			// draw the asteroid until it reaches the bottom of the screen
			if (status.getAsteroidsDestroyed() > 10)
			{
				if (asteroid3.getX() > getWidth() / 2)
				{
					if (asteroid3.getY() + asteroid3.getSpeed() < this.getHeight())
					{
						asteroid3.translate(-1, asteroid3.getSpeed() + 2);
						graphicsMan.drawAsteroid(asteroid3, g2d, this);
					} else
					{
						asteroid3.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
					}
				} else
				{
					if (asteroid3.getY() + asteroid3.getSpeed() < this.getHeight())
					{
						asteroid3.translate(1, asteroid3.getSpeed());
						graphicsMan.drawAsteroid(asteroid3, g2d, this);
					} else
					{
						asteroid3.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
					}
				}
			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastAsteroid3Time) > NEW_ASTEROID_DELAY)
			{
				// draw a new asteroid
				lastAsteroid3Time = currentTime;
				status.setNewAsteroid3(false);
				asteroid3.setLocation(rand.nextInt(getWidth() - asteroid3.width), 0);
			} else
			{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion3, g2d, this);
			}
		}

		// draw enemy
		if (!status.isNewEnemy())
		{
			// draw the enemy until it reaches the bottom of the screen

			if (status.getAsteroidsDestroyed() >= 50)
			{
				if (enemy.getCenterY() > getHeight()/2)
				{
					if (badBullets.getY() + badBullets.getSpeed() < this.getHeight())
					{
						badBullets.translate(0, badBullets.getSpeed());
						graphicsMan.drawBadBullet(badBullets, g2d, this);

					} else
					{
						badBullets.setLocation((int) enemy.getCenterX() - 3, (int) enemy.getCenterY());
						soundMan.playBulletSound();
					}

					if (enemy.getY() + enemy.getSpeed() < this.getHeight())
					{
						if((enemy.getCenterX() > (this.getWidth()/2)) && !(enemy.getIsMovingL() || enemy.getIsMovingR()))
						{
							enemy.isMovingL();
						}
						else if((enemy.getCenterX() < (this.getWidth()/2)) && !(enemy.getIsMovingL() || enemy.getIsMovingR()))
						{
							enemy.isMovingR();
						}
						else if(enemy.getIsMovingR())
						{
							enemy.translate(enemy.getSpeed(), 0);
						}
						else if(enemy.getIsMovingL())
						{
							enemy.translate(-enemy.getSpeed(), 0);
						}

						if ((enemy.getCenterX() > getWidth()) || (enemy.getCenterX() < 0))
						{
							enemy.translate(0, 15);
							enemy.notMovingL();
							enemy.notMovingR();
						}
						graphicsMan.drawEnemy(enemy, g2d, this);

					}

					else
					{
						enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);
					}
				}
				else
				{
					if (badBullets.getY() + badBullets.getSpeed() < this.getHeight())
					{
						badBullets.translate(0, badBullets.getSpeed());
						graphicsMan.drawBadBullet(badBullets, g2d, this);

					} else
					{
						badBullets.setLocation((int) enemy.getCenterX() - 3, (int) enemy.getCenterY());
						soundMan.playBulletSound();
					}

					if (enemy.getY() + enemy.getSpeed() < this.getHeight())
					{
						enemy.translate(0, enemy.getDefaultSpeed());
						graphicsMan.drawEnemy(enemy, g2d, this);

					}

					else
					{
						enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);
					}
				}

			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastEnemyTime) > NEW_ENEMY_DELAY)
			{
				// draw a new enemy
				lastEnemyTime = currentTime;
				status.setNewEnemy(false);
				enemy.setLocation(rand.nextInt(getWidth() - enemy.width), 0);
			} else
			{
				// draw explosion
				graphicsMan.drawEnemyExplosion(enemyExplosion, g2d, this);
			}
		}

		//Boss spawn after 90 kills
		if((status.getAsteroidsDestroyed() + status.getEnemiesDestroyed() + 1) % 90 == 0){
			status.setBossSpawn(true);
		}
		
		// draw Boss
		if (!status.isNewBoss())
		{
			// draw the enemy until it reaches the bottom of the screen

			if (status.doesBossSpawn())
			{
				if (boss.getCenterY() > getHeight()/8)
				{
					if (bossBullets.getY() + bossBullets.getSpeed() < this.getHeight())
					{
						if(bossBullets.getCenterX() > ship.getCenterX()){
							bossBullets.translate(-2, bossBullets.getSpeed());
						}
						else if(bossBullets.getCenterX() < ship.getCenterX()){
							bossBullets.translate(2, bossBullets.getSpeed());
						}
						graphicsMan.drawBossBullet(bossBullets, g2d, this);
					} else
					{
						bossBullets.setLocation((int) boss.getCenterX() - 10, (int) boss.getCenterY());
						soundMan.playBossBulletSound();
					}

					if ((boss.getY() + boss.getSpeed() < this.getHeight()))
					{
						if((boss.getCenterX() > this.getWidth()/2) && !(boss.getIsMovingL() || boss.getIsMovingR()))
						{
							boss.isMovingL();
						}
						else if((boss.getCenterX() < this.getWidth()/2) && !(boss.getIsMovingL() || boss.getIsMovingR()))
						{
							boss.isMovingR();
						}
						else if(boss.getIsMovingR())
						{
							boss.translate(boss.getSpeed(), 0);
						}
						else if(boss.getIsMovingL())
						{
							boss.translate(-boss.getSpeed(), 0);
						}
						if((boss.getCenterX() > this.getWidth() - 50)){
							boss.notMovingR();
							boss.isMovingL();
						}
						else if((boss.getCenterX() < 50)){
							boss.notMovingL();
							boss.isMovingR();
						}
						graphicsMan.drawBoss(boss, g2d, this);
					}

					else
					{
						boss.setLocation(rand.nextInt(getWidth() - boss.width), 0);
					}
				}

				else
				{
					if (bossBullets.getY() + bossBullets.getSpeed() < this.getHeight())
					{
						bossBullets.translate(0, bossBullets.getSpeed());
						graphicsMan.drawBossBullet(bossBullets, g2d, this);

					} else
					{
						bossBullets.setLocation((int) boss.getCenterX() - 10, (int) boss.getCenterY());
						soundMan.playBossBulletSound();
					}

					if (boss.getY() + boss.getSpeed() < this.getHeight())
					{
						boss.translate(0, boss.getDefaultSpeed());
						graphicsMan.drawBoss(boss, g2d, this);

					}

					else
					{
						boss.setLocation(rand.nextInt(getWidth() - boss.width), 0);
					}
				}

			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastBossTime) > NEW_BOSS_DELAY)
			{
				// draw a new enemy
				lastBossTime = currentTime;
				status.setNewBoss(false);
				boss.setLocation(rand.nextInt(getWidth() - boss.width), 0);
			} else
			{
				// draw explosion
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}
		}

		// draw extraLife
		if (!status.isNewExtraLife())
		{
			// draw the asteroid until it reaches the bottom of the screen
			if(status.getAsteroidsDestroyed()>=1)
			{
				if (extraLife.getY() + extraLife.getSpeed() < this.getHeight())
				{
					extraLife.translate(0, extraLife.getSpeed());
					graphicsMan.drawExtraLife(extraLife, g2d, this);
				} else
				{
					extraLife.setLocation(rand.nextInt(getWidth() - extraLife.width), 0);
				}
			}
		} else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastExtraLifeTime) > NEW_EXTRALIFE_DELAY)
			{
				// draw a new extra life
				lastExtraLifeTime = currentTime;
				status.setNewExtraLife(false);
				extraLife.setLocation(rand.nextInt(getWidth() - extraLife.width), 0);
			} else
			{
				// draw extra life sparkle
				graphicsMan.drawExtraLifeSparkle(extraLifeSparkle, g2d, this);
			}
		}

		// draw bullets
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if (remove)
			{
				bullets.remove(i);
				i--;
			}
		}

		// check bullet-asteroid collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if ((asteroid.intersects(bullet)) && (asteroid.getY() > 0))
			{
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(asteroid.x, asteroid.y, asteroid.width, asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check bullet-asteroid2 collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if ((asteroid2.intersects(bullet)) && (asteroid2.getY() > 0))
			{
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion2 = new Rectangle(asteroid2.x, asteroid2.y, asteroid2.width, asteroid2.height);
				asteroid2.setLocation(-asteroid2.width, -asteroid2.height);
				status.setNewAsteroid2(true);
				lastAsteroid2Time = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check bullet-asteroid2 collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if ((asteroid3.intersects(bullet)) && (asteroid3.getY() > 0))
			{
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion3 = new Rectangle(asteroid3.x, asteroid3.y, asteroid3.width, asteroid3.height);
				asteroid3.setLocation(-asteroid3.width, -asteroid3.height);
				status.setNewAsteroid3(true);
				lastAsteroid3Time = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check bullet-enemy collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if ((enemy.intersects(bullet)) && (enemy.getY() > 0))
			{
				// increase asteroids destroyed count
				status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);

				// "remove" asteroid
				enemyExplosion = new Rectangle(enemy.x, enemy.y, enemy.width, enemy.height);
				enemy.setLocation(-enemy.width, -enemy.height);
				status.setNewEnemy(true);
				lastEnemyTime = System.currentTimeMillis();
				enemy.notMovingL();
				enemy.notMovingR();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check bullet-boss collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if ((boss.intersects(bullet)) && (boss.getY() > 0))
			{
				// increase asteroids destroyed count
				status.setBossHealth(status.getBossHealth() - 1);

				// "remove" boss //MUST add GAME OVER OTHERWISE BOSS IS INVINSIBLE
				//Other option reset get bossHealth to default
				if(status.getBossHealth()== 0)
				{
					bossExplosion = new Rectangle(boss.x, boss.y, boss.width, boss.height);
					boss.setLocation(-boss.width, -boss.height);
					status.setNewBoss(true);
					lastBossTime = System.currentTimeMillis();
					status.setBossHealth(10);
					boss.notMovingL();
					boss.notMovingR();
					status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);
					status.setBossesDestroyed(status.getBossesDestroyed() +1);
					status.setBossSpawn(false);
				}

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check bullet-extra life (POWERUP) collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = bullets.get(i);
			if (extraLife.intersects(bullet))
			{
				// increase asteroids destroyed count
				gameLogic.powerUp();
				gameLogic.firePowerBullet();
				lastPowerUpTime = Timer;
				// "remove" extra life
				extraLifeSparkle = new Rectangle(ship.x - 2, ship.y, ship.width, ship.height);
				extraLife.setLocation(-extraLife.width, -extraLife.height);
				status.setNewExtraLife(true);
				lastExtraLifeTime = System.currentTimeMillis();

				// play Extra life acquired sound
				soundMan.playPowerUpSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// draw ship
		if (!status.isNewShip())
		{
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		} else
		{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastShipTime) > NEW_SHIP_DELAY)
			{
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			} else
			{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}

		// check ship-asteroid collisions
		if (asteroid.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion = new Rectangle(asteroid.x, asteroid.y, asteroid.width, asteroid.height);
			asteroid.setLocation(-asteroid.width, -asteroid.height);
			status.setNewAsteroid(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-asteroid2 collisions
		if (asteroid2.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion2 = new Rectangle(asteroid2.x, asteroid2.y, asteroid2.width, asteroid2.height);
			asteroid2.setLocation(-asteroid2.width, -asteroid2.height);
			status.setNewAsteroid2(true);
			lastAsteroid2Time = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-asteroid3 collisions
		if (asteroid3.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion3 = new Rectangle(asteroid3.x, asteroid3.y, asteroid3.width, asteroid3.height);
			asteroid3.setLocation(-asteroid3.width, -asteroid3.height);
			status.setNewAsteroid3(true);
			lastAsteroid3Time = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-enemy collisions
		if (enemy.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);

			// "remove" enemy
			enemyExplosion = new Rectangle(enemy.x, enemy.y, enemy.width, enemy.height);
			enemy.setLocation(-enemy.width, -enemy.height);
			status.setNewEnemy(true);
			lastEnemyTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-boss collisions
		if (boss.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);

			// "remove" enemy
			bossExplosion = new Rectangle(boss.x, boss.y, boss.width, boss.height);
			boss.setLocation(-boss.width, -boss.height);
			status.setNewBoss(true);
			lastBossTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// check ship-badBullet collisions
		if (badBullets.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();

		}

		// check ship-bossBullet collisions
		if (bossBullets.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			gameLogic.disablePowerUp();
			// "remove" ship
			shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();

		}


		// check ship-extralife collisions

		if (extraLife.intersects(ship))
		{
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() + 1);

			// "remove" extra life
			extraLifeSparkle = new Rectangle(ship.x - 2, ship.y, ship.width, ship.height);
			extraLife.setLocation(-extraLife.width, -extraLife.height);
			status.setNewExtraLife(true);
			lastExtraLifeTime = System.currentTimeMillis();

			// play Extra life acquired sound
			soundMan.playExtraLifeSound();
		}

		// update Score label
		destroyedValueLabel.setText(Long.toString(status.getScore()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		//PowerUp timer
		if((Timer - lastPowerUpTime) > NEW_POWERUP_DELAY){
			lastPowerUpTime = Timer;
			gameLogic.disablePowerUp();
		}
	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver()
	{
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null ? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if (strWidth > this.getWidth() - 10)
		{
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth) / 2;
		int strY = (this.getHeight() + ascent) / 2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady()
	{
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth) / 2;
		int strY = (this.getHeight() + ascent) / 2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * 
	 * @param numberOfStars
	 *            the number of stars to draw
	 */
	private void drawStars(int numberOfStars)
	{
		for (int i = 0; i < numberOfStars; i++)
		{
			int x = (int) (Math.random() * this.getWidth());
			int y = (int) (Math.random() * this.getHeight());
			if (i / 2 == 0)
			{
				g2d.setColor(Color.WHITE);
				g2d.drawLine(x, y, x, y);
			} else if (i / 3 == 0)
			{
				g2d.setColor(Color.RED);
				g2d.drawLine(x, y, x, y);
			} else if (i / 7 == 0)
			{
				g2d.setColor(Color.YELLOW);
				g2d.drawLine(x, y, x, y);
			} else if (i / 11 == 0)
			{
				g2d.setColor(Color.GREEN);
				g2d.drawLine(x, y, x, y);
			}

		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage()
	{
		String gameTitleStr = "Void Space";

		Font currentFont = biggestFont == null ? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if (strWidth > this.getWidth() - 10)
		{
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth) / 2;
		int strY = (this.getHeight() + ascent) / 2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth) / 2;
		strY = (this.getHeight() + fm.getAscent()) / 2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth) / 2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver()
	{
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame()
	{
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getScore()));
		status.setAsteroidsDestroyed(0);
		status.setEnemiesDestroyed(0);
		status.setBossesDestroyed(0);
		status.setBossSpawn(false);
	}

	/**
	 * Sets the game graphics manager.
	 * 
	 * @param graphicsMan
	 *            the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan)
	{
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * 
	 * @param gameLogic
	 *            the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic)
	{
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * 
	 * @param destroyedValueLabel
	 *            the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel)
	{
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * 
	 * @param shipsValueLabel
	 *            the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel)
	{
		this.shipsValueLabel = shipsValueLabel;
	}
}
