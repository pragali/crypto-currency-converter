package com.zooplus.cryptocurrencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinMarketResponse {


    private Status status;

    private List<Data> data;
}
