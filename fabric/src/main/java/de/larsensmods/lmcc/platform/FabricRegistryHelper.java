package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.FabricWrappedRegister;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new FabricWrappedRegister<>(modID, key);
    }

    @Override
    public void registerToEntityAttributeRegistry(DeferredSupplier<? extends EntityType<? extends LivingEntity>> entityType, Supplier<AttributeSupplier> attributes) {
        FabricDefaultAttributeRegistry.register(entityType.get(), attributes.get());
    }
}
