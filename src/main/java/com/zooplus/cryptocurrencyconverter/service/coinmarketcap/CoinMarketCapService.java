package com.zooplus.cryptocurrencyconverter.service.coinmarketcap;



import com.google.common.collect.ImmutableMap;
import com.zooplus.cryptocurrencyconverter.client.coinmarket.CoinMarketCapClient;
import com.zooplus.cryptocurrencyconverter.dto.CoinMarketResponse;
import com.zooplus.cryptocurrencyconverter.dto.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import java.util.Map;

@Service
public class CoinMarketCapService {

    public static final String UNIT_AMOUNT = "1";
    private final CoinMarketCapClient coinMarketCapClient;


    public CoinMarketCapService(CoinMarketCapClient coinMarketCapClient) {
        this.coinMarketCapClient = coinMarketCapClient;
    }

    public Double convert(String coinSymbol, String fiatSymbol) throws URISyntaxException, IOException, CoinMarketCapServiceException {
        if (StringUtils.isEmpty(coinSymbol) || StringUtils.isEmpty(fiatSymbol)) {
            throw new CoinMarketCapServiceException("coin symbol or fiat symbol are null");
        }

        Map<String, String> paramMap = ImmutableMap.of("symbol", coinSymbol, "convert",fiatSymbol,"amount","1");
        CoinMarketResponse coinMarketCapResponseDTO = coinMarketCapClient.getConversionPriceV1(paramMap);
        Map map = (Map) coinMarketCapResponseDTO.getData().get(0).getQuote().get(fiatSymbol);
        Currency currency = new Currency(Double.parseDouble(map.get("price").toString()),map.get("last_updated").toString());

        return currency.getPrice();

    }


}
