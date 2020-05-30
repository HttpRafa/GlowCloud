package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 10:57
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.group.setup.GroupSetup;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class CreateCommand extends Command {

    public CreateCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1 && args[0].equalsIgnoreCase("ServerGroup")) {
            GroupSetup groupSetup = new GroupSetup();
            GlowCloud.getGlowCloud().getGroupManager().setGroupSetup(groupSetup);
        } else {
            GlowCloud.getGlowCloud().getLogger().info(getName() + " " + getUsage());
        }
    }
}
