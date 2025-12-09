package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.ForgeWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ForgeRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new ForgeWrappedRegister<>(modID, key);
    }
}
