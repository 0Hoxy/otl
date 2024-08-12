package com.otl.air.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
class DFlightServiceImplTest {

    @Value("${service_key}")
    private String serviceKey;

    @Test
    public void test() {
    System.out.println("Service Key: " + serviceKey);

    }
}