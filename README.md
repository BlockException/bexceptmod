# BExceptMod

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11%20%7C%2026.1--26.3-brightgreen)
![Fabric](https://img.shields.io/badge/Loader-Fabric-orange)
![Client side](https://img.shields.io/badge/Environment-Client-blue)

**BExceptMod** ist eine clientseitige Fabric-Mod für Minecraft 1.21.11, 26.1,
26.2 und 26.3. Sie
zeigt Systemwerte an, bietet umschaltbare Nachtsicht und enthält einen
konfigurierbaren High-FPS-Modus.

> **Praxiserfahrung:** Auf einem Laptop von 2018 mit 8 GB RAM wurden bei
> deaktiviertem VSync und ohne FPS-Limit **140+ FPS** erreicht. Die tatsächliche
> Bildrate hängt von Hardware, Auflösung, Welt, Mods und Einstellungen ab.

## Funktionen

| Funktion | Beschreibung | Standardtaste |
| --- | --- | :---: |
| System-HUD | FPS, CPU-Auslastung von Minecraft und belegter/verfügbarer Java-Heap oben rechts | — |
| Nachtsicht | Clientseitig ein- und ausschalten; Stärke anpassbar | `G` |
| High-FPS-Modus | Grafikoptionen für mehr Leistung anpassen | `H` |

Der High-FPS-Modus kann Render- und Simulationsdistanz, Entity-Distanz, Wolken,
Umgebungsverdeckung, Entity-Schatten, Partikel, Biome-Übergänge und Mipmaps
anpassen. Beim Ausschalten werden die vorherigen Grafikwerte wiederhergestellt.

## Installation

1. Installiere **Fabric Loader** für deine Minecraft-Version.
2. Lege die BExceptMod-JAR und **Fabric API** in den Minecraft-Ordner `mods`.
3. Für das grafische Einstellungsmenü installiere zusätzlich kompatible
   Versionen von [Mod Menu](https://modrinth.com/mod/modmenu) und
   [Cloth Config](https://modrinth.com/mod/cloth-config).
4. Starte Minecraft mit dem Fabric-Profil.

Mod Menu und Cloth Config sind nur für das grafische Einstellungsmenü nötig.
Die Mod funktioniert auch ohne sie.

## Einstellungen

Die Tastenbelegungen lassen sich unter **Optionen → Steuerung → Tastenbelegung**
ändern. Im BExceptMod-Einstellungsmenü kannst du außerdem die HUD-Anzeigen,
Nachtsicht-Stärke und Optionen des High-FPS-Modus anpassen.

## Download

Die fertigen Mod-Dateien findest du auf der
[Releases-Seite](../../releases). Jeder Release enthält alle vier unterstützten
Minecraft-Versionen. Die Dateien heißen `bexceptmod-<minecraft-version>-<mod-version>.jar`,
zum Beispiel `bexceptmod-26.2-1.0.0.jar`.
