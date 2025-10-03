# KMP Module Structure

This directory contains Kotlin Multiplatform modules organized following Clean Architecture principles.

## Directory Structure

```
kmp/
├── base/           # Core functionality modules
│   ├── core/       # Platform abstractions and core utilities
│   └── navigation/ # Navigation components using Tiamat
├── compose/        # Compose UI related modules
│   └── theme/      # Material Design 3 theme configuration
└── features/       # Feature modules
    └── root/       # Root feature containing App entry point
```

## Module Organization

### Base Modules (`kmp/base/`)
Foundational modules that provide core functionality:

- **core**: Platform detection, common utilities
- **navigation**: Navigation components and utilities

### Compose Modules (`kmp/compose/`)
Reusable UI components and theming:

- **theme**: Material Design 3 theme with colors, typography, and shapes

### Feature Modules (`kmp/features/`)
Application features organized by domain:

- **root**: Main application entry point and root composition

## Module Dependencies

Modules follow these dependency rules:
- **Base modules** → No dependencies on other app modules
- **Compose modules** → Can depend on base modules
- **Feature modules** → Can depend on base and compose modules

## Creating a New Module

1. Create module directory: `kmp/<category>/<module-name>/`
2. Create `build.gradle.kts` with appropriate plugins
3. Use `applyTargets("<namespace>")` to configure platforms
4. Register in `settings.gradle.kts`
5. Create source sets: `src/commonMain/kotlin/` and platform-specific as needed

## Build Configuration

All modules use:
- `hannasequestrianproject.kmp.library` - Base KMP library plugin
- `hannasequestrianproject.kmp.compose` - For Compose UI modules (optional)
- `hannasequestrianproject.kmp.ksp` - For KSP annotation processing (optional)

## Package Naming

All modules follow the base package structure:
```
com.nfrevolution.hannasequestrianproject.<module-name>
```
