package de.rafadev.glowcloud.lib.logging.exeption;

//------------------------------
//
// This class was developed by RafaDev
// On 24.05.2020 at 14:31
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudHandler;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;

public class ExceptionHandler implements IGlowCloudObject, IGlowCloudHandler {

    private CloudLogger cloudLogger;

    public ExceptionHandler(CloudLogger cloudLogger) {
        this.cloudLogger = cloudLogger;
    }

    @Override
    public void handle(Object object) {
        Exception exception = (Exception) object;
        System.out.println(ConsoleColor.toColouredString('§', "\n§cAn Exception has occurred§8: §4" + exception.getMessage()));
        System.out.println(ConsoleColor.toColouredString('§', "§c"));
        exception.printStackTrace();
        System.out.println(ConsoleColor.toColouredString('§', "§r"));
        cloudLogger.error("This error is not intentional");
    }
}
