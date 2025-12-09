package de.larsensmods.lmcc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LMCCConstants.MOD_ID)
public class LMCCore {

    private static IEventBus modBus;

    public LMCCore(IEventBus eventBus) {
        modBus = eventBus;

        LMCCConstants.LOG.info("Hello NeoForge world!");
        LMCCoreCommon.init();
    }

    public static IEventBus getEventBus(){
        if(modBus == null){
            throw new IllegalStateException("Trying to use LMCC before it is initialised");
        }
        return modBus;
    }
}
