package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.exceptions.NoSuchCurrencyException;
import pl.fastus.waluta.mappers.ExchangeMapper;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.ExchangeResponse;
import pl.fastus.waluta.model.DTO.RateRequest;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Exchange;
import pl.fastus.waluta.repositories.CurrenciesRepository;
import pl.fastus.waluta.repositories.ExchangeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrenciesService {

    private final CurrenciesRepository currenciesRepository;
    private final ExchangeRepository exchangeRepository;
    private final TableRequestMapper tableRequestMapper;
    private final ExchangeMapper exchangeMapper;

    public Currencies saveTable(TableRequest toSave){
        final Currencies currencies = tableRequestMapper.tableRequestToCurrencies(toSave);
        return currenciesRepository.save(currencies);
    }

    public Exchange exchangeCurrencies(ExchangeRequest request, TableRequest tableRequest) {
        Set<RateRequest> rates = tableRequest.getRates();
        rates.add(new RateRequest("złotówki", "PLN", 1D));

        BigDecimal rateFrom = getRateMid(rates, request.getCodeFrom());
        BigDecimal rateTo = getRateMid(rates, request.getCodeTo());

        BigDecimal multiply = rateFrom.multiply(BigDecimal.valueOf(request.getAmount()));
        BigDecimal result = multiply.divide(rateTo, RoundingMode.HALF_UP);

        log.info(result.toPlainString());
        Exchange exchange = exchangeMapper.toExchange(request, result.toPlainString());
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
