package me.quartz.cndisguise.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.quartz.cndisguise.CNDisguise;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PapiHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "cndisguise";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Bennis";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("rank")) {
            Group group = CNDisguise.getInstance().getRank(player.getUniqueId());
            if(group != null) return group.getCachedData().getMetaData().getPrefix();

            LuckPerms api = LuckPermsProvider.get();
            String prefix = api.getGroupManager().getGroup(api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup()).getCachedData().getMetaData().getPrefix();

            return prefix;
        }
        return null;
    }
}
