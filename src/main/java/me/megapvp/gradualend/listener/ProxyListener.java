package me.megapvp.gradualend.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import me.megapvp.gradualend.GradualEndPlugin;

public class ProxyListener {

    private final GradualEndPlugin plugin;

    public ProxyListener(GradualEndPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxyServer().getEventManager().register(plugin, this);
    }

    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent event) {
        // This event gets called prior to the count changing, so we deduct 1 from the count
        if (this.plugin.isEnding() && (this.plugin.getProxyServer().getPlayerCount() - 1) <= 0) {
            this.plugin.getLogger().info("All players have disconnected from the proxy, ending the instance...");
            this.plugin.getProxyServer().shutdown();
        }
    }
}
