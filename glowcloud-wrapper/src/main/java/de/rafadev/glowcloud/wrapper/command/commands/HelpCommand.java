package de.rafadev.glowcloud.wrapper.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 21.05.2020 at 11:50
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.wrapper.command.executer.Command;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;

public class HelpCommand extends Command {

    public HelpCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        GlowCloudWrapper.getGlowCloud().getLogger().info("All executable commands:");
        for (Command command : GlowCloudWrapper.getGlowCloud().getCommandManager().getCommandList()) {
            GlowCloudWrapper.getGlowCloud().getLogger().info("§8- §7" + command.getName() + (command.getUsage() != null ? " " + command.getUsage() : "") + " §8| §7"+ command.getDescription());
        }
    }

}
