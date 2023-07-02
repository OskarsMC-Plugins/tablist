package com.oskarsmc.tablist.module;

import com.oskarsmc.tablist.TabList;
import com.oskarsmc.tablist.namemanager.DisplayName;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;

import java.util.concurrent.CompletableFuture;

public class GlobalTabList {
    private ProxyServer proxyServer;

    public GlobalTabList(TabList plugin, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    public void updatePlayer(Player player) {
        for(Player onlinePlayer : this.proxyServer.getAllPlayers()) {
            if(onlinePlayer.getTabList().containsEntry(player.getUniqueId())) {
                onlinePlayer.getTabList().removeEntry(player.getUniqueId());
            }
            onlinePlayer.getTabList().addEntry(TabListEntry.builder()
                    .displayName(Component.text(DisplayName.of(player)))
                    .profile(player.getGameProfile())
                    .gameMode(0) // Impossible to get player game mode from proxy, always assume survival
                    .tabList(player.getTabList())
                    .latency((int)player.getPing())
                    .build());

        }
    }

    public void removePlayer(Player player) {
        for(Player onlinePlayer : this.proxyServer.getAllPlayers()) {
            onlinePlayer.getTabList().removeEntry(player.getUniqueId());
        }
    }

    public void update() {
        CompletableFuture.runAsync(() -> {
            for(Player player : proxyServer.getAllPlayers()) {
                updatePlayer(player);
            }
        });
    }
}
