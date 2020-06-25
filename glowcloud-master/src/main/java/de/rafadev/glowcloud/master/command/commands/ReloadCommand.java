package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:45
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class ReloadCommand extends Command {

    public ReloadCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            GlowCloud.getGlowCloud().getLogger().info("§7" + getName() + " §8" + getUsage());
        } else if(args.length == 1 && args[0].equalsIgnoreCase("all")) {
            GlowCloud.getGlowCloud().reloadAll();
        }
    }
}
