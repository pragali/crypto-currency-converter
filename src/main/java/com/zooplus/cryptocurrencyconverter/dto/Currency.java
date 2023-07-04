
package com.zooplus.cryptocurrencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Currency {


    private Double price;

    private String lastUpdated;
}
