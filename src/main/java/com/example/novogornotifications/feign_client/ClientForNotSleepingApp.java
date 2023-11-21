package com.example.novogornotifications.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "client", url="${url.app}")
public interface ClientForNotSleepingApp {

    @GetMapping("/")
    void checkForNotSleeping() throws Exception;
}
