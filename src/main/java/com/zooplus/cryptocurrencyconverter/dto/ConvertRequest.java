package com.zooplus.cryptocurrencyconverter.dto;

import lombok.Data;

@Data
public class ConvertRequest {

    private String coinSymbol;
    private String ipAddress;
}
