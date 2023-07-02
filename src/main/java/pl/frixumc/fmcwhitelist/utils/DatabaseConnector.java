package pl.frixumc.fmcwhitelist.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private String hostname;
    private int port;
    private String username;
    private String name;

    private String password;
    private Connection connection;

    public DatabaseConnector(JavaPlugin plugin) {
        ConfigurationFile config = new ConfigurationFile(plugin.getDataFolder() + "/database.yml");

        hostname = config.get("database.hostname").toString();
        port = Integer.parseInt(config.get("database.port").toString());
        username = config.get("database.username").toString();
        password = config.get("database.password").toString();
        name = config.get("database.name").toString();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + name, username, password);

            System.out.println("Połączenie z bazą danych zostało nawiązane. HOST: " + hostname);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return name;
    }
    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}