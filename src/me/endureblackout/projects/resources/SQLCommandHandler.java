package me.endureblackout.projects.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a simple way to interact with an SQL server that is defined in the plugin configuration
 * 
 * @author Jake Ellingson
 *
 */

public class SQLCommandHandler {
    protected String server;
    protected String port;
    protected String user;
    protected String pass;
    protected String database;

    protected Connection conn;

    /**
     * Sets up the SQL server information to execute sql command
     *
     * @param core The main class of the plugin
     */
    public SQLCommandHandler() {
        this.server = "localhost";
        this.port = "3306";
        this.user = "root";
        this.pass = "<yourpassword>";
        this.database = "messageDB"; 
    }

    /**
     * Executes a SQL query on the server and database in the config.
     *
     * @param command SQL command for query.
     * @return ResultSet returns the information gathered from the database.
     */
    public ResultSet executeSQLQuery(String command) {
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, user, pass);
            Statement stmt = conn.createStatement();

            rs = stmt.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * Executes a SQL update on the server and database in the config.
     *
     * @param command SQL command for update
     * @return boolean Returns whether the update succeeded or not.
     */
    public boolean executeSQLUpdate(String command) {
        boolean success = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, user, pass);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(command);

            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        
        return success;
    }

    /**
     * Gets the current database
     * 
     * @return String
     */
    public String getDB() {
        return database;
    }

    /**
     * Closes the connection to the database
     */
    public void connectionClose() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

