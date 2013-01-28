package org.hannes.util.tasks;

import java.util.Iterator;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.entity.sync.CharacterSync;
import org.hannes.entity.sync.impl.PlayerSync;
import org.hannes.util.CycleUnit;
import org.hannes.util.GameEngine;
import org.hannes.util.TimedTask;

/**
 * Periodically updates all entities
 * 
 * @author red
 */
public class EntitySynchronizationTask extends TimedTask {

	/**
	 * All of the character synchronizations
	 */
	private final CharacterSync[] syncs = new CharacterSync[2];

	/**
	 * Create a new EntitySynchronizationTask with 1 gamecycle delay
	 */
	public EntitySynchronizationTask() throws Exception {
		super(CycleUnit.GAME_CYCLE, 1);

		syncs[0] = new PlayerSync();
		
	}

	@Override
	public boolean cycle(GameEngine engine) throws Exception {
		for (Iterator<Player> iterator = Main.getRealm().getPlayers().iterator(); iterator.hasNext(); ){
			Player player = iterator.next();
			
			// TODO: create the update block
			
			// TODO: Store the player's "update parameters" for new player login requests (direction, ...)
			
			/*
			 * Process the player's next movement.
			 */
			player.getWalkingQueue().processNextMovement();
		}
		
		for (Iterator<Player> iterator = Main.getRealm().getPlayers().iterator(); iterator.hasNext(); ){
			Player player = iterator.next();
			
			/*
			 * Send the sync
			 */
			player.getConnection().write(syncs[0].synchronize(player));
			 
			/*
			 * Finish the synchronization
			 */
			player.finishSynchronization();
		}
		return false;
	}

}