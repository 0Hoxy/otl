package com.otl.air.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-API-KEY.properties")
class DFlightServiceImplTest {

    @Value("${service_key}")
    private String serviceKey;

    @Test
    public void test() {
    System.out.println("Service Key: " + serviceKey);

    }
}