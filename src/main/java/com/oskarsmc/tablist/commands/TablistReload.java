package com.oskarsmc.tablist.commands;

import com.oskarsmc.tablist.TabList;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class TablistReload implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        if(!invocation.source().hasPermission("tablist.reload")) return;
        TabList.getInstance().updateSettings();
        invocation.source().sendMessage(Component.text("Config reloaded successfully!").color(TextColor.fromHexString("#5e2bbc")));
    }
}
