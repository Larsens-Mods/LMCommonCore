package de.larsensmods.lmcc.api;

import de.larsensmods.lmcc.platform.Services;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import org.jetbrains.annotations.NotNull;

/**
 * Class for accessing things related to registration of things
 */
public class LMCCRegistry {

    /**
     * Obtain a registry object to register things to for your mod. This will handle most platform-specific specialties.
     * @param modID The ModID of the mod you are registering for
     * @return An {@link IRegistryHelper} instance for your ModID to register to
     */
    public static IRegistryHelper getRegistry(@NotNull String modID){
        return Services.REGISTRY.withModID(modID);
    }

}
