package com.freightfox.linkedinalumni.repository;

import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumniProfileRepository extends JpaRepository<AlumniProfileEntity, Long> {
    Optional<AlumniProfileEntity> findByLinkedinProfileUrl(String linkedinProfileUrl);
}
