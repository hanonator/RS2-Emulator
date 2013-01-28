package org.hannes.rs2.container;

public class ContainerContext {

	/**
	 * The container
	 */
	private final Container container;
	
	/**
	 * The interface id on which the items will be displayed
	 */
	private final int interfaceId;

	/**
	 * 
	 * 
	 * @param container
	 * @param interfaceId
	 */
	public ContainerContext(Container container, int interfaceId) {
		this.container = container;
		this.interfaceId = interfaceId;
	}

	public Container getContainer() {
		return container;
	}

	public int getInterfaceId() {
		return interfaceId;
	}

}