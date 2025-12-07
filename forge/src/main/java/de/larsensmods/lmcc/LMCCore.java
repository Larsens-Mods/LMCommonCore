package de.larsensmods.lmcc;

import de.larsensmods.lmcc.platform.ForgeRegistryHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class LMCCore {

    public LMCCore() {
        Constants.LOG.info("Hello Forge world!");
        LMCCoreCommon.init();

        for(ForgeRegistryHelper helperInstance : ForgeRegistryHelper.getInstanceSet()){
            helperInstance.finishRegistration(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}
