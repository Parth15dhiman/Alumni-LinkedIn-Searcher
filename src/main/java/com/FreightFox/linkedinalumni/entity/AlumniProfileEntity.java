package com.freightfox.linkedinalumni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "alumni_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"linkedin_profile_url"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlumniProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "current_role", nullable = false)
    private String currentRole;

    @Column(nullable = false)
    private String university;

    private String location;

    @Column(name = "linkedin_headline", length = 500)
    private String linkedinHeadline;

    @Column(name = "passout_year")
    private Integer passoutYear;

    @Column(name = "linkedin_profile_url", nullable = false, unique = true)
    private String linkedinProfileUrl;
}
