package com.zooplus.cryptocurrencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.cryptocurrencyconverter.dto.CoinMarketResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinMarketCap {

    private static final String APPLICATION_JSON_HEADER = "application/json";
    private static final String API_KEY_KEY = "X-CMC_PRO_API_KEY";

    @Value("${coin-market-cap.url.price-conversion.url}")
    static String coinMarketApiEndpoint;

    @Value("${coin-market-cap.api-key}")
    static String coinMarketApikey;

    public static void main(String[] args) throws IOException {
        getConversionPrice();

    }

    public static String getConversionPrice() throws IOException {
        String url = "https://pro-api.coinmarketcap.com/v2/tools/price-conversion";
        String params = "?symbol="+"ETH";
        params += "&convert=USD";
        params += "&amount=1";
        url+=params;
        URL coinapi = new URL(url);

        List<String> headers = new ArrayList<>();
        headers.add(APPLICATION_JSON_HEADER);
        headers.add(API_KEY_KEY);

        HttpURLConnection c = (HttpURLConnection)coinapi.openConnection();

        c.setRequestProperty("ACCEPT", APPLICATION_JSON_HEADER);
        c.setRequestProperty("Content-Type", APPLICATION_JSON_HEADER);
        c.setRequestProperty(API_KEY_KEY,"66810199-df61-40f2-8c8a-9bff68327df1");
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
        CoinMarketResponse response = objectMapper.readValue(json, CoinMarketResponse.class);

        response.getData().stream().forEach(e-> System.out.println(e));

        Map quote = response.getData().get(0).getQuote();
        Map<String,Double> quoteList =(Map)quote.get("USD");
        System.out.println(quoteList.get("price"));
        System.out.println(quoteList.get("last_updated"));


    }
}
