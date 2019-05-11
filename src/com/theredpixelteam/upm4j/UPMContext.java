package com.theredpixelteam.upm4j;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.theredpixelteam.redtea.util.Optional;
import com.theredpixelteam.redtea.util.ShouldNotReachHere;
import com.theredpixelteam.upm4j.invoke.ClassicInvokerProviders;
import com.theredpixelteam.upm4j.invoke.InvokerProvider;
import com.theredpixelteam.upm4j.loader.*;
import com.theredpixelteam.upm4j.plugin.PluginStateTree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("all")
public class UPMContext {
    UPMContext(@Nullable String name,
               @Nonnull UPMClassLoaderProvider classLoaderProvider,
               @Nonnull PluginFileDiscoveringPolicy fileDiscoveringPolicy,
               @Nonnull PluginInstancePolicy instancePolicy,
               @Nonnull PluginEntryDiscoverer entryDiscoverer,
               @Nonnull InvokerProvider invokerProvider,
               @Nonnull PluginStateTree pluginStateTree,
               @Nullable SubscriberExceptionHandler eventBusExceptionHandler)
    {
        this.name = name;
        this.classLoaderProvider = classLoaderProvider;
        this.fileDiscoveringPolicy = fileDiscoveringPolicy;
        this.instancePolicy = instancePolicy;
        this.entryDiscoverer = entryDiscoverer;
        this.invokerProvider = invokerProvider;
        this.pluginStateTree = pluginStateTree;
        this.eventBus = eventBusExceptionHandler == null ?
                new EventBus() : new EventBus(eventBusExceptionHandler);
    }

    public @Nonnull UPMClassLoaderProvider getClassLoaderProvider()
    {
        return classLoaderProvider;
    }

    public @Nonnull
    PluginEntryDiscoverer getEntryDiscoverer()
    {
        return entryDiscoverer;
    }

    public @Nonnull
    PluginInstancePolicy getInstancePolicy()
    {
        return instancePolicy;
    }

    public @Nonnull PluginFileDiscoveringPolicy getFileDiscoveringPolicy()
    {
        return fileDiscoveringPolicy;
    }

    public @Nonnull InvokerProvider getInvokerProvider()
    {
        return invokerProvider;
    }

    public @Nonnull PluginStateTree getPluginStateTree()
    {
        return pluginStateTree;
    }

    public @Nonnull Optional<String> getName()
    {
        return Optional.ofNullable(name);
    }

    public @Nonnull EventBus getEventBus()
    {
        return eventBus;
    }

    public static @Nonnull Builder builder()
    {
        return new Builder();
    }

    private final EventBus eventBus;

    private final String name;

    private final InvokerProvider invokerProvider;

    private final PluginFileDiscoveringPolicy fileDiscoveringPolicy;

    private final PluginInstancePolicy instancePolicy;

    private final PluginEntryDiscoverer entryDiscoverer;

    private final UPMClassLoaderProvider classLoaderProvider;

    private final PluginStateTree pluginStateTree;

    public static final class Builder
    {
        Builder()
        {
        }

        public @Nonnull Builder invocationPolicy(@Nonnull PluginInvocationPolicy invocationPolicy)
        {
            Objects.requireNonNull(invocationPolicy);

            switch (invocationPolicy)
            {
                case ASM_INVOKE:
                    return invokerProvider(ClassicInvokerProviders.ASM);

                case REFLECTION:
                    return invokerProvider(ClassicInvokerProviders.REFLECTION);
            }

            throw new ShouldNotReachHere();
        }

        public @Nonnull Builder invokerProvider(@Nonnull InvokerProvider invokerProvider)
        {
            this.invokerProvider = Objects.requireNonNull(invokerProvider);
            return this;
        }

        public @Nonnull Builder fileDiscoveringPolicy(@Nonnull PluginFileDiscoveringPolicy fileDiscoveringPolicy)
        {
            this.fileDiscoveringPolicy = Objects.requireNonNull(fileDiscoveringPolicy);
            return this;
        }

        public @Nonnull Builder entryDiscoverer(@Nonnull PluginEntryDiscoverer entryDiscoverer)
        {
            this.entryDiscoverer = Objects.requireNonNull(entryDiscoverer);
            return this;
        }

        public @Nonnull Builder classLoaderPolicy(@Nonnull UPMClassLoaderProvider classLoaderProvider)
        {
            this.classLoaderProvider = Objects.requireNonNull(classLoaderProvider);
            return this;
        }

        public @Nonnull Builder instancePolicy(@Nonnull PluginInstancePolicy instancePolicy)
        {
            this.instancePolicy = Objects.requireNonNull(instancePolicy);
            return this;
        }

        public @Nonnull Builder pluginStateTree(@Nonnull PluginStateTree pluginStateTree)
        {
            this.pluginStateTree = Objects.requireNonNull(pluginStateTree);
            return this;
        }

        public @Nonnull Builder name(@Nullable String name)
        {
            this.name = name;
            return this;
        }

        public @Nullable Builder eventBusExceptionHandler(@Nullable SubscriberExceptionHandler handler)
        {
            this.eventBusExceptionHandler = handler;
            return this;
        }

        public @Nullable String getName()
        {
            return name;
        }

        public @Nullable UPMClassLoaderProvider getClassLoaderProvider()
        {
            return classLoaderProvider;
        }

        public @Nullable
        PluginEntryDiscoverer getEntryDiscoverer()
        {
            return entryDiscoverer;
        }

        public @Nullable
        PluginInstancePolicy getInstancePolicy()
        {
            return instancePolicy;
        }

        public @Nullable PluginFileDiscoveringPolicy getFileDiscoveringPolicy()
        {
            return fileDiscoveringPolicy;
        }

        public @Nullable InvokerProvider getInvokerProvider()
        {
            return invokerProvider;
        }

        public @Nullable SubscriberExceptionHandler getEventBusExceptionHandler()
        {
            return eventBusExceptionHandler;
        }

        public @Nullable PluginStateTree getPluginStateTree()
        {
            return pluginStateTree;
        }

        public @Nonnull UPMContext build()
        {
            return new UPMContext(
                    name,
                    Objects.requireNonNull(classLoaderProvider, "ClassLoaderPolicy"),
                    Objects.requireNonNull(fileDiscoveringPolicy, "FileDiscoveringPolicy"),
                    Objects.requireNonNull(instancePolicy, "InstancePolicy"),
                    Objects.requireNonNull(entryDiscoverer, "EntryDiscoverer"),
                    Objects.requireNonNull(invokerProvider, "InvokerProvider"),
                    Objects.requireNonNull(pluginStateTree, "PluginStateTree"),
                    eventBusExceptionHandler
            );
        }

        private String name;

        private SubscriberExceptionHandler eventBusExceptionHandler;

        private InvokerProvider invokerProvider;

        private PluginFileDiscoveringPolicy fileDiscoveringPolicy;

        private PluginInstancePolicy instancePolicy;

        private PluginEntryDiscoverer entryDiscoverer;

        private UPMClassLoaderProvider classLoaderProvider;

        private PluginStateTree pluginStateTree;
    }
}
