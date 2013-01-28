package org.hannes.util.tasks;

import java.util.Iterator;
import java.util.Map.Entry;

import org.hannes.net.Connection;
import org.hannes.net.RS2ChannelHandler;
import org.hannes.util.CycleUnit;
import org.hannes.util.GameEngine;
import org.hannes.util.TimedTask;
import org.jboss.netty.channel.Channel;

/**
 * Periodically flushes the connections so the server keeps in synch with the client
 * 
 * @author red
 *
 */
public class ConnectionFlushTask extends TimedTask {

	/**
	 * Creates a new {@link ConnectionFlushTask}
	 */
	public ConnectionFlushTask() {
		super(CycleUnit.GAME_CYCLE, 1);
	}

	@Override
	public boolean cycle(GameEngine engine) throws Exception {
		for (Iterator<Entry<Channel, Connection>> iterator = RS2ChannelHandler.getLocal().iterator(); iterator.hasNext(); ) {
			iterator.next().getValue().flush();
		}
		return false;
	}

}