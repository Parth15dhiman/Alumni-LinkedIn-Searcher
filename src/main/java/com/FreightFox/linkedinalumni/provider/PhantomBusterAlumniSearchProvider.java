package com.freightfox.linkedinalumni.provider;

import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;
import com.freightfox.linkedinalumni.provider.AlumniSearchProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("phantombuster")
public class PhantomBusterAlumniSearchProvider implements AlumniSearchProvider {
    @Override
    public List<AlumniProfileEntity> search(AlumniSearchRequest request) {
        throw new UnsupportedOperationException("PhantomBuster integration not implemented yet");
    }
}
