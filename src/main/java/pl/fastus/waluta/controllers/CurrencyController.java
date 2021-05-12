package pl.fastus.waluta.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.waluta.mappers.RateToAvailableRate;
import pl.fastus.waluta.mappers.RateToRateResponseMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.AvailableRate;
import pl.fastus.waluta.model.DTO.RateResponse;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiWebService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tom - 12.05.2021
 */
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@RestController
public class CurrencyController {

    private final NbpApiWebService nbpApiWebService;
    private final CurrenciesService currenciesService;
    private final RateToRateResponseMapper rateToRateResponseMapper;
    private final RateToAvailableRate rateToAvailableRate;

    @GetMapping
    public List<AvailableRate> getAvailableCurrencies(){
        return getStreamRate()
                .map(rateToAvailableRate::mapToAvailableRateResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/rates")
    public List<RateResponse> getCurrentRates(){
        return getStreamRate().map(rateToRateResponseMapper::mapToRateResponse)
                .collect(Collectors.toList());
    }

    private Stream<Rate> getStreamRate(){
        final TableRequest tableRequest = nbpApiWebService.getTodayTableACourses().blockFirst();
        final Currencies savedCurrencies = currenciesService.saveTable(tableRequest);
        return savedCurrencies
                .getRates()
                .stream();
    }
}
