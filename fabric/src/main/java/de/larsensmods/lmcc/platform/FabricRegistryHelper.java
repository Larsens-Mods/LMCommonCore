package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.FabricWrappedRegister;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new FabricWrappedRegister<>(modID, key);
    }

    @Override
    public <T extends LivingEntity> void registerToEntityAttributeRegistry(DeferredSupplier<EntityType<T>> entityType, Supplier<AttributeSupplier> attributes) {
        FabricDefaultAttributeRegistry.register(entityType.get(), attributes.get());
    }

    @Override
    public <T extends Mob> void registerToSpawnPlacementsRegistry(DeferredSupplier<EntityType<T>> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType.get(), spawnPlacementType, heightmapType, predicate);
    }
}
