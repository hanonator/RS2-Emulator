package org.hannes;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.hannes.archive.Archive;
import org.hannes.event.hub.EventHub;
import org.hannes.locale.Realm;
import org.hannes.net.RS2PipelineFactory;
import org.hannes.net.codec.Decoders;
import org.hannes.net.codec.Encoders;
import org.hannes.rs2.content.misc.Animations;
import org.hannes.rs2.content.misc.WeaponInterface;
import org.hannes.rs2.util.NPCDefinition;
import org.hannes.sql.SQLHandler;
import org.hannes.sql.impl.ItemDefinitionsQuery;
import org.hannes.util.Configuration;
import org.hannes.util.GameEngine;
import org.hannes.util.tasks.ConnectionFlushTask;
import org.hannes.util.tasks.EntitySynchronizationTask;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * The main class
 * 
 * @author red
 *
 */
public class Main {

	/**
	 * The logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	/**
	 * The cycle time of the server.
	 */
	private static final int CYCLE_TIME =  50;

	/**
	 * The GameEngine
	 */
	private static final GameEngine engine = new GameEngine(CYCLE_TIME);
	
	/**
	 * The realm
	 */
	private static final Realm realm = new Realm();
	
	/**
	 * The event hub
	 */
	private static final EventHub eventHub = new EventHub();
	
	/**
	 * The configuration
	 */
	private static final Configuration configuration = new Configuration();

	/**
	 * The character database
	 */
	private static final SQLHandler characterDatabase = new SQLHandler();
	
	/**
	 * The configuration database
	 */
	private static final SQLHandler configurationDatabase = new SQLHandler();

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			logger.info("Setting up server ...");
			
			/*
			 * Load the configuration archive
			 */
			
			Archive archive = Archive.create(new File("data/config.rsa"));
			
			/*
			 * Create a new SAX reader
			 */
			SAXReader reader = new SAXReader();
			
			/*
			 * Get the server configuration
			 */
			Document document = reader.read(new File("configuration.xml"));
			
			/*
			 * Load the server configuration
			 */
			configuration.load(document);
			
			/*
			 * Register the periodic updates
			 */
			engine.submit(new EntitySynchronizationTask());
			engine.submit(new ConnectionFlushTask());
			
			/*
			 * Initialize the sql handler
			 */
			SQLHandler.initializeDriver();
			
			/*
			 * Connect to the rsps database
			 */
			characterDatabase.connect(configuration.get("db-url"), configuration.get("character-db"), 
					configuration.get("db-username"), configuration.get("db-password"));

			/*
			 * initialize the character db queries
			 */
			characterDatabase.initializeQueries();

			/*
			 * connect to the config database
			 */
			configurationDatabase.connect(configuration.get("db-url"), configuration.get("configuration-db"), 
					configuration.get("db-username"), configuration.get("db-password"));
			
			/*
			 * Revision string
			 */
			String v = configuration.get("version");
			
			/*
			 * Initialize the decoders
			 */
			Decoders.initialize(reader.read("protocols/decoders-" + v + ".xml"));
			
			/*
			 * Initialize the encoders
			 */
			Encoders.initialize(reader.read("protocols/encoders-" + v + ".xml"));
			
			/*
			 * Initialize the event handlers
			 */
			eventHub.initialize(reader.read(new File("event-handlers.xml")));
			
			/*
			 * Initialize the cool weapon interfaces
			 */
			WeaponInterface.initialize(reader.read(new File("data/weapon-interfaces.xml")));
			
			/*
			 * Initialize the item definitions
			 */
			Main.getConfigurationdatabase().submit(new ItemDefinitionsQuery()).get();
			
			/*
			 * Initialize the animations
			 */
			Main.getConfigurationdatabase().submit(new Animations()).get();
			
			/*
			 * Initialize the npc definitions
			 */
			NPCDefinition.initialize(archive);
			
			/*
			 * Create the server boostrap
			 */
			ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
			
			/*
			 * Configure
			 */
			bootstrap.setOption("keepAlive", true);
			bootstrap.setOption("reuseAddress", true);
			bootstrap.setOption("child.tcpNoDelay", true);
			
			/*
			 * Set the pipeline factory
			 */
			bootstrap.setPipelineFactory(new RS2PipelineFactory());
	
			String host = configuration.get("host");
			int port = Integer.valueOf(configuration.get("port"));
			
			/*
			 * Bind to localhost on port 43594 
			 */
			bootstrap.bind(new InetSocketAddress(host, port));
			
			/*
			 * Ready to go
			 */
			logger.info("OK");
		} catch (Exception ex) {
			engine.shutdown();
			ex.printStackTrace();
		}
	}

	public static Realm getRealm() {
		return realm;
	}

	public static EventHub getEventHub() {
		return eventHub;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static SQLHandler getCharacterdatabase() {
		return characterDatabase;
	}

	public static SQLHandler getConfigurationdatabase() {
		return configurationDatabase;
	}

}