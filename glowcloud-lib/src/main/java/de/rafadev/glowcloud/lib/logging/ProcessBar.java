package de.rafadev.glowcloud.lib.logging;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.06.2020 at 14:46
// In the project GlowCloud
//
//------------------------------

public class ProcessBar {

    public ProcessBar(CloudLogger logger, int perSecond, long end, long state) {

        long portent = state * 100/end;

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 100; i += 3) {
            if(i < portent) {
               if(portent >= 100) {
                    result.append("§a»");
                } else {
                    result.append("§e»");
                }
            } else {
                result.append("§7«");
            }
        }

        if(portent > 100) {
            logger.overrideLine(1, "100 % §7" + result.toString() + " §e" + (state / 1000) + "KB §8/ §7" + (state / 1000) + "KB §8| §e" + perSecond + "KB/s");
        } else {
            String s1 = portent > 9 ? portent + "" : portent + " ";
            if(portent == 100) {
                logger.overrideLine(1, s1 + " % §7" + result.toString() + " §e" + (state / 1000) + "KB §8/ §7" + (state / 1000) + "KB §8| §e" + perSecond + "KB/s");
            } else
                logger.overrideLine(1, s1 + " % §7" + result.toString() + " §e" + (state / 1000) + "KB §8/ §7" + (end / 1000) + "KB §8| §e" + perSecond + "KB/s");
        }

    }

}
