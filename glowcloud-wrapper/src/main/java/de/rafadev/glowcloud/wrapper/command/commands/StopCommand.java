package de.rafadev.glowcloud.wrapper.command.commands;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.06.2020 at 16:09
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.wrapper.command.executer.Command;

public class StopCommand extends Command {

    public StopCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {

        System.exit(0);

    }
}
