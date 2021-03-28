package de.rafadev.glowcloud.master.command.commands;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 11:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.command.executer.Command;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.server.seletor.CloudServerGroupSelector;
import de.rafadev.glowcloud.lib.classes.selector.selectors.CloudStringSelector;
import de.rafadev.glowcloud.master.wrapper.classes.ConnectedCloudWrapper;

import java.util.LinkedList;
import java.util.List;

public class ShutdownCommand extends Command {

    public ShutdownCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    private List<String> blocked = new LinkedList<>();

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {

            GlowCloud.getGlowCloud().getLogger().info("§7shutdown §8<§eServer§8/§eGroup§8/§eWrapper§8> <§eName§8>");

        } else if(args.length == 2 && args[0].equalsIgnoreCase("server")) {

            if(!blocked.contains(args[1])) {

                blocked.add(args[1]);
                GlowCloud.getGlowCloud().getScheduler().runWaitTask(new Runnable() {
                    @Override
                    public void run() {
                        blocked.remove(args[1]);
                    }
                }, 1500);

                if (GlowCloud.getGlowCloud().getServerManager().get(args[1]) != null) {

                    GlowCloud.getGlowCloud().getServerManager().stopServer(new CloudStringSelector(args[1]));

                } else {
                    GlowCloud.getGlowCloud().getLogger().error("The selected server is not registered§8.");
                }

            } else {
                GlowCloud.getGlowCloud().getLogger().error("Please stop spamming the shutdown command§8.");
            }
        } else if(args.length == 2 && args[0].equalsIgnoreCase("group")) {

            if(GlowCloud.getGlowCloud().getGroupManager().get(args[1]) != null) {

                if(GlowCloud.getGlowCloud().getServerManager().search(new CloudServerGroupSelector(GlowCloud.getGlowCloud().getGroupManager().get(args[1]))).size() > 0) {

                    GlowCloud.getGlowCloud().getServerManager().stopServer(new CloudServerGroupSelector(GlowCloud.getGlowCloud().getGroupManager().get(args[1])));

                } else {
                    GlowCloud.getGlowCloud().getLogger().error("No started server on the group found§8.");
                }

            } else {
                GlowCloud.getGlowCloud().getLogger().error("The selected servergroup is not registered§8.");
            }

        } else if(args.length == 2 && args[0].equalsIgnoreCase("wrapper")) {

            if(GlowCloud.getGlowCloud().getWrapperManager().search(args[1]) != null && GlowCloud.getGlowCloud().getWrapperManager().search(args[1]) instanceof ConnectedCloudWrapper) {
                GlowCloud.getGlowCloud().getWrapperManager().requestShutdown((ConnectedCloudWrapper) GlowCloud.getGlowCloud().getWrapperManager().search(args[1]));
            } else {
                GlowCloud.getGlowCloud().getLogger().error("The selected wrapper is not online or registered§8.");
            }

        }
    }
}
