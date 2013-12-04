package com.upgradingdave.jdbc.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper for creating database table
 */
public class DbUtils {

  Logger log = LoggerFactory.getLogger(DbUtils.class);

  DatabaseManager databaseManager;

  public static final String dropTable = "DROP TABLE %s";

  public DbUtils(DatabaseManager databaseManager) {

    this.databaseManager = databaseManager;

  }

  private Boolean dropTable(final String tableName) {

    log.info("Dropping Table {}", tableName);

    return databaseManager.execute(new DatabaseCall<Boolean>() {
      @Override
      public Boolean withConnection(Connection connection) {
        try {
          Statement statement = connection.createStatement();
          return statement.execute(String.format(dropTable, tableName));

        } catch (SQLException e) {
          log.debug("Unable to drop table", e);
          return false;
        }
      }
    });
  }

  private Boolean createTable(final String name, final String sql) {

    log.info("Creating Table {}", name);

    return databaseManager.execute(new DatabaseCall<Boolean>() {
      @Override
      public Boolean withConnection(Connection connection) {
        try {
          Statement statement = connection.createStatement();
          return statement.execute(sql);
        } catch (SQLException e) {
          log.debug("Unable to create table", e);
          return false;
        }
      }
    });
  }
}
