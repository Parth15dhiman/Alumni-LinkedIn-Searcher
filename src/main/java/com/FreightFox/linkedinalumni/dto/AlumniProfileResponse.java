package com.freightfox.linkedinalumni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlumniProfileResponse {private String name;
    private String currentRole;
    private String university;
    private String location;
    private String linkedinHeadline;
    private Integer passoutYear;
}
