package org.hannes.rs2.util;


/**
 * Indicates what type of client the user is running.
 * 
 * @author red
 *
 */
public enum MemoryUsage {
	
	/**
	 * High memory client. This does have the music tab
	 */
	HIGH(SidebarInterface.HIGH_MEMORY_MUSIC),

	/**
	 * Low memory client. This does not have the music tab
	 */
	LOW(SidebarInterface.LOW_MEMORY_MUSIC),
	
	/**
	 * Not supported yet
	 */
	FULL_SCREEN(null);
	
	/**
	 * The sidebar interface for the music tab
	 */
	private final SidebarInterface sidebarInterface;

	private MemoryUsage(SidebarInterface sidebarInterface) {
		this.sidebarInterface = sidebarInterface;
	}

	public SidebarInterface getSidebarInterface() {
		return sidebarInterface;
	}

}