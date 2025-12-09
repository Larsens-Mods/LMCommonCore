package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ForgeWrappedRegister<T> implements IWrappedRegister<T> {

    private final String modID;
    private final DeferredRegister<T> register;

    public ForgeWrappedRegister(String modID, ResourceKey<Registry<T>> registryKey) {
        this.modID = modID;
        this.register = DeferredRegister.create(registryKey, modID);
    }

    @Override
    public <O extends T> DeferredSupplier<O> register(String name, Supplier<? extends O> supplier) {
        return new ForgeSupplier<>(this.register.register(name, supplier));
    }

    @Override
    public void register() {
        this.register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
