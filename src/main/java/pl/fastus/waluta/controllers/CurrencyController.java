package pl.fastus.waluta.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.waluta.mappers.RateToAvailableRate;
import pl.fastus.waluta.mappers.RateMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.*;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiWebService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tom - 12.05.2021
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@RestController
public class CurrencyController {

    private final NbpApiWebService nbpApiWebService;
    private final CurrenciesService currenciesService;
    private final RateMapper rateMapper;
    private final RateToAvailableRate rateToAvailableRate;

    @GetMapping
    public List<AvailableRate> getAvailableCurrencies(){
        return getStreamRate()
                .map(rateToAvailableRate::mapToAvailableRateResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/rates")
    public List<RateResponse> getCurrentRates(){
        return getStreamRate().map(rateMapper::mapToRateResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/exchange", consumes="application/json", produces = "application/json")
    public ExchangeResponse exchange(@RequestBody ExchangeRequest request){
        return currenciesService.exchangeCurrencies(request, getTableRequest());
    }

    private Stream<Rate> getStreamRate(){
        TableRequest tableRequest = getTableRequest();
        final Currencies savedCurrencies = currenciesService.saveTable(tableRequest);
        return savedCurrencies
                .getRates()
                .stream();
    }

    private TableRequest getTableRequest() {
        TableRequest tableRequest = nbpApiWebService.getTodayTableACourses().blockFirst();
        if(tableRequest==null){
            tableRequest = nbpApiWebService.getPreviousDayTableACourses().blockFirst();
        }
        return tableRequest;
    }
}
