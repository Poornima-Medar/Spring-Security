package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;

    @ManyToOne
    private UserEntity user;
}
