package com.oskarsmc.tablist.namemanager;

import com.oskarsmc.tablist.TabList;
import com.velocitypowered.api.proxy.Player;

public interface DisplayName {
    static String of(Player player) {
        return TabList.getInstance().getDisplayNameManager().getDisplayName(player);
    }

    String getDisplayName(Player player);
}
