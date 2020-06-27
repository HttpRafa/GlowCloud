package de.rafadev.glowcloud.wrapper.command.executer;

//------------------------------
//
// This class was developed by RafaDev
// On 20.05.2020 at 23:15
// In the project GlowCloud
//
//------------------------------

public class Command {

    private String name;
    private String usage;
    private String description;

    public Command(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    public void execute(String[] args) {

    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }
}
