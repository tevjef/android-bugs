#  Incorrect package name for <include> ids 

Issue: https://issuetracker.google.com/issues/140882631


### To reproduce

Run command on this project.
```bash
./gradlew :app:assembleDebug
```

Observe compilation failure in generate code:
```
> Task :app:compileDebugJavaWithJavac FAILED
/MyApplication/app/build/generated/data_binding_base_class_source_out/debug/out/com/example/myapplication/databinding/ActivityMainBinding.java:57: error: package com.example.library.databinding.R does not exist
      View myIdForLibraryInclude = rootView.findViewById(com.example.library.databinding.R.id.my_id_for_library_include);
                                                                                          ^
1 error

```
  
Specifically:
```java
      View myIdForLibraryInclude = rootView.findViewById(com.example.library.databinding.R.id.my_id_for_library_include);
```

`ActivityMainBinding` references an invalid package name.

Expected: `rootView.findViewById(R.id.my_id_for_library_include)` 

Actual: `rootView.findViewById(com.example.library.databinding.R.id.my_id_for_library_include)`


### Another bug?

![AS](/another_bug.png)

The TextView the root of `LibraryIncludeBinding` is not correctly resolved in Android Studio.


### System Info
```
Android Studio 3.6 Beta 1
Build #AI-192.6603.28.36.5916306, built on October 3, 2019
Runtime version: 1.8.0_212-release-1586-b4-5784211 x86_64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
macOS 10.14.5
GC: ParNew, ConcurrentMarkSweep
Memory: 1237M
Cores: 8
Registry: ide.new.welcome.screen.force=true
Non-Bundled Plugins: Shifter, String Manipulation, com.genymotion.idea, eu.inmite.android.plugin.butterknifezelezny, io.protostuff.protostuff-jetbrains-plugin, mobi.hsz.idea.gitignore, org.intellij.plugins.markdown, BashSupport, com.developerphil.adbidea, google-sceneform-tools, org.sonarlint.idea, pl.charmas.parcelablegenerator, wu.seal.tool.jsontokotlin

```
