package com.freightfox.linkedinalumni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freightfox.linkedinalumni.controller.AlumniController;
import com.freightfox.linkedinalumni.dto.AlumniProfileResponse;
import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.service.AlumniSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AlumniControllerTest {

    private MockMvc mockMvc;
    private AlumniSearchService alumniSearchService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        alumniSearchService = mock(AlumniSearchService.class);
        AlumniController alumniController = new AlumniController();

        mockMvc = MockMvcBuilders
                .standaloneSetup(alumniController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void searchAlumni_shouldReturn200AndData() throws Exception {

        AlumniProfileResponse response =
                new AlumniProfileResponse(
                        "John Doe",
                        "Software Engineer",
                        "XYZ University",
                        "New York",
                        "Backend Engineer",
                        2020
                );

        when(alumniSearchService.searchAlumni(any()))
                .thenReturn(List.of(response));

        AlumniSearchRequest request = new AlumniSearchRequest();
        request.setUniversity("XYZ University");
        request.setDesignation("Software Engineer");

        mockMvc.perform(post("/api/alumni/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].currentRole").value("Software Engineer"));
    }
}
