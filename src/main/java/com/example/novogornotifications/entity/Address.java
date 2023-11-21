package com.example.novogornotifications.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "member_address", joinColumns = @JoinColumn(name = "address_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Member> memberList;

    @OneToMany(mappedBy = "address", cascade = {CascadeType.ALL})
    private List<Notification> notifications;

    public Address(String name) {
        this.name = name;
        notifications = new ArrayList<>();
        memberList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(this.getId(), address.getId()) && Objects.equals(getName(), address.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), getName());
    }
}
