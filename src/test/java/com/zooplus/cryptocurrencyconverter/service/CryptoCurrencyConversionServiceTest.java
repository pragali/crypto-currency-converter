package com.zooplus.cryptocurrencyconverter.service;


import com.zooplus.cryptocurrencyconverter.dto.ConvertRequest;
import com.zooplus.cryptocurrencyconverter.dto.ConvertResponse;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import com.zooplus.cryptocurrencyconverter.service.coinmarketcap.CoinMarketCapService;
import com.zooplus.cryptocurrencyconverter.service.coinmarketcap.CoinMarketCapServiceException;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiService;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CryptoCurrencyConversionServiceTest {

    private CryptoCurrencyConversionService cryptoCurrencyConversionService;

    @Mock
    private CoinMarketCapService coinMarketCapService;

    @Mock
    private IpApiService ipApiService;


    @BeforeEach
    public void setUp() {
        cryptoCurrencyConversionService = new CryptoCurrencyConversionService(coinMarketCapService, ipApiService);
    }

    @Test
    public void shouldSuccess() throws   URISyntaxException, IOException, CoinMarketCapServiceException, IpApiValidationException {
        ConvertRequest requestDTO = new ConvertRequest();
        requestDTO.setCoinSymbol("BTC");
        requestDTO.setIpAddress("0.0.0.0");

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setCountry_code("TR");
        geoLocation.setLanguages("tr");

        when(ipApiService.getGeoLocation(requestDTO.getIpAddress())).thenReturn(geoLocation);
        when(coinMarketCapService.convert(requestDTO.getCoinSymbol(), "TRY")).thenReturn(1d);

        ConvertResponse responseDTO = cryptoCurrencyConversionService.convertCryptoCurrency(requestDTO, null);

        assertEquals(responseDTO.getPrice(), "₺1,00");
    }
}