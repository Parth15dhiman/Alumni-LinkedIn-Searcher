package com.freightfox.linkedinalumni.provider;

import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("mock")
public class MockAlumniSearchProvider implements  AlumniSearchProvider{
    @Override
    public List<AlumniProfileEntity> search(AlumniSearchRequest request) {
        AlumniProfileEntity profile = new AlumniProfileEntity();
        profile.setName("John Doe");
        profile.setCurrentRole(request.getDesignation());
        profile.setUniversity(request.getUniversity());
        profile.setLocation("New York, USA");
        profile.setLinkedinHeadline("Software Engineer passionate about backend systems");
        profile.setPassoutYear(request.getPassoutYear());
        profile.setLinkedinProfileUrl("https://linkedin.com/in/johndoe");

        return List.of(profile);
    }
}
