package de.larsensmods.lmcc.api.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Special type of {@link Supplier} and {@link Holder} enabling the execution of code once the referenced object is made fully available
 * by the underlying loader
 * @param <T> Type held in this instance
 */
public interface DeferredSupplier<T> extends Supplier<T>, Holder<T> {

    /**
     * Register a callback to be executed once the underlying object is fully registered
     * @param callback {@link Consumer} called with the fully registered object once it is available
     */
    void onRegistration(Consumer<T> callback);

    /**
     * Check whether a value is present in this deferred supplier
     * @return Whether a value is present
     */
    boolean isPresent();

    /**
     * Get the value stored if it is available and null otherwise
     * @return The value or null
     */
    @Nullable
    default T getOrNull(){
        if(isPresent()){
            return get();
        }else{
            return null;
        }
    }

    /**
     * Get the value wrapped as an {@link Optional}
     * @return The value wrapped as an {@link Optional}
     */
    default Optional<T> toOptional() {
        return Optional.ofNullable(getOrNull());
    }

    /**
     * Perform an action if a value is available and do nothing otherwise
     * @param action The action to perform if a value is available
     */
    default void ifPresent(Consumer<? super T> action) {
        if (isPresent()) {
            action.accept(get());
        }
    }

    /**
     * Perform an action if a value is available and another action if it is not
     * @param action The action to perform if a value is available
     * @param emptyAction The action to perform if a value is not available
     */
    default void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (isPresent()) {
            action.accept(get());
        } else {
            emptyAction.run();
        }
    }

    /**
     * Get a {@link Stream} of the underlying value
     * @return The value as a {@link Stream}
     */
    default Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(get());
        }
    }

    /**
     * Get the value if it is available and otherwise return a given value
     * @param other The value to return if no value is available in this Deferred Supplier
     * @return The stored value if available and otherwise the parameter
     */
    default T orElse(T other) {
        return isPresent() ? get() : other;
    }

    /**
     * Get the value if it is available and otherwise get the value from a given {@link Supplier}
     * @param supplier The supplier to get the value from if no value is available in this Deferred Supplier
     * @return The stored value if available and otherwise the value obtained from the parameter
     */
    default T orElseGet(Supplier<? extends T> supplier) {
        return isPresent() ? get() : supplier.get();
    }

    /**
     * Get the {@link ResourceLocation} of the registry this Deferred Suppliers value is registered in
     * @return The registry the value is registered in
     */
    ResourceLocation getRegistryId();

    /**
     * Get a {@link ResourceKey} of the registry this Deferred Suppliers value is registered in
     * @return The registry the value is registered in
     */
    default ResourceKey<Registry<T>> getRegistryKey() {
        return ResourceKey.createRegistryKey(getRegistryId());
    }

    /**
     * Get the {@link ResourceLocation} of the registered value in this Deferred Supplier
     * @return The location of the registered value
     */
    ResourceLocation getId();

    /**
     * Get a {@link ResourceKey} of the registered value in this Deferred Supplier
     * @return The key of the registered value
     */
    default ResourceKey<T> getKey() {
        return ResourceKey.create(getRegistryKey(), getId());
    }

    /**
     * Get a {@link Holder} from the registered value
     * @return The holder of the value
     */
    @Nullable
    Holder<T> getHolder();

    /**
     * Get the value stored in this Deferred Supplier, similar to {@link DeferredSupplier#get()}
     * @return The value stored
     */
    @Override
    default @NotNull T value(){
        return this.get();
    }

    /**
     * Get whether the value is registered to the game, similar to {@link DeferredSupplier#isPresent()}
     * @return True if a value is available
     */
    @Override
    default boolean isBound(){
        return isPresent();
    }

    /**
     * Check whether this Deferred Suppliers content corresponds to a {@link ResourceLocation}
     * @param resourceLocation The location to check against
     * @return True, if the location matches the stored value
     */
    @Override
    default boolean is(@NotNull ResourceLocation resourceLocation) {
        return getId().equals(resourceLocation);
    }

    /**
     * Check whether this Deferred Suppliers content corresponds to a {@link ResourceKey}
     * @param resourceKey The key to check against
     * @return True, if the key matches the stored value
     */
    @Override
    default boolean is(@NotNull ResourceKey<T> resourceKey) {
        return getKey().equals(resourceKey);
    }

    /**
     * Check whether this Deferred Suppliers content is matching a {@link ResourceLocation}
     * @param predicate The predicate to match against
     * @return True, if the stored value matches the predicate
     */
    @Override
    default boolean is(@NotNull Predicate<ResourceKey<T>> predicate) {
        return predicate.test(getKey());
    }

    /**
     * Check whether this Deferred Suppliers content is included in a {@link TagKey}
     * @param tagKey The key to check against
     * @return True, if the stored value is included in the key
     */
    @Override
    default boolean is(@NotNull TagKey<T> tagKey) {
        Holder<T> holder = getHolder();
        return holder != null && holder.is(tagKey);
    }

    /**
     * Check whether this Deferred Suppliers content matches a {@link Holder}
     * @param holder The holder to check against
     * @return True, if the stored value matches the holder
     */
    @Override
    default boolean is(@NotNull Holder<T> holder) {
        return holder.is(getKey());
    }

    /**
     * Get a {@link Stream} of tags the stored value matches with
     * @return The corresponding stream
     */
    @Override
    default @NotNull Stream<TagKey<T>> tags() {
        Holder<T> holder = getHolder();
        return holder != null ? holder.tags() : Stream.empty();
    }

    /**
     * Unwrap into a {@link ResourceKey} and the value
     * @return The result of the unwrapping
     */
    @Override
    default @NotNull Either<ResourceKey<T>, T> unwrap() {
        return Either.left(getKey());
    }

    /**
     * Unwrap into an {@link Optional} {@link ResourceKey}
     * @return The result of the unwrapping
     */
    @Override
    default @NotNull Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of(getKey());
    }

    /**
     * Get the {@link net.minecraft.core.Holder.Kind} of value storage in this Deferred Supplier
     * @return How the data is stored
     */
    @Override
    default @NotNull Kind kind() {
        return Kind.REFERENCE;
    }

    /**
     * Check whether the stored value can be serialized into a {@link HolderOwner}
     * @param holderOwner What to check against
     * @return True if the serialization is possible
     */
    @Override
    default boolean canSerializeIn(@NotNull HolderOwner<T> holderOwner) {
        Holder<T> holder = getHolder();
        return holder != null && holder.canSerializeIn(holderOwner);
    }
}
