package com.example.novogornotifications.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "member_address", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addressList;


    public Member(Long id) {
        this.id = id;
    }

    public void call(Notification notification) {

    }
}
