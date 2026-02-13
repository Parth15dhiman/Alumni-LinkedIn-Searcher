package com.freightfox.linkedinalumni.dto;

import java.util.Map;

public record PhantomLaunchRequest(
        String id,
        Map<String, Object> arguments
) {}
