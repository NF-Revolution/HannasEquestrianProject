# Hanna's Equestrian Project

A Kotlin Multiplatform application for managing equestrian activities, horses, and stables. Built
with Compose Multiplatform.

## Architecture

The project follows Clean Architecture with modular structure:

- **Domain**: Business logic and use cases
- **Data**: Repository implementations and data sources
- **Presentation**: Compose UI and ViewModels
- **Foundation**: Reusable UI components

## Gradle Commands

### Development

```shell
# Run Web application (Wasm)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Run Desktop application
./gradlew :composeApp:jvmRun

# Update Yarn.lock
./gradlew kotlinUpgradeYarnLock
```

### Build

```shell
# Build project
./gradlew build

# Clean build
./gradlew clean build
```

### Testing

```shell
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew testCoverage
```

### Maintenance

```shell
# Check for dependency updates
./gradlew checkDependencyUpdates

# Format code
./gradlew ktlintFormat
```

## License

See [LICENSE](LICENSE) file for details.
