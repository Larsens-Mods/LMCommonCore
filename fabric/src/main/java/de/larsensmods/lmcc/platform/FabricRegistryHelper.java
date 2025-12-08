package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.api.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.FabricWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new FabricWrappedRegister<>(modID, key);
    }
}
