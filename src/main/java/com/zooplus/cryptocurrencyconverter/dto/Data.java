
package com.zooplus.cryptocurrencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@lombok.Data
public class Data {


    private Integer id;

    private String symbol;

    private String name;

    private Integer amount;

    private String last_updated;

    private Map quote;

}
