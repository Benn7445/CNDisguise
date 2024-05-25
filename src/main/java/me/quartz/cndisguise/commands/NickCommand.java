package me.quartz.cndisguise.commands;

import me.quartz.cndisguise.CNDisguise;
import net.pinger.disguise.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.nick")) {
                String name = strings[0];
                DisguiseAPI.getDefaultProvider().updatePlayer(player, DisguiseAPI.getSkinManager().getFromMojang(name), name);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.disguise"))
                                .replace("%player%", name)));
                CNDisguise.getInstance().names.put(player.getUniqueId(), name);
                CNDisguise.getInstance().skins.put(player.getUniqueId(), name);
                CNDisguise.getInstance().putRank(player.getUniqueId(), CNDisguise.getInstance().getConfig().getString("default-rank"));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(strings.length >= 2) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.nick.other")) {
                String name = strings[0];
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null) {
                    String targetName = target.getName();
                    DisguiseAPI.getDefaultProvider().updatePlayer(target, DisguiseAPI.getSkinManager().getFromMojang(name), name);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.disguise-other"))
                                    .replace("%target%", targetName)
                                    .replace("%player%", name)));

                    CNDisguise.getInstance().names.put(target.getUniqueId(), name);
                    CNDisguise.getInstance().skins.put(target.getUniqueId(), name);
                    CNDisguise.getInstance().putRank(target.getUniqueId(), CNDisguise.getInstance().getConfig().getString("default-rank"));
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.nick")) {
                List<String> names = CNDisguise.getInstance().getConfig().getStringList("random-names");
                String randomName = names.get(new Random().nextInt(names.size() - 1));
                DisguiseAPI.getDefaultProvider().updatePlayer(player, DisguiseAPI.getSkinManager().getFromMojang(randomName), randomName);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.disguise"))
                                .replace("%player%", randomName)));

                CNDisguise.getInstance().putRank(player.getUniqueId(), CNDisguise.getInstance().getConfig().getString("default-rank"));
            } else commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else commandSender.sendMessage(ChatColor.RED + "Usage: /nick <name> <player>");
        return true;
    }
}
