package de.larsensmods.lmcc.platform;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import de.larsensmods.lmcc.registry.ForgeSupplier;
import de.larsensmods.lmcc.registry.IWrappedRegister;
import de.larsensmods.lmcc.platform.services.IRegistryHelper;
import de.larsensmods.lmcc.registry.ForgeWrappedRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {

    @Override
    public <R> IWrappedRegister<R> getWrappedRegister(ResourceKey<Registry<R>> key, String modID) {
        return new ForgeWrappedRegister<>(modID, key);
    }

    @Override
    public void registerToEntityAttributeRegistry(DeferredSupplier<? extends EntityType<? extends LivingEntity>> entityType, Supplier<AttributeSupplier> attributes) {
        CommonModEventsHandler.attributes.put(entityType, attributes);
    }

    @Mod.EventBusSubscriber(modid = LMCCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
