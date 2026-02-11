package com.freightfox.linkedinalumni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freightfox.linkedinalumni.dto.AlumniProfileResponse;
import com.freightfox.linkedinalumni.dto.AlumniSearchRequest;
import com.freightfox.linkedinalumni.service.AlumniSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AlumniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlumniSearchService alumniSearchService;

    @Autowired
    private ObjectMapper objectMapper;

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
