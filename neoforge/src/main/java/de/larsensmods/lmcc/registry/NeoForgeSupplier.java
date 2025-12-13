package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeSupplier<O, T extends O> implements DeferredSupplier<T> {

    private static final Set<NeoForgeSupplier<?, ?>> SUPPLIERS = new HashSet<>();
    private static boolean REGISTERED = false;

    private final Supplier<T> deferredHolder;
    private final Set<Consumer<T>> consumers = new HashSet<>();

    private final Registry<O> registry;
    private final ResourceLocation id;
    private Holder<T> holder;

    public NeoForgeSupplier(Supplier<T> deferredHolder, Registry<O> registry, ResourceLocation id) {
        this.deferredHolder = deferredHolder;
        this.registry = registry;
        this.id = id;

        SUPPLIERS.add(this);
    }

    @Override
    public void onRegistration(Consumer<T> callback) {
        if(REGISTERED) {
            callback.accept(this.deferredHolder.get());
        }else{
            this.consumers.add(callback);
        }
    }

    @Override
    public T get() {
        return deferredHolder.get();
    }

    @Override
    public boolean isPresent() {
        return REGISTERED;
    }

    @Override
    public ResourceLocation getRegistryId() {
        return this.registry.key().location();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @Nullable Holder<T> getHolder() {
        if (this.holder != null) {
            return this.holder;
        }
        Holder<O> tmpHolder = this.registry.getHolder(this.id).orElse(null);
        return this.holder = (Holder<T>) tmpHolder;
    }

    private void acceptConsumers() {
        for(Consumer<T> consumer : this.consumers) {
            consumer.accept(this.deferredHolder.get());
        }
    }

    @EventBusSubscriber(modid = LMCCConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    private static class CommonHandler {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            REGISTERED = true;
            for(NeoForgeSupplier<?, ?> supplier : SUPPLIERS) {
                supplier.acceptConsumers();
            }
        }
    }

}
