package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 20.05.2020 at 23:28
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class StopCommand extends Command {


    public StopCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        GlowCloud.getGlowCloud().shutdown();
    }

}
