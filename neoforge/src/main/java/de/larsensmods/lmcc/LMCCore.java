package de.larsensmods.lmcc;


import de.larsensmods.lmcc.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class LMCCore {

    public LMCCore(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        LMCCoreCommon.init();

        for(NeoForgeRegistryHelper helperInstance : NeoForgeRegistryHelper.getInstanceSet()){
            helperInstance.finishRegistration(eventBus);
        }
    }
}
