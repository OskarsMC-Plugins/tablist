package com.oskarsmc.tablist.util;

import com.moandjiezana.toml.Toml;
import com.oskarsmc.tablist.configuration.TabSettings;

public class VersionUtils {
    public static final double CONFIG_VERSION = getDefaultConfiguration().getDouble("developer-info.config-version");

    public static boolean isLatestConfigVersion(TabSettings tabSettings) {
        if (tabSettings.getConfigVersion() == null) {
            return false;
        }
        return tabSettings.getConfigVersion() == CONFIG_VERSION;
    }

    public static Toml getDefaultConfiguration() {
        return new Toml().read(VersionUtils.class.getResourceAsStream("/config.toml"));
    }
}
