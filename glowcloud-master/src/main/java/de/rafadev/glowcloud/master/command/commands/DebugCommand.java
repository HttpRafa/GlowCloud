package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by Rafael K.
// On 01.06.2020 at 19:38
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;

public class DebugCommand extends Command {

    public DebugCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public void execute(String[] args) {
        if(!GlowCloud.getGlowCloud().getLogger().isDebugging()) {
            GlowCloud.getGlowCloud().getLogger().setDebugging(true);
            GlowCloud.getGlowCloud().getLogger().debug("The debug mode is now §aenabled§8.");
        } else {
            GlowCloud.getGlowCloud().getLogger().debug("The debug mode is now §cdisabled§8.");
            GlowCloud.getGlowCloud().getLogger().setDebugging(false);
        }
    }
}
