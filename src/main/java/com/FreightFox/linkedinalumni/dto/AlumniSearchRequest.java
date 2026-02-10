package com.FreightFox.linkedinalumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlumniSearchRequest {
    @NotBlank(message = "University name must not be blank")
    private String university;

    @NotBlank(message = "Designation must not be blank")
    private String designation;

    private Integer passoutYear;
}
