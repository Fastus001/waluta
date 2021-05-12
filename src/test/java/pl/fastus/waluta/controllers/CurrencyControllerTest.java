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
import pl.fastus.waluta.mappers.RateToRateResponseMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.services.CurrenciesService;
import pl.fastus.waluta.services.NbpApiWebService;
import reactor.core.publisher.Flux;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Mock
    NbpApiWebService nbpApiWebService;

    @Mock
    CurrenciesService currenciesService;

    @Mock
    RateToRateResponseMapper rateToRateResponseMapper;

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
        currencies.setRates(Set.of(
                Rate.builder().currency("bat (Tajlandia)").code("THB").mid(0.1201).build(),
                Rate.builder().currency("dolar amerykański").code("USD").mid(3.7456).build()
        ));

        BDDMockito.given(nbpApiWebService.getTodayTableACourses()).willReturn(Flux.just(new TableRequest()));

        BDDMockito.given(currenciesService.saveTable(any())).willReturn(currencies);

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].", is("bat (Tajlandia), code: THB")));
    }

    @Test
    void getCurrentRates() {
    }
}
