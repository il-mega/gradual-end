package me.megapvp.gradualend.command;

import me.megapvp.gradualend.GradualEndPlugin;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class GradualEndCommand extends Command {

    private final GradualEndPlugin plugin;

    public GradualEndCommand(GradualEndPlugin plugin) {
        super("gradualend", "bungeecord.command.end", "gradualstop", "gstop", "gend", "slowend", "slowstop");

        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerCommand(plugin, this);
    }

    public void execute(CommandSender commandSender, String[] strings) {
        if (this.plugin.isEnding()) {
            commandSender.sendMessage("The proxy is already in the process of gradually rebooting and will finish once all players have disconnected, to force a shutdown use /end");
        } else {
            this.plugin.setEnding(true);
            BungeeCord.getInstance().stopListeners();
            commandSender.sendMessage("The proxy will now gradually end and stop listening for new connections on this port, you may start a new instance.");
        }
    }
}
