# tablist <a href="https://discord.gg/KfmcRzv6Gh"><img src="https://img.shields.io/discord/840618521611337759?color=pink&label=Discord&logo=discord&logoColor=pink&style=for-the-badge"></a>

<a href="https://bstats.org/plugin/velocity/send/11443"><img src="https://img.shields.io/bstats/servers/11443?color=green&style=for-the-badge"></a>

Tablist Plugin for velocity - Prefix and Suffix fork, Heron4gf

# Features
* Tablist header and footer
* Global tablist
* Playername prefix and suffix from LuckPermsVelocity

# Configuration
```toml
# tablist
[plugin]
enabled=true

# Global tablist
[global-tablist]
enabled=true

[tablist-header-footer]
update_ms=1000 # updates every 1000 milliseconds
enabled=true
# If you are unsure how to hanle new lines, take a look at https://toml.io/en/ or just use \n
header="""
        &cExample Network

        &aLine 3!"""
footer="&dhave fun!"

[developer-info]
config-version=0.1
```
All options are disabled by default.

# Download
Get the latest release <a href="https://github.com/OskarsMC-Plugins/tablist/releases">here</a>