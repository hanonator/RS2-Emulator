package org.hannes.rs2.content.misc;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.ButtonEvent;
import org.hannes.event.hub.EventHandler;
import org.hannes.sql.impl.UpdateSettingsQuery;

/**
 * Handles all the buttons in the settings interface
 * 
 * @author red
 *
 */
public class SettingsEventHandler implements EventHandler<ButtonEvent> {

	/**
	 * The button index for the brightness setting "Dark"
	 */
	public static final int BRIGHTNESS_DARK = 906;

	/**
	 * The button index for the brightness setting "Normal"
	 */
	public static final int BRIGHTNESS_NORMAL = 908;

	/**
	 * The button index for the brightness setting "Bright"
	 */
	public static final int BRIGHTNESS_BRIGHT = 910;

	/**
	 * The button index for the brightness setting "V-Bright"
	 */
	public static final int BRIGHTNESS_VERY_BRIGHT = 912;

	/**
	 * The button index for the mouse button setting "single"
	 */
	public static final int MOUSE_BUTTONS_SINGLE = 914;

	/**
	 * The button index for the mouse button setting "double"
	 */
	public static final int MOUSE_BUTTONS_DOUBLE = 913;

	/**
	 * The button index for the chat-effects setting "on"
	 */
	public static final int CHAT_EFFECTS_ON = 915;

	/**
	 * The button index for the chat-effects setting "off"
	 */
	public static final int CHAT_EFFECTS_OFF = 916;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int MUSIC_VOLUME_OFF = 930;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int MUSIC_VOLUME_1 = 931;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int MUSIC_VOLUME_2 = 932;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int MUSIC_VOLUME_3 = 933;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int MUSIC_VOLUME_4 = 934;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int EFFECT_VOLUME_OFF = 941;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int EFFECT_VOLUME_1 = 942;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int EFFECT_VOLUME_2 = 943;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int EFFECT_VOLUME_3 = 944;

	/**
	 * The button index for the music-volume setting "off"
	 */
	public static final int EFFECT_VOLUME_4 = 945;

	@Override
	public void handleEvent(ButtonEvent event, Player player) throws Exception {
		switch (event.getIndex()) {
		case BRIGHTNESS_BRIGHT:
		case BRIGHTNESS_DARK:
		case BRIGHTNESS_NORMAL:
		case BRIGHTNESS_VERY_BRIGHT:
			int brightness = 1 + (event.getIndex() - BRIGHTNESS_DARK) / 2;
			
			player.getSettings().setBrightness(brightness);
			Main.getCharacterdatabase().submit(
					new UpdateSettingsQuery("brightness", player, brightness));
			break;
		
		case MOUSE_BUTTONS_DOUBLE:
		case MOUSE_BUTTONS_SINGLE:
			int buttons = 1 - (event.getIndex() - MOUSE_BUTTONS_DOUBLE);
			
			player.getSettings().setMouseButtons(buttons);
			Main.getCharacterdatabase().submit(
					new UpdateSettingsQuery("mouse_buttons", player, buttons));
			break;
		
		case CHAT_EFFECTS_OFF:
		case CHAT_EFFECTS_ON:
			int chat_effects = 1 - (event.getIndex() - CHAT_EFFECTS_ON);
			
			player.getSettings().setChatEffects(chat_effects);
			Main.getCharacterdatabase().submit(
					new UpdateSettingsQuery("chat_effects", player, chat_effects));
			break;
			
		case MUSIC_VOLUME_OFF:
		case MUSIC_VOLUME_1:
		case MUSIC_VOLUME_2:
		case MUSIC_VOLUME_3:
		case MUSIC_VOLUME_4:
			int volume = 4 - (event.getIndex() - MUSIC_VOLUME_OFF);
			
			player.getSettings().setMusicVolume(volume);
			Main.getCharacterdatabase().submit(
					new UpdateSettingsQuery("music_volume", player, volume));
			break;
			
		case EFFECT_VOLUME_OFF:
		case EFFECT_VOLUME_1:
		case EFFECT_VOLUME_2:
		case EFFECT_VOLUME_3:
		case EFFECT_VOLUME_4:
			volume = 4 - (event.getIndex() - EFFECT_VOLUME_OFF);
			
			player.getSettings().setSoundVolume(volume);
			Main.getCharacterdatabase().submit(
					new UpdateSettingsQuery("effect_volume", player, volume));
			break;
		}
		
		 
	}

}