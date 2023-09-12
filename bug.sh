#!/usr/bin/env bash
target_file=app/src/main/java/com/company/myapplication/Target.kt

echo "::group::Initial compilation.."
./gradlew :app:assembleDebug
echo "::endgroup::"

echo "::group::Modifying source.."
cat $target_file
sed -i.bak 's/\/\///g' "$target_file"

cat $target_file
echo "::endgroup::"

./gradlew :app:assembleDebug --stacktrace

#android-bugs/app/build/generated/ksp/debug/java/com/company/myapplication/DaggerComponent.java:65: error: cannot find symbol
#      this.factoryProvider = BugDemonstration_Factory_Impl.create(bugDemonstrationProvider);
#                             ^
#  symbol:   variable BugDemonstration_Factory_Impl
#  location: class ComponentImpl

