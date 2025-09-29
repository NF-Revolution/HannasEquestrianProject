---
description: 
globs: 
alwaysApply: true
---
Multiplatform Setup Rules:
- Use Kotlin Multiplatform for shared logic (Domain, Data).
- Use Compose Multiplatform for UI, with reusable components in Foundation.
- Use expect/actual for platform-specific code (e.g., image picking).
- Minimize platform-specific code with Composes cross-platform features.
- Use Gradle for multi-module setup (commonMain, androidMain, iosMain).