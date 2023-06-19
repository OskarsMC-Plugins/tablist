package com.oskarsmc.tablist.module;

import com.oskarsmc.tablist.TabList;
import com.oskarsmc.tablist.configuration.TabSettings;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.themoep.minedown.adventure.MineDown;
import net.kyori.adventure.text.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TabListHeaderFooter {
    private Component header;
    private Component footer;
    private ProxyServer proxyServer;

    public TabListHeaderFooter(TabList plugin, ProxyServer proxyServer, TabSettings tabSettings) {
        this.header = MineDown.parse(tabSettings.getToml().getString("tablist-header-footer.header"));
        this.footer = MineDown.parse(tabSettings.getToml().getString("tablist-header-footer.footer"));

        this.proxyServer = proxyServer;

        this.proxyServer.getScheduler().buildTask(plugin, () -> {
            TabList.getInstance().getGlobalTabList().update();
            update();
        }).repeat(tabSettings.getToml().getLong("tablist-header-footer.update_ms",1000l), TimeUnit.MILLISECONDS).schedule();
    }

    public void updatePlayer(Player player) {
        player.getTabList().setHeaderAndFooter(header, footer);
    }

    public void update() {
        CompletableFuture.runAsync(() -> {
            for (Player player : this.proxyServer.getAllPlayers()) {
                updatePlayer(player);
            }
        });
    }
}
