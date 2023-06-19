package com.oskarsmc.tablist.namemanager;

import com.velocitypowered.api.proxy.Player;

public class DefaultDisplayName implements DisplayName{
    @Override
    public String getDisplayName(Player player) {
        return player.getUsername();
    }
}
