package pl.fastus.waluta.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.waluta.model.DTO.TableDTO;
import pl.fastus.waluta.services.NbpApiWebService;
import pl.fastus.waluta.services.CurrenciesService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom - 12.05.2021
 */
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@RestController
public class CurrencyController {

    private final NbpApiWebService nbpApiWebService;
    private final CurrenciesService currenciesService;

    @GetMapping
    public List<String> getAvailableCurrencies(){
        final TableDTO tableDTO = nbpApiWebService.getTodayTableACourses().blockFirst();
        assert tableDTO != null;
        return tableDTO.getRates()
                .stream()
                .map(rateDTO -> rateDTO.getCurrency()+", code: "+ rateDTO.getCode())
                .collect(Collectors.toList());
    }
}
