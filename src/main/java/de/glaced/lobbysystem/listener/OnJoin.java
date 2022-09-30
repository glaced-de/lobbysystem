package de.glaced.lobbysystem.listener;

import de.glaced.lobbysystem.scoreboard.LobbyScoreboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import static de.glaced.lobbysystem.Lobbysystem.*;

public class OnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getDisplayName() + " §7hat den Server betreten!");
        Player p = event.getPlayer();
        new LobbyScoreboard(p);
        final Component header = MiniMessage.miniMessage().deserialize("<white><bold>«</white> <gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient> <gradient:#e583fe:#a351fe:#c2cef6>Network</gradient> <white><bold>»");
        final Component footer = MiniMessage.miniMessage().deserialize(("<gray><italic>Thanks a lot to</italic> <bold><gradient:#5e4fa2:#f79459:red>DeinServerHost</gradient></bold> <italic>for sponsoring this server!</italic></gray>"));
        p.sendPlayerListHeaderAndFooter(header, footer);

        // player send actionbar message repeat all 5 seconds
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                // random choose one of the messages
                // switch the another when the first is done
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        //p.sendActionBar(Component.text("§8» §7Current player online: " + MiniMessage.miniMessage().deserialize("<gradient:#e583fe:#a351fe:#c2cef6>") + Bukkit.getServer().getOnlinePlayers().size() + " §8«"));
                        p.sendActionBar(Component.text("§8» §e§o#players§8, §7Lobby stats§8: §9" + Bukkit.getServer().getOnlinePlayers().size() + "§8/§730 §8«"));
                        break;
                    case 1:
                        // wenn tps höher als 15 dann sag "§a" sonst "§c"
                        p.sendActionBar(Component.text("§8» §e§o#tps§8, §7Lobby stats§8: " + (Bukkit.getServer().getTPS()[0] > 15 ? "§a" : "§c") + Arrays.toString(Bukkit.getServer().getTPS()).substring(1, 3) + "§8/§720 §8«"));
                        break;
                }
            }
        }).start();
        // mysql iftableexists check if player is in the database if not create a new entry for the player with language = de

        //Connection vars
        final String username="root"; // Enter in your db username
        final String password=""; // Enter your password for the db
        final String url = "jdbc:mysql://localhost:3306/glaced"; // Enter URL with db name
        try {
            connection = DriverManager.getConnection(url, username, password);
            Bukkit.getConsoleSender().sendMessage("§aMySQL connected!");
        } catch (SQLException e) { // catching errors
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
        }

        // string sql when its not exist create glaced database with table playerdata and create the columns uuid, name, language
        // if player is not in database then, set player uuid and language de as default value
        String sql = "INSERT INTO language (uuid, language) SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT uuid FROM language WHERE uuid = ?)";
        // prepare the statement to be executed
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, p.getUniqueId().toString());
            stmt.setString(2, "de");
            stmt.setString(3, p.getUniqueId().toString());
            // console send if its done
            Bukkit.getConsoleSender().sendMessage("§aMYSQL§8: §7Player §e" + p.getName() + "§7 is now registered in the database!");
            // use executeUpdate() to update the databases table.
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
