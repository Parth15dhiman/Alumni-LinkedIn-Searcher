package com.freightfox.linkedinalumni.service;

import com.freightfox.linkedinalumni.dto.AlumniProfileResponse;
import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import com.freightfox.linkedinalumni.provider.AlumniSearchProvider;
import com.freightfox.linkedinalumni.repository.AlumniProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlumniSearchService {

    private final AlumniSearchProvider alumniSearchProvider;
    private final AlumniProfileRepository alumniProfileRepository;

    public AlumniSearchService(AlumniSearchProvider alumniSearchProvider, AlumniProfileRepository alumniProfileRepository) {
        this.alumniSearchProvider = alumniSearchProvider;
        this.alumniProfileRepository = alumniProfileRepository;
    }

    public List<AlumniProfileResponse> searchAlumni(AlumniSearchRequest request) {

        List<AlumniProfileEntity> fetchedProfiles = alumniSearchProvider.search(request);

        List<AlumniProfileEntity> savedProfiles = fetchedProfiles.stream()
                .map(this::saveIfNotExists)
                .collect(Collectors.toList());

        return savedProfiles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AlumniProfileResponse> getAllAlumni() {
        return alumniProfileRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AlumniProfileEntity saveIfNotExists(AlumniProfileEntity profile) {
        return alumniProfileRepository
                .findByLinkedinProfileUrl(profile.getLinkedinProfileUrl())
                .orElseGet(() -> alumniProfileRepository.save(profile));
    }

    private AlumniProfileResponse mapToResponse(AlumniProfileEntity entity) {
        return new AlumniProfileResponse(
                entity.getName(),
                entity.getCurrentRole(),
                entity.getUniversity(),
                entity.getLocation(),
                entity.getLinkedinHeadline(),
                entity.getPassoutYear()
        );
    }
}
