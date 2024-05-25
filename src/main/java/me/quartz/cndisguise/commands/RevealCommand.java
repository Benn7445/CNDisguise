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

public class RevealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            if(commandSender.hasPermission("cndisguise.reveal")) {
                Player player = Bukkit.getPlayer(strings[0]);
                if(player != null) {
                    String name = strings[0];
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.reveal"))
                                    .replace("%player%", name)
                                    .replace("%name%", DisguiseAPI.getPlayerManager().getDisguisePlayer(player).getDefaultName())));
                } else commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else commandSender.sendMessage(ChatColor.RED + "Usage: /reveal <player>");
        return true;
    }
}
