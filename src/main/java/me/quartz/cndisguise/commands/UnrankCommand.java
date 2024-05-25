package me.quartz.cndisguise.commands;

import me.quartz.cndisguise.CNDisguise;
import net.pinger.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class UnrankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.rank")) {
                CNDisguise.getInstance().resetRank(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.unrank"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(strings.length >= 1) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.rank.other")) {
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null) {
                    String targetName = target.getName();
                    CNDisguise.getInstance().resetRank(target.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.unrank-other"))
                                    .replace("%target%", targetName)));
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        }
        return true;
    }
}
