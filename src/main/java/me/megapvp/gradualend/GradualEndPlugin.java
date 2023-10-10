package me.megapvp.gradualend;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.megapvp.gradualend.command.GradualEndCommand;
import me.megapvp.gradualend.listener.ProxyListener;

import java.lang.reflect.Field;
import java.util.logging.Logger;

@Plugin(id = "gradual-end")
public class GradualEndPlugin {

    @Getter
    private final ProxyServer proxyServer;
    @Getter
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

    @Getter @Setter
    private boolean ending = false;

    private void initializeCommands() {
        new GradualEndCommand(this);
    }

    private void initializeListeners() {
        new ProxyListener(this);
    }

    @SneakyThrows
    public void shutdownConnectionManager() {
        try {
            Field connectionManagerField = getProxyServer().getClass().getDeclaredField("cm");

            if (!connectionManagerField.canAccess(getProxyServer())) {
                connectionManagerField.setAccessible(true);
            }

            Object connectionManager = connectionManagerField.get(getProxyServer());
            connectionManager.getClass().getMethod("shutdown").invoke(connectionManager);

            getLogger().info("Successfully shut down proxy connections!");
        } catch (NoSuchFieldException e) {
            getLogger().info("No field found for ConnectionManager in Velocity, unable to run.");
        } catch (NoSuchMethodException e) {
            getLogger().info("No method found for shutdown() in ConnectionManager, unable to run.");
        }
    }
}