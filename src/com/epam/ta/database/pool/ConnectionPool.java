package com.epam.ta.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.epam.ta.database.pool.exception.ConnectionPoolException;

public final class ConnectionPool {
	private int poolSize;

	private String dbURL;

	private String dbUser;

	private String dbPassword;

	private String dbDriverName;
	
	private static ConnectionPool pool;

	private BlockingQueue<Connection> busyConnections;

	private BlockingQueue<Connection> availableConnections;

	private static final Logger logger = Logger.getLogger(ConnectionPool.class);

	public ConnectionPool(int poolSize) throws ConnectionPoolException {
		this.poolSize = poolSize;
		loadDatabaseDriver();
		// create queues for available and busy connections
		availableConnections = new ArrayBlockingQueue<Connection>(
				poolSize);
		busyConnections = new ArrayBlockingQueue<Connection>(
				poolSize);
		// fill these queues
		for (int i = 0; i < this.poolSize; i++) {
			availableConnections.add(makeNewConnection());
		}
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setDbDriverName(String dbDriverName) {
		this.dbDriverName = dbDriverName;
	}

	private Connection makeNewConnection() throws ConnectionPoolException {
		try {
			return DriverManager.getConnection(dbURL, dbUser,
					dbPassword);
		} catch (SQLException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	private void loadDatabaseDriver() throws ConnectionPoolException {
		try {
			String driver = dbDriverName;
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	public Connection getConnection() throws ConnectionPoolException {
		try {
			Connection connection = availableConnections.take();
			busyConnections.add(connection);
			return connection;
		} catch (InterruptedException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}

	public void makeConnectionFree(Connection connection) {
			busyConnections.remove(connection);
			availableConnections.add(connection);
	}

	public void closeAllConnections() throws ConnectionPoolException {
		closeConnections(busyConnections);
		busyConnections.clear();
		closeConnections(availableConnections);
		availableConnections.clear();
	}

	private void closeConnections(BlockingQueue<Connection> connections)
			throws ConnectionPoolException {
		try {
			Connection connection;
			for (int i = 0; i < connections.size(); i++) {
				connection = connections.poll();
				closeConnection(connection);
			}
		} catch (ConnectionPoolException e) {
			closeConnections(connections);
			throw e;
		}
	}

	private void closeConnection(Connection connection)
			throws ConnectionPoolException {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new ConnectionPoolException(e);
		}
	}
}
