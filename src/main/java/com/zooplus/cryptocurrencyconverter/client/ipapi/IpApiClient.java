package com.zooplus.cryptocurrencyconverter.client.ipapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
@Component
public class IpApiClient {


    private final String ipApiEndpoint;

    private final String format;

    public IpApiClient(@Value("${ip-api.url}") String ipApiEndpoint,  @Value("${ip-api.format}") String format) {
        this.ipApiEndpoint = ipApiEndpoint;
        this.format = format;
    }


    public  GeoLocation getGeoLocation(String ipAddress) throws IOException {
        String url = ipApiEndpoint + ipAddress + "/"+format;
        //String url = "https://ipapi.co/208.67.222.222/json";
        URL ipapi = new URL(url);


        URLConnection c = ipapi.openConnection();
        c.setRequestProperty("User-Agent", "java-ipapi-v1.02");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(c.getInputStream())
        );
        String line;
        String json= new String();
        while ((line = reader.readLine()) != null)
        {
            json+=line;
        }

        reader.close();

        return objectMapper(json);

    }

    private  GeoLocation objectMapper(String json) throws JsonProcessingException {
        // ObjectMapper instantiation

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        GeoLocation location = objectMapper.readValue(json,GeoLocation.class);
        //System.out.println(location);
        return location;
    }

}