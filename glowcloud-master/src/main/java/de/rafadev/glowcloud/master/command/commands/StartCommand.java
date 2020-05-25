package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 21.05.2020 at 12:07
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class StartCommand extends Command {

    public StartCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            GlowCloud.getGlowCloud().getLogger().info("§7start §8<§egroup§8> <§eamount§8>");
        }
    }
}
