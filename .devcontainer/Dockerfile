FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

ARG GRADLE_VERSION=8.14.3
ARG GRADLE_SHA256=bd71102213493060956ec229d946beee57158dbd89d0e62b91bca0fa2c5f3531

# The base image contains a Yarn APT source whose signing key may be outdated.
# Yarn is not needed for these Java/Spring Boot labs.
RUN rm -f /etc/apt/sources.list.d/yarn.list \
    && rm -f /etc/apt/sources.list.d/yarn*.sources \
    && apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates curl unzip \
    && curl -fsSL --retry 3 "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o /tmp/gradle.zip \
    && echo "${GRADLE_SHA256}  /tmp/gradle.zip" | sha256sum --check \
    && unzip -q /tmp/gradle.zip -d /opt \
    && rm /tmp/gradle.zip \
    && rm -rf /var/lib/apt/lists/*

ENV GRADLE_HOME=/opt/gradle-${GRADLE_VERSION}
ENV PATH="${GRADLE_HOME}/bin:${PATH}"
