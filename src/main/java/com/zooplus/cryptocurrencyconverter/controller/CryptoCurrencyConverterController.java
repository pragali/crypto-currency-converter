package com.zooplus.cryptocurrencyconverter.controller;


import com.zooplus.cryptocurrencyconverter.dto.ConvertRequest;
import com.zooplus.cryptocurrencyconverter.dto.ConvertResponse;
import com.zooplus.cryptocurrencyconverter.service.CryptoCurrencyConversionService;
import com.zooplus.cryptocurrencyconverter.service.coinmarketcap.CoinMarketCapServiceException;
import com.zooplus.cryptocurrencyconverter.service.ipapi.IpApiValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CryptoCurrencyConverterController {

    private final CryptoCurrencyConversionService cryptoCurrencyConversionService;

    public CryptoCurrencyConverterController(CryptoCurrencyConversionService cryptoCurrencyConversionService) {
        this.cryptoCurrencyConversionService = cryptoCurrencyConversionService;
    }

    @GetMapping("/convert")
    public String convert(@ModelAttribute ConvertRequest requestDTO, HttpServletRequest httpServletRequest, Model model) {

        model.addAttribute("requestDTO", requestDTO);
        ConvertResponse responseDTO = new ConvertResponse();

        // landing
        if (requestDTO.getCoinSymbol() == null) {
            model.addAttribute("responseDTO", responseDTO);
            return "convert";
        }

        try {
            responseDTO = cryptoCurrencyConversionService.convertCryptoCurrency(requestDTO, httpServletRequest);
            model.addAttribute("responseDTO", responseDTO);
        } catch (IpApiValidationException | URISyntaxException | IOException | CoinMarketCapServiceException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "convert";
    }
}
