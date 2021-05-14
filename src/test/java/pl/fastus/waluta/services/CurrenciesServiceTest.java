package pl.fastus.waluta.services;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.waluta.mappers.ExchangeRequestMapper;
import pl.fastus.waluta.mappers.RateMapper;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.*;
import pl.fastus.waluta.model.Exchange;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.repositories.CurrenciesRepository;
import pl.fastus.waluta.repositories.ExchangeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrenciesServiceTest {

    @Mock
    CurrenciesRepository currenciesRepository;
    @Mock
    ExchangeRepository exchangeRepository;
    @Mock
    TableRequestMapper tableRequestMapper;
    @Mock
    RateMapper rateMapper;
    @Mock
    ExchangeRequestMapper exchangeRequestMapper;

    @InjectMocks
    CurrenciesService service;

    TableRequest tableRequest;
    Currencies currencies;

    @BeforeEach
    void setUp(){
        tableRequest = getTableRequest();
        currencies = getCurrencies();
    }


    @Test
    void testCurrentExchangeRates(){
        given(tableRequestMapper.toCurrencies(tableRequest)).willReturn(currencies);
        given(currenciesRepository.save(any())).willReturn(currencies);
        given(rateMapper.toRateResponse(any())).willReturn(new RateResponse("bat (Tajlandia)","THB", 0.1201));

        List<RateResponse> rateResponses = service.currentExchangeRates(tableRequest);

        assertNotNull(rateResponses);
        assertEquals(2, rateResponses.size());

        verify(tableRequestMapper, times(1)).toCurrencies(any());
        verify(currenciesRepository, times(1)).save(any());
        verify(rateMapper, times(2)).toRateResponse(any());
    }

    @Test
    void getAvailableRates() {
        given(tableRequestMapper.toCurrencies(tableRequest)).willReturn(currencies);
        given(currenciesRepository.save(any())).willReturn(currencies);
        given(rateMapper.toAvailableRate(any()))
                .willReturn(new AvailableRate("bat (Tajlandia)","THB"));

        List<AvailableRate> availableRates = service.getAvailableRates(tableRequest);

        assertNotNull(availableRates);
        assertEquals(2, availableRates.size());

        verify(tableRequestMapper, times(1)).toCurrencies(any());
        verify(currenciesRepository, times(1)).save(any());
        verify(rateMapper, times(2)).toAvailableRate(any());
    }

    @Test
    void getAvailableRatesChosen() {
        given(tableRequestMapper.toCurrencies(tableRequest)).willReturn(currencies);
        given(currenciesRepository.save(any())).willReturn(currencies);
        given(rateMapper.toRateResponse(any()))
                .willReturn(new RateResponse("bat (Tajlandia)","THB", 0.1201));

        List<RateResponse> ratesChosen = service.getAvailableRatesChosen(tableRequest, List.of("THB"));

        assertNotNull(ratesChosen);
        assertEquals(1, ratesChosen.size());

        verify(tableRequestMapper, times(1)).toCurrencies(any());
        verify(currenciesRepository, times(1)).save(any());
        verify(rateMapper, times(1)).toRateResponse(any());

    }

    @Test
    void exchange() {
        ExchangeRequest request = new ExchangeRequest(100D,"THB","USD");
        Exchange exchange =  new Exchange(1L,100D,"THB","USD",3.2064, LocalDateTime.now());

        given(exchangeRequestMapper.toExchange(any(),any())).willReturn(exchange);
        given(exchangeRepository.save(any(Exchange.class))).willReturn(exchange);

        Exchange savedExchange = service.exchange(request, tableRequest);

        assertNotNull(savedExchange);
        assertEquals(3.2064, savedExchange.getAmountToReturn());

        verify(exchangeRequestMapper, times(1)).toExchange(any(), any());
        verify(exchangeRepository, times(1)).save(any());
    }

    @NotNull
    private TableRequest getTableRequest() {
        Set<RateRequest> rateRequests = new HashSet<>();
        rateRequests.add(new RateRequest("bat (Tajlandia)","THB",0.1201));
        rateRequests.add(new RateRequest("dolar amerykański", "USD", 3.7456));
        TableRequest request = new TableRequest();
        request.setTable("A");
        request.setNo("090/A/NBP/2021");
        request.setRates(rateRequests);
        return request;
    }

    @NotNull
    private Currencies getCurrencies() {
        Currencies currencies = new Currencies();
        currencies.setId(1L);
        currencies.setTableName("A");
        currencies.setNumber("090/A/NBP/2021");
        currencies.setRates(Set.of(
                Rate.builder().currency("bat (Tajlandia)").code("THB").mid(BigDecimal.valueOf(0.1201)).build(),
                new Rate("dolar amerykański", "USD", BigDecimal.valueOf(3.7456))
        ));
        return currencies;
    }
}
