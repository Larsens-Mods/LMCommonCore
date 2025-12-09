package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeSupplier<T> implements DeferredSupplier<T> {

    private static final Set<NeoForgeSupplier<?>> SUPPLIERS = new HashSet<>();
    private static boolean REGISTERED = false;

    private final Supplier<T> deferredHolder;
    private final Set<Consumer<T>> consumers = new HashSet<>();

    public NeoForgeSupplier(Supplier<T> deferredHolder) {
        this.deferredHolder = deferredHolder;

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
            for(NeoForgeSupplier<?> supplier : SUPPLIERS) {
                supplier.acceptConsumers();
            }
        }
    }

}
