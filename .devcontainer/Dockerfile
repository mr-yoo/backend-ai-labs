FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

# The base image contains a Yarn APT source whose signing key may be outdated.
# Yarn is not needed for these Java/Spring Boot labs.
RUN rm -f /etc/apt/sources.list.d/yarn.list \
    && rm -f /etc/apt/sources.list.d/yarn*.sources \
    && apt-get update \
    && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*
