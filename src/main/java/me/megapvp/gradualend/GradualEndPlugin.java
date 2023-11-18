package me.megapvp.gradualend;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.Setter;
import me.megapvp.gradualend.command.GradualEndCommand;
import me.megapvp.gradualend.listener.ProxyListener;

import java.util.logging.Logger;

@Getter
@Plugin(id = "gradual-end")
public class GradualEndPlugin {

    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public GradualEndPlugin(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        long startTime = System.currentTimeMillis();

        initializeCommands();
        initializeListeners();

        getLogger().info("Plugin initialized in ~" + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Setter
    private boolean ending = false;

    private void initializeCommands() {
        new GradualEndCommand(this);
    }

    private void initializeListeners() {
        new ProxyListener(this);
    }
}