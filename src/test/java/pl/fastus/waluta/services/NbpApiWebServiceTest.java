package pl.fastus.waluta.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.fastus.waluta.model.DTO.RateRequest;
import pl.fastus.waluta.model.DTO.TableRequest;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        service = new NbpApiWebService(baseUrl, baseUrl);
        objectMapper = new ObjectMapper();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getTodayTableACourses() throws JsonProcessingException {
        TableRequest tableRequest = new TableRequest();
        tableRequest.setTable("A");
        tableRequest.setNo("090/A/NBP/2021");
        tableRequest.setEffectiveDate("date");
        tableRequest.setRates(Set.of(
                    new RateRequest("bat (Tajlandia)", "THB", 0.1201),
                    new RateRequest("dolar amerykański", "USD", 3.7456)
                ));

        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(tableRequest))
        .addHeader("Content-Type", "application/json"));

        TableRequest response = service.getTodayTableACourses().blockFirst();

        assertNotNull(response);
        assertEquals( 2, response.getRates().size() );
        assertEquals( "A", response.getTable());
        assertEquals( "090/A/NBP/2021", response.getNo());
    }
}
