#!/usr/bin/env bash
set -euo pipefail

ROOT="$(pwd)"
WORK="${RUNNER_TEMP:-/tmp}/maps-review-agent-build"
PROJECT="$WORK/project/maps_review_agent_motorola"
ZIP="$WORK/maps_review_agent_motorola.zip"
B64="$WORK/maps_review_agent_motorola.b64"

rm -rf "$WORK"
mkdir -p "$WORK/project" "$ROOT/dist"

cat "$ROOT"/apkbuild/chunks/part*.txt > "$B64"
base64 -d "$B64" > "$ZIP"
unzip -t "$ZIP"
unzip -q "$ZIP" -d "$WORK/project"
test -f "$PROJECT/app/build.gradle"

# AGP 8.6.x requires Java 17. GitHub-hosted runners expose JAVA_HOME_17_X64.
if [[ -n "${JAVA_HOME_17_X64:-}" && -x "${JAVA_HOME_17_X64}/bin/java" ]]; then
  export JAVA_HOME="$JAVA_HOME_17_X64"
elif [[ -x /usr/lib/jvm/temurin-17-jdk-amd64/bin/java ]]; then
  export JAVA_HOME=/usr/lib/jvm/temurin-17-jdk-amd64
elif [[ -x /usr/lib/jvm/java-17-openjdk-amd64/bin/java ]]; then
  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
else
  echo "Java 17 not found on runner" >&2
  exit 17
fi
export PATH="$JAVA_HOME/bin:$PATH"
java -version

# Android SDK is normally preinstalled on GitHub-hosted Ubuntu runners.
SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-/usr/local/lib/android/sdk}}"
export ANDROID_SDK_ROOT="$SDK_ROOT"
export ANDROID_HOME="$SDK_ROOT"

SDKMANAGER=""
for c in \
  "$SDK_ROOT/cmdline-tools/latest/bin/sdkmanager" \
  "$SDK_ROOT/cmdline-tools/16.0/bin/sdkmanager" \
  "$SDK_ROOT/cmdline-tools/12.0/bin/sdkmanager" \
  "$SDK_ROOT/tools/bin/sdkmanager"; do
  if [[ -x "$c" ]]; then SDKMANAGER="$c"; break; fi
done

if [[ -z "$SDKMANAGER" ]]; then
  echo "sdkmanager not found; installing Android command-line tools"
  mkdir -p "$SDK_ROOT/cmdline-tools"
  curl -fL --retry 3 -o "$WORK/cmdline-tools.zip" \
    https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
  unzip -q "$WORK/cmdline-tools.zip" -d "$WORK/cmdline-tools"
  rm -rf "$SDK_ROOT/cmdline-tools/latest"
  mkdir -p "$SDK_ROOT/cmdline-tools/latest"
  cp -a "$WORK/cmdline-tools/cmdline-tools/." "$SDK_ROOT/cmdline-tools/latest/"
  SDKMANAGER="$SDK_ROOT/cmdline-tools/latest/bin/sdkmanager"
fi

yes | "$SDKMANAGER" --licenses >/dev/null 2>&1 || true
"$SDKMANAGER" "platform-tools" "platforms;android-35" "build-tools;35.0.0"

# Use Gradle 8.7, required by this project.
GRADLE_BIN=""
if command -v gradle >/dev/null 2>&1 && gradle --version | grep -q 'Gradle 8\.7'; then
  GRADLE_BIN="$(command -v gradle)"
else
  curl -fL --retry 3 -o "$WORK/gradle-8.7-bin.zip" \
    https://services.gradle.org/distributions/gradle-8.7-bin.zip
  unzip -q "$WORK/gradle-8.7-bin.zip" -d "$WORK"
  GRADLE_BIN="$WORK/gradle-8.7/bin/gradle"
fi

cd "$PROJECT"
"$GRADLE_BIN" --no-daemon --stacktrace assembleDebug

APK="$PROJECT/app/build/outputs/apk/debug/app-debug.apk"
test -s "$APK"
unzip -t "$APK"
unzip -l "$APK" | grep -E 'AndroidManifest.xml|classes.dex|resources.arsc'
sha256sum "$APK" | tee "$ROOT/dist/MapsReviewAgent-Motorola.sha256"

# Existing repository workflow uploads dist/manola.jar. Store the APK bytes there.
cp "$APK" "$ROOT/dist/manola.jar"
cp "$APK" "$ROOT/dist/MapsReviewAgent-Motorola.apk"

echo "APK_READY=$ROOT/dist/MapsReviewAgent-Motorola.apk"
