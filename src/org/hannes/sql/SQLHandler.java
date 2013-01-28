package org.hannes.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.hannes.Main;
import org.hannes.event.Event;

/**
 * SQLite connectionstring: jdbc:sqlite:./database/database.sqlite
 * 
 * @author tentaquil
 * 
 */
public class SQLHandler {

	/**
	 * The folder containing all the queries
	 */
	private static final File QUERY_FOLDER = new File("sql");

	/**
	 * The queries
	 */
	private final Map<String, PreparedStatement> queries = new HashMap<>();

	/**
	 * The object pool
	 */
	private ObjectPool objectPool;
	
	/**
	 * The connection factory
	 */
	private ConnectionFactory connectionFactory;
	
	/**
	 * The poolable connection factory
	 */
	private PoolableConnectionFactory poolableConnectionFactory;
	
	/**
	 * The pooling datasource
	 */
	private PoolingDataSource dataSource;

	/**
	 * The executorservice
	 */
	private static ExecutorService service = Executors.newFixedThreadPool(8);

	/**
	 * Initializes the driver
	 * 
	 * @throws Exception
	 */
	public static void initializeDriver() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
	}

	/**
	 * Connect to a MySQL database
	 * 
	 * @param service
	 * @param connectionUri
	 * @param username
	 * @param password
	 */
	public void connect(String connectionUri, String database, String username, String password) {
		objectPool = new GenericObjectPool();
		connectionFactory = new DriverManagerConnectionFactory(connectionUri + database, username, password);
		poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, objectPool, null, "SELECT * FROM null-db;", false, true);
		dataSource = new PoolingDataSource(poolableConnectionFactory.getPool());
	}

	/**
	 * Initializes the query files
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException 
	 */
	public void initializeQueries() throws SQLException, FileNotFoundException {
		Connection connection = dataSource.getConnection();
		loadQueries(QUERY_FOLDER, connection);
	}

	/**
	 * Recursive n shit
	 * 
	 * @param folder
	 * @param connection
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	private void loadQueries(File folder, Connection connection) throws FileNotFoundException, SQLException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				loadQueries(file, connection);
			} else {
				Scanner scanner = new Scanner(file);
				StringBuilder builder = new StringBuilder();
				while(scanner.hasNext()) {
					builder.append(scanner.nextLine()).append(" ");
				}
				queries.put(file.getName().replaceAll(".sql", ""), connection.prepareStatement(builder.toString()));
				scanner.close();
			}
		}
	}

	/**
	 * Set the executorservice
	 * 
	 * @param service
	 */
	public void setExecutorService(ExecutorService service) {
		SQLHandler.service = service;
	}

	/**
	 * Submit a class to the threadz n shit
	 * 
	 * @param job
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Event> Future<T> submit(final Query<T> job) {
		return service.submit(new Callable<T>() {
			
			@Override
			public T call() throws Exception {
				try {
					Connection connection = dataSource.getConnection();
					try {
						Event event = job.execute(connection, SQLHandler.this);
						if (event != null) {
							Main.getEventHub().offer(event);
						}
						return (T) event;
					} finally {
						connection.close();
					}
				} catch (Exception ex) {
					job.exceptionCaught(ex);
					return null;
				}
			}
			
		});
	}

	public PreparedStatement getQuery(String file) {
		return queries.get(file);
	}

	public PreparedStatement prepare(String query, Connection connection) throws SQLException {
		if (queries.containsKey(query)) {
			return queries.get(query);
		}
		return connection.prepareStatement(query);
	}

}