package com.freightfox.linkedinalumni.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "phantombuster")
public record PhantomBusterProperties(
        String baseUrl,
        String apiKey,
        String phantomId,
        int timeoutSeconds
) {}
