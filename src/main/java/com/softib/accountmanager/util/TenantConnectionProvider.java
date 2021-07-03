package com.softib.accountmanager.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import com.softib.accountmanager.services.CreditCardServiceImpl;

@Component
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(CreditCardServiceImpl.class);
	private String DEFAULT_TENANT = "accountmanager";
	private DataSource datasource;

	public TenantConnectionProvider(DataSource dataSource) {
		this.datasource = dataSource;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return datasource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		logger.info("Get connection for tenant {}", tenantIdentifier);
		final Connection connection = getAnyConnection();
		try (Statement statement = connection.createStatement()) {
			statement.execute("USE " + tenantIdentifier);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		logger.info("Release connection for tenant {}", tenantIdentifier);
		connection.setSchema(DEFAULT_TENANT);
		releaseAnyConnection(connection);
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean isUnwrappableAs(Class aClass) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> aClass) {
		return null;
	}
}