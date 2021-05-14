package pl.fastus.waluta.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.waluta.model.DTO.AvailableRate;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.RateResponse;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Exchange;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Tom - 12.05.2021
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@RestController
public class CurrencyController {

    private final NbpApiService nbpApiService;
    private final CurrenciesService currenciesService;


    @GetMapping
    public List<AvailableRate> getAvailableCurrencies(){
        TableRequest ratesTable = nbpApiService.getTableA();
        return currenciesService.getAvailableRates(ratesTable);
    }

    @GetMapping("/rates")
    public List<RateResponse> getCurrentExchangeRates(){
        TableRequest ratesTable = nbpApiService.getTableA();

        return currenciesService.currentExchangeRates(ratesTable);
    }

    @GetMapping(value = "/exchange", consumes="application/json", produces = "application/json")
    public Exchange exchange(@Valid @RequestBody ExchangeRequest request) {
        TableRequest ratesTable = nbpApiService.getTableA();

        return currenciesService
                .exchange(request,ratesTable);
    }
}
