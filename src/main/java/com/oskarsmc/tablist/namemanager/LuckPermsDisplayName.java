package com.oskarsmc.tablist.namemanager;

import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.model.user.User;
import org.checkerframework.checker.nullness.qual.NonNull;

public class LuckPermsDisplayName implements DisplayName{

    private final @NonNull LuckPerms api = LuckPermsProvider.get();
    @Override
    public String getDisplayName(Player player) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user == null) return player.getUsername();
        final CachedDataManager cachedData = user.getCachedData();

        StringBuilder fullName = new StringBuilder();

        final String prefix = cachedData.getMetaData().getPrefix();
        if (prefix != null) {
            fullName.append(prefix);
        }
        fullName.append(player.getUsername());
        final String suffix = cachedData.getMetaData().getSuffix();
        if (suffix != null) {
            fullName.append(suffix);
        }
        return fullName.toString();
    }
}
