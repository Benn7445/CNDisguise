package me.quartz.cndisguise.commands;

import me.quartz.cndisguise.CNDisguise;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.rank")) {
                LuckPerms api = LuckPermsProvider.get();
                String rank = api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
                if(CNDisguise.getInstance().putRank(player.getUniqueId(), strings[0]) && CNDisguise.getInstance().getConfig().getStringList("ranks." + rank.toLowerCase()).stream().anyMatch(s1 -> s1.equalsIgnoreCase(strings[0]))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.rank"))
                                    .replace("%rank%", strings[0])));
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.rank-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else if(strings.length >= 2) {
            Player player = (Player) commandSender;
            if(player.hasPermission("cndisguise.rank.other")) {
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null) {
                    LuckPerms api = LuckPermsProvider.get();
                    String rank = api.getUserManager().getUser(target.getUniqueId()).getPrimaryGroup();
                    if(CNDisguise.getInstance().putRank(target.getUniqueId(), strings[0]) && CNDisguise.getInstance().getConfig().getStringList("ranks." + rank.toLowerCase()).stream().anyMatch(s1 -> s1.equalsIgnoreCase(strings[0]))) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.rank-other"))
                                        .replace("%target%", target.getName())
                                        .replace("%rank%", strings[0])));
                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.rank-not-found"))));
                } else if(strings[1].equalsIgnoreCase("all")) {
                    for(Player players : Bukkit.getOnlinePlayers()){
                        CNDisguise.getInstance().putRank(players.getUniqueId(), strings[0]);
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.rank-other"))
                                    .replace("%target%", "all")
                                    .replace("%rank%", strings[0])));
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.player-not-found"))));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(CNDisguise.getInstance().getConfig().getString("messages.no-permissions"))));
        } else commandSender.sendMessage(ChatColor.RED + "Usage: /rank <rank>");
        return true;
    }
}
