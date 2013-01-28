package org.hannes.rs2.content;

import org.hannes.entity.Player;
import org.hannes.event.WalkEvent;
import org.hannes.event.hub.EventHandler;

/**
 * Handles the walking events
 * 
 * @author red
 *
 */
public class WalkEventHandler implements EventHandler<WalkEvent> {

	@Override
	public void handleEvent(WalkEvent event, Player player) throws Exception {
		/*
		 * Reset the player's walking queue
		 */
		player.getWalkingQueue().reset();

		/*
		 * Make the player walk or run depending on the event
		 */
		player.getWalkingQueue().setRunningQueue(event.isRunning());

		/*
		 * n = data.length / 2 where n is the amount of steps
		 */
		final int steps = event.getData().length / 2;
		
		/*
		 * Gather dat path from dem message
		 */
		final int[][] path = new int[steps][2];
		for (int i = 0, offset = 0; i < steps; i++) {
			path[i][0] = event.getData(offset++);
			path[i][1] = event.getData(offset++);
		}
		
		/*
		 * Add everything to the walking queue
		 */
		player.getWalkingQueue().addStep(event.getInitialX(), event.getInitialY());
		for (int i = 0; i < steps; i++) {
			path[i][0] += event.getInitialX();
			path[i][1] += event.getInitialY();
			player.getWalkingQueue().addStep(path[i][0], path[i][1]);
		}
		
		/*
		 * Finish the walking queue, otherwise there will be a full cycle delay
		 * between the request and the client
		 */
		player.getWalkingQueue().finish();
	}

}