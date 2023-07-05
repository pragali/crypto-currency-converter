package com.zooplus.cryptocurrencyconverter.client.coinmarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.zooplus.cryptocurrencyconverter.dto.CoinMarketResponse;
import com.zooplus.cryptocurrencyconverter.dto.Currency;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class CoinMarketCapClient {

    private static final String APPLICATION_JSON_HEADER = "application/json";
    private static final String API_KEY_KEY = "X-CMC_PRO_API_KEY";


     private final String coinMarketApiEndpoint;


     private final String coinMarketApikey;

    public CoinMarketCapClient( @Value("${coin-market-cap.url.price-conversion.url}") String coinMarketApiEndpoint,@Value("${coin-market-cap.api-key}") String coinMarketApikey) {
        this.coinMarketApiEndpoint = coinMarketApiEndpoint;
        this.coinMarketApikey = coinMarketApikey;
    }

    public  CoinMarketResponse getConversionPriceV1(Map<String, String> paramMap) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(coinMarketApiEndpoint);
        //URIBuilder uriBuilder = new URIBuilder(coinMarketApiEndpoint);
        uriBuilder.addParameters(paramMap.entrySet()
                .stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(toList()));

        URL coinapi = new URL(uriBuilder.toString());

        HttpURLConnection c =(HttpURLConnection)coinapi.openConnection(); c.setRequestProperty("ACCEPT", APPLICATION_JSON_HEADER);
        c.setRequestProperty("Content-Type", APPLICATION_JSON_HEADER);
        c.setRequestProperty(API_KEY_KEY,coinMarketApikey);
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
        System.out.println(objectMapper(json));
        return objectMapper(json);


    }



    private  CoinMarketResponse objectMapper(String json) throws JsonProcessingException {
        // ObjectMapper instantiation

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        CoinMarketResponse response = objectMapper.readValue(json, CoinMarketResponse.class);

        return response;


    }
}
