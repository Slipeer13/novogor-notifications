package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Member;
import com.example.novogornotifications.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MembersRepository membersRepository;

    @Override
    public Member save(long id) {
        return membersRepository.save(new Member(id));
    }

    @Override
    public Optional<Member> getMember(long id) {
        return membersRepository.findById(id);
    }

    @Override
    public Member save(Member member) {
        return membersRepository.save(member);
    }


}
