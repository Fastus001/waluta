package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.mappers.ExchangeMapper;
import pl.fastus.waluta.mappers.RateMapper;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.ExchangeResponse;
import pl.fastus.waluta.model.DTO.RateRequest;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.repositories.TableRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrenciesService {

    private final TableRequestMapper tableRequestMapper;
    private final TableRepository repository;
    private final ExchangeMapper exchangeMapper;

    public Currencies saveTable(TableRequest toSave){
        final Currencies currencies = tableRequestMapper.tableRequestToCurrencies(toSave);
        return repository.save(currencies);
    }

    public ExchangeResponse exchangeCurrencies(ExchangeRequest request, TableRequest tableRequest){
        Set<RateRequest> rates = tableRequest.getRates();
        rates.add(new RateRequest("złotówki", "PLN", 1D));

        BigDecimal rateFrom = getRateMid(rates, request.getCodeFrom());
        BigDecimal rateTo = getRateMid(rates, request.getCodeTo());
        BigDecimal multiply = rateFrom.multiply(BigDecimal.valueOf(request.getAmount()));
        BigDecimal result = multiply.divide(rateTo, RoundingMode.HALF_UP);

        log.info(result.toPlainString());
        return exchangeMapper.toExchangeResponse(request, result.toPlainString());
    }

    private BigDecimal getRateMid(Set<RateRequest> rates, String codeFrom) {
        return rates.stream()
                .filter(rate->rate.getCode().equals(codeFrom))
                .map(rate->BigDecimal.valueOf(rate.getMid()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
