package pl.fastus.waluta.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.waluta.mappers.RateMapper;
import pl.fastus.waluta.mappers.RateToAvailableRate;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.AvailableRate;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiWebService;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    public static final String BAT = "bat (Tajlandia)";
    public static final String THB = "THB";
    public static final String DOLAR = "dolar amerykański";
    public static final String USD = "USD";
    @Mock
    NbpApiWebService nbpApiWebService;

    @Mock
    CurrenciesService currenciesService;

    @InjectMocks
    CurrencyController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void getAvailableCurrencies() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setId(1L);
        currencies.setRates(Set.of(
                Rate.builder().currency(BAT).code(THB).mid(BigDecimal.valueOf(0.1201)).build(),
                Rate.builder().currency(DOLAR).code(USD).mid(BigDecimal.valueOf(3.7456)).build()
        ));

        AvailableRate rate1 = new AvailableRate(BAT, THB);
        AvailableRate rate2 = new AvailableRate(DOLAR, USD);

        BDDMockito.given(nbpApiWebService.getTodayTableACourses()).willReturn(new TableRequest());
        BDDMockito.given(currenciesService.getAvailableRates(any())).willReturn(List.of(rate1,rate2));

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is(BAT)))
                .andExpect(jsonPath("$[0].code", is(THB)))
                .andExpect(jsonPath("$[1].currency", is(DOLAR)))
                .andExpect(jsonPath("$[1].code", is(USD)));
    }

    @Test
    void getCurrentRates() {
    }
}
