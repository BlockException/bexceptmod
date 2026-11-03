# Contributing to BExceptMod

Thank you for helping improve BExceptMod! Bug reports, suggestions, and pull
requests are welcome.

## Proposing changes

- **Bugs or ideas:** Open an issue describing what happened or what you would
  like to see. For bug reports, include the Minecraft and Fabric versions,
  reproduction steps, and relevant logs when possible.
- **Code changes:** Create a branch, keep the change focused, and open a pull
  request against `main`.
- Describe the problem, your changes, and any visible effects on the mod in the
  pull request.

## Before opening a pull request

- [ ] The change is limited to its stated purpose.
- [ ] Affected Minecraft versions and possible effects on other supported
  versions have been checked.
- [ ] The build succeeds.
- [ ] Documentation and translations have been updated where needed.
- [ ] No credentials, private data, or generated build files have been added.

The mod currently supports Minecraft **1.21.11, 26.1, 26.2, and 26.3**. The
GitHub Actions build checks all four variants. Run the default 26.2 build
locally with:

```sh
./gradlew clean build
```

For a targeted build, use the matching dependency versions from
[`.github/workflows/build.yml`](./.github/workflows/build.yml), for example:

```sh
./gradlew clean build \
  -Pminecraft_version=1.21.11 \
  -Pfabric_api_version=0.141.6+1.21.11 \
  -Pmodmenu_version=17.0.1 \
  -Pcloth_config_version=21.11.153
```

All supported variants require Java 25 to run the Gradle build. The 1.21.11 mod
is still compiled with Java 21 as its target.
