package me.megapvp.gradualend.listener;

import me.megapvp.gradualend.GradualEndPlugin;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyListener implements Listener {

    private final GradualEndPlugin plugin;

    public ProxyListener(GradualEndPlugin plugin) {
        this.plugin = plugin;

        this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        // This event gets called prior to the count changing, so we deduct 1 from the count
        if (this.plugin.isEnding() && (this.plugin.getProxy().getOnlineCount() - 1) <= 0) {
            this.plugin.getLogger().info("All players have disconnected from the proxy, ending the instance...");
            this.plugin.getProxy().stop();
        }
    }
}
