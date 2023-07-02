package com.oskarsmc.tablist.listener;

import com.oskarsmc.tablist.TabList;
import com.oskarsmc.tablist.configuration.TabSettings;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;

public class EventListener {
    private LuckPerms luckPermsAPI;
    private final TabSettings configuration;
    private final ProxyServer proxyServer;

    public EventListener(final ProxyServer proxyServer, final TabSettings configuration) {
        this.configuration = configuration;
        this.proxyServer = proxyServer;
    }

    @Subscribe
    private void onLeave(final DisconnectEvent event) {
        // remove player from tablist
        TabList.getInstance().getGlobalTabList().removePlayer(event.getPlayer());
        if (!configuration.getToml().getBoolean("leave-message.use")) {
            return;
        }

        if (!event.getLoginStatus().equals(DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN)){
            return;
        }

        final Player player = event.getPlayer();
        String message = configuration.getToml().getString("leave-message.format");
        message = message
                .replace("#displayname#", TabList.getInstance().getDisplayNameManager().getDisplayName(player));
        proxyServer.sendMessage(Component.text(message.replace("&", "§")).color(NamedTextColor.GRAY));
    }

    @Subscribe
    private void onJoin(final PostLoginEvent event) {
        // set tablist header and footer to joining players
        TabList.getInstance().getTabListHeaderFooter().updatePlayer(event.getPlayer());

        // set global tablist to joining players
        TabList.getInstance().getGlobalTabList().updatePlayer(event.getPlayer());
        if (!configuration.getToml().getBoolean("join-message.use")) {
            return;
        }

        final Player player = event.getPlayer();
        String message = configuration.getToml().getString("join-message.format");
        message = message
                .replace("#displayname#", TabList.getInstance().getDisplayNameManager().getDisplayName(player));
        proxyServer.sendMessage(Component.text(message.replace("&", "§")).color(NamedTextColor.GRAY));
    }
}
