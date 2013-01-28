package org.hannes.rs2.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.hannes.archive.Archive;

/**
 * NIGGA THIS IS BUBU
 * 
 * @author red
 *
 */
public class NPCDefinition implements Closeable {
	
	/**
	 * Creates a logger
	 */
	private static final Logger logger = Logger.getLogger(NPCDefinition.class.getName());
	
	/**
	 * The buffer containing the data file
	 */
	private static ByteBuffer buffer;

	/**
	 * The map contiaining the definitions
	 */
	private static final Map<Integer, NPCDefinition> definitions = new HashMap<>();
	
	/**
	 * The pointers
	 */
	private static int[] pointers;
	
	/**
	 * The index
	 */
	private final int index;
	
	/**
	 * The name of the NPC
	 */
	private String name;
	
	/**
	 * The examination info
	 */
	private String description;
	
	/**
	 * The size of the NPC (in tiles)
	 */
	private int size;
	
	/**
	 * The level of the npc
	 */
	private int level;
	
	/**
	 * The index of the idle animation
	 */
	private int idleAnimationIndex;
	
	/**
	 * The index of the walking animation
	 */
	private int walkAnimationIndex;
	
	/**
	 * The index of the animation performed when turning left 
	 */
	private int rotateLeftAnimationIndex;

	/**
	 * The index of the animation performed when turning left 
	 */
	private int rotateRightAnimationIndex;

	/**
	 * 1337 sniper tr1x (I don't know what this one is)
	 */
	private int sniperTrickShotz;
	
	/**
	 * The actions that are able to be performed on the NPC
	 */
	private String[] actions;
	
	/**
	 * The models of the npc
	 */
	private int[] models;
	
	/**
	 * The default color
	 */
	private int[] defaultColor;
	
	/**
	 * The new color
	 */
	private int[] newColor;

	/**
	 * The additional models (dunno)
	 */
	private int[] additionalModels;
	
	/**
	 * Indicates this is a hidden NPC (it does not appear on the minimap)
	 */
	private boolean hidden;
	
	/**
	 * The width of the NPC (both the Z and X axis) (default 128)
	 */
	private int width;
	
	/**
	 * The height on the Y axis (default 128)
	 */
	private int height;
	
	/**
	 * The luminosity of the models
	 */
	private int luminosity;
	
	/**
	 * Shading factor
	 */
	private int shade;
	
	/**
	 * The "headicon" of the NPC
	 */
	private int icon;
	
	/**
	 * The rotation of the NPC
	 */
	private int rotation;
	
	/**
	 * Indicates the npc is interactable or not
	 */
	private boolean interactable;

	/**
	 * Creates a new NpcDefinition with default settings
	 * 
	 * @param index
	 */
	public NPCDefinition(int index) {
		this.index = index;
		this.interactable = true;
		this.width = 128;
		this.height = 128;
		this.level = -1;
		this.icon = -1;
		this.size = 1;
		this.rotation = 32;
		this.walkAnimationIndex = -1;
		this.idleAnimationIndex = -1;
		this.sniperTrickShotz = -1;
		this.rotateLeftAnimationIndex = -1;
		this.rotateRightAnimationIndex = -1;
	}

