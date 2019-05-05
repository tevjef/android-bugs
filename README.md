## Dagger sources are not regenerated when sources change.

With the changes made to kapt in 1.3.31, kotlin does some aggressive caching to decrease incremental built times. Unfortunately, some annotation processors is use namely Dagger and Butterknife, don’t work well together and kapt since the cache is not properly invalidated in some conditions.
- Deleted classes with `@Inject` may still have code generated for them.
- Moved classes with `@Inject` may still have code generated in the old location.

This behavior is enabled by default in Kotlin 1.3.31. Is there something I need to configure kapt to ensure the cache is properly invalidated?
This is causing some issues for my team, especially when we switch between branches.

## Steps to reproduce.


### Build the project a few times

```bash
 $ ./gradlew :app:assembleDebug
```

Run this command 3 times to ensure Gradle daemon is warmed and compilation is cache.


### Modify Dagger's configuration

Delete the lines below from `MainActivity.kt`:

```
    @Inject
    lateinit var someDependency: SomeDependency
```

### Build the project again

```bash
 $ ./gradlew :app:assembleDebug
```


### Observe

Compilation crashes with this error:

```
> Task :app:compileDebugJavaWithJavac FAILED
/android-bugs/app/build/generated/source/kapt/debug/com/example/tevjef/gradienttest/MainActivity_MembersInjector.java:25: error: cannot find symbol
    instance.someDependency = someDependency;
            ^
  symbol:   variable someDependency
  location: variable instance of type MainActivity
1 error
```

### Additional issues

Running clean then building the project does cause kapt to regenerate sources to clean up the now unused inject.

```bash
 $ ./gradlew clean :app:assembleDebug
```

#### What works!

```bash
 $ ./gradlew --no-build-cache clean :app:assembleDebug
```

I have no concrete evidence of this but it believe the issue is due to `kapt.incremental.apt=true`.

#### Additional details

[./gradlew :app:assembleDebug --debug](https://github.com/tevjef/android-bugs/blob/kapt_cache_bug/debug.txt)

[Gradle Build Scan](https://scans.gradle.com/s/yy74q5rwzz4ei)

This issue also exists on `1.3.40-dev-2527`