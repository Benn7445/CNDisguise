package me.quartz.cndisguise.listeners;

import me.quartz.cndisguise.CNDisguise;
import net.pinger.disguise.DisguiseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        if(CNDisguise.getInstance().names.containsKey(event.getPlayer().getUniqueId()) && CNDisguise.getInstance().skins.containsKey(event.getPlayer().getUniqueId()))
            DisguiseAPI.getDefaultProvider().updatePlayer(event.getPlayer(), DisguiseAPI.getSkinManager().getFromMojang(CNDisguise.getInstance().skins.get(event.getPlayer().getUniqueId())), CNDisguise.getInstance().names.get(event.getPlayer().getUniqueId()));
        else if(CNDisguise.getInstance().names.containsKey(event.getPlayer().getUniqueId()))
            DisguiseAPI.getDefaultProvider().updatePlayer(event.getPlayer(), DisguiseAPI.getSkinManager().getFromMojang(event.getPlayer().getName()), CNDisguise.getInstance().names.get(event.getPlayer().getUniqueId()));
        else if(CNDisguise.getInstance().skins.containsKey(event.getPlayer().getUniqueId()))
            DisguiseAPI.getDefaultProvider().updatePlayer(event.getPlayer(), DisguiseAPI.getSkinManager().getFromMojang(event.getPlayer().getName()));
    }
}
