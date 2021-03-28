package de.rafadev.glowcloud.modules.sign;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:40
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.module.CloudModule;
import de.rafadev.glowcloud.modules.sign.commands.SignCommand;
import de.rafadev.glowcloud.modules.sign.listener.WrapperConnectListener;

import java.util.UUID;

public class SignModule extends CloudModule {

    @Override
    public void onLoad() {
        GlowCloud.getGlowCloud().getCommandManager().registerCommand(new SignCommand("sign", null, "The command of the sign module"));
    }

    @Override
    public void onEnable() {

        GlowCloud.getGlowCloud().getModuleManager().getEventManager().register(new WrapperConnectListener(), this);

    }

    @Override
    public void onDisable() {

    }
}
