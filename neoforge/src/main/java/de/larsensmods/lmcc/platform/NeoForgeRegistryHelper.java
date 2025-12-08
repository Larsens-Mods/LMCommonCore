package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.api.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.NeoForgeWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new NeoForgeWrappedRegister<>(modID, key);
    }
}
