package com.example.novogornotifications.service;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class formatServiceImpl implements formatService {

    @Override
    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }
}
