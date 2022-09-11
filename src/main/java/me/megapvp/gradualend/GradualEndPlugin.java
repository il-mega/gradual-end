package me.megapvp.gradualend;

import lombok.Getter;
import lombok.Setter;
import me.megapvp.gradualend.command.GradualEndCommand;
import me.megapvp.gradualend.listener.ProxyListener;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class GradualEndPlugin extends Plugin implements Listener {

    @Getter @Setter
    private boolean ending = false;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        initializeCommands();
        initializeListeners();

        getLogger().info("Plugin initialized in ~" + (System.currentTimeMillis() - startTime) + " ms");
    }

    private void initializeCommands() {
        new GradualEndCommand(this);
    }

    private void initializeListeners() {
        new ProxyListener(this);
    }

}