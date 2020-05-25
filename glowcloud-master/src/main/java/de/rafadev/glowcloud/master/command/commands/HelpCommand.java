package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 21.05.2020 at 11:50
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class HelpCommand extends Command {

    public HelpCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        GlowCloud.getGlowCloud().getLogger().info("All executable commands:");
        for (Command command : GlowCloud.getGlowCloud().getCommandManager().getCommandList()) {
            GlowCloud.getGlowCloud().getLogger().info("§8- §7" + command.getName() + (command.getUsage() != null ? " " + command.getUsage() : "") + " §8| §7"+ command.getDescription());
        }
    }

}
