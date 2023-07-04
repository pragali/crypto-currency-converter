package com.zooplus.cryptocurrencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Ipapi {

   /* https://ipapi.co/ip/
    https://ipapi.co/city/
    https://ipapi.co/country/
    https://ipapi.co/timezone/
    https://ipapi.co/languages/
    https://ipapi.co/currency/*/

    @Value("${ip-api.url}")
    static String ipApiEndpoint;

    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}" +
                    "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";

    public static void main(String[] args) {
        try {
           getGeoLocation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getGeoLocation() throws IOException {
        URL ipapi = new URL("https://ipapi.co/87.123.247.221/json");

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
        objectMapper(json);
        reader.close();

        return line;

    }

    private static void objectMapper(String json) throws JsonProcessingException {
        // ObjectMapper instantiation

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        GeoLocation location = objectMapper.readValue(json,GeoLocation.class);
        System.out.println(location);

    }

}