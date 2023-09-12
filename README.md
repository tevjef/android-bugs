# Assisted Factory KSP bug reproducer

https://github.com/google/dagger/issues/4054

```bash
    git clone -b dagger_assisted_factory_bug git@github.com:tevjef/android-bugs.git
    
    cd android-bugs
    
    chmod +x bug.sh
    
    ./bug.sh
```

#### bug.sh

```bash
    echo "Initial compilation.."
    ./gradlew :app:assembleDebug -q
    echo "Modifying source.."
    sed -i '' 's/\/\///g' app/src/main/java/com/company/myapplication/Target.kt
    sed -i '' 's/\/\///g' app/src/main/java/com/company/myapplication/Target.kt
    echo "Incremental compilation.."
    ./gradlew :app:assembleDebug
    echo "Observe failure..."
    
    #android-bugs/app/build/generated/ksp/debug/java/com/company/myapplication/DaggerComponent.java:65: error: cannot find symbol
    #      this.factoryProvider = BugDemonstration_Factory_Impl.create(bugDemonstrationProvider);
    #                             ^
    #  symbol:   variable BugDemonstration_Factory_Impl
    #  location: class ComponentImpl
```
