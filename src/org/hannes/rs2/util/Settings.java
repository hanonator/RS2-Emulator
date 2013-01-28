package org.hannes.rs2.util;

import org.hannes.util.Serializable;


/**
 * The settings in the settings tab
 * 
 * @author red
 *
 */
public class Settings implements Serializable {

	/**
	 * The brightness of the player's screen
	 */
	private int brightness;

	/**
	 * Indicates the player uses 1 or 2 mouse buttons
	 */
	private int mouseButtons;

	/**
	 * Indicates whether or not the private chat is split from the public chat
	 */
	private int splitChat;

	/**
	 * Indicates the music volume
	 */
	private int musicVolume;
	
	/**
	 * Indicates the sound volume
	 */
	private int soundVolume;
	
	/**
	 * Indicates whether or not the chat effects are on
	 */
	private int chatEffects;

	/**
	 * Creates a new instance of this class with the default settings
	 */
	public Settings() {
		brightness = 2;
		mouseButtons = 0;
		splitChat = 0;
		chatEffects = 1;
		musicVolume = 0;
		soundVolume = 0;
	}

	@Override
	public Object serialize() {
		return new Object[] {
				new ClientConfig(166, brightness),
				new ClientConfig(168, musicVolume),
				new ClientConfig(169, soundVolume),
				new ClientConfig(171, chatEffects),
				new ClientConfig(287, splitChat)
		};
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness() {
		return brightness;
	}

	/**
	 * @param brightness the brightness to set
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	/**
	 * @return the mouseButtons
	 */
	public int getMouseButtons() {
		return mouseButtons;
	}

	/**
	 * @param mouseButtons the mouseButtons to set
	 */
	public void setMouseButtons(int mouseButtons) {
		this.mouseButtons = mouseButtons;
	}

	/**
	 * @return the splitChat
	 */
	public int getSplitChat() {
		return splitChat;
	}

	/**
	 * @param splitChat the splitChat to set
	 */
	public void setSplitChat(int splitChat) {
		this.splitChat = splitChat;
	}

	/**
	 * @return the musicVolume
	 */
	public int getMusicVolume() {
		return musicVolume;
	}

	/**
	 * @param musicVolume the musicVolume to set
	 */
	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

	/**
	 * @return the soundVolume
	 */
	public int getEffectVolume() {
		return soundVolume;
	}

	/**
	 * @param soundVolume the soundVolume to set
	 */
	public void setSoundVolume(int soundVolume) {
		this.soundVolume = soundVolume;
	}

	/**
	 * @return the chatEffects
	 */
	public int getChatEffects() {
		return chatEffects;
	}

	/**
	 * @param chatEffects the chatEffects to set
	 */
	public void setChatEffects(int chatEffects) {
		this.chatEffects = chatEffects;
	}

}