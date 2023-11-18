package me.megapvp.gradualend.command;

import com.velocitypowered.api.command.SimpleCommand;
import me.megapvp.gradualend.GradualEndPlugin;
import net.kyori.adventure.text.Component;

public class GradualEndCommand implements SimpleCommand {

    private final GradualEndPlugin plugin;

    public GradualEndCommand(GradualEndPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxyServer().getCommandManager().register("gradualend", this, "gradualstop", "gstop", "gend", "slowend", "slowstop");
    }

    @Override
    public void execute(final Invocation invocation) {
        if (this.plugin.isEnding()) {
            invocation.source().sendMessage(Component.text("The proxy is already in the process of gradually rebooting and will finish once all players have disconnected, to force a shutdown use /end"));
        } else {
            this.plugin.setEnding(true);
            this.plugin.getProxyServer().closeListeners();
            invocation.source().sendMessage(Component.text("The proxy will now gradually end and stop listening for new connections on this port, you may start a new instance."));
        }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("velocity.command.end");
    }
}
