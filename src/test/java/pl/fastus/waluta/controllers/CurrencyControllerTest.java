package pl.fastus.waluta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.AvailableRate;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.RateResponse;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Exchange;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    public static final String THB_NAME = "bat (Tajlandia)";
    public static final String THB_CODE = "THB";
    public static final String USD_NAME = "dolar amerykański";
    public static final String USD_CODE = "USD";
    public static final double THB_MID = 0.1201;
    public static final double USD_MID = 3.7456;
    public static final double AMOUNT = 100D;
    @Mock
    NbpApiService nbpApiService;

    @Mock
    CurrenciesService currenciesService;

    @InjectMocks
    CurrencyController controller;

    MockMvc mockMvc;

    Currencies currencies;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        currencies = new Currencies();
        currencies.setId(1L);
        currencies.setRates(Set.of(
                Rate.builder().currency(THB_NAME).code(THB_CODE).mid(BigDecimal.valueOf(THB_MID)).build(),
                Rate.builder().currency(USD_NAME).code(USD_CODE).mid(BigDecimal.valueOf(USD_MID)).build()
        ));
    }

    @Test
    void getAvailableCurrencies() throws Exception {
        List<AvailableRate> availableRates = List.of(new AvailableRate(THB_NAME, THB_CODE), new AvailableRate(USD_NAME, USD_CODE));

        given(nbpApiService.getTableA()).willReturn(new TableRequest());
        given(currenciesService.getAvailableRates(any())).willReturn(availableRates);

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is(THB_NAME)))
                .andExpect(jsonPath("$[0].code", is(THB_CODE)))
                .andExpect(jsonPath("$[1].currency", is(USD_NAME)))
                .andExpect(jsonPath("$[1].code", is(USD_CODE)));

        verify(nbpApiService, times(1)).getTableA();
        verify(currenciesService, times(1)).getAvailableRates(any());
    }

    @Test
    void getCurrentExchangeRates() throws Exception {
        List<RateResponse> rateResponses = List.of(
                new RateResponse(THB_NAME, THB_CODE, THB_MID), new RateResponse(USD_NAME, USD_CODE, USD_MID));

        given(nbpApiService.getTableA()).willReturn(new TableRequest());
        given(currenciesService.currentExchangeRates(any())).willReturn(rateResponses);

        mockMvc.perform(get("/api/currencies/rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is(THB_NAME)))
                .andExpect(jsonPath("$[0].code", is(THB_CODE)))
                .andExpect(jsonPath("$[0].mid", is(THB_MID)))
                .andExpect(jsonPath("$[1].currency", is(USD_NAME)))
                .andExpect(jsonPath("$[1].code", is(USD_CODE)))
                .andExpect(jsonPath("$[1].mid", is(USD_MID)));

        verify(nbpApiService, times(1)).getTableA();
        verify(currenciesService, times(1)).currentExchangeRates(any());
    }

    @Test
    void getCurrentExchangeRatesWithParams() throws Exception {
        List<RateResponse> rateResponses = List.of(
                new RateResponse(THB_NAME, THB_CODE, THB_MID));

        given(nbpApiService.getTableA()).willReturn(new TableRequest());
        given(currenciesService.getAvailableRatesChosen(any(),any())).willReturn(rateResponses);

        mockMvc.perform(get("/api/currencies/rates")
                .param("codes", "THB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is(THB_NAME)))
                .andExpect(jsonPath("$[0].code", is(THB_CODE)))
                .andExpect(jsonPath("$[0].mid", is(THB_MID)));

        verify(nbpApiService, times(1)).getTableA();
        verify(currenciesService, times(1)).getAvailableRatesChosen(any(),any());
    }

    @Test
    void exchange() throws Exception {
        ExchangeRequest body = new ExchangeRequest(AMOUNT,THB_CODE,USD_CODE);
        Exchange exchange =  new Exchange(1L,AMOUNT,THB_CODE,USD_CODE,3.2064, LocalDateTime.now());

        given(nbpApiService.getTableA()).willReturn(new TableRequest());
        given(currenciesService.exchange(any(), any())).willReturn(exchange);

        mockMvc.perform(get("/api/currencies/exchange")
                .param("amount", "100")
                .param("from", THB_CODE)
                .param("to", USD_CODE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.amount", is(100.0)))
                .andExpect(jsonPath("$.fromCurrency", is(THB_CODE)))
                .andExpect(jsonPath("$.toCurrency", is(USD_CODE)))
                .andExpect(jsonPath("$.amountToReturn", is(3.2064)));

        verify(nbpApiService, times(1)).getTableA();
        verify(currenciesService, times(1)).exchange(any(), any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
