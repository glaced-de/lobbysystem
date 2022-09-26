package de.glaced.lobbysystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnFirstJoin implements Listener {
    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore()) {
            return;
        }
        p.sendTitle("§eWillkommen", "§7auf dem §e§o§lGlaced §7Server!");
    }
}
