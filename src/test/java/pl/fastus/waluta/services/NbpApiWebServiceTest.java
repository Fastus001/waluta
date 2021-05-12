package pl.fastus.waluta.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.fastus.waluta.model.DTO.RateDTO;
import pl.fastus.waluta.model.DTO.TableDTO;
import pl.fastus.waluta.model.Rate;
import pl.fastus.waluta.model.Table;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NbpApiWebServiceTest {

    public static MockWebServer mockWebServer;

    NbpApiWebService service;

    ObjectMapper objectMapper;

    @BeforeAll
    static void setMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void setUp() {
        String baseUrl = String.format( "http://localhost:%s", mockWebServer.getPort() );
        service = new NbpApiWebService(baseUrl);
        objectMapper = new ObjectMapper();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getTodayTableACourses() throws JsonProcessingException {
        TableDTO tableDTO = TableDTO.builder()
                .table("A")
                .no("090/A/NBP/2021")
                .effectiveDate("date")
                .rates(List.of(
                    new RateDTO("bat (Tajlandia)", "THB", BigDecimal.valueOf(0.1201)),
                    new RateDTO("dolar amerykański", "USD", BigDecimal.valueOf(3.7456))
                )).build();

        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(tableDTO))
        .addHeader("Content-Type", "application/json"));

        TableDTO responseDTO = service.getTodayTableACourses().block();

        assertNotNull(responseDTO);
        assertEquals( 2, responseDTO.getRates().size() );
        assertEquals( "A", responseDTO.getTable());
        assertEquals( "090/A/NBP/2021", responseDTO.getNo());
    }
}
