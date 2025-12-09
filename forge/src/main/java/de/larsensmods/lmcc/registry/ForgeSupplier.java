package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.Constants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ForgeSupplier<T> implements DeferredSupplier<T> {

    private static final Set<ForgeSupplier<?>> SUPPLIERS = new HashSet<>();
    private static boolean REGISTERED = false;

    private final RegistryObject<T> registryObject;
    private final Set<Consumer<T>> consumers = new HashSet<>();

    public ForgeSupplier(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;

        SUPPLIERS.add(this);
    }

    @Override
    public void onRegistration(Consumer<T> callback) {
        if(REGISTERED) {
            callback.accept(this.registryObject.get());
        }else{
            this.consumers.add(callback);
        }
    }

    @Override
    public T get() {
        return registryObject.get();
    }

    private void acceptConsumers() {
        for(Consumer<T> consumer : this.consumers) {
            consumer.accept(this.registryObject.get());
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class CommonHandler {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            REGISTERED = true;
            for(ForgeSupplier<?> supplier : SUPPLIERS) {
                supplier.acceptConsumers();
            }
        }
    }
}
