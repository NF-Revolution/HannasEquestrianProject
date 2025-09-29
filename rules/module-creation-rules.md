---
description: 
globs: 
alwaysApply: true
---
## Module creation rules:
* Use Common Module Template:

- Use the commonModule template from the local/commonModule directory as the default setup for any
  new regular module.
- Use the commonFeatureModule template from the local/commonFeatureModule directory when creating a
  feature module.
* Update Namespace in build.gradle.kts:
- In the build.gradle.kts file of the new module, find the string "com.nfrevolution.ynth.xxx".  
- Replace xxx with the module name (e.g., for a module named auth, set "com.nfrevolution.ynth.auth").
* Configure Package Structure:

- In the local/commonModule/src or local/commonFeatureModule/src directory, the default package is
  kotlin.com.nfrevolution.ynth.
- For each target (e.g., Android, iOS) in the src directory of the new module, append the module name as a subpackage.  
- Example: For a module named auth, use the package kotlin.com.nfrevolution.ynth.auth.
* Place Module in KMP Directory: 
- Create the new module inside the kmp directory.  
- Select the most suitable subdirectory based on the module's purpose (e.g., kmp/feature for feature modules, kmp/core for core utilities, kmp/compose for UI stuff).
* Register Module in settings.gradle.kts:
- Add the new module to the settings.gradle.kts file in the project root.  
- Use the format include(":kmp:xxx"), where xxx is the module name (e.g., include(":kmp:auth") for an auth module).  
- Ensure the path matches the module's location within the kmp directory structure.
