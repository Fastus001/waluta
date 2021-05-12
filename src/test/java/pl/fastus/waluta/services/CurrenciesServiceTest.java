package pl.fastus.waluta.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.repositories.TableRepository;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CurrenciesServiceTest {

    @Mock
    TableRepository repository;

    @InjectMocks
    CurrenciesService service;

    Currencies currencies;

    @BeforeEach
    void setService(){
        currencies = new Currencies();
        currencies.setId(1L);
        currencies.setTableName("A");
        currencies.setNo("090/A/NBP/2021");
        currencies.setRates(Set.of(
                Rate.builder().currency("bat (Tajlandia)").code("THB").mid(BigDecimal.valueOf(0.1201)).build(),
                new Rate("dolar amerykański", "USD", BigDecimal.valueOf(3.7456))
        ));
    }

    @Test
    void testSave(){
        BDDMockito.given(repository.save(any())).willReturn(currencies);

        Currencies savedCurrencies = service.saveTable(this.currencies);

        assertNotNull(savedCurrencies);
        assertEquals(2, savedCurrencies.getRates().size());
    }
}
