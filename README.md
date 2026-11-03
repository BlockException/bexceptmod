# BExceptMod

<p align="center">
  <img src="src/main/resources/assets/bexceptmod/icon.png" alt="BExceptMod logo" width="160">
</p>

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11%20%7C%2026.1--26.3-brightgreen)
![Fabric](https://img.shields.io/badge/Loader-Fabric-orange)
![Client side](https://img.shields.io/badge/Environment-Client-blue)

**BExceptMod** is a client-side Fabric mod for Minecraft 1.21.11, 26.1, 26.2,
and 26.3. It displays system statistics, provides toggleable night vision, and
includes a configurable high-FPS mode.

> **Performance note:** On a 2018 laptop with 8 GB of RAM, the mod reached
> **140+ FPS** with VSync disabled and no FPS limit. Actual frame rates depend
> on hardware, resolution, world, mods, and settings.

## Features

| Feature | Description | Default key |
| --- | --- | :---: |
| System HUD | FPS, optional frame time below it, Minecraft CPU usage, and used/available Java heap in the top-right corner | — |
| Night vision | Toggle night vision on the client; adjust its strength | `G` |
| High-FPS mode | Adjust graphics options for improved performance | `H` |

The high-FPS mode can adjust render and simulation distance, entity distance,
clouds, ambient occlusion, entity shadows, particles, biome blending, and
mipmaps. Previous graphics settings are restored when the mode is turned off.
Frame-time display is disabled by default. In the **Explosion Effects**
category, you can independently hide the central explosion effect and/or
explosion debris particles on the client. Both options are disabled by default;
explosion damage, knockback, and sound remain unchanged.

The mod settings screen offers English, German, Spanish, French, and Polish.
English is the default, independent of Minecraft's selected language.

## Installation

1. Install **Fabric Loader** for your Minecraft version.
2. Put the BExceptMod JAR and **Fabric API** in the Minecraft `mods` folder.
3. For the graphical settings screen, also install compatible versions of
   [Mod Menu](https://modrinth.com/mod/modmenu) and
   [Cloth Config](https://modrinth.com/mod/cloth-config).
4. Launch Minecraft using the Fabric profile.

Mod Menu and Cloth Config are only required for the graphical settings screen.
The mod also works without them.

## Settings

Change key bindings under **Options → Controls → Key Binds**. The BExceptMod
settings screen also lets you configure the HUD, mod language, night-vision
strength, high-FPS mode, and explosion effects.

## Downloads

Download finished mod files from the
[Releases page](../../releases). Each release contains all four supported
Minecraft versions. Files are named `bexceptmod-<minecraft-version>-<mod-version>.jar`,
for example `bexceptmod-26.2-1.0.0.jar`.

## Contributing

Bug reports and pull requests are welcome. See the
[contribution guide](CONTRIBUTING.md) for reporting and verification details.

