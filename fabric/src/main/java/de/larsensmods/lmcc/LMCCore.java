package de.larsensmods.lmcc;

import net.fabricmc.api.ModInitializer;

public class LMCCore implements ModInitializer {

    @Override
    public void onInitialize() {
        LMCCoreCommon.init();
    }
}
