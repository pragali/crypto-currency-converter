package com.zooplus.cryptocurrencyconverter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.cryptocurrencyconverter.client.ipapi.IpApiClient;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IpApiServiceTest {

    public static final String CA_IP_ADDRESS = "192.206.151.131";
    @Value("${ip-api.url}") String ipApiEndpoint;
    @Value("${ip-api.format}") String geoLocationFormatPath;
    private IpApiService ipApiService;

    @BeforeEach
    public void setUp() {
        IpApiClient ipApiClient = new IpApiClient(ipApiEndpoint, geoLocationFormatPath);
        ipApiService = new IpApiService(ipApiClient);
    }

    @Test
    public void shouldSuccess_ipApiCall() throws URISyntaxException, IOException {
        GeoLocation geoLocation = ipApiService.getGeoLocation(CA_IP_ADDRESS);
        assertEquals(geoLocation.getCountry_code(), "CA");
        assertEquals(geoLocation.getLanguages(), "en-CA,fr-CA,iu");
    }


}