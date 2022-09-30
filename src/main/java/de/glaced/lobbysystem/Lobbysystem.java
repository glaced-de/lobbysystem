package de.glaced.lobbysystem;

import de.glaced.lobbysystem.commands.FlyCommand;
import de.glaced.lobbysystem.commands.LangCommand;
import de.glaced.lobbysystem.listener.OnFirstJoin;
import de.glaced.lobbysystem.listener.OnJoin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Lobbysystem extends JavaPlugin {
    private static Lobbysystem instance;
    final String username="root"; // Enter in your db username
    final String password=""; // Enter your password for the db
    final String url = "jdbc:mysql://localhost:3306/glaced"; // Enter URL with db name

    //Connection vars
    public static Connection connection; //This is the variable we will use to connect to database


    @Override
    public void onEnable() {
        // mysql
        try {
            connection = DriverManager.getConnection(url, username, password);
            Bukkit.getConsoleSender().sendMessage("§aMySQL connected!");
        } catch (SQLException e) { // catching errors
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
        }

        // string sql when its not exist create glaced database with table playerdata and create the columns uuid, name, language
        String sql = "CREATE TABLE IF NOT EXISTS language (uuid VARCHAR(100), language VARCHAR(100))";
        // prepare the statement to be executed
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            // use executeUpdate() to update the databases table.
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Plugin startup logic
        // send message to console
        Bukkit.getConsoleSender().sendMessage("§aLobbysystem wurde erfolgreich geladen!");
        instance = this;
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnFirstJoin(), this);

        // cmds
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("lang").setExecutor(new LangCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try { // using a try catch to catch connection errors (like wrong sql password...)
            if (connection!=null && !connection.isClosed()){ // checking if connection isn't null to
                // avoid receiving a nullpointer
                connection.close(); // closing the connection field variable.
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Lobbysystem getInstance() {
        return instance;
    }
}
