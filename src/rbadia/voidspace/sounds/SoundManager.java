package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager
{
	private static final boolean SOUND_ON = true;

	private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource("/rbadia/voidspace/sounds/shipExplosion.wav"));
	private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource("/rbadia/voidspace/sounds/laser.wav"));
	private AudioClip extraLifeSound = Applet.newAudioClip(GameScreen.class.getResource("/rbadia/voidspace/sounds/1UP.wav"));
	private AudioClip powerUpSound = Applet.newAudioClip(GameScreen.class.getResource("/rbadia/voidspace/sounds/powerup.wav"));
	private AudioClip bossBulletSound = Applet.newAudioClip(GameScreen.class.getResource("/rbadia/voidspace/sounds/BossBullet.wav"));

	/**
	 * Plays sound for bullets fired by the ship.
	 */
	public void playBulletSound()
	{
		if (SOUND_ON)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					bulletSound.play();
				}
			}).start();
		}
	}
	
	/**
	 * Plays sound for bullets fired by the boss.
	 */
	public void playBossBulletSound()
	{
		if (SOUND_ON)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					bossBulletSound.play();
				}
			}).start();
		}
	}
	
	/**
	 * Plays sound for extra lives acquired.
	 */
	public void playExtraLifeSound()
	{
		if (SOUND_ON)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					extraLifeSound.play();
				}
			}).start();
		}
	}
	
	/**
	 * Plays sound for extra lives acquired.
	 */
	public void playPowerUpSound()
	{
		if (SOUND_ON)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					powerUpSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for ship explosions.
	 */
	public void playShipExplosionSound()
	{
		if (SOUND_ON)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					shipExplosionSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playAsteroidExplosionSound()
	{
		// play sound for asteroid explosions
		if (SOUND_ON)
		{

		}
	}
}
