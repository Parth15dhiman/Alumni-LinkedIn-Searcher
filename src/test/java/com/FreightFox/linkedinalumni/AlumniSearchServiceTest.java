package com.freightfox.linkedinalumni;

import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import com.freightfox.linkedinalumni.provider.AlumniSearchProvider;
import com.freightfox.linkedinalumni.repository.AlumniProfileRepository;
import com.freightfox.linkedinalumni.service.AlumniSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AlumniSearchServiceTest {

    private AlumniSearchProvider alumniSearchProvider;
    private AlumniProfileRepository alumniProfileRepository;
    private AlumniSearchService alumniSearchService;

    @BeforeEach
    void setUp() {
        alumniSearchProvider = mock(AlumniSearchProvider.class);
        alumniProfileRepository = mock(AlumniProfileRepository.class);

        alumniSearchService = new AlumniSearchService(
                alumniSearchProvider,
                alumniProfileRepository
        );
    }

    @Test
    void searchAlumni_shouldSaveAndReturnProfiles() {

        AlumniSearchRequest request = new AlumniSearchRequest();
        request.setUniversity("XYZ University");
        request.setDesignation("Software Engineer");

        AlumniProfileEntity profile = new AlumniProfileEntity();
        profile.setName("John Doe");
        profile.setLinkedinProfileUrl("https://linkedin.com/in/johndoe");

        when(alumniSearchProvider.search(any()))
                .thenReturn(List.of(profile));

        when(alumniProfileRepository.findByLinkedinProfileUrl(any()))
                .thenReturn(Optional.empty());

        when(alumniProfileRepository.save(any()))
                .thenReturn(profile);

        var result = alumniSearchService.searchAlumni(request);

        assertEquals(1, result.size());
        verify(alumniProfileRepository, times(1)).save(any());
    }

    @Test
    void searchAlumni_shouldNotSaveDuplicateProfiles() {

        AlumniProfileEntity profile = new AlumniProfileEntity();
        profile.setLinkedinProfileUrl("https://linkedin.com/in/johndoe");

        when(alumniSearchProvider.search(any()))
                .thenReturn(List.of(profile));

        when(alumniProfileRepository.findByLinkedinProfileUrl(any()))
                .thenReturn(Optional.of(profile));

        alumniSearchService.searchAlumni(new AlumniSearchRequest());

        verify(alumniProfileRepository, never()).save(any());
    }
}
