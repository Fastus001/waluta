package pl.fastus.waluta.mappers;

import org.junit.jupiter.api.Test;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.RateRequest;
import pl.fastus.waluta.model.DTO.TableRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TableRequestMapperTest {

    TableRequestMapper mapper = new TableRequestMapperImpl();

    @Test
    void tableRequestToCurrencies() {
        TableRequest tableRequest = new TableRequest();
        tableRequest.setTable("A");
        tableRequest.setNo("090/A/NBP/2021");
        tableRequest.setEffectiveDate("date");
        tableRequest.setRates(Set.of(
                new RateRequest("bat (Tajlandia)", "THB", 0.1201),
                new RateRequest("dolar amerykański", "USD", 3.7456)
        ));

        final Currencies mappedCurrencies = mapper.tableRequestToCurrencies(tableRequest);
        assertNotNull(mappedCurrencies);
        assertEquals("A", mappedCurrencies.getTableName());
        assertEquals(2, mappedCurrencies.getRates().size());
    }
}
