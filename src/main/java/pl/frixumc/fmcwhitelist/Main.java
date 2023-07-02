package pl.frixumc.fmcwhitelist;

import org.bukkit.plugin.java.JavaPlugin;
import pl.frixumc.fmcwhitelist.listeners.PlayerLoginListener;
import pl.frixumc.fmcwhitelist.utils.ConfigurationFile;
import pl.frixumc.fmcwhitelist.utils.DatabaseConnector;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private ConfigurationFile config;
    private DatabaseConnector databaseConnector;

    @Override
    public void onEnable() {

        config = new ConfigurationFile(getDataFolder() + "/database.yml");

        Map<String, Object> defaults = new HashMap<>();
        defaults.put("database.hostname", "hostname");
        defaults.put("database.port", 3306);
        defaults.put("database.username", "root");
        defaults.put("database.password", "password");
        defaults.put("database.name", "name");
        defaults.put("database.query", "query");

        config.addDefaults(defaults);

        config.save();

        System.out.println("test");

        databaseConnector = new DatabaseConnector(this);
        Connection connection = databaseConnector.getConnection();

        PlayerLoginListener loginListener = new PlayerLoginListener(connection, config);
        getServer().getPluginManager().registerEvents(loginListener, this);

    }

    @Override
    public void onDisable() {

        databaseConnector.closeConnection();

    }
}
