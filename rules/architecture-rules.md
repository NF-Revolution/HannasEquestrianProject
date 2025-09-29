---
description: 
globs: 
alwaysApply: true
---
## Follow Clean Architecture with modules:
- main package is "com.nfrevolution.ynth"
- Domain: Use cases, entities, repository interfaces.
- Data: Repository implementations, Firebase interactions, data mapping.
- Presentation: Compose UI, ViewModels, UI state.
- Util: Utility functions/extensions.
- Foundation: Reusable Compose UI components.
- Module dependencies: Domain → none, Data → Domain, Presentation → Domain, Foundation.
- Encapsulate business logic in Domain use cases.