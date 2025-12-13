package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.LMCCConstants;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ForgeSupplier<O, T extends O> implements DeferredSupplier<T> {

    private static final Set<ForgeSupplier<?, ?>> SUPPLIERS = new HashSet<>();
    private static boolean REGISTERED = false;

    private final RegistryObject<? extends T> registryObject;
    private final Set<Consumer<T>> consumers = new HashSet<>();

    private final Registry<O> registry;
    private final ResourceLocation id;
    private Holder<T> holder;

    public ForgeSupplier(RegistryObject<? extends T> registryObject, Registry<O> registry, ResourceLocation id) {
        this.registryObject = registryObject;
        this.registry = registry;
        this.id = id;

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
    public @NotNull T get() {
        return registryObject.get();
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
            consumer.accept(this.registryObject.get());
        }
    }

    @Mod.EventBusSubscriber(modid = LMCCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class CommonHandler {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            REGISTERED = true;
            for(ForgeSupplier<?, ?> supplier : SUPPLIERS) {
                supplier.acceptConsumers();
            }
        }
    }
}
