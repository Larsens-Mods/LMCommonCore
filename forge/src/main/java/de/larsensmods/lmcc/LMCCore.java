package de.larsensmods.lmcc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LMCCConstants.MOD_ID)
public class LMCCore {

    public LMCCore() {
        LMCCoreCommon.init();

        FMLJavaModLoadingContext.get().getModEventBus();
    }

}
