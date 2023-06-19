package com.oskarsmc.tablist.listener;

import com.oskarsmc.tablist.TabList;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;

public class EventListener {

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        // set tablist header and footer to joining players
        TabList.getInstance().getTabListHeaderFooter().updatePlayer(event.getPlayer());

        // set global tablist to joining players
        TabList.getInstance().getGlobalTabList().updatePlayer(event.getPlayer());
    }

    @Subscribe
    public void onPlayerLeave(DisconnectEvent event) {
        // remove player from tablist
        TabList.getInstance().getGlobalTabList().removePlayer(event.getPlayer());
    }

}
