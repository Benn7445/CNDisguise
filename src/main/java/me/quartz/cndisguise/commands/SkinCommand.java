package me.quartz.cndisguise.commands;

import me.quartz.cndisguise.CNDisguise;
import net.pinger.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SkinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.skin")) {
                String name = strings[0];
                DisguiseAPI.getDefaultProvider().updatePlayer(player, DisguiseAPI.getSkinManager().getFromMojang(name));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.skin"))
                                .replace("%player%", name)));
                CNDisguise.getInstance().skins.put(player.getUniqueId(), name);
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(strings.length >= 2) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.skin.other")) {
                String name = strings[0];
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null) {
                    String targetName = target.getName();
                    DisguiseAPI.getDefaultProvider().updatePlayer(target, DisguiseAPI.getSkinManager().getFromMojang(name));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.skin-other"))
                                    .replace("%target%", targetName)
                                    .replace("%player%", name)));
                    CNDisguise.getInstance().skins.put(target.getUniqueId(), name);
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else commandSender.sendMessage(ChatColor.RED + "Usage: /skin <name>");
        return true;
    }
}
