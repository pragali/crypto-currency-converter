
package com.zooplus.cryptocurrencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Status {


    private String timestamp;

    private Integer errorCode;

    private String errorMessage;

    private Integer elapsed;

    private Integer creditCount;

    private Object notice;

}
