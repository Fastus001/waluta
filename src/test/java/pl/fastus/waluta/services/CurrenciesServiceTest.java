package pl.fastus.waluta.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.RateRequest;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.repositories.TableRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CurrenciesServiceTest {

    @Mock
    TableRepository repository;

    @Mock
    TableRequestMapper mapper;

    @InjectMocks
    CurrenciesService service;

    @Test
    void testSave(){
        TableRequest request = new TableRequest();
        request.setTable("A");
        request.setNo("090/A/NBP/2021");
        request.setRates(Set.of(
                new RateRequest("bat (Tajlandia)","THB",0.1201),
                new RateRequest("dolar amerykański", "USD", 3.7456)
        ));

        Currencies currencies = new Currencies();
        currencies.setId(1L);
        currencies.setTableName("A");
        currencies.setNumber("090/A/NBP/2021");
        currencies.setRates(Set.of(
                Rate.builder().currency("bat (Tajlandia)").code("THB").mid(0.1201).build(),
                new Rate("dolar amerykański", "USD", 3.7456)
        ));

        BDDMockito.given(mapper.tableRequestToCurrencies(request)).willReturn(currencies);
        BDDMockito.given(repository.save(any())).willReturn(currencies);

        Currencies savedCurrencies = service.saveTable(request);

        assertNotNull(savedCurrencies);
        assertEquals(2, savedCurrencies.getRates().size());
    }
}
