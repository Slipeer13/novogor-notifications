package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;
import com.example.novogornotifications.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisableServiceImpl implements DisableService{

    @Autowired
    private MemberService memberService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private formatService formatService;
    @Autowired
    private CheckAddressService checkAddressService;


    @Override
    @Transactional
    public String getConnect(long chatId, String addressName) {
        Member member = memberService.getMember(chatId).orElse(null);
        String result;
        if (member == null) {
            member = memberService.save(chatId);
        }

            addressName = checkAddressService.formatAddress(addressName);
            Optional<Address> addressOptional = addressService.get(addressName);
            Address address;
            if(addressOptional.isEmpty()) {
                address = addressService.save(addressName);
            } else {
                address = addressOptional.get();
            }
            List<Member> memberList = address.getMemberList();
            if(memberList.contains(member)) {
                result = "вы уже подключены к адресу: " + addressName;
            } else {
                memberList.add(member);
                addressService.save(address);
                result = "вы подключены к адресу: " + addressName;
            }


        return result;
    }

    @Override
    @Transactional
    public String getDisconnect(long chatId, String addressName) {
        Member member = memberService.getMember(chatId).orElse(null);
        String result;
        if (member != null) {

            addressName = checkAddressService.formatAddress(addressName);
                Optional<Address> addressOptional = addressService.get(addressName);
                if(addressOptional.isPresent()) {
                    List<Member> memberList = addressOptional.get().getMemberList();
                    if(memberList.contains(member)) {
                        memberList.remove(member);
                        result = "вы отключены от адреса: " + addressName;
                    } else {
                        result = "вы не подключены к такому адресу";
                    }
                } else {
                    result = "такого адреса нет в бд";
                }

        } else {
            result = "вы не подписаны на отключения";
        }
        return result;
    }

    @Override
    @Transactional
    public String getInfoDisable(long chatId) {
        Member member = memberService.getMember(chatId).orElse(null);
        StringBuilder result = new StringBuilder();
        LocalDate nowDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        if (member != null && member.getAddressList().isEmpty() == false) {
            List<Address> addressList = member.getAddressList();
            result.append("Подключены адреса: ");
            addressList.forEach(a-> result.append("\n").append(a.getName()));
            String disable = addressList.stream().flatMap(a-> a.getNotifications().stream()
                    .filter(n-> n.getDateDisabling().isEqual(nowDate) || n.getDateDisabling().isAfter(nowDate)).map(o-> "\n" + a.getName() + ", дата: "
                    + o.getDateDisabling().format(formatService.getFormatter()) + " " + o.getInfo())).collect(Collectors.joining());
            if(disable.isEmpty()) {
                result.append("\nближайших отключений воды нет");
            } else {
                result.append("\nБлижайшие отключения воды : ").append(disable);
            }
        } else {
            result.append("вы не подписаны на отключения");
        }

        return result.toString();
    }


}
