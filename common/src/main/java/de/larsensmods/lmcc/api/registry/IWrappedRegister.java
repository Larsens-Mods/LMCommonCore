package de.larsensmods.lmcc.api.registry;

import java.util.function.Supplier;

public interface IWrappedRegister<T> {

    <O extends T> Supplier<O> register(String name, Supplier<? extends O> supplier);

    void register();

}
