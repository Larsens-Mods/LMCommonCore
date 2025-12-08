package de.larsensmods.lmcc.platform.services;

import de.larsensmods.lmcc.api.registry.IWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface IRegistryHelper {

    <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID);

}
