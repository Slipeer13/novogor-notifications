package com.example.novogornotifications.repository;

import com.example.novogornotifications.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Member, Long> {
}
