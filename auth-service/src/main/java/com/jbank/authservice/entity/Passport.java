package com.jbank.authservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String series;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String issued;

    @Column(nullable = false)
    private String registrationAddress;
}
