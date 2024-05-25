package me.quartz.cndisguise;

import me.quartz.cndisguise.commands.*;
import me.quartz.cndisguise.listeners.PlayerJoinListener;
import me.quartz.cndisguise.papi.PapiHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class CNDisguise extends JavaPlugin {

    private static CNDisguise instance;
    private final HashMap<UUID, String> ranks = new HashMap<>();
    public final HashMap<UUID, String> names = new HashMap<>();
    public final HashMap<UUID, String> skins = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        registerListeners();
        registerPapi();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("skin").setExecutor(new SkinCommand());
        getCommand("unnick").setExecutor(new UnnickCommand());
        getCommand("unskin").setExecutor(new UnskinCommand());
        getCommand("reveal").setExecutor(new RevealCommand());
        getCommand("rank").setExecutor(new RankCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void registerPapi() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiHook().register();
        }
    }

    public static CNDisguise getInstance() {
        return instance;
    }

    public Group getRank(UUID uuid) {
        LuckPerms api = LuckPermsProvider.get();
        return api.getGroupManager().getGroup(ranks.get(uuid));
    }

    public boolean putRank(UUID uuid, String rank) {
        LuckPerms api = LuckPermsProvider.get();
        if(api.getGroupManager().getGroup(rank) != null) {
            ranks.put(uuid, rank);
            return true;
        }
        return false;
    }

    public void resetRank(UUID uuid) {
        ranks.remove(uuid);
    }
}
