## kapt runs even when there's no annotation processors in the kapt configuration

In this sample project kapt can account for as much as 10% of the build tile despite not having any
annotation processors to run.

## Steps to reproduce.


```bash
 $ ./gradlew :app:assembleDebug --scan
```

### Observe

These two kapt tasks run:

```
:app:kaptGenerateStubsDebugKotlin   2.814s  0.614s	org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
:app:kaptDebugKotlin    3.429s	0.149s	org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask

```

I expect kapt to run only when annotation processors are in the kapt configuration. Expecially with this flag defined:

```
kapt.include.compile.classpath=false
```

#### What works!

Not applying the kapt-plugin on modules with an annotation processor.

#### Additional details

[./gradlew :app:assembleDebug --debug](https://github.com/tevjef/android-bugs/blob/kapt_runs_enexpectedly/debug.txt)

[Gradle Build Scan](https://scans.gradle.com/s/wh7mid6etr2je)