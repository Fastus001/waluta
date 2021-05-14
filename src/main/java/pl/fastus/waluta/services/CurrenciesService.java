package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.exceptions.NoSuchCurrencyException;
import pl.fastus.waluta.mappers.ExchangeRequestMapper;
import pl.fastus.waluta.mappers.RateMapper;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.*;
import pl.fastus.waluta.model.Exchange;
import pl.fastus.waluta.repositories.CurrenciesRepository;
import pl.fastus.waluta.repositories.ExchangeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrenciesService {

    private final CurrenciesRepository currenciesRepository;
    private final ExchangeRepository exchangeRepository;

    private final TableRequestMapper tableRequestMapper;
    private final ExchangeRequestMapper exchangeRequestMapper;
    private final RateMapper rateMapper;

    public List<RateResponse> currentExchangeRates(TableRequest toSave){
        Currencies savedCurrencies = saveCurrencies(toSave);

        return savedCurrencies
                .getRates()
                .stream()
                .map(rateMapper::toRateResponse)
                .collect(Collectors.toList());
    }

    public List<AvailableRate> getAvailableRates(TableRequest toSave){
        Currencies savedCurrencies = saveCurrencies(toSave);

        return savedCurrencies
                .getRates()
                .stream()
                .map(rateMapper::toAvailableRate)
                .collect(Collectors.toList());
    }

    public List<RateResponse> getAvailableRatesChosen(TableRequest ratesTable, List<String> params) {
        Currencies currencies = saveCurrencies(ratesTable);
        return currencies.getRates()
                .stream()
                .filter(rate -> params.contains(rate.getCode()))
                .map(rateMapper::toRateResponse)
                .collect(Collectors.toList());
    }

    private Currencies saveCurrencies(TableRequest toSave) {
        final Currencies currencies = tableRequestMapper.toCurrencies(toSave);
        return currenciesRepository.save(currencies);
    }

    public Exchange exchange(ExchangeRequest request, TableRequest tableRequest) {
        Set<RateRequest> rates = tableRequest.getRates();
        rates.add(new RateRequest("złotówki", "PLN", 1D));

        BigDecimal rateFrom = getRateMid(rates, request.getCodeFrom());
        BigDecimal rateTo = getRateMid(rates, request.getCodeTo());

        BigDecimal multiply = rateFrom.multiply(BigDecimal.valueOf(request.getAmount()));
        BigDecimal result = multiply.divide(rateTo, RoundingMode.HALF_UP);

        log.info(result.toPlainString());
        Exchange exchange = exchangeRequestMapper.toExchange(request, result.toPlainString());
        return exchangeRepository.save(exchange);
    }

    private BigDecimal getRateMid(Set<RateRequest> rates, String codeFrom) {
        return rates.stream()
                .filter(rate->rate.getCode().equals(codeFrom))
                .map(rate->BigDecimal.valueOf(rate.getMid()))
                .findFirst()
                .orElseThrow(()->new NoSuchCurrencyException("Can't exchange from this currency or the code is incorrect!"));
    }
}
