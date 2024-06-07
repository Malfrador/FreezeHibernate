package de.malfrador.freezehibernate;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.ServerTickManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FreezeHibernate extends JavaPlugin implements Listener {

    private final ServerTickManager serverTickManager = Bukkit.getServerTickManager();
    private final Server server = Bukkit.getServer();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

   @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        if (server.getOnlinePlayers().size() == 1) {
            serverTickManager.setFrozen(true);
            getLogger().info("Last player disconnected. Server is now frozen.");
        }
   }

   @EventHandler
   private void onJoin(PlayerJoinEvent event){
         if (serverTickManager.isFrozen()) {
              serverTickManager.setFrozen(false);
              getLogger().info("A player joined. Server is now unfrozen.");
         }
   }

   @EventHandler
    private void onStartComplete(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            return;
        }
        if (server.getOnlinePlayers().isEmpty()) {
            serverTickManager.setFrozen(true);
            getLogger().info("Server is frozen until a player joins.");
        }
    }
}
