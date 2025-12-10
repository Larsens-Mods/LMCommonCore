package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.NeoForgeWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new NeoForgeWrappedRegister<>(modID, key);
    }

    @Override
    public void registerToEntityAttributeRegistry(DeferredSupplier<? extends EntityType<? extends LivingEntity>> entityType, Supplier<AttributeSupplier> attributes) {
        CommonModEventsHandler.attributes.put(entityType, attributes);
    }

    @EventBusSubscriber(modid = LMCCConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    private static class CommonModEventsHandler {

        static final Map<DeferredSupplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>> attributes = new HashMap<>();

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            LMCCConstants.LOG.info("Registering attributes for entity types");
            for(Map.Entry<DeferredSupplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>> entry : attributes.entrySet()){
                event.put(entry.getKey().get(), entry.getValue().get());
            }
        }
    }
}
