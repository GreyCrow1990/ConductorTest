FROM openjdk:8

ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-darwin-4333796.zip" \
    ANDROID_HOME="/usr/local/android-sdk" \
    ANDROID_NDK_HOME="/usr/local/android-ndk" \
    ANDROID_VERSION=27 \
    ANDROID_BUILD_TOOLS_VERSION=27.0.3 \
    ANDROID_NDK_URL="https://dl.google.com/android/repository/android-ndk-r14b-darwin-x86_64.zip"

# Download Android SDK
RUN mkdir "$ANDROID_HOME" .android \
    && cd "$ANDROID_HOME" \
    && curl -o sdk.zip $SDK_URL \
    && unzip sdk.zip \
    && rm sdk.zip \
    && yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses

# Install Android Build Tool and Libraries
RUN $ANDROID_HOME/tools/bin/sdkmanager --update
RUN $ANDROID_HOME/tools/bin/sdkmanager "build-tools;${ANDROID_BUILD_TOOLS_VERSION}" \
    "platforms;android-${ANDROID_VERSION}" \
    "platform-tools"

# Download Android NDK
RUN mkdir -p "$ANDROID_NDK_HOME" .android \
    && cd "$ANDROID_NDK_HOME" \
    && curl -o ndk.zip $ANDROID_NDK_URL \
    && unzip ndk.zip \
    && rm ndk.zip


RUN mkdir /application
WORKDIR /application