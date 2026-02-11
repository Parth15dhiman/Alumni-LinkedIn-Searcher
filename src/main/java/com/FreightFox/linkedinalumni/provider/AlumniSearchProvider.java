package com.freightfox.linkedinalumni.provider;

import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.entity.AlumniProfileEntity;

import java.util.List;

public interface AlumniSearchProvider {

    List<AlumniProfileEntity> search(AlumniSearchRequest request);

}