	/**
	 * Initializes the NpcDefinition
	 * 
	 * @throws IOException
	 */
	public static void initialize(Archive archive) throws IOException {
		/*
		 * Load the main file buffer
		 */
		buffer = archive.get("NPC.DAT");
		
		/*
		 * Load the index buffer
		 */
		ByteBuffer buffer = archive.get("NPC.IDX");
		
		/*
		 * The length of the file
		 */
		int length = buffer.getShort();
		
		/*
		 * Create the pointers array
		 */
		pointers = new int[length];
		
		/*
		 * Add all the pointers to the array
		 */
		for (int index = 0, pointer = 2; index < length; index++) {
			/*
			 * Set the current pointer
			 */
			pointers[index] = pointer;
			
			/*
			 * Add the length of the file to the pointer for the next file
			 */
			pointer += buffer.getShort() & 0xFFFF;
		}
		
		/*
		 * Info output
		 */
		logger.info("Loaded " + length + " NPC definitions.");
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static NPCDefinition get(int id) {
		if (definitions.containsKey(id)) {
			return definitions.get(id);
		}
		NPCDefinition npcdef = new NPCDefinition(id);
		definitions.put(id, npcdef);
		return npcdef.read();
	}

	/**
	 * 
	 * @return
	 */
	private NPCDefinition read() {
		buffer.position(pointers[index]);
		for (int opcode = buffer.get() & 0xFF; opcode != 0; opcode = buffer.get() & 0xFF) {
			switch (opcode) {
			case 1:
				models = new int[buffer.get()];
				for (int i = 0; i < models.length; i++) {
					models[i] = buffer.getShort();
				}
				break;

			case 2:
				name = readRS2String(buffer);
				break;

			case 4:
				description = readRS2String(buffer);
				break;

			case 12:
				size = buffer.get() & 0xFF;
				break;

			case 13:
				idleAnimationIndex = buffer.getShort();
				break;
			case 14:
				walkAnimationIndex = buffer.getShort();
				break;

			case 17:
				walkAnimationIndex = buffer.getShort();
				sniperTrickShotz = buffer.getShort();
				rotateLeftAnimationIndex = buffer.getShort();
				rotateRightAnimationIndex = buffer.getShort();
				break;

			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
				if (actions == null) {
					actions = new String[5];
				}
				actions[opcode - 30] = readRS2String(buffer);
				if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
					actions[opcode - 30] = null;
				}
				break;

			case 40:
				int colours = buffer.get();
				defaultColor = new int[colours];
				newColor = new int[colours];
				for (int i = 0; i < colours; i++) {
					defaultColor[i] = buffer.getShort() & 0xFFFF;
					newColor[i] = buffer.getShort() & 0xFFFF;
				}
				break;

			case 60:
				additionalModels = new int[buffer.get()];
				for (int i = 0; i < additionalModels.length; i++)
					additionalModels[i] = buffer.getShort();
				break;

			case 90:
			case 91:
			case 92:
				buffer.getShort();
				break;

			case 93:
				hidden = false;
				break;

			case 95:
				level = buffer.getShort();
				break;

			case 97:
				width = buffer.getShort();
				break;
			case 98:
				height = buffer.getShort();
				break;

			case 100:
				luminosity = buffer.get() & 0xFF;
				break;

			case 101:
				shade = (buffer.get() & 0xFF) * 5;
				break;

			case 102:
				icon = buffer.getShort();
				break;

			case 103:
				rotation = buffer.getShort();
				break;

			case 106:
				int varBitID = buffer.getShort() & 0xFFFF;
				if (varBitID == 65535)
					varBitID = -1;
				int sessionSettingID = buffer.getShort() & 0xFFFF;
				if (sessionSettingID == 65535)
					sessionSettingID = -1;
				int length = buffer.get();
				int[] childrenIDs = new int[length + 1];
				for (int i2 = 0; i2 <= length; i2++) {
					childrenIDs[i2] = buffer.getShort() & 0xFFFF;
					if (childrenIDs[i2] == 65535)
						childrenIDs[i2] = -1;
				}
				break;

			case 107:
				interactable = false;
				break;
			}
		}
		
		/*
		 * Add the definition to the collection
		 */
		definitions.put(index, this);
		
		/*
		 * Return this instance for chaining
		 */
		return this;
	}

	/**
	 * Reads a string from a buffer
	 * 
	 * @param buffer
	 * @return
	 */
	private static String readRS2String(ByteBuffer buffer) {
		StringBuilder bldr = new StringBuilder();
		byte b;
		while(buffer.hasRemaining() && (b = buffer.get()) != 10) {
			bldr.append((char) b);
		}
		return bldr.toString();
	}

	@Override
	public void close() throws IOException {
		pointers = null;
		buffer.clear();
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getSize() {
		return size;
	}

	public int getLevel() {
		return level;
	}

	public int getIdleAnimationIndex() {
		return idleAnimationIndex;
	}

	public int getWalkAnimationIndex() {
		return walkAnimationIndex;
	}

	public int getRotateLeftAnimationIndex() {
		return rotateLeftAnimationIndex;
	}

	public int getRotateRightAnimationIndex() {
		return rotateRightAnimationIndex;
	}

	public int getSniperTrickShotz() {
		return sniperTrickShotz;
	}

	public String[] getActions() {
		return actions;
	}

	public int[] getModels() {
		return models;
	}

	public int[] getDefaultColor() {
		return defaultColor;
	}

	public int[] getNewColor() {
		return newColor;
	}

	public int[] getAdditionalModels() {
		return additionalModels;
	}

	public boolean isHidden() {
		return hidden;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLuminosity() {
		return luminosity;
	}

	public int getShade() {
		return shade;
	}

	public int getIcon() {
		return icon;
	}

	public int getRotation() {
		return rotation;
	}

	public boolean isInteractable() {
		return interactable;
	}

}