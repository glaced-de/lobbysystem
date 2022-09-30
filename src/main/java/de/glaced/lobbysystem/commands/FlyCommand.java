package de.glaced.lobbysystem.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player =(Player) sender;
        // glaced gradient prefix
        Component parsed = MiniMessage.miniMessage().deserialize("<gradient:#e583fe:#a351fe:#c2cef6>Glaced</gradient>");
        if (player.hasPermission("lobby.fly")) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(parsed + "§cDu kannst nun nicht mehr fliegen!");
                player.sendTitle("§c§lFLY", "§7Du kannst nun nicht mehr fliegen!", 20, 150, 20);
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(parsed + "§aDu kannst nun fliegen!");
                player.sendTitle("§a§lFLY", "§7Du kannst nun fliegen!", 20, 150, 20);
            }
        } else {
            player.sendMessage(parsed + "§cDu hast keine Rechte um diesen Befehl auszuführen!");
        }
        return false;
    }
}
