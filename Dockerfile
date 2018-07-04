FROM openjdk:8

ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-darwin-4333796.zip" \
    ANDROID_SDK="/usr/local/android-sdk" \
    ANDROID_NDK="/usr/local/android-ndk" \
    ANDROID_VERSION=27 \
    ANDROID_BUILD_TOOLS_VERSION=27.0.3 \
    ANDROID_NDK_URL="https://dl.google.com/android/repository/android-ndk-r14b-darwin-x86_64.zip"


# Use machine cache Android SDK
RUN mkdir -p $ANDROID_SDK
VOLUME $ANDROID_SDK

# Use machine cache gradle
VOLUME /root/.gradle

RUN mkdir /application
WORKDIR /application

# Download Android SDK
RUN mkdir -p "$ANDROID_SDK" .android \
    && cd "$ANDROID_SDK" \
    && curl -o sdk.zip $SDK_URL \
    && unzip sdk.zip \
    && rm sdk.zip \
    && yes | $ANDROID_SDK/tools/bin/sdkmanager --licenses

# Download Android NDK
RUN mkdir -p "$ANDROID_NDK" .android \
    && cd "$ANDROID_NDK" \
    && curl -o ndk.zip $ANDROID_NDK_URL \
    && unzip ndk.zip \
    && rm ndk.zip


# Install Android Build Tool and Libraries
RUN $ANDROID_SDK/tools/bin/sdkmanager --update
RUN $ANDROID_SDK/tools/bin/sdkmanager "build-tools;$ANDROID_BUILD_TOOLS_VERSION" \
    "platforms;android-$ANDROID_VERSION" \
    "platform-tools"