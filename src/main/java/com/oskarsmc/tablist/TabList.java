package com.oskarsmc.tablist;

import com.google.inject.Inject;
import com.oskarsmc.tablist.commands.TablistReload;
import com.oskarsmc.tablist.configuration.TabSettings;
import com.oskarsmc.tablist.listener.EventListener;
import com.oskarsmc.tablist.module.GlobalTabList;
import com.oskarsmc.tablist.module.TabListHeaderFooter;
import com.oskarsmc.tablist.namemanager.DefaultDisplayName;
import com.oskarsmc.tablist.namemanager.DisplayName;
import com.oskarsmc.tablist.namemanager.LuckPermsDisplayName;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public class TabList {

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer proxyServer;

    @Inject
    private @DataDirectory
    Path dataDirectory;

    @Inject
    private Metrics.Factory metricsFactory;


    public void updateSettings() {
        this.tabSettings = new TabSettings(dataDirectory.toFile(), logger);
    }

    private TabSettings tabSettings;

    @Getter
    private GlobalTabList globalTabList;

    @Getter
    private TabListHeaderFooter tabListHeaderFooter;

    @Getter
    private static TabList instance;

    @Getter
    private DisplayName displayNameManager = new DefaultDisplayName();

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;

        updateSettings();

        if (this.tabSettings.isEnabled()) {
            if (this.tabSettings.getToml().getBoolean("global-tablist.enabled")) {
                this.globalTabList = new GlobalTabList(this, this.proxyServer);
                this.proxyServer.getEventManager().register(this, this.globalTabList);
                logger.info("Loaded Global Tablist");
            }

            if (this.tabSettings.getToml().getBoolean("tablist-header-footer.enabled")) {
                this.tabListHeaderFooter = new TabListHeaderFooter(this, this.proxyServer, this.tabSettings);
                this.proxyServer.getEventManager().register(this, this.tabListHeaderFooter);
                logger.info("Loaded Header & Footer");
            }

            // Register listener
            proxyServer.getEventManager().register(this, new EventListener(proxyServer,tabSettings));

            // Hook into LuckPerms
            Optional<PluginContainer> luckPerms = proxyServer.getPluginManager().getPlugin("luckperms");
            if (luckPerms.isPresent()) {
                displayNameManager = new LuckPermsDisplayName();
                logger.info("Hooked into LuckPerms");
            }

            // Register reload command
            proxyServer.getCommandManager().register("tablistreload",new TablistReload());
        }
    }
}
