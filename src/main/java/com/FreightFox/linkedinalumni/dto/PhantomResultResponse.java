package com.freightfox.linkedinalumni.dto;

import java.util.List;
import java.util.Map;

public record PhantomResultResponse(
        String status,
        List<Map<String, Object>> result
) {
}
