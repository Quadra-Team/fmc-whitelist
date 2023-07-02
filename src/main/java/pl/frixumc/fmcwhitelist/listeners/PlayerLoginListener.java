package pl.frixumc.fmcwhitelist.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.frixumc.fmcwhitelist.utils.ConfigurationFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerLoginListener implements Listener {
    private Connection connection;
    private ConfigurationFile config;

    public PlayerLoginListener(Connection connection, ConfigurationFile config) {
        this.connection = connection;
        this.config = config;
    }

    @EventHandler
    public void OnPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();

        try {
            // Sprawdzenie czy użytkownik jest na liście whitelisty w bazie danych
            PreparedStatement statement = connection.prepareStatement(config.get("database.query").toString());
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Nie jesteś na liście whitelisty!");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
