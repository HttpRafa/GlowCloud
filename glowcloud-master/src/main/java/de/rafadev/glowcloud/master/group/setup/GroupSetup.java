package de.rafadev.glowcloud.master.group.setup;

//------------------------------
//
// This class was developed by Rafael K.
// On 30.05.2020 at 13:02
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.master.main.GlowCloud;
import javafx.scene.effect.Glow;

public class GroupSetup {

    private int step = -1;
    private GroupSetupResult groupSetupResult;

    public GroupSetup() {
        groupSetupResult = new GroupSetupResult();
        step = 0;
        GlowCloud.getGlowCloud().getLogger().info("Please enter the name of the group.");
    }

    public void next(Object object) {
        if(step == 0) {
            groupSetupResult.setName(object.toString());
            GlowCloud.getGlowCloud().getLogger().info("Please enter the wrapper for the group.");
        } else if(step == 1) {
            groupSetupResult.setWrapperID(object.toString());
            GlowCloud.getGlowCloud().getLogger().info("How much ram should a server have?");
        } else if(step == 2) {
            try {
                groupSetupResult.setMemory(Integer.parseInt(object.toString()));
                GlowCloud.getGlowCloud().getLogger().info("How many servers should always be online?");
            } catch (NumberFormatException exception) {
                GlowCloud.getGlowCloud().getLogger().error("Your input is not a number.");
                return;
            }
        } else if(step == 3) {
            try {
                groupSetupResult.setMinServerCount(Integer.parseInt(object.toString()));
                GlowCloud.getGlowCloud().getLogger().info("Should the server be STATIC or DYNAMIC?");
            } catch (NumberFormatException exception) {
                GlowCloud.getGlowCloud().getLogger().error("Your input is not a number.");
                return;
            }
        } else if(step == 4) {
            try {
                groupSetupResult.setGroupMode(GroupMode.valueOf(object.toString()));
                GlowCloud.getGlowCloud().getLogger().info("Is the server a BUKKIT or a BUNGEECORD server?");
            } catch (Exception ex) {
                GlowCloud.getGlowCloud().getLogger().error("What you have entered is not available.");
                return;
            }
        } else if(step == 5) {
            try {
                groupSetupResult.setServerType(ServerType.valueOf(object.toString()));
                GlowCloud.getGlowCloud().getLogger().info("The setup is finished.");
                step = -1;
                GlowCloud.getGlowCloud().getGroupManager().createGroup(groupSetupResult);
                GlowCloud.getGlowCloud().getGroupManager().setGroupSetup(null);
            } catch (Exception ex) {
                GlowCloud.getGlowCloud().getLogger().error("What you have entered is not available.");
                return;
            }
        } else {
            step = -1;
        }
        step++;
    }

    public GroupSetupResult getGroupSetupResult() {
        return groupSetupResult;
    }

    public int getStep() {
        return step;
    }
}
