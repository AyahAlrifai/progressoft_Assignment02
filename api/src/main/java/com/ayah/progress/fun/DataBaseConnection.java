package com.ayah.progress.fun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * DatabaseConnection used to create new connection on database
 * <p>
 * use JDBC 
 * <p>
 * should initialize class before create any object by call this method 
 * <p>
 * {@link #DataBaeConnection.setConnectionData() setConnectionData()} method
 * <p>
 * @author Ayah Al-refai
 */
public class DataBaseConnection {

    private static String dbAccount;
    private static String dbPassword;
    private static String dbHost;
    private static String dbPort;
    private static String dbDatabase;

    private Connection connection;
    private ProgressLogger logger;

    public DataBaseConnection(String className) {
        this.logger = new ProgressLogger(className);
        startConnection();
    }

    /**
     * set Database parameter connection
     * get these value from properties file {@link #Progressoft.json src\main\Progressoft.json}
     * @param dbAccount userName for database
     * @param dbPassword password of database
     * @param dbHost host
     * @param dbPort port
     * @param dbDatabase database name
     */
    public static void setConnectionData(String dbAccount, String dbPassword, String dbHost, String dbPort,
            String dbDatabase) {
        DataBaseConnection.dbAccount = dbAccount;
        DataBaseConnection.dbPassword = dbPassword;
        DataBaseConnection.dbHost = dbHost;
        DataBaseConnection.dbPort = dbPort;
        DataBaseConnection.dbDatabase = dbDatabase;
    }

    /**
     * start new connection to database
     */
    private void startConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbDatabase,
                    dbAccount, dbPassword);
            logger.log(Level.INFO, "Start connect " + "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbDatabase);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * get connection
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * close connection
     */
    public void closeConnection() {
        try {
            connection.close();
            logger.log(Level.WARNING, "Close connection " + "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbDatabase);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    protected void finalize() {
        closeConnection();
    }


}
