package de.larsensmods.lmcc.api;

import de.larsensmods.lmcc.platform.Services;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import org.jetbrains.annotations.NotNull;

public class LMCCRegistry {

    public static IRegistryHelper getRegistry(@NotNull String modID){
        return Services.REGISTRY.withModID(modID);
    }

}
