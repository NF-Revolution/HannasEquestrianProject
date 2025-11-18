---
description: 
globs: 
alwaysApply: true
---
## Testing Rules:
- Write unit tests for Domain and Data using Kotlin Test and MockK.
- Use jvmTest directory for writing tests in multiplatform modules.
- Name the tests Following the behavior-driven development style with "should" statements.
- Follow Arrange-Act-Assert with clear variable names (e.g., inputLook, mockRepository).
- Write only necessary tests for realistic scenarios; avoid testing impossible edge cases or
  imagined failures.
- **Avoid Duplicate Tests**: Before writing a new test, verify that no existing test already covers
  the same behavior. Tests that verify the same functionality with different names, different test
  data, or slightly different assertions are considered duplicates. Each test should validate a
  unique scenario or behavior. Review all existing tests in the test class to ensure the new test
  adds distinct value.
