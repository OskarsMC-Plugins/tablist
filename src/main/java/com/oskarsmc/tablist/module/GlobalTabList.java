package com.oskarsmc.tablist.module;

import com.oskarsmc.tablist.TabList;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;

import java.util.concurrent.TimeUnit;

public class GlobalTabList {
    private ProxyServer proxyServer;

    public GlobalTabList(TabList plugin, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;

        this.proxyServer.getScheduler().buildTask(plugin, GlobalTabList.this::update).repeat(50, TimeUnit.MILLISECONDS).schedule();
    }

    public void update() {
        for (Player player : this.proxyServer.getAllPlayers()) {
            for (Player player1 : this.proxyServer.getAllPlayers()) {
                if (!player.getTabList().containsEntry(player1.getUniqueId())) {
                    player.getTabList().addEntry(
                            TabListEntry.builder()
                                    .displayName(Component.text(player1.getUsername()))
                                    .latency(((int) player1.getPing()))
                                    .profile(player1.getGameProfile())
                                    .gameMode(0) // Impossible to get player game mode from proxy, always assume survival
                                    .build()
                    );
                }
            }
        }
    }
}
