package de.larsensmods.lmcc.registry;

import de.larsensmods.lmcc.api.registry.DeferredSupplier;

import java.util.function.Supplier;

public interface IWrappedRegister<T> {

    <O extends T> DeferredSupplier<O> register(String name, Supplier<? extends O> supplier);

    void register();

}
