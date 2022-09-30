package de.glaced.lobbysystem.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class LangCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        final String username="root"; // Enter in your db username
        final String password=""; // Enter your password for the db
        final String url = "jdbc:mysql://localhost:3306/glaced"; // Enter URL with db name
        if (args.length == 0) {
            final Component error_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <red>Bitte benutze: <bold>/lang <de/en></bold></red>");
            player.sendMessage(error_message);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("de")) {
                try {
                    Connection con = DriverManager.getConnection(url, username, password);
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `language` WHERE `uuid` = '" + player.getUniqueId() + "'");
                    if (rs.next()) {
                        st.executeUpdate("UPDATE `language` SET `language` = 'de' WHERE `uuid` = '" + player.getUniqueId() + "'");
                        final Component success_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <green>Deine Sprache wurde erfolgreich auf Deutsch geändert!</green>");
                        player.sendMessage(success_message);
                    } else {
                        st.executeUpdate("INSERT INTO `language` (`uuid`, `language`) VALUES ('" + player.getUniqueId() + "', 'de')");
                        final Component success_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <green>Deine Sprache wurde erfolgreich auf Deutsch geändert!</green>");
                        player.sendMessage(success_message);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("en")) {
                try {
                    Connection con = DriverManager.getConnection(url, username, password);
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `language` WHERE `uuid` = '" + player.getUniqueId() + "'");
                    if (rs.next()) {
                        st.executeUpdate("UPDATE `language` SET `language` = 'en' WHERE `uuid` = '" + player.getUniqueId() + "'");
                        final Component success_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <green>Your language has been successfully changed to English!</green>");
                        player.sendMessage(success_message);
                    } else {
                        st.executeUpdate("INSERT INTO `language` (`uuid`, `language`) VALUES ('" + player.getUniqueId() + "', 'en')");
                        final Component success_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <green>Your language has been successfully changed to English!</green>");
                        player.sendMessage(success_message);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                final Component error_message = MiniMessage.miniMessage().deserialize("<italic><bold><gray>» </bold></italic><bold><gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient></bold> <gray>|</gray> <red>Bitte benutze: <bold>/lang <de/en></bold></red>");
                player.sendMessage(error_message);
            }
        }
        return false;
    }
}
