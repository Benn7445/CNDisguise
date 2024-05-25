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

public class UnnickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.nick")) {
                DisguiseAPI.getDefaultProvider().resetPlayer(player);
                CNDisguise.getInstance().resetRank(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.unnick"))));
                CNDisguise.getInstance().names.remove(player.getUniqueId());
                CNDisguise.getInstance().skins.remove(player.getUniqueId());
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(strings.length >= 1) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.nick.other")) {
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null) {
                    String targetName = target.getName();
                    DisguiseAPI.getDefaultProvider().resetPlayer(target);
                    CNDisguise.getInstance().resetRank(target.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.unnick-other"))
                                    .replace("%target%", targetName)));
                    CNDisguise.getInstance().names.remove(target.getUniqueId());
                    CNDisguise.getInstance().skins.remove(target.getUniqueId());
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        }
        return true;
    }
}
