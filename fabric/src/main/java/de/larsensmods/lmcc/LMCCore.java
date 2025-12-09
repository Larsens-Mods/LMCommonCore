package de.larsensmods.lmcc;

import net.fabricmc.api.ModInitializer;

public class LMCCore implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        LMCCConstants.LOG.info("Hello Fabric world!");
        LMCCoreCommon.init();
    }
}
