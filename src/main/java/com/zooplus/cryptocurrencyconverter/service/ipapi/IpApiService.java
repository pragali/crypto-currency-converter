package com.zooplus.cryptocurrencyconverter.service.ipapi;

import com.zooplus.cryptocurrencyconverter.client.ipapi.IpApiClient;
import com.zooplus.cryptocurrencyconverter.dto.GeoLocation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class IpApiService {

    private final IpApiClient ipApiClient;

    public IpApiService(IpApiClient ipApiClient) {
        this.ipApiClient = ipApiClient;
    }

    public GeoLocation getGeoLocation(String ipAddress) throws URISyntaxException, IOException{
        GeoLocation geoLocation = ipApiClient.getGeoLocation(ipAddress);
        return geoLocation;
    }
}
