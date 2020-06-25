package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 11:11
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class ClearCommand extends Command {

    public ClearCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        GlowCloud.getGlowCloud().getLogger().clearConsole();
    }
}
