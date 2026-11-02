# Mitwirken an BExceptMod

Danke, dass du BExceptMod verbessern möchtest! Fehlerberichte, Vorschläge und
Pull Requests sind willkommen.

## Änderungen vorschlagen

- **Fehler oder Ideen:** Erstelle ein Issue und beschreibe, was passiert oder
  was du dir wünschst. Bei Fehlern helfen Minecraft-Version, Fabric-Version,
  Schritte zum Reproduzieren und relevante Logs.
- **Codeänderungen:** Erstelle einen Branch, ändere nur das für den Beitrag
  Notwendige und eröffne anschließend einen Pull Request gegen `main`.
- Erkläre im Pull Request das Problem, die Änderung und gegebenenfalls
  sichtbare Auswirkungen auf die Mod.

## Vor dem Pull Request

- [ ] Die Änderung ist auf den beschriebenen Zweck begrenzt.
- [ ] Betroffene Minecraft-Versionen und mögliche Auswirkungen auf andere
  unterstützte Versionen sind geprüft.
- [ ] Der Build ist erfolgreich.
- [ ] Dokumentation und Übersetzungen sind bei Bedarf aktualisiert.
- [ ] Es wurden keine Zugangsdaten, privaten Daten oder generierten Build-Dateien
  hinzugefügt.

Die Mod unterstützt derzeit Minecraft **1.21.11, 26.1, 26.2 und 26.3**. Der
GitHub-Actions-Build prüft alle vier Varianten. Lokal kannst du den Standardbuild
für 26.2 mit folgendem Befehl prüfen:

```sh
./gradlew clean build
```

Für einen gezielten Build verwende die passenden Abhängigkeiten aus
[`.github/workflows/build.yml`](./.github/workflows/build.yml), zum Beispiel:

```sh
./gradlew clean build \
  -Pminecraft_version=1.21.11 \
  -Pfabric_api_version=0.141.6+1.21.11 \
  -Pmodmenu_version=17.0.1 \
  -Pcloth_config_version=21.11.153
```

Alle unterstützten Varianten benötigen Java 25 zum Ausführen des Gradle-Builds.
Die 1.21.11-Mod wird trotzdem mit Java 21 als Zielversion kompiliert.

## Wo Regeln und Checklisten gepflegt werden

- **Allgemeine Beitragsregeln und lokale Prüfschritte:** Diese Datei
  [`CONTRIBUTING.md`](./CONTRIBUTING.md).
- **Checkliste, die bei jedem Pull Request erscheint:** die Vorlage
  [`.github/pull_request_template.md`](./.github/pull_request_template.md).
  Passe deren Checkboxen an, wenn Contributors weitere Schritte abarbeiten
  sollen.
- **Automatische Build-Prüfungen:** [`.github/workflows/build.yml`](./.github/workflows/build.yml).
- **Vorgeschriebene Statusprüfungen vor dem Mergen:** in GitHub unter
  **Settings → Rules → Rulesets** (oder **Settings → Branches**, je nach
  Repository-Konfiguration). Dort kann ein Maintainer die Build-Prüfungen als
  erforderlich festlegen.
