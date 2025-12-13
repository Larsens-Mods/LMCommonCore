package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.LMCCore;
import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeWrappedRegister<T> implements IWrappedRegister<T> {

    private final String modID;
    private final DeferredRegister<T> register;
    private final Registry<T> registry;

    public NeoForgeWrappedRegister(String modID, ResourceKey<Registry<T>> registryKey) {
        this.modID = modID;
        this.register = DeferredRegister.create(registryKey, modID);
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
    }

    @Override
    public <O extends T> DeferredSupplier<O> register(String name, Supplier<? extends O> supplier) {
        return new NeoForgeSupplier<>(this.register.register(name, supplier), this.registry, ResourceLocation.fromNamespaceAndPath(modID, name));
    }

    @Override
    public void register() {
        this.register.register(LMCCore.getEventBus());
    }
}
