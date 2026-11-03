# BExceptMod

<p align="center">
  <img src="src/main/resources/assets/bexceptmod/icon.png" alt="BExceptMod logo" width="160">
</p>

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11%20%7C%2026.1--26.3-brightgreen)
![Fabric](https://img.shields.io/badge/Loader-Fabric-orange)
![Client side](https://img.shields.io/badge/Environment-Client-blue)

**BExceptMod** is a client-side Fabric mod for Minecraft 1.21.11, 26.1, 26.2,
and 26.3. It adds a compact system HUD, toggleable night vision, and a
configurable performance mode. Rather than promising a specific FPS increase,
it lets you choose graphics settings that suit your system and restore the
previous settings when you turn the mode off.

## Features

| Feature | Description | Default key |
| --- | --- | :---: |
| System HUD | FPS, optional frame time below it, Minecraft CPU usage, and used/available Java heap in the top-right corner | — |
| Night vision | Toggle client-side night vision and temporarily raise Minecraft's gamma; adjust strength | `G` |
| High-FPS mode | Adjust graphics options for improved performance | `H` |

The high-FPS mode can adjust render and simulation distance, entity distance,
clouds, ambient occlusion, entity shadows, particles, biome blending, and
mipmaps. Previous graphics settings are restored when the mode is turned off.
Frame-time display is disabled by default. In the **Explosion Effects**
category, you can independently hide the central explosion effect and/or
explosion debris particles on the client. Both options are disabled by default;
explosion damage, knockback, and sound remain unchanged.

While night vision is active, BExceptMod temporarily sets Minecraft's gamma
to its maximum and provides the configured night-vision strength through
Minecraft's renderer hook used by Iris. The previous gamma value is restored
when the effect is disabled. Shader packs must use Iris's night-vision
uniform; packs with custom lighting that ignore it may still need their own
brightness or night-vision option.

For example, the performance mode applies the configured render distance and
particle setting:

```java
client.options.renderDistance().set(CONFIG.renderDistance);
client.options.particles().set(CONFIG.minimalParticles
        ? ParticleStatus.MINIMAL
        : performanceSnapshot.particles);
```

When the mode is turned off, it restores the values captured before activation:

```java
client.options.renderDistance().set(renderDistance);
client.options.particles().set(particles);
```

These are excerpts from `BExceptModClient`; the actual frame rate depends on
the system, Minecraft settings, and other installed mods.

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
