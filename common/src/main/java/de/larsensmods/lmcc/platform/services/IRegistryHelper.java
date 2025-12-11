package de.larsensmods.lmcc.platform.services;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public interface IRegistryHelper {

    //General Registry Stuff
    <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID);

    //Common Entity Registry Stuff
    <T extends LivingEntity> void registerToEntityAttributeRegistry(DeferredSupplier<EntityType<T>> entityType, Supplier<AttributeSupplier> attributes);
    <T extends Mob> void registerToSpawnPlacementsRegistry(DeferredSupplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate);

    //Client Entity Registry Stuff

}
