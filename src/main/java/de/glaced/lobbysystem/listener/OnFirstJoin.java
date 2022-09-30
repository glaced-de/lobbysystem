package de.glaced.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        p.sendTitle("§7Willkommen (Neuling): §9" + p.getName(), "§7/tutorial, für Unklarheiten!", 20, 150, 20);
        p.playSound(p.getLocation(), "minecraft:entity.player.levelup", 2, 1);
        p.sendMessage("§8» ");
        p.sendMessage(MiniMessage.miniMessage().deserialize("§8» <gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient> §e§l§oINFO§8: §7If you are not a german player, please use §9§l/lang en §7to get a english server version!"));
        p.sendMessage("§8» ");
    }
}
