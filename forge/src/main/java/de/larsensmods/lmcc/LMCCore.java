package de.larsensmods.lmcc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class LMCCore {

    public LMCCore() {
        Constants.LOG.info("Hello Forge world!");
        LMCCoreCommon.init();

        FMLJavaModLoadingContext.get().getModEventBus();
    }

}
