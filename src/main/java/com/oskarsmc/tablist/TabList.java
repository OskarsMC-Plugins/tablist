package com.oskarsmc.tablist;

import com.google.inject.Inject;
import com.oskarsmc.tablist.configuration.TabSettings;
import com.oskarsmc.tablist.module.GlobalTabList;
import com.oskarsmc.tablist.module.TabListHeaderFooter;
import com.oskarsmc.tablist.util.StatsUtils;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bstats.charts.AdvancedPie;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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

    private TabSettings tabSettings;

    private GlobalTabList globalTabList;
    private TabListHeaderFooter tabListHeaderFooter;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.tabSettings = new TabSettings(dataDirectory.toFile(), logger);
        Metrics metrics = metricsFactory.make(this, StatsUtils.PLUGIN_ID);
        metrics.addCustomChart(new AdvancedPie("modules_enabled", new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() {
                Map<String, Integer> valueMap = new HashMap<>();
                valueMap.put("Global Tablist", getEnabled("global-tablist.enabled"));
                valueMap.put("Tablist Header & Footer", getEnabled("tablist-header-footer.enabled"));
                return valueMap;
            }

            public int getEnabled(String module) {
                if (tabSettings.getToml().getBoolean(module)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }));

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
        }
    }
}
