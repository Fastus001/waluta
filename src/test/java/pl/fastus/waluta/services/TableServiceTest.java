package pl.fastus.waluta.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.model.Table;
import pl.fastus.waluta.repositories.TableRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    TableRepository repository;

    @InjectMocks
    TableService service;

    Table table;

    @BeforeEach
    void setService(){
        table = new Table();
        table.setId(1L);
        table.setTable("A");
        table.setNo("090/A/NBP/2021");
        table.setRates(List.of(
                new Rate("bat (Tajlandia)", "THB", BigDecimal.valueOf(0.1201)),
                new Rate("dolar amerykański", "USD", BigDecimal.valueOf(3.7456))
        ));
    }

    @Test
    void testSave(){
        BDDMockito.given(repository.save(any())).willReturn(table);

        Table savedTable = service.saveTable(this.table);

        assertNotNull(savedTable);
        assertEquals(2, savedTable.getRates().size());
    }

}
