package com.freightfox.linkedinalumni.controller;

import com.freightfox.linkedinalumni.dto.AlumniProfileResponse;
import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.service.AlumniSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {

    @Autowired
    private AlumniSearchService alumniSearchService;
    @PostMapping("/search")
    public ResponseEntity<List<AlumniProfileResponse>> searchAlumni(
            @Valid @RequestBody AlumniSearchRequest request) {

        List<AlumniProfileResponse> response = alumniSearchService.searchAlumni(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlumniProfileResponse>> getAllAlumni() {
        return ResponseEntity.ok(alumniSearchService.getAllAlumni());
    }
}
