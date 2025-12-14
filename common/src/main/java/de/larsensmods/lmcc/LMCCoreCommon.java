package de.larsensmods.lmcc;

import de.larsensmods.lmcc.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class LMCCoreCommon {

    public static void init() {
        /*LMCCConstants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        LMCCConstants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));*/

        if (Services.PLATFORM.isModLoaded("lmcc")) {
            LMCCConstants.LOG.info("Loading LMCommonCore mod");
        }
    }
}
