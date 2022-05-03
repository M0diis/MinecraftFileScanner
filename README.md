# MinecraftFileScanner
An implementation of minecraft file scanning and sending data via discord webhooks.

A tool for minecraft server staff to check the whether the player is or is not using any unwanted software.

![image](https://user-images.githubusercontent.com/53468997/166491655-75777a96-2e87-4753-99d3-727783d39058.png)

The first input is the player name, the second is webhook id & token.  
(Ex. `9707711851316752405/g63_94VYvKJdQVZMN9asxaszsdRnLo03gsiyeo2UzJfP48asdkhXe0AfTipLwjI7mC2gA1ijXUoK`)

Once the button is clicked, the data will be read from the specified minecraft folders and sent via the webhook to the channel.

![image](https://user-images.githubusercontent.com/53468997/166491869-554ec9bc-f437-446c-b8c2-13b2ce6f9d89.png)

Current scanned directories:
- %APPDATA%/.minecraft/mods
- %APPDATA%/.minecraft/versions
- %APPDATA%/.minecraft/config

### Building
To build the tool you need JDK 8 or higher and Gradle installed on your system.

Clone the repository or download the source code from releases. Run gradlew shadowjar to build the jar. The jar will be created in /build/libs/ folder.

```
git clone https://github.com/M0diis/MinecraftFileScanner.git
cd M0-OnlinePlayersGUI
gradlew shadowjar
```

