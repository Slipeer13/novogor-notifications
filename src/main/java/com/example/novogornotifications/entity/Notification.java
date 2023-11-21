package com.example.novogornotifications.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String info;

    private LocalDate dateDisabling;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;
}
