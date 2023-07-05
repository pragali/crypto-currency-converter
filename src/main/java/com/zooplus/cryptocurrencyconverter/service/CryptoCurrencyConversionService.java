package com.zooplus.cryptocurrencyconverter.service;


import com.zooplus.cryptocurrencyconverter.dto.ConvertRequest;
import com.zooplus.cryptocurrencyconverter.dto.ConvertResponse;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import com.zooplus.cryptocurrencyconverter.service.coinmarketcap.CoinMarketCapService;
import com.zooplus.cryptocurrencyconverter.service.coinmarketcap.CoinMarketCapServiceException;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiService;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CryptoCurrencyConversionService {

    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}" +
                    "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    private final CoinMarketCapService coinMarketCapService;
    private final IpApiService ipApiService;

    public CryptoCurrencyConversionService(CoinMarketCapService coinMarketCapService, IpApiService ipApiService) {
        this.coinMarketCapService = coinMarketCapService;
        this.ipApiService = ipApiService;
    }


    public ConvertResponse convertCryptoCurrency(ConvertRequest requestDTO, HttpServletRequest httpServletRequest) throws URISyntaxException, IOException, IpApiValidationException, CoinMarketCapServiceException {

        String ipAddress = getIpAddress(requestDTO, httpServletRequest);

       if (!isIpAddressValid(ipAddress)) {
            throw new IpApiValidationException("Ip address is invalid, ip address: " + ipAddress);
        }

        GeoLocation geoLocation = ipApiService.getGeoLocation(ipAddress);

        NumberFormat fiatFormatter = NumberFormat.getCurrencyInstance(new Locale(geoLocation.getLanguages(), geoLocation.getCountry_code()));

        String fiatSymbol = fiatFormatter.getCurrency().getCurrencyCode();

        Double unformattedPrice = coinMarketCapService.convert(requestDTO.getCoinSymbol(), fiatSymbol);


        ConvertResponse responseDTO = new ConvertResponse();
        responseDTO.setPrice(fiatFormatter.format(unformattedPrice));

        return responseDTO;
    }

    private boolean isIpAddressValid(String ipAddress) {
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    private String getIpAddress(ConvertRequest requestDTO, HttpServletRequest httpServletRequest) {
        String ipAddress;
        if (requestDTO.getIpAddress() == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        } else {
            ipAddress = requestDTO.getIpAddress();
        }
        return ipAddress;
    }
}
