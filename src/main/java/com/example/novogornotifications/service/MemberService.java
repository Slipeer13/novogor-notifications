package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Member;
import com.example.novogornotifications.entity.Notification;

import java.util.Optional;

public interface MemberService {

    Member save(long id);

    Optional<Member> getMember(long id);

    Member save(Member member);

}
