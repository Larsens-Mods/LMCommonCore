package de.larsensmods.lmcc.platform.services;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public interface IRegistryHelper {

    //General Registry Stuff
    <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID);

    //Common Entity Registry Stuff
    void registerToEntityAttributeRegistry(DeferredSupplier<? extends EntityType<? extends LivingEntity>> entityType, Supplier<AttributeSupplier> attributes);

    //Client Entity Registry Stuff

}
