package com.freightfox.linkedinalumni.dto;

import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PhantomResultMapper {

    public List<AlumniProfileEntity> map(PhantomResultResponse response) {

        if (response.result() == null) {
            return List.of();
        }

        return response.result().stream()
                .map(this::toEntity)
                .toList();
    }

    private AlumniProfileEntity toEntity(Map<String, Object> row) {

        AlumniProfileEntity entity = new AlumniProfileEntity();

        entity.setName((String) row.getOrDefault("fullName", null));
        entity.setCurrentRole((String) row.getOrDefault("currentJob", null));
        entity.setUniversity((String) row.getOrDefault("university", null));
        entity.setLocation((String) row.getOrDefault("location", null));
        entity.setLinkedinHeadline((String) row.getOrDefault("headline", null));
        entity.setPassoutYear(parseYear(row.get("passoutYear")));

        return entity;
    }

    private Integer parseYear(Object value) {
        if (value instanceof Number n) {
            return n.intValue();
        }
        return null;
    }
}
