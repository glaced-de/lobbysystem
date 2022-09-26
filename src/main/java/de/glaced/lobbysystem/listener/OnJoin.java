package de.glaced.lobbysystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }
}
