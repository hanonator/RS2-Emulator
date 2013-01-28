package org.hannes.rs2.util;

public class Sidebar {

	/**
	 * The amount of tabs in the sidebar
	 */
	private static final int TAB_COUNT = 14;

	/**
	 * The sidebar interfaces
	 */
	private final SidebarInterface[] interfaces = new SidebarInterface[TAB_COUNT];

	/**
	 * Sets the interface on the given index
	 * @param index
	 * @param iface
	 * 
	 * @return the iface for chaining
	 */
	public SidebarInterface set(int index, SidebarInterface iface) {
		interfaces[index] = iface;
		return iface;
	}

	/**
	 * Get the interface at the given index
	 * 
	 * @param index
	 * @return
	 */
	public SidebarInterface get(int index) {
		return interfaces[index];
	}

	/**
	 * Get an array of all the interfaces
	 * @return
	 */
	public SidebarInterface[] getInterfaces() {
		return interfaces;
	}

	/**
	 * Get as object array
	 * @return
	 */
	public Object[] get() {
		return interfaces;
	}

	public int size() {
		return TAB_COUNT;
	}

}